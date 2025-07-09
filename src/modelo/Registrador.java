package src.modelo;

public class Registrador {
    private int[] posicaoRegsitrador; // cada posição é um registrador
    private int quantidadeTotalRegistradores;

    public Registrador(){
        this.quantidadeTotalRegistradores = 8;
        this.posicaoRegsitrador = new int[8]; // vamos trabalhar com 8 registradores  segundo o professor
    }


    public int lerPorIndice(int indice){
        return posicaoRegsitrador[indice];
    }


    public void escrever(int valor, int indice){
        this.posicaoRegsitrador[indice] = valor;
    }

    public void visualizarRegistrador(){ // ajudar na visualização do registrador
        System.out.println("Estado Final Dos Registradores: ");
        for(int i =0; i<quantidadeTotalRegistradores; i++){
            System.out.printf("r%d = %d%n", i, posicaoRegsitrador[i]);
        }

    }



}




