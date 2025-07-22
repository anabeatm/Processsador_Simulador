package src.app;

import src.simulador.Processador;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Processador cpu = new Processador();
        String caminhoArquivoBin = "src/testes/memory.bin";
        int pcInicial = 1;
        cpu.carregarPrograma(caminhoArquivoBin, pcInicial);
        cpu.executarProgramaCompleto();
        System.err.println("Terminou de executar");
    }
}
