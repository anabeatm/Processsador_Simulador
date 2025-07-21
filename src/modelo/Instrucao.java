package src.modelo;

import src.enums.OperacoesR; // Importado
import src.enums.OperacoesI; // Importado
import src.enums.TipoInstrucao;

public class Instrucao {
    private short codigoBinario;
    private TipoInstrucao tipo; // Formato da instrução (R ou I)
    private OperacoesR operacaoR; // Operação para Tipo R
    private OperacoesI operacaoI; // Operação para Tipo I

    // Campos genéricos para os valores da instrução
    private short registradorDestino;
    private short registrador1; // Pode ser RS para Tipo R, ou valor do serviço para SYSCALL
    private short registrador2; // RT para Tipo R
    private short imediato;     // Imediato para Tipo I

    // Construtor para Tipo R
    public Instrucao(short codigoBinario, TipoInstrucao tipo, OperacoesR operacao, int rd, int rs, int rt) {
        this.codigoBinario = codigoBinario;
        this.operacaoR = operacao;
        this.operacaoI = null;
        this.registradorDestino = (short) rd;
        this.registrador1 = (short) rs;
        this.registrador2 = (short) rt;
    }

    // Construtor para Tipo I (MOV, LOAD_IMMEDIATE, JUMP, JUMP_COND)
    public Instrucao(short codigoBinario, TipoInstrucao tipo, OperacoesI operacao, int rd, short imediato) {
        this.codigoBinario = codigoBinario;
        this.tipo = tipo;
        this.operacaoR = null;
        this.operacaoI = operacao;
        this.registradorDestino = (short) rd;
        this.imediato = imediato;
        this.registrador1 = 0;
        this.registrador2 = 0;
    }


    // Getters
    public TipoInstrucao getTipoInstrucao() {
        return tipo;
    }

    // Getter para Operação R-Type
    public OperacoesR getOperacaoR() {
        return operacaoR;
    }

    // Getter para Operação I-Type
    public OperacoesI getOperacaoI() {
        return operacaoI;
    }

    public short getRegistradorDestino() {
        return registradorDestino;
    }

    public short getRegistrador1() { // Usado como RS ou serviço
        return registrador1;
    }

    public short getRegistrador2() { // Usado como RT
        return registrador2;
    }

    public short getImediato() {
        return imediato;
    }


    public short getOpcode() {
        return (short) ((codigoBinario >> 12) & 0xF);
    }


    @Override
    public String toString() {
        if (tipo == TipoInstrucao.R) {
            if (operacaoR == OperacoesR.SYSCALL) {
                return "Instrucao [Tipo=" + tipo + ", Operacao=" + operacaoR + ", Serviço=" + registrador1 + "]";
            }
            return "Instrucao [Tipo=" + tipo + ", Operacao=" + operacaoR +
                    ", RD=" + registradorDestino + ", RS=" + registrador1 + ", RT=" + registrador2 + "]";
        } else { // Tipo I
            return "Instrucao [Tipo=" + tipo + ", Operacao=" + operacaoI +
                    ", RD=" + registradorDestino + ", Imediato=" + imediato + "]";
        }
    }
}
