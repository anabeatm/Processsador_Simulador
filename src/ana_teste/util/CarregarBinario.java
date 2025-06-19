package src.ana_teste.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class CarregarBinario {
    public int ler_memoria(int[] dados, int addr) {
            return dados[addr];
    }

    public void memory_write (int[] dados, short addr, short value) {
        //                                    endereço, valor
        dados[addr] = value;
    }
    void load_binary (String binary_name, int[] dados) {
        try {
            FileInputStream fileInputStream = new FileInputStream(binary_name);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            long tamanhoArquivo = fileInputStream.getChannel().size();

            int numShorts = (int) (tamanhoArquivo / 2);

            for (int i = 0; i < numShorts; i++) {
                int low = dataInputStream.readByte() & 0x000000FF;
                int high = dataInputStream.readByte() & 0x000000FF;
                int value = (low | (high << 8)) & 0x0000FFFF;

                this.memory_write(dados, (short)i, (short)value);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
