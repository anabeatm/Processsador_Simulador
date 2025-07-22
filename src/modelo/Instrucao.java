package src.modelo;

import src.enums.OperacoesI;
import src.enums.OperacoesR;
import src.enums.TipoInstrucao;

public class Instrucao {
    private short codigoBinario;
    private TipoInstrucao tipo;
    private OperacoesR operacaoR;
    private OperacoesI operacaoI;
    private short registradorDestino;
    private short registrador1;
    private short registrador2;
    private short imediato;

    public Instrucao(short codigoBinario, TipoInstrucao tipo, OperacoesR operacao, int rd, int rs, int rt) {
        this.codigoBinario = codigoBinario;
        this.operacaoR = operacao;
        this.operacaoI = null;
        this.registradorDestino = (short)rd;
        this.registrador1 = (short)rs;
        this.registrador2 = (short)rt;
    }

    public Instrucao(short codigoBinario, TipoInstrucao tipo, OperacoesI operacao, int rd, short imediato) {
        this.codigoBinario = codigoBinario;
        this.tipo = tipo;
        this.operacaoR = null;
        this.operacaoI = operacao;
        this.registradorDestino = (short)rd;
        this.imediato = imediato;
        this.registrador1 = 0;
        this.registrador2 = 0;
    }

    public TipoInstrucao getTipoInstrucao() {
        return this.tipo;
    }

    public OperacoesR getOperacaoR() {
        return this.operacaoR;
    }

    public OperacoesI getOperacaoI() {
        return this.operacaoI;
    }

    public short getRegistradorDestino() {
        return this.registradorDestino;
    }

    public short getRegistrador1() {
        return this.registrador1;
    }

    public short getRegistrador2() {
        return this.registrador2;
    }

    public short getImediato() {
        return this.imediato;
    }

//    public short getOpcode() {
//        return (short)(this.codigoBinario >> 12 & 15);
//    }

    public String toString() {
        if (this.tipo == TipoInstrucao.R) {
            if (this.operacaoR == OperacoesR.SYSCALL) {
                String var2 = String.valueOf(this.tipo);
                return "Instrucao [Tipo=" + var2 + ", Operacao=" + String.valueOf(this.operacaoR) + ", Serviço=" + this.registrador1 + "]";
            } else {
                String var1 = String.valueOf(this.tipo);
                return "Instrucao [Tipo=" + var1 + ", Operacao=" + String.valueOf(this.operacaoR) + ", RD=" + this.registradorDestino + ", RS=" + this.registrador1 + ", RT=" + this.registrador2 + "]";
            }
        } else {
            String var10000 = String.valueOf(this.tipo);
            return "Instrucao [Tipo=" + var10000 + ", Operacao=" + String.valueOf(this.operacaoI) + ", RD=" + this.registradorDestino + ", Imediato=" + this.imediato + "]";
        }
    }
}