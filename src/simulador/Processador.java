package src.simulador;

import src.enums.*;
import src.modelo.*;


public class Processador {
    private Memoria memoria;
    private Registrador registrador;
    private int contadorPrograma; // PC
    private int ciclos;
    private boolean executando;

    public Processador() {
        memoria = new Memoria(256);
        registrador = new Registrador();
        contadorPrograma = 0;
        ciclos = 0;
        executando = true;
    }

    public void carregarPrograma(String caminhoArquivoBinario, int enderecoInicial) {
        memoria.carregarBinario(caminhoArquivoBinario, enderecoInicial);
        this.contadorPrograma = enderecoInicial;
        this.executando = true;
        this.ciclos = 0;
        registrador.resetar();
        System.out.println("[INFO] Programa carregado. PC inicial: " + enderecoInicial);
    }

    public void executarProgramaCompleto() {
        while (executando) {
            executarProximaInstrucao();
        }
        registrarEstadoFinal();
    }

    private void executarProximaInstrucao() {
        if (!executando) return;

        ciclos++;
        System.out.println("\n------------------------------------");
        System.out.printf("[Ciclo %d] PC: %d (0x%X)\n", ciclos, contadorPrograma, contadorPrograma);

        short instrucaoBinaria = memoria.ler(contadorPrograma);
        System.out.printf("[FETCH] Instrução Binária Lida: 0x%04X (%s)\n",
                instrucaoBinaria & 0xFFFF,
                Integer.toBinaryString(instrucaoBinaria & 0xFFFF)
        );

        Instrucao instrucaoDecodificada = Decodificador.decodificar(instrucaoBinaria);
        System.out.println("[DECODE] Instrução Decodificada: " + instrucaoDecodificada.getTipoInstrucao() + " - " + instrucaoDecodificada);

        System.out.println("[DEBUG Processador] PC ANTES da execução: " + contadorPrograma);

        boolean pcModificado = executarInstrucao(instrucaoDecodificada, instrucaoBinaria);

        if (!pcModificado) {
            contadorPrograma++;
            System.out.println("[DEBUG Processador] PC AVANÇOU para: " + contadorPrograma);
        } else {
            System.out.println("[DEBUG Processador] PC MODIFICADO por salto para: " + contadorPrograma);
        }

        registrador.mostrarConteudo();
    }

    private boolean executarInstrucao(Instrucao instrucao, short instrucaoBinaria) {
        boolean pcModificado = false;
        int rd = instrucao.getRegistradorDestino();
        int rs = instrucao.getRegistrador1();
        int rt = instrucao.getRegistrador2();
        short imediato = instrucao.getImediato();

        switch (instrucao.getTipoInstrucao()) {
            case R:
                OperacoesR operacaoR = instrucao.getOperacaoR();
                if (operacaoR == OperacoesR.NOP_R && instrucao.getOpcode() != OperacoesR.NOP_R.getOpcode()) {
                    System.err.println("[ERRO] Opcode R inválido: " + instrucao.getOpcode() + " na instrução 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                    pararExecucao();
                    return false;
                }
                if (operacaoR == null) {
                    System.err.println("[ERRO] Operação R nula após decodificação. Instrução binária: 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                    pararExecucao();
                    return false;
                }

                switch (operacaoR) {
                    case ADD:
                        registrador.escrever(rd, registrador.ler(rs) + registrador.ler(rt));
                        System.out.printf("[EXECUTE] ADD R%d = R%d + R%d (%d = %d + %d)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        break;
                    case SUB:
                        registrador.escrever(rd, registrador.ler(rs) - registrador.ler(rt));
                        System.out.printf("[EXECUTE] SUB R%d = R%d - R%d (%d = %d - %d)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        break;
                    case MUL:
                        registrador.escrever(rd, registrador.ler(rs) * registrador.ler(rt));
                        System.out.printf("[EXECUTE] MUL R%d = R%d * R%d (%d = %d * %d)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        break;
                    case DIV:
                        if (registrador.ler(rt) == 0) {
                            System.err.println("[ERRO] Divisão por zero em R" + rt + "!");
                            pararExecucao();
                        } else {
                            registrador.escrever(rd, registrador.ler(rs) / registrador.ler(rt));
                            System.out.printf("[EXECUTE] DIV R%d = R%d / R%d (%d = %d / %d)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        }
                        break;
                    case CMP_EQUAL:
                        registrador.escrever(rd, (short) ((registrador.ler(rs) == registrador.ler(rt)) ? 1 : 0));
                        System.out.printf("[EXECUTE] CMP_EQUAL R%d = (R%d == R%d) ? 1 : 0 (%d = (%d == %d) ? 1 : 0)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        break;
                    case CMP_NEQ:
                        registrador.escrever(rd, (short) ((registrador.ler(rs) != registrador.ler(rt)) ? 1 : 0));
                        System.out.printf("[EXECUTE] CMP_NEQ R%d = (R%d != R%d) ? 1 : 0 (%d = (%d != %d) ? 1 : 0)\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs), registrador.ler(rt));
                        break;
                    case LOAD:
                        registrador.escrever(rd, memoria.ler(registrador.ler(rs) + registrador.ler(rt)));
                        System.out.printf("[EXECUTE] LOAD R%d = M[R%d + R%d] (%d = M[%d])\n", rd, rs, rt, registrador.ler(rd), registrador.ler(rs) + registrador.ler(rt));
                        break;
                    case STORE:
                        // Cast explícito para short no valor a ser escrito, conforme erro reportado
                        memoria.escrever(registrador.ler(rs) + registrador.ler(rt), (short) registrador.ler(rd));
                        System.out.printf("[EXECUTE] STORE M[R%d + R%d] = R%d (M[%d] = %d)\n", rs, rt, rd, registrador.ler(rs) + registrador.ler(rt), registrador.ler(rd));
                        break;
                    case SYSCALL:
                        executarSyscall(rs);
                        break;
                    default:
                        System.err.println("[ERRO] Operação R não implementada: " + operacaoR + " na instrução 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                        pararExecucao();
                        break;
                }
                break;

            case I:
                OperacoesI operacaoI = instrucao.getOperacaoI();
                if (operacaoI == OperacoesI.NOP_I && instrucao.getOpcode() != OperacoesI.NOP_I.getOpcode()) {
                    System.err.println("[ERRO DECODIFICADOR] Opcode I inválido: " + instrucao.getOpcode() + " na instrução 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                    pararExecucao();
                    return false;
                }
                if (operacaoI == null) {
                    System.err.println("[ERRO] Operação I nula após decodificação. Instrução binária: 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                    pararExecucao();
                    return false;
                }

                switch (operacaoI) {
                    case JUMP:
                        contadorPrograma = imediato;
                        pcModificado = true;
                        System.out.printf("[EXECUTE] JUMP Incondicional para PC: %d (0x%X)\n", contadorPrograma, contadorPrograma);
                        break;
                    case JUMP_COND:
                        if (registrador.ler(rd) == 1) {
                            contadorPrograma = imediato;
                            pcModificado = true;
                            System.out.printf("[EXECUTE] JUMP Condicional REALIZADO para PC: %d (0x%X) (R%d == 1)\n", contadorPrograma, contadorPrograma, rd);
                        } else {
                            System.out.printf("[EXECUTE] JUMP Condicional NÃO REALIZADO (R%d != 1). PC avança normalmente.\n", rd);
                        }
                        break;
                    case MOV:
                        registrador.escrever(rd, imediato);
                        System.out.printf("[EXECUTE] MOV R%d = %d\n", rd, imediato);
                        break;
                    default:
                        System.err.println("[ERRO] Operação I não implementada ou inválida: " + operacaoI + " na instrução 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                        pararExecucao();
                        break;
                }
                break;
            default:
                System.err.println("[ERRO] Tipo de instrução desconhecido: " + instrucao.getTipoInstrucao() + " na instrução 0x" + Integer.toHexString(instrucaoBinaria & 0xFFFF));
                pararExecucao();
                break;
        }
        return pcModificado;
    }

    private void executarSyscall(int servico) {
        System.out.printf("[EXECUTE] SYSCALL Serviço: %d\n", servico);
        switch (servico) {
            case 0:
                executando = false;
                System.out.println("[SYSCALL] Fim da execução (Serviço 0).");
                break;
            case 1:
                System.out.println("[SYSCALL] print_int: " + registrador.ler(2));
                break;
            case 4:
                int endereco = registrador.ler(2);
                System.out.print("[SYSCALL] print_str: ");
                short ch;
                while (true) {
                    ch = memoria.ler(endereco++);
                    if (ch == 0) {
                        break;
                    }
                    System.out.print((char) ch);
                }
                System.out.println(" (Fim da string)");
                break;
            default:
                System.err.println("[ERRO] SYSCALL de serviço " + servico + " não implementada.");
                pararExecucao();
                break;
        }
    }

    public boolean isExecutando() {
        return executando;
    }

    public void pararExecucao() {
        this.executando = false;
        System.out.println("[DEBUG] Execução parada forçosamente.");
    }

    private void registrarEstadoFinal() {
        System.out.println("\n--- Relatório Final da Execução ---");
        System.out.printf("Total de Ciclos da CPU: %d\n", ciclos);
        System.out.printf("PC Final: %d\n", contadorPrograma);
        registrador.mostrarConteudo();
        memoria.mostrarConteudo(0, 30);
        System.out.println("----------------------------------");
    }
}