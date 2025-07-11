package src.simulador;

import src.enums.Operacoes;
import src.enums.TipoInstrucao;
import src.modelo.*;

public class Processador {
    private Memoria memoriaInstrucao;
    private Memoria memoriaDeDados;
    private Registrador registrador;
    private int PCcontador;
    private boolean executando;
    private int ciclosBusca = 0;
    private int ciclosExecucao = 0;

    public Processador() {
        this.memoriaInstrucao = new Memoria(256);
        this.memoriaDeDados = new Memoria(10000);
        this.registrador = new Registrador(8);
        this.PCcontador = 2; // Ignora cabeçalho do binário
        this.executando = true;
    }

    public void carregarPrograma(String caminhoArquivo) {
        memoriaInstrucao.carregarBinario(caminhoArquivo);
    }

    public void iniciar() {
        while (executando) {
            short binario = memoriaInstrucao.lerValor(PCcontador);
            ciclosBusca++;

            Instrucao instrucao = Decodificador.decodificar(binario);
            executarInstrucao(instrucao);
        }

        System.out.println("FIM DA EXECUCÇÃO");
        System.out.println("TOTAL DE CICLOS: " + (ciclosBusca + ciclosExecucao));
        System.out.println("BUSCAS FEITAS: " + ciclosBusca);
        System.out.println("EXECUÇÕES: " + ciclosExecucao);
        System.out.println("\nREGISTRADORES:");
        registrador.mostrarConteudo();
    }

    private void executarInstrucao(Instrucao instrucao) {
        int opcode = instrucao.getUpcode();
        TipoInstrucao tipo = instrucao.getTipoInstrucao();

        if (tipo == TipoInstrucao.R) {
            int op1 = registrador.lerPorIndice(instrucao.getRegistrador1());
            int op2 = registrador.lerPorIndice(instrucao.getRegistrador2());
            int resultado = 0;

            switch (opcode) {
                case 0: resultado = op1 + op2; break;
                case 1: resultado = op1 - op2; break;
                case 2: resultado = op1 * op2; break;
                case 3: resultado = op1 / op2; break;
                case 4: resultado = (op1 == op2) ? 1 : 0; break;
                case 5: resultado = (op1 != op2) ? 1 : 0; break;
                case 15: resultado = memoriaDeDados.lerValor(op1); break;
                case 16:
                    System.out.println("[DEBUG] Tentando escrever na memória de dados: endereço " + op1 + ", valor " + op2);

                    if (op1 >= memoriaDeDados.getTamanho() || op1 < 0) {
                        System.out.println("[ERRO] STORE: endereço fora do limite da memória de dados -> " + op1);
                        executando = false;
                        return;
                    }

                    memoriaDeDados.escreverMemoria(op1, (short) op2);
                    incrementarPC();
                    ciclosExecucao++;
                    return;
                case 63:
                    if (op1 == 0) {
                        executando = false;
                        return;
                    }
                    break;
                default:
                    System.out.println("Opcode R inválido: " + opcode);
                    executando = false;
                    return;
            }

            registrador.escrever(instrucao.getRegistradorDestino(), resultado);
        }

        else if (tipo == TipoInstrucao.I) {
            switch (opcode) {
                case 0: // JUMP
                    PCcontador = instrucao.getImediato();
                    ciclosExecucao++;
                    return;
                case 1: // JUMP_COND
                    int cond = registrador.lerPorIndice(instrucao.getRegistradorDestino());
                    if (cond != 0) {
                        PCcontador = instrucao.getImediato();
                    } else {
                        incrementarPC();
                    }
                    ciclosExecucao++;
                    return;
                case 3: // MOV
                    registrador.escrever(instrucao.getRegistradorDestino(), instrucao.getImediato());
                    break;
                default:
                    System.out.println("Opcode I inválido: " + opcode);
                    executando = false;
                    return;
            }
        }

        incrementarPC();
        ciclosExecucao++;
    }

    private void incrementarPC() {
        PCcontador++;
    }
}
