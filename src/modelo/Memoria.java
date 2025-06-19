package src.modelo;

public class Memoria { // vai ser utilizada para simular memoria RAM
    private int[] instrucao32Bits;
    private int tamanho; // número de posições dentro de 'intrução32Bits'


    public int lerValor(int endereco){
        return instrucao32Bits[endereco]; // retornar o valor na posição do endereço
    }



}
