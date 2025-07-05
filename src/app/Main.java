package src.app;


import src.modelo.ALU;
import src.simulador.Processador;
public class Main {
    public static void main(String[] args){

        Processador cpu = new Processador();

//        String caminhoArquivo = "C:/msys64/home/gusta/arq-sim-assembler/count.bin";
        cpu.carregarPrograma("C:/msys64/home/gusta/arq-sim-assembler/count.bin");

        // Inicializar registradores com valores de teste
        cpu.getRegistrador().escrever(0, 5); // exemplo: escrever valor 5 no registrador 0
        cpu.getRegistrador().escrever(1, 3); // exemplo: escrever valor 3 no registrador 1

        cpu.testandoCicloSimples(); // executa o mini ciclo completo

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
