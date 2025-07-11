package src.modelo;

public class Registrador {
    private int[] registradores;

    public Registrador(int quantidade) {
        this.registradores = new int[quantidade];
    }

    public int lerPorIndice(int indice) {
        return registradores[indice];
    }

    public void escrever(int indice, int valor) {
        registradores[indice] = valor;
    }

    public void mostrarConteudo() {
        for (int i = 0; i < registradores.length; i++) {
            System.out.printf("r%d = %d\n", i, registradores[i]);
        }
    }
}
