package src.simulador;

import src.enums.OperacoesI;
import src.enums.OperacoesR;
import src.enums.TipoInstrucao;
import src.modelo.Decodificador;
import src.modelo.Instrucao;
import src.modelo.Memoria;
import src.modelo.Registrador;
import src.util.FuncaoExtracaoBits;

public class Processador {
    private Memoria memoria = new Memoria();
    private Registrador registradores = new Registrador();
    private int contadorPrograma = 1;
    public boolean executando = true;
    private int ciclos = 0;
//    private int formato;

    public Processador() {
    }

//    public boolean isExecutando() {
//        return this.executando;
//    }

    public void carregarPrograma(String caminhoArquivoBin, int pcInicial) {
        this.memoria.carregarBinario(caminhoArquivoBin, pcInicial);
        this.contadorPrograma = pcInicial;
        this.executando = true;
        System.out.println("\n[Processador] Programa carregado. PC inicial: " + pcInicial);
    }

    public void executarProgramaCompleto() {
        System.out.println("\n--- Iniciando Execução do Programa ---");

        while(this.executando) {
            this.executarProximaInstrucao();
            if (this.ciclos > 1000) {
                System.err.println("[ERRO FATAL] Limite de ciclos excedido. Possível loop infinito ou SYSCALL ausente.");
                this.executando = false;
            }
        }

        System.out.println("--- Execução do Programa Concluída ---");
        this.registrarEstadoFinal();
    }

    private void executarProximaInstrucao() {
        ++this.ciclos;
        System.out.println("\n------------------------------------");
        System.out.printf("[Ciclo %d] PC: %d\n", this.ciclos, this.contadorPrograma);
        short instrucao = this.memoria.ler(this.contadorPrograma);
        System.out.printf("[FETCH] Instrução binária lida (0x%04X): %s\n", instrucao, Integer.toBinaryString(instrucao & '\uffff'));
        Instrucao instrucaoDecodificada = Decodificador.decodificar(instrucao);
        System.out.println("[DECODE] Instrução Decodificada: " + String.valueOf(instrucaoDecodificada));
        this.registradores.mostrarConteudo();
        boolean pcModificado = this.executarInstrucao(instrucaoDecodificada, instrucao);
        if (!pcModificado) {
            ++this.contadorPrograma;
        }

    }

    private boolean executarInstrucao(Instrucao instrucao, short instrucaoBinaria) {
        TipoInstrucao tipo = instrucao.getTipoInstrucao();
        short instrucaoTeste = this.memoria.ler(this.contadorPrograma);
        boolean pcModificado = false;
        short format = FuncaoExtracaoBits.extractBits(instrucaoTeste, 15, 1);
        System.out.println("FORMATO É:" + format);
        if (format == 0) {
            OperacoesR operacao = instrucao.getOperacaoR();
            short rd = instrucao.getRegistradorDestino();
            short rs = instrucao.getRegistrador1();
            short rt = instrucao.getRegistrador2();
            short valorRs = this.registradores.ler(rs);
            short valorRt = this.registradores.ler(rt);
            short resultado = 0;
            pcModificado = false;
            switch (operacao) {
                case ADD:
                    resultado = (short)(valorRs + valorRt);
                    break;
                case SUB:
                    resultado = (short)(valorRs - valorRt);
                    break;
                case MUL:
                    resultado = (short)(valorRs * valorRt);
                    break;
                case DIV:
                    if (valorRt == 0) {
                        System.err.println("[Erro DIV] Divisão por zero.");
                        this.executando = false;
                        return false;
                    }

                    resultado = (short)(valorRs / valorRt);
                    break;
                case CMP_EQUAL:
                    resultado = (short)(valorRs == valorRt ? 1 : 0);
                    break;
                case CMP_NEQ:
                    resultado = (short)(valorRs != valorRt ? 1 : 0);
                    break;
                case LOAD:
                    int enderecoLoad = valorRs + 1;
                    if (enderecoLoad < 0 || enderecoLoad >= this.memoria.getTamanhoMemoria()) {
                        System.err.println("[Erro LOAD] Endereço de memória inválido: " + enderecoLoad);
                        this.executando = false;
                        return false;
                    }

                    resultado = this.memoria.ler(enderecoLoad);
                    break;
                case STORE:
                    if (valorRs >= 0 && valorRs < this.memoria.getTamanhoMemoria()) {
                        this.memoria.store(valorRs, valorRt);
                        System.out.printf("[EXECUTE] STORE Mem[%d] <- R%d = %d\n", Integer.valueOf(valorRs), rd, valorRt);
                        return false;
                    }

                    System.err.printf("[Erro STORE] Endereço inválido: %d\n", Integer.valueOf(valorRs));
                    this.executando = false;
                    return false;
                case SYSCALL:
                    int servico = this.registradores.ler(0);
                    System.out.printf("[EXECUTE] SYSCALL (Serviço: %d)\n", servico);
                    switch (servico) {
                        case 0:
                            this.executando = false;
                            System.out.println("[SYSCALL] Encerrando programa.");
                            break;
                        case 1:
                            System.out.print((char)this.registradores.ler(2));
                            break;
                        case 2:
                            System.out.println();
                            break;
                        case 3:
                            System.out.print(this.registradores.ler(2));
                            break;
                        case 4:
                            int endereco = this.registradores.ler(2);

                            short ch;
                            while((ch = this.memoria.ler(endereco++)) != 0) {
                                System.out.print((char)ch);
                            }
                            break;
                        default:
                            System.err.println("[SYSCALL] Código inválido.");
                            this.executando = false;
                    }

                    return false;
                case NOP_R:
                    System.out.println("[EXECUTE] NOP (R-Type)");
                    return false;
                default:
                    System.err.println("[Erro] Operação R-Type inválida: " + String.valueOf(operacao));
                    this.executando = false;
                    return false;
            }

            this.registradores.escrever(rd, resultado);
            System.out.printf("[EXECUTE] R-Type %s -> R%d = %d\n", operacao, rd, resultado);
        } else {
            OperacoesI operacao = instrucao.getOperacaoI();
            short rd = instrucao.getRegistradorDestino();
            short imediato = instrucao.getImediato();
            switch (operacao) {
                case JUMP:
                    this.contadorPrograma = imediato + 1;
                    pcModificado = true;
                    System.out.printf("[EXECUTE] JUMP -> PC = %d\n", imediato);
                    break;
                case JUMP_COND:
                    if (this.registradores.ler(rd) == 1) {
                        this.contadorPrograma = imediato + 1;
                        pcModificado = true;
                        System.out.printf("[EXECUTE] JUMP_COND -> PC = %d\n", imediato + 1);
                    } else {
                        System.out.printf("[EXECUTE] JUMP_COND não realizado (R%d != 1)\n", rd);
                    }
                    break;
                case MOV:
                    this.registradores.escrever(rd, imediato);
                    System.out.printf("[EXECUTE] MOV R%d = %d\n", rd, imediato);
                    break;
                case NOP_I:
                    System.out.println("[EXECUTE] NOP (I-Type)");
                    break;
                default:
                    System.err.println("[Erro] Operação I-Type inválida: " + String.valueOf(operacao));
                    this.executando = false;
            }
        }

        return pcModificado;
    }

    private void registrarEstadoFinal() {
        System.out.println("\n--- Relatório Final da Execução ---");
        System.out.printf("Total de Ciclos da CPU: %d\n", this.ciclos);
        System.out.printf("PC Final: %d\n", this.contadorPrograma);
        this.registradores.mostrarConteudo();
        this.memoria.mostrarConteudo(0, 15);
        System.out.println("----------------------------------");
    }
}