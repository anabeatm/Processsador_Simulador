package src.ana_teste;

public class Registradores {
    private static final int TOTAL = 3; // ou 8, depende
    private final int[] registrador = new int[TOTAL];

    public int ler(int indice) {
        return registrador[indice];
    }
    public void escrever(int indice, int valor) {
        registrador[indice] = valor;
    }

    public void imprimirRegistrador() {
        for (int i = 0; i < TOTAL; i++) {
            System.out.println("registrador" + i + ": " + registrador[i] + "\n");
        }
        System.out.println();
    }
}
