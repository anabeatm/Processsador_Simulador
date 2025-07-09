package src.app;


import src.simulador.Processador;
public class Main {
    public static void main(String[] args) {
        Processador cpu = new Processador();

        String caminhoArquivoBin = "C:/msys64/home/gusta/arq-sim-assembler/testeJump.bin";
        cpu.carregarPrograma(caminhoArquivoBin);
        cpu.executarProgramaCompleto();
    }
}
