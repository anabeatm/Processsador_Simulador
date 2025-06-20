package src.app;


import src.simulador.Processador;
public class Main {
    public static void main(String[] args){
        Processador cpu = new Processador();


        String caminhoArquivoBin = "C:/msys64/home/gusta/arq-sim-assembler/teste.bin";
        cpu.carregarPrograma(caminhoArquivoBin);
        System.out.println("SE FUNCIONOU AMEM DEUS É PAI!!!!!!!!");
    }
}
