package src.modelo;

public class Registrador {
    private short[] registradores = new short[8];
//    private final int numeroRegistradores = 8;

    public Registrador() {
        for(int i = 0; i < 8; ++i) {
            this.registradores[i] = 0;
        }

    }

    public short ler(int indice) {
        if (indice >= 0 && indice < 8) {
            return this.registradores[indice];
        } else {
            System.err.println("[Erro Registrador] Tentativa de leitura em registrador inválido: R" + indice);
            return 0;
        }
    }

    public void escrever(int indice, short valor) {
        if (indice >= 0 && indice < 8) {
            this.registradores[indice] = valor;
        } else {
            System.err.println("[Erro Registrador] Tentativa de escrita em registrador inválido: R" + indice);
        }

    }

    public void mostrarConteudo() {
        System.out.println("\n--- Conteúdo dos Registradores ---");

        for(int i = 0; i < 8; ++i) {
            System.out.printf("R%02d: (%d) ", i, this.registradores[i]);
            if ((i + 1) % 8 == 0) {
                System.out.println();
            }
        }

        System.out.println("----------------------------------");
    }
}