package src.modelo;
import src.enums.Operacoes;
public class ALU {

    public int executar(int operacao, int operando1, int operando2) {
        Operacoes op = null;
        for (Operacoes valor : Operacoes.values()) { // for onde 'valor' recebe cada valor presente na classe Operacoes
            if (valor.getUpcode() == operacao) { // se o valor
                op = valor;
                break;
            }
        }

        if (op == null) {
            throw new IllegalArgumentException("Operação da ALU inválida: " + operacao);
        }

        switch (op) {
            case ADD:
                return operando1 + operando2;
            case SUB:
                return operando1 - operando2;
            case MUL:
                return operando1 * operando2;
            case DIV:
                if (operando2 == 0) {
                    throw new ArithmeticException("ERRO!!!! Divisão por zero!");
                }
                return operando1 / operando2;
            case CMP_EQUAL: // Compara se são iguais (retorna 1 se sim, 0 se não)
                return (operando1 == operando2) ? 1 : 0;
            case CMP_NEQ: // Compara se são diferentes (retorna 1 se sim, 0 se não)
                return (operando1 != operando2) ? 1 : 0;
            // Para LOAD/STORE, a ALU pode ser usada para calcular o endereço efetivo (base + offset)
            case LOAD:
            case STORE:
                return operando1 + operando2; // Base + Offset (imediato)
            case MOV:
                return  operando2;
            default:
                throw new UnsupportedOperationException("Operação ALU não implementada: " + op);
        }
    }
}





