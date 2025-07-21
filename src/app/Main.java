package src.app;

import src.simulador.Processador;

public class Main {
    public static void main(String[] args) {
        Processador cpu = new Processador();

        // Caminho para o arquivo binário (ajuste se necessário)
        String caminhoArquivoBin = "C:/msys64/home/gusta/arq-sim-assembler/simple-1.bin";
        int pcInicial = 1;

        while(cpu.isExecutando()){
            cpu.carregarPrograma(caminhoArquivoBin, pcInicial);
            cpu.executarProgramaCompleto();
        }
        System.err.println("Terminou de executar");
    }
}
