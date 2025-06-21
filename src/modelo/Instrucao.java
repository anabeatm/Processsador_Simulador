package src.modelo;
import src.enums.TipoInstrucao;

public class Instrucao {
    private TipoInstrucao tipoInstrucao; // para determinar o tipo R ou tipo I segundo a classe 'TipoInstrução'
    private int upcode;
    private int funct; // ou instrucao
    // no livro "organização e projeto de comp." diz que funct/instrucao
    // seleciona a variante específica da operação no campo op

    private int registradorDestino;
    private int registradorOperando1;
    private int registradorOperando2;
    private int imediato;

    // TODO: agora decodificar() é uma classe Decodificador -> mudar isso no código
//    public Instrucao(int valor) {
//        funct = valor;
//        decodificar();
//    }
//
//    private void decodificar() {
//        tipo = ExtracaoBits.extract_bits((short) funct, 15, 1);
//    }


}
