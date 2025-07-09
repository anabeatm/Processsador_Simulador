package src.simulador;

import src.enums.TipoInstrucao;
import src.modelo.*;

public class Processador { // controla o ciclo
    private Memoria memoriaInstrucao; // instrução em binário é guardada aqui, 32 bits,
    // acessada pelo PCcontador --> busca próxima instrução
    private Memoria memoriaDeDdados; // guarda valores DURANTE a execução
    private Registrador registrador; //registradores t0, t1, etc...
    private int PCcontador; // "aponta" para a proxima instrução que vai ser buscada, acrescenta + 4
    private boolean ativo; // quando encontra o syscall --> false; ativo == true; inativo == false


    //Criar um construtor que inicie os atributos, falando partirculamente da memoria para definir seu tamanho
    public Processador() {
        this.memoriaInstrucao = new Memoria(256);
        this.memoriaDeDdados = new Memoria(256);
        this.PCcontador = 0;
        this.registrador = new Registrador();
        this.ativo = true;
    }


    public void incrementarPCcontador() {
        if (PCcontador + 1 < memoriaInstrucao.getTamanho()) {
            PCcontador++;
        } else {
            System.out.println("Fim da memória de instruções alcançado. Encerrando programa.");
            ativo = false;
        }
    }

    public Registrador getRegistrador() {
        return registrador;
    }


    //     teste para ver se MEMORIA esta funcionando
    public void carregarPrograma(String caminhoArquivo) {
        this.memoriaInstrucao.carregarBinario(caminhoArquivo);
        System.out.println("Arquivo carregado com sucesso!!");
    }


    public Memoria getMemoriaInstrucao() { // testar memoria
        return memoriaInstrucao;
    }

    public void saltarPara(int endereco) {
        if (endereco < 0 || endereco >= memoriaInstrucao.getTamanho()) {
            throw new IllegalArgumentException("Endereço inválido para salto: " + endereco);
        }
        PCcontador = endereco;
    }

// TESTE FEITO POR ISSO COMENTADO
//    public void testandoCicloSimples() {
//        Decodificador decodificador = new Decodificador();
//        ALU alu = new ALU();
//
//        short binario = memoriaInstrucao.lerValor(PCcontador); // lendo valor na posição que o nosso PCcontador aponta
//        System.out.printf("Instrução lida (binário): 0x%04X%n", binario);
//
//        Instrucao instrucao = decodificador.decodificar(binario);
//        System.out.println("intrução decodificada --> " + instrucao);
//
//        int resultado = alu.executar(instrucao.getUpcode(), 5, 3); // TESTE --> por isso operadores fixos
//        int operando1 = registrador.lerPorIndice(instrucao.getRegistradorOperando1());
//        int operando2;
//
//        if (instrucao.getTipoInstrucao() == TipoInstrucao.R) {
//            operando2 = registrador.lerPorIndice(instrucao.getRegistradorOperando2());
//
//        } else {
//            operando2 = instrucao.getImediato();
//        }
//
//        int resultado = alu.executar(instrucao.getUpcode(), operando1, operando2);
//        System.out.println("resultado --> " + resultado);
//
//        registrador.escrever(resultado, instrucao.getRegistradorDestino());
//
//        incrementarPCcontador();
//
//    }

    // testando em todo o arquivo .bin
    public void executarProgramaCompleto() {
        System.out.println("Memória bruta (posição 0 a 10):");
        for (int i = 0; i <= 10; i++) {
            System.out.printf("[%d] = 0x%04X%n", i, memoriaInstrucao.lerValor(i));
        }
        System.out.println("Começando execução...");
        System.out.println("===> INICIANDO EXECUÇÃO COMPLETA <===");
        Decodificador decodificador = new Decodificador();
        ALU alu = new ALU();

        while (ativo) { // até encontrar syscall
            short binario = memoriaInstrucao.lerValor(PCcontador);
            System.out.printf("Instrução lida (binário): 0x%04X%n", binario);

            Instrucao instrucao = decodificador.decodificar(binario);
            System.out.println("intrução decodificada --> " + instrucao);
            System.out.println("Upcode: " + instrucao.getUpcode() + " | Tipo: " + instrucao.getTipoInstrucao());

            if (instrucao.getUpcode() == 63) { // syscall encerra programa
                System.out.println("Syscall --> encerrando programa");
                ativo = false;
                break;
            }

            int operando1 = registrador.lerPorIndice(instrucao.getRegistradorOperando1());
            int operando2 = (instrucao.getTipoInstrucao() == TipoInstrucao.R) ?
                    registrador.lerPorIndice(instrucao.getRegistradorOperando2()) :
                    instrucao.getImediato();

            int resultado = alu.executar(instrucao.getUpcode(), operando1, operando2, instrucao.getTipoInstrucao());

            System.out.println("Resultado ALU: " + resultado);

            // Trata LOAD
            if (instrucao.getTipoInstrucao() == TipoInstrucao.R && instrucao.getUpcode() == 15) {
                System.out.println("Executando LOAD...");
                if (resultado < 0 || resultado >= memoriaDeDdados.getTamanho()) {
                    System.err.printf("Erro: Endereço inválido para LOAD: %d%n", resultado);
                    ativo = false;
                    break;
                }
                int valorLido = memoriaDeDdados.lerValor(resultado);
                registrador.escrever(valorLido, instrucao.getRegistradorDestino());
                System.out.printf("LOAD: r%d <- Mem[%d] = %d%n", instrucao.getRegistradorDestino(), resultado, valorLido);
                incrementarPCcontador();
                continue;
            }

            // Trata STORE
            if (instrucao.getTipoInstrucao() == TipoInstrucao.R && instrucao.getUpcode() == 16) {
                System.out.println("Executando STORE...");
                if (resultado < 0 || resultado >= memoriaDeDdados.getTamanho()) {
                    System.err.printf("Erro: Endereço inválido para STORE: %d%n", resultado);
                    ativo = false;
                    break;
                }
                int valor = registrador.lerPorIndice(instrucao.getRegistradorOperando2());
                memoriaDeDdados.escreverMemoria(resultado, (short) valor);
                System.out.printf("STORE: Mem[%d] <- r%d = %d%n", resultado, instrucao.getRegistradorOperando2(), valor);
                incrementarPCcontador();
                continue;
            }

            // Para instruções tipo R que não são LOAD/STORE, escreve resultado no registrador destino
            if (instrucao.getTipoInstrucao() == TipoInstrucao.R &&
                    instrucao.getUpcode() != 15 &&
                    instrucao.getUpcode() != 16) {
                registrador.escrever(resultado, instrucao.getRegistradorDestino());
                System.out.printf("Registrador destino: r%d%n", instrucao.getRegistradorDestino());
                System.out.println("Estado parcial dos registradores:");
                registrador.visualizarRegistrador();
                System.out.println("------------");
            }

            // Trata JUMP (tipo I, upcode 0)
            if (instrucao.getTipoInstrucao() == TipoInstrucao.I && instrucao.getUpcode() == 0) {
                int destino = PCcontador + instrucao.getImediato();
                System.out.println("JUMP para endereço: " + destino);
                try {
                    saltarPara(destino);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro no salto: " + e.getMessage());
                    ativo = false;
                    break;
                }
                continue;
            }

            // Trata JUMP_COND (tipo I, upcode 1)
            if (instrucao.getTipoInstrucao() == TipoInstrucao.I && instrucao.getUpcode() == 1) {
                int condicao = registrador.lerPorIndice(instrucao.getRegistradorDestino());
                System.out.println("Condição jump_cond (r" + instrucao.getRegistradorDestino() + ") = " + condicao);
                if (condicao != 0) {
                    int destino = PCcontador + instrucao.getImediato();
                    System.out.println("JUMP CONDICIONAL para endereço: " + destino);
                    try {
                        saltarPara(destino);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro no salto condicional: " + e.getMessage());
                        ativo = false;
                        break;
                    }
                } else {
                    System.out.println("Condição falsa, não saltando.");
                    incrementarPCcontador();
                }
                continue;
            }

            // Para as demais instruções, incrementa PC normalmente
            incrementarPCcontador();
        }

        System.out.println("Execução do arquivo .bin finalizada");
        System.out.println();
        System.out.println("Estado Final Dos Registradores:");
        registrador.visualizarRegistrador();
    }

}
//    public void testarDecodificacao() {
//        Decodificador decodificador = new Decodificador();
//        short binario = memoriaInstrucao.lerValor(PCcontador); // geralmente posição 0
//        Instrucao instrucao = decodificador.decodificar(binario);
//
//        System.out.println("Instrução decodificada:");
//        System.out.println(instrucao); // toString da classe Instrucao
//    }
//}
