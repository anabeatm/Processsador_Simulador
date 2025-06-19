package src.modelo;

public class Memoria { // vai ser utilizada para simular memoria RAM
    private int[] dado; // guarda um valor ou instrução de 32 bits
    private int tamanho; // número de posições dentro de 'intrução32Bits']



    public Memoria(int tamanho){
        this.tamanho = tamanho;
        this.dado = new int[tamanho];
    }


    public int lerValor(int endereco){
        return dado[endereco]; // retornar o valor na posição do endereço
    }

    public void escreverMemorai(int endereco, int valor){
        dado[endereco] = valor;
    }


}
