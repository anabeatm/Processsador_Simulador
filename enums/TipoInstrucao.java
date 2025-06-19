package enums;

public enum TipoInstrucao {
    R(0),
    I(1);

    private final int tipo;


    TipoInstrucao(int tipo){
        this.tipo = tipo;
    }
}
