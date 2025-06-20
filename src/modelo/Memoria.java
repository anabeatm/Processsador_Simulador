package src.modelo;

import java.io.DataInputStream; // interpreta os bytes
import java.io.FileInputStream; // abre o arquivo dos bytes
import java.io.IOException;

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


    public void carregarBinario(){
        try(DataInputStream dis = new DataInputStream(new FileInputStream('caminhoArquivo'))){ // se der erro fecha o arquivo
        int i = 0; // inicializando um int i para se referir a memoria
        while(dis.available() >= 2 && i < dado.length){// dis.available --> retorna o numero de bytes disponiveis
                                                        // e verifica se ainda tem espaco na memoria com dado.length com indice i
            int low = dis.readByte() & 0xFF; // 0xFF --> converte o byte para inteiro --> numero entre 0 e 255 --> valores quando lemos um arquivo.bin, evitando o erro de lermos o bit mais a esquerda como um sinal (trasnformando o numero em negativo)
            int high = dis.readByte() & 0xFF;
            int instrucao = (high << 8) | low; // o bits de high vão para a mais significativa e junta se com low formando a instrução
            escreverMemoria(i, instrucao);
            i++;
        }
        } catch(IOException erro){ // verifica se der erro e guarda
            System.err.println("Erro ao carregar o arquivo" + erro.getMessage());
        }

    }






}
