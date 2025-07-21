package src.app;

import src.simulador.Processador;

public class Main {

    public static void main(String[] args) {
        Processador cpu = new Processador();


        String caminhoArquivoBin = "C:/msys64/home/gusta/arq-sim-assembler/perfect-squares.bin";


        int pcInicial = 1;


        while (cpu.isExecutando()) {
            try {

                cpu.carregarPrograma(caminhoArquivoBin, pcInicial);
                cpu.executarProgramaCompleto();
            } catch (RuntimeException e) {
                System.err.println("[ERRO CRÍTICO NA EXECUÇÃO] " + e.getMessage());
            }
        }

        System.err.println("Terminou de executar (Flag isExecutando() é false).");
    }
}