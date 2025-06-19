package src.modelo;

public class Memoria { // vai ser utilizada para simular memoria RAM
    private int[] dado; // guarda um valor ou instrução de 32 bits
    private int tamanho; // número de posições dentro de 'intrução32Bits']



    public Memoria(int tamanho){ // vai ser chamado no inicio da classe processador para criar o tamanho da memoria
        this.tamanho = tamanho;
        this.dado = new int[tamanho];
    }


    public int lerValor(int endereco){
        if(endereco < 0 || endereco >= tamanho){
            throw new IllegalArgumentException("Endereço inválido!!!"); // esse if verifica se o endereço esta dentro do intervalo
        }
        return dado[endereco]; // retornar o valor na posição do endereço
    }

    public void escreverMemoria(int endereco, int valor){
        if(endereco < 0 || endereco >= tamanho){
            throw new IllegalArgumentException("Endereço inválido!!!");
        }
        dado[endereco] = valor;
    }




}
