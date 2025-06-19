package src.enums;

public enum TipoInstrucao {
    R(0),
    I(1);

    private final int tipo;


    TipoInstrucao(int tipo){
        this.tipo = tipo; // construtor para permitir argumento do tipo inteiro para diferenciar entre
        // os tipos R e tipo I
    }
}
