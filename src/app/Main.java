package src.app;
import simulador.Processador;

public class Main {
    public static void main(String[] args) {
        Processador processador = new Processador();
        processador.carregarPrograma("C:/msys64/home/gusta/arq-sim-assembler/perfect-squares.bin");
        processador.iniciar();
    }
}