package src.ana_teste;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Memoria {
    private static final int TAMANHO = 64 * 1024;
    private final int[] dados = new int[TAMANHO];

    public int ler_memoria(int addr) {
        return dados[addr];
    }

    public void memory_write(short addr, short value) {
        //                     endereço, valor
        dados[addr] = value;
    }
    void load_binary (String nomeArquivo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(nomeArquivo);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            long tamanhoArquivo = fileInputStream.getChannel().size();

            int numShorts = (int) (tamanhoArquivo / 2);

            for (int i = 0; i < numShorts; i++) {
                int low = dataInputStream.readByte() & 0x000000FF;
                int high = dataInputStream.readByte() & 0x000000FF;
                int value = (low | (high << 8)) & 0x0000FFFF;

                this.memory_write((short)i, (short)value);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // TODO: printar a mémoria
    // mas não acho necessário, mesmo que no repositório do tavares tenha
    // acho que é só capricho, mas posso estar enganada
}
