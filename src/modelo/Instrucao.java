package src.modelo;

import src.enums.TipoInstrucao;

public class Instrucao {
    private TipoInstrucao tipoInstrucao;
    private int upcode;
    private int rd;
    private int rs;
    private int rt;
    private int imediato;

    public Instrucao(TipoInstrucao tipoInstrucao, int upcode, int rd, int rs, int rt, int imediato) {
        this.tipoInstrucao = tipoInstrucao;
        this.upcode = upcode;
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.imediato = imediato;
    }

    public TipoInstrucao getTipoInstrucao() {
        return tipoInstrucao;
    }

    public int getUpcode() {
        return upcode;
    }

    public int getRegistradorDestino() {
        return rd;
    }

    public int getRegistrador1() {
        return rs;
    }

    public int getRegistrador2() {
        return rt;
    }

    public int getImediato() {
        return imediato;
    }
}
