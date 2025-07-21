package src.simulador;

import src.modelo.*;
import src.enums.TipoInstrucao;
import src.enums.OperacoesR;
import src.enums.OperacoesI;
import src.util.FuncaoExtracaoBits;

public class Processador {
    private Memoria memoria;
    private Registrador registradores;
    private int contadorPrograma; // Program Counter (PC)
    public boolean executando;   // Flag para controlar o ciclo de execução
    private int ciclos;
    private int formato;

    public Processador() {
        this.memoria = new Memoria();
        this.registradores = new Registrador();
        this.contadorPrograma = 1;
        this.executando = true;
        this.ciclos = 0;
    }

    public boolean isExecutando() {
        return executando;
    }


    public void carregarPrograma(String caminhoArquivoBin, int pcInicial) {
        memoria.carregarBinario(caminhoArquivoBin, pcInicial);
        this.contadorPrograma = pcInicial;
        this.executando = true;
        System.out.println("\n[Processador] Programa carregado. PC inicial: " + pcInicial);
    }

    public void executarProgramaCompleto() {
        System.out.println("\n--- Iniciando Execução do Programa ---");
        while (executando) {
            executarProximaInstrucao();
            if (ciclos > 100000000) {
                System.err.println("[ERRO FATAL] Limite de ciclos excedido. Possível loop infinito ou SYSCALL ausente.");
                executando = false;
            }
        }
        System.out.println("--- Execução do Programa Concluída ---");
        registrarEstadoFinal();
    }

    private void executarProximaInstrucao() {
        ciclos++;
        System.out.println("\n------------------------------------");
        System.out.printf("[Ciclo %d] PC: %d\n", ciclos, contadorPrograma);

        short instrucao = memoria.ler(contadorPrograma);
        System.out.printf("[FETCH] Instrução binária lida (0x%04X): %s\n", instrucao, Integer.toBinaryString(instrucao & 0xFFFF));

        Instrucao instrucaoDecodificada = Decodificador.decodificar(instrucao);
        System.out.println("[DECODE] Instrução Decodificada: " + instrucaoDecodificada);
        registradores.mostrarConteudo();

        boolean pcModificado = executarInstrucao(instrucaoDecodificada, instrucao);

        if (!pcModificado) {
            contadorPrograma++;
        }
    }

    private boolean executarInstrucao(Instrucao instrucao, short instrucaoBinaria) {
        TipoInstrucao tipo = instrucao.getTipoInstrucao();
        short instrucaoTeste = memoria.ler(contadorPrograma);
        boolean pcModificado = false;

        short format = FuncaoExtracaoBits.extractBits(instrucaoTeste, 15, 1);
        System.out.println("FORMATO É:"+format);


        if (format == 0) {
            OperacoesR operacao = instrucao.getOperacaoR();
            short rd = instrucao.getRegistradorDestino();
            short rs = instrucao.getRegistrador1();
            short rt = instrucao.getRegistrador2();
            short valorRs = registradores.ler(rs);
            short valorRt = registradores.ler(rt);
            short resultado = 0;

            pcModificado = false;
            switch (operacao) {
                case ADD:
                    resultado = (short) (valorRs + valorRt);
                    break;
                case SUB:
                    resultado = (short) (valorRs - valorRt);
                    break;
                case MUL:
                    resultado = (short) (valorRs * valorRt);
                    break;
                case DIV:
                    if (valorRt == 0) {
                        System.err.println("[Erro DIV] Divisão por zero.");
                        executando = false;
                        return false;
                    }
                    resultado = (short) (valorRs / valorRt);
                    break;
                case CMP_EQUAL:
                    resultado = (short) ((valorRs == valorRt) ? 1 : 0);
                    break;
                case CMP_NEQ:
                    resultado = (short) ((valorRs != valorRt) ? 1 : 0);
                    break;
                case LOAD:
                    int enderecoLoad = valorRs + valorRt;
                    if (enderecoLoad < 0 || enderecoLoad >= memoria.getTamanhoMemoria()) {
                        System.err.println("[Erro LOAD] Endereço de memória inválido: " + enderecoLoad);
                        executando = false;
                        return false;
                    }
                    resultado = memoria.ler(enderecoLoad);
                    break;
                case STORE:
                    int enderecoStore = valorRs;
                    if (enderecoStore < 0 || enderecoStore >= memoria.getTamanhoMemoria()) {
                        System.err.printf("[Erro STORE] Endereço inválido: %d\n", enderecoStore);
                        executando = false;
                        return false;
                    }
                    memoria.store(enderecoStore, registradores.ler(rd));
                    System.out.printf("[EXECUTE] STORE Mem[%d] <- R%d = %d\n", enderecoStore, rd, registradores.ler(rd));
                    return false;
                case SYSCALL:
                    int servico = registradores.ler(0);
                    System.out.printf("[EXECUTE] SYSCALL (Serviço: %d)\n", servico);
                    switch (servico) {
                        case 0 -> {
                            executando = false;
                            System.out.println("[SYSCALL] Encerrando programa.");
                        }
                        case 1 -> System.out.print((char) registradores.ler(2));
                        case 2 -> System.out.println();
                        case 3 -> System.out.print(registradores.ler(2));
                        case 4 -> {
                            int endereco = registradores.ler(2);
                            short ch;
                            while ((ch = memoria.ler(endereco++)) != 0) {
                                System.out.print((char) ch);
                            }
                        }
                        default -> {
                            System.err.println("[SYSCALL] Código inválido.");
                            executando = false;
                        }
                    }
                    return false;
                case NOP_R:
                    System.out.println("[EXECUTE] NOP (R-Type)");
                    return false;
                default:
                    System.err.println("[Erro] Operação R-Type inválida: " + operacao);
                    executando = false;
                    return false;
            }

            registradores.escrever(rd, resultado);
            System.out.printf("[EXECUTE] R-Type %s -> R%d = %d\n", operacao, rd, resultado);
        } else {
            OperacoesI operacao = instrucao.getOperacaoI();
            short rd = instrucao.getRegistradorDestino();
            short imediato = instrucao.getImediato();

            switch (operacao) {
                case JUMP:
                    contadorPrograma = instrucao.getRegistrador1();
                    pcModificado = true;
                    System.out.printf("[EXECUTE] JUMP -> PC = %d\n", imediato);
                    break;
                case JUMP_COND:
                    if (registradores.ler(rd) == 1) {
                        contadorPrograma = imediato;
                        pcModificado = true;
                        System.out.printf("[EXECUTE] JUMP_COND -> PC = %d\n", imediato);
                    } else {
                        System.out.printf("[EXECUTE] JUMP_COND não realizado (R%d != 1)\n", rd);
                    }
                    break;
                case MOV:
                    registradores.escrever(rd, instrucao.getRegistrador1());
                    System.out.printf("[EXECUTE] MOV R%d = %d\n", rd, imediato);
                    break;
                case NOP_I:
                    System.out.println("[EXECUTE] NOP (I-Type)");
                    break;
                default:
                    System.err.println("[Erro] Operação I-Type inválida: " + operacao);
                    executando = false;
                    break;
            }
        }

        return pcModificado;
    }

    private void registrarEstadoFinal() {
        System.out.println("\n--- Relatório Final da Execução ---");
        System.out.printf("Total de Ciclos da CPU: %d\n", ciclos);
        System.out.printf("PC Final: %d\n", contadorPrograma);
        registradores.mostrarConteudo();
        memoria.mostrarConteudo(0, 30);
        System.out.println("----------------------------------");
    }
}
