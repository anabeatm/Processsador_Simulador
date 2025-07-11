package src.modelo;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Memoria {
    private short[] dado;
    private int tamanho;

    public Memoria(int tamanho) {
        this.tamanho = tamanho;
        this.dado = new short[tamanho];
    }

    public short lerValor(int endereco) {
        if (endereco < 0 || endereco >= tamanho) {
            throw new IllegalArgumentException("Endereço inválido!!!");
        }
        return dado[endereco];
    }

    public void escreverMemoria(int endereco, short valor) {
        if (endereco < 0 || endereco >= tamanho) {
            throw new IllegalArgumentException("Endereço inválido!!!");
        }
        dado[endereco] = valor;
    }

    public void carregarBinario(String caminhoArquivo) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminhoArquivo))) {
            int i = 0;
            while (dis.available() >= 2 && i < dado.length) {
                int low = dis.readByte() & 0xFF;
                int high = dis.readByte() & 0xFF;
                short instrucao = (short)((high << 8) | low);
                escreverMemoria(i, instrucao);
                i++;
            }
        } catch (IOException erro) {
            System.err.println("Erro ao carregar o arquivo: " + erro.getMessage());
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}