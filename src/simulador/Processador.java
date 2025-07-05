package src.simulador;

import src.enums.TipoInstrucao;
import src.modelo.*;

public class Processador { // controla o ciclo
    private Memoria memoriaInstrucao; // instrução em binário é quardada aqui, 32 bits,
    // acessada pelo PCcontador --> busca próxima instrução
    private Memoria memoriaDeDdados; // guarda valores DURANTE a execução
    private Registrador registrador; //registradores t0, t1, etc...
    private int PCcontador; // "aponta" para a proxima instrução que vai ser buscada, acrescenta + 4
    private boolean ativo; // quando encontra o syscall --> false; ativo == true; inativo == false




    //Criar um construtor que inicie os atributos, falando partirculamente da memoria para definir seu tamanho
    public Processador(){
        this.memoriaInstrucao = new Memoria(256);
        this.memoriaDeDdados = new Memoria(256);
        this.PCcontador = 1;
        this.registrador = new Registrador();
        this.ativo = true;
    }


    public void incrementarPCcontador(){
        PCcontador ++;
    }

    public Registrador getRegistrador() {
        return registrador;
    }


//     teste para ver se MEMORIA esta funcionando
    public void carregarPrograma(String caminhoArquivo){
        this.memoriaInstrucao.carregarBinario(caminhoArquivo);
        System.out.println("Arquivo carredado com sucesso!!");
    }


    public Memoria getMemoriaInstrucao() { // testar memoria
        return memoriaInstrucao;
    }

    public void testandoCicloSimples() {
        Decodificador decodificador = new Decodificador();
        ALU alu = new ALU();

        short binario = memoriaInstrucao.lerValor(PCcontador); // lendo valor na posição que o nosso PCcontador aponta
        System.out.printf("Instrução lida (binário): 0x%04X%n", binario);

        Instrucao instrucao = decodificador.decodificar(binario);
        System.out.println("intrução decodificada --> " + instrucao);

//        int resultado = alu.executar(instrucao.getUpcode(), 5, 3); // TESTE --> por isso operadores fixos
        int operando1 = registrador.lerPorIndice(instrucao.getRegistradorOperando1());
        int operando2;

        if(instrucao.getTipoInstrucao() == TipoInstrucao.R){
            operando2 = registrador.lerPorIndice(instrucao.getRegistradorOperando2());

        } else {
            operando2 =instrucao.getImediato();
        }

        int resultado = alu.executar(instrucao.getUpcode(), operando1, operando2);
        System.out.println("resultado --> " + resultado);

        registrador.escrever(resultado, instrucao.getRegistradorDestino());

        incrementarPCcontador();

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
