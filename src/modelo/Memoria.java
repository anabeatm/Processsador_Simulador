package src.modelo;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Memoria {
    private short[] memoria;
    private final int tamanhoMemoria = 65536; // Exemplo: 256 posições de memória, ajuste conforme necessário

    public Memoria() {
        this.memoria = new short[tamanhoMemoria];
        // Inicializa a memória com zeros
        for (int i = 0; i < tamanhoMemoria; i++) {
            memoria[i] = 0;
        }
    }

    public short ler(int endereco) {
        if (endereco >= 0 && endereco < tamanhoMemoria) {
            return memoria[endereco];
        } else {
            System.err.println("[Erro Memoria] Tentativa de leitura em endereço inválido: " + endereco);
            return 0; // Retorna 0 ou lança uma exceção, dependendo da sua política de erro
        }
    }

    public void escrever(int endereco, short valor) {
        if (endereco >= 0 && endereco < tamanhoMemoria) {
            memoria[endereco] = valor;
        } else {
            System.err.println("[Erro Memoria] Tentativa de escrita em endereço inválido: " + endereco);
        }
    }

    public void carregarBinario(String caminhoArquivoBinario, int enderecoInicial) {
        try {
            FileInputStream fileInputStream = new FileInputStream(caminhoArquivoBinario);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            long tamanhoArquivo = fileInputStream.getChannel().size();
            int numShorts = (int) (tamanhoArquivo / 2); // Cada instrução é um short (2 bytes)
            for (int i = 0; i < numShorts; i++) {
                // Lê bytes e os combina em um short, cuidando da ordem de bytes (little-endian, se for o caso do xxd)
                int low = dataInputStream.readByte() & 0x000000FF;
                int high = dataInputStream.readByte() & 0x000000FF;
                short valor = (short) ((low & 0xFF) | ((high & 0xFF) << 8)); // Assumindo little-endian conforme testes

                if (enderecoInicial + i < tamanhoMemoria) {
                    memoria[enderecoInicial + i] = valor;
                    // --- NOVA LINHA DE DEBUG AQUI ---
                    System.out.printf("[DEBUG Memoria] Carregando 0x%04X (low: 0x%02X, high: 0x%02X) no endereço %d\n",
                            valor & 0xFFFF, low, high, enderecoInicial + i);
                    // --- FIM DA NOVA LINHA DE DEBUG ---
                } else {
                    System.err.println("[Aviso Memoria] Arquivo binário excede o tamanho da memória na posição: " + (enderecoInicial + i));
                    break;
                }
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
        return tamanhoMemoria;
    }

    public void mostrarConteudo(int inicio, int fim) {
        System.out.println("\n--- Conteúdo da Memória (" + inicio + " - " + fim + ") ---");
        for (int i = inicio; i < fim && i < tamanhoMemoria; i++) {
            System.out.printf("Memória[%d]: 0x%04X (%d)\n", i, memoria[i], memoria[i]);
        }
        System.out.println("--------------------------");
    }

    public void store(int endereco, short valor) {
        memoria[endereco] = valor;
    }


}
