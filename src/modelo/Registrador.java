package src.modelo;

public class Registrador { // Nome da classe corrigido para o singular
    private int[] registradores;

    public Registrador() {
        this.registradores = new int[8]; // 8 registradores de 32 bits (int)
        // Inicializa os registradores com zero
        for (int i = 0; i < 8; i++) {
            registradores[i] = 0;
        }
        System.out.println("[DEBUG Registrador] Nova instância de Registrador criada e inicializada com zeros.");
    }

    public int ler(int indice) {
        if (indice < 0 || indice >= registradores.length) {
            System.err.println("[ERRO Registrador] Tentativa de ler registrador inválido: " + indice);
            return 0; // Ou lançar exceção
        }
        System.out.println("[DEBUG Registrador.ler] Lendo R" + indice + ". Valor do array: " + registradores[indice]); // Adicione esta linha
        return registradores[indice];
    }

    public void escrever(int indice, int valor) {

        if (indice < 0 || indice >= registradores.length) {
            System.err.println("[ERRO Registrador] Tentativa de escrever em registrador inválido: " + indice);
            return;
        }
        registradores[indice] = valor;
        System.out.println("[DEBUG Registrador.escrever] Escreveu " + valor + " em R" + indice + ". Valor atual do array: " + registradores[indice]);
    }

    // NOVO MÉTODO: Para resetar os registradores para zero
    public void resetar() {
        for (int i = 0; i < 8; i++) {
            registradores[i] = 0;
        }
        System.out.println("[DEBUG Registrador] Registradores resetados para zero.");
    }

    public void mostrarConteudo() {
        System.out.println("\n--- Conteúdo dos Registradores ---");
        for (int i = 0; i < 8; i++) {
            System.out.printf("R%d: %d\n", i, registradores[i]);
        }
        System.out.println("----------------------------------");
    }
}