package src.modelo;
import src.enums.TipoInstrucao;

public class Instrucao {
    private TipoInstrucao tipoInstrucao; // para determinar o tipo R ou tipo I segundo a classe 'TipoInstrução'
    private int upcode;
    // private int funct; // ou instrucao COMENTADA POR MOTIVOS não sabemos se vai ser utilizada
    // no livro "organização e projeto de comp." diz que funct/instrucao
    // seleciona a variante específica da operação no campo op

    private int registradorDestino;
    private int registradorOperando1;
    private int registradorOperando2;
    private int imediato;

    // TODO: colocar getters e setter para os atributos

    public void setImediato(int imediato) {
        this.imediato = imediato;
    }

    public void setRegistradorDestino(int registradorDestino) {
        this.registradorDestino = registradorDestino;
    }

    public void setRegistradorOperando1(int registradorOperando1) {
        this.registradorOperando1 = registradorOperando1;
    }

    public void setRegistradorOperando2(int registradorOperando2) {
        this.registradorOperando2 = registradorOperando2;
    }

    public void setTipoInstrucao(TipoInstrucao tipoInstrucao) {
        this.tipoInstrucao = tipoInstrucao;
    }

    public void setUpcode(int upcode) {
        this.upcode = upcode;
    }

    public int getUpcode() {
        return upcode;
    }

    public int getRegistradorOperando1() {
        return registradorOperando1;
    }

    public TipoInstrucao getTipoInstrucao() {
        return tipoInstrucao;
    }

    public int getRegistradorOperando2() {
        return registradorOperando2;
    }

    public int getImediato() {
        return imediato;
    }

    public int getRegistradorDestino() {
        return registradorDestino;
    }

    // TODO: agora decodificar() é uma classe Decodificador -> mudar isso no código
//    public Instrucao(int valor) {
//        funct = valor;
//        decodificar();
//    }
//
//    private void decodificar() {
//        tipo = ExtracaoBits.extract_bits((short) funct, 15, 1);
//    }

    @Override
    public String toString() {
        if (tipoInstrucao == TipoInstrucao.R) {
            return String.format(
                    "[Tipo R] opcode: %d | rd: r%d | rs: r%d | rt: r%d",
                    upcode, registradorDestino, registradorOperando1, registradorOperando2
            );
        } else {
            return String.format(
                    "[Tipo I] opcode: %d | rd: r%d | imediato: %d",
                    upcode, registradorDestino, imediato
            );
        }
    }


}
