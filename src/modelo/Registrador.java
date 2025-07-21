package src.modelo;

public class Registrador {
    private short[] registradores;
    private final int numeroRegistradores = 8; // Exemplo: R0 a R31

    public Registrador() {
        this.registradores = new short[numeroRegistradores];
        // Inicializa todos os registradores com zero
        for (int i = 0; i < numeroRegistradores; i++) {
            registradores[i] = 0;
        }
    }

    public short ler(int indice) {
        if (indice >= 0 && indice < numeroRegistradores) {
            return registradores[indice];
        } else {
            System.err.println("[Erro Registrador] Tentativa de leitura em registrador inválido: R" + indice);
            return 0;
        }
    }

    public void escrever(int indice, short valor) {
        if (indice >= 0 && indice < numeroRegistradores) {
            registradores[indice] = valor;
        } else {
            System.err.println("[Erro Registrador] Tentativa de escrita em registrador inválido: R" + indice);
        }
    }

    // Método para depuração
    public void mostrarConteudo() {
        System.out.println("\n--- Conteúdo dos Registradores ---");
        for (int i = 0; i < numeroRegistradores; i++) {
            System.out.printf("R%02d: 0x%04X (%d)\n", i, registradores[i], registradores[i]);
            if ((i + 1) % 8 == 0) { // Formatação para melhor visualização
                System.out.println();
            }
        }
        System.out.println("----------------------------------");
    }
}