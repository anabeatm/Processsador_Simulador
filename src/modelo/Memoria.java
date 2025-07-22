package src.modelo;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
// Não precisa importar Instrucao aqui diretamente

public class Memoria {
    private short[] memoria;
    private int tamanhoMemoria;

    public Memoria(int tamanho) {
        this.tamanhoMemoria = tamanho;
        this.memoria = new short[tamanho];
        for (int i = 0; i < tamanhoMemoria; i++) {
            memoria[i] = 0;
        }
    }

    public short ler(int endereco) {
        if (endereco < 0 || endereco >= tamanhoMemoria) {
            throw new RuntimeException("[ERRO] Acesso de memória inválido: endereço " + endereco);
        }
        return memoria[endereco];
    }

    public void escrever(int endereco, short valor) {
        if (endereco < 0 || endereco >= tamanhoMemoria) {
            throw new RuntimeException("[ERRO] Acesso de memória inválido: endereço " + endereco);
        }
        memoria[endereco] = valor;
    }

    public void carregarBinario(String caminhoArquivoBinario, int enderecoInicial) {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(caminhoArquivoBinario))) {
            int bytesDisponiveis = dataInputStream.available();
            int numShorts = bytesDisponiveis / 2;

            System.out.println("[DEBUG Memoria] Carregando " + numShorts + " shorts do arquivo " + caminhoArquivoBinario + " a partir do endereço " + enderecoInicial);

            for (int i = 0; i < numShorts; i++) {
                int low = dataInputStream.readByte() & 0x000000FF;
                int high = dataInputStream.readByte() & 0x000000FF;

                short valor = (short) ((low & 0xFF) | ((high & 0xFF) << 8));

                if (enderecoInicial + i < tamanhoMemoria) {
                    memoria[enderecoInicial + i] = valor;
                    System.out.printf("[DEBUG Memoria] Carregando 0x%04x (low: 0x%02x, high: 0x%02X) no endereço %d\n",
                            valor & 0xFFFF, low, high, enderecoInicial + i);
                } else {
                    System.err.println("[Aviso Memoria] Arquivo binário excede o tamanho da memória na posição: " + (enderecoInicial + i));
                    break;
                }
            }
            System.out.println("[DEBUG Memoria] Carregamento do binário concluído.");
        } catch (IOException e) {
            System.err.println("[ERRO Memoria] Falha ao carregar o arquivo binário: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar binário.", e);
        }
    }

    public void mostrarConteudo(int inicio, int fim) {
        System.out.println("\n--- Conteúdo da Memória (" + inicio + " a " + fim + ") ---");
        for (int i = inicio; i < fim && i < tamanhoMemoria; i++) {
            System.out.printf("Memória[%03d]: 0x%04X (%s)\n", i, memoria[i] & 0xFFFF, Integer.toBinaryString(memoria[i] & 0xFFFF));
        }
        System.out.println("----------------------------------");
    }
}