package src.simulador;

import src.modelo.Memoria;
import src.modelo.Registrador;

public class Processador { // controla o ciclo
    private Memoria memoriaInstrucao; // instrução em binário é quardada aqui, 32 bits,
    // acessada pelo PCcontador --> busca próxima instrução
    private Memoria memoriaDeDdados; // guarda valores DURANTE a execução
    private Registrador registrador; //registradores t0, t1, etc...
    private int PCcontador; // "aponta" para a proxima instrução que vai ser buscada, acrescenta + 4
    private boolean ativo; // quando encontra o syscall --> false; ativo == true; inativo == false




    //Criar um construtor que inicie os atributos, falando partirculamente da memoria para definir seu tamanho
    public Processador(){
        this.memoriaInstrucao = new Memoria(// colocar tamamnho);
        this.memoriaDeDdados = new Memoria(// colocar tamanho);
        // colocar os outros atributos
    }
}
