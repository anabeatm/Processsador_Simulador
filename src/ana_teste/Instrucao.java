package src.ana_teste;

import src.ana_teste.util.ExtracaoBits;

public class Instrucao {

    public int funct; // no livro "organização e projeto de comp" diz que funct/instrucao
    // seleciona a variante específica da operação no campo op
    public int tipo;
    public int opcode;
    public int regDestino;
    public int regOp1;
    public int regOp2;

    public Instrucao(int valor) {
        funct = valor;
        decodificar();
    }

    private void decodificar() {
        tipo = ExtracaoBits.extract_bits((short) funct, 15, 1);
    }
}
