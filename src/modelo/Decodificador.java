package src.modelo;

import src.util.FuncaoExtracaoBits;

public class Decodificador {
    // função principal vai transformar o codigo em binário em um objeto do tipo Intrucao





    public Instrucao decodificar(int binario){
        Instrucao instrucao = new Instrucao();
        int tipo = FuncaoExtracaoBits.extract_bits ((short)binario, 15, 1);


    }
}
