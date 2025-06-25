package src.app;


import src.modelo.ALU;
import src.simulador.Processador;
public class Main {
    public static void main(String[] args){

        Processador cpu = new Processador();

        String caminhoArquivo = "programas/exemplo.bin"; // Ajustar o caminho
        cpu.carregarPrograma(caminhoArquivo);

        cpu.testandoCicloSimples(); // 🚀 Executa o mini ciclo completo

//        Processador cpu = new Processador();
//
//
//        String caminhoArquivoBin = "C:/msys64/home/gusta/arq-sim-assembler/count.bin";
//        cpu.carregarPrograma(caminhoArquivoBin);
//
//        cpu.testarDecodificacao();


//        ALU alu = new ALU();
//        int resultado = alu.executar(0, 2, 3);
//        System.out.println("resultado: " + resultado);
    }
}
