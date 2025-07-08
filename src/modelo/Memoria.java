package src.modelo;

import java.io.DataInputStream; // interpreta os bytes
import java.io.FileInputStream; // abre o arquivo dos bytes
import java.io.IOException;

public class Memoria { // vai ser utilizada para simular memoria RAM
    private short[] dado; // guarda um valor ou instrução de 32 bits
    private int tamanho; // número de posições dentro de 'intrução32Bits'



    public Memoria(int tamanho){ // vai ser chamado no inicio da classe processador para criar o tamanho da memoria
        this.tamanho = tamanho;
        this.dado = new short[tamanho];
    }


    public short lerValor(int endereco){
        if(endereco < 0 || endereco >= tamanho){
            throw new IllegalArgumentException("Endereço inválido!!!"); // esse if verifica se o endereço esta dentro do intervalo
        }
        return dado[endereco]; // retornar o valor na posição do endereço
    }

    public void escreverMemoria(int endereco, short valor){
        if(endereco < 0 || endereco >= tamanho){
            throw new IllegalArgumentException("Endereço inválido!!!");
        }
        dado[endereco] = valor;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void carregarBinario(String caminhoArquivo){
        try(DataInputStream dis = new DataInputStream(new FileInputStream(caminhoArquivo))){ // se der erro fecha o arquivo
        int i = 0; // inicializando um int i para se referir a memoria
        while(dis.available() >= 2 && i < dado.length){// dis.available --> retorna o numero de bytes disponiveis
                                                        // e verifica se ainda tem espaco na memoria com dado.length com indice i
            int low = dis.readByte() & 0xFF; // 0xFF --> converte o byte para inteiro --> numero entre 0 e 255 -->
            // valores quando lemos um arquivo.bin, evitando o erro de lermos o bit mais a esquerda como um sinal
            // (trasnformando o numero em negativo)
            int high = dis.readByte() & 0xFF;
            short instrucao = (short)((high << 8) | low); // o bits de high vão para a mais significativa e junta se
            // com low formando a instrução de 16 bits
            // o high a parte mais significativa do 8 ao 15, e o low para 0 ao 17
            escreverMemoria(i, instrucao);
            i++;
        }
        } catch(IOException erro){ // verifica se der erro e guarda
            System.err.println("Erro ao carregar o arquivo" + erro.getMessage());
        }

    }






}
