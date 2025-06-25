package src.modelo;
import src.enums.Operacoes;
public class ALU {






    public int executar(int operacao, int registradorOperando1, int registradorOperando2) {
        Operacoes op = null;
        for (Operacoes valor : Operacoes.values()) {
            if (valor.getUpcode() == operacao) {
                op = valor;
                break;

            }
        }
    if(op == null){
        throw new IllegalArgumentException("Operando da classe ALU inválido: "+ operacao);
    }

    switch (op){
        case ADD:
            return registradorOperando1 + registradorOperando2;
        case SUB:
            return registradorOperando1 - registradorOperando2;
        case MUL:
            return registradorOperando1 * registradorOperando2;
        default:
            throw new UnsupportedOperationException("Operação não implementada" +  op);



    }

    }
}
