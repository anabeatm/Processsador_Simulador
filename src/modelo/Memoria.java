package src.modelo;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Memoria {
    private short[] memoria = new short[65536];
//    private final int tamanhoMemoria = 65536;

    public Memoria() {
        for(int i = 0; i < 65536; ++i) {
            this.memoria[i] = 0;
        }

    }

    public short ler(int endereco) {
        if (endereco >= 0 && endereco < 65536) {
            return this.memoria[endereco];
        } else {
            System.err.println("[Erro Memoria] Tentativa de leitura em endereço inválido: " + endereco);
            return 0;
        }
    }

    public void carregarBinario(String caminhoArquivoBinario, int enderecoInicial) {
        try {
            FileInputStream fileInputStream = new FileInputStream(caminhoArquivoBinario);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            long tamanhoArquivo = fileInputStream.getChannel().size();
            int numShorts = (int)(tamanhoArquivo / 2L);

            for(int i = 0; i < numShorts; ++i) {
                int low = dataInputStream.readByte() & 255;
                int high = dataInputStream.readByte() & 255;
                short valor = (short)(low & 255 | (high & 255) << 8);
                if (enderecoInicial + i >= 65536) {
                    System.err.println("[Aviso Memoria] Arquivo binário excede o tamanho da memória na posição: " + (enderecoInicial + i));
                    break;
                }

                this.memoria[enderecoInicial + i] = valor;
                System.out.printf("[DEBUG Memoria] Carregando 0x%04X (low: 0x%02X, high: 0x%02X) no endereço %d\n", valor & '\uffff', low, high, enderecoInicial + i);
            }

            dataInputStream.close();
            fileInputStream.close();
            System.out.println("Arquivo binário '" + caminhoArquivoBinario + "' carregado com sucesso a partir do endereço " + enderecoInicial);
        } catch (IOException e) {
            System.err.println("[Erro Memoria] Falha ao carregar o arquivo binário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha crítica ao carregar o programa binário: " + e.getMessage(), e);
        }
    }

    public int getTamanhoMemoria() {
        return 65536;
    }

    public void mostrarConteudo(int inicio, int fim) {
        System.out.println("\n--- Conteúdo da Memória (" + inicio + " - " + fim + ") ---");

        for(int i = inicio; i < fim && i < 65536; ++i) {
            System.out.printf("Memória[%d]: 0x%04X (%d)\n", i, this.memoria[i], this.memoria[i]);
        }

        System.out.println("--------------------------");
    }

    public void store(int endereco, short valor) {
        this.memoria[endereco + 1] = valor;

        for(int i = 0; i < 10; ++i) {
            System.out.println(this.memoria[i]);
        }

    }
}
