package src.modelo;

import src.enums.OperacoesR;

public class ALU {
    public ALU() {
    }

    public short executar(OperacoesR operacao, short operando1, short operando2, short rd) {
        switch (operacao) {
            case ADD:
                rd = (short)(operando1 + operando2);
                break;
            case SUB:
                return (short)(operando1 - operando2);
            case MUL:
                rd = (short)(operando1 * operando2);
                break;
            case DIV:
                if (operando2 == 0) {
                    System.err.println("[Erro ALU] Tentativa de divisão por zero!");
                    return 0;
                }

                return (short)(operando1 / operando2);
            case CMP_EQUAL:
                return (short)(operando1 == operando2 ? 1 : 0);
            case CMP_NEQ:
                return (short)(operando1 != operando2 ? 1 : 0);
            case STORE:
            case SYSCALL:
            case NOP_R:
                System.err.println("[Erro ALU] Operação " + String.valueOf(operacao) + " não é uma operação de ALU.");
                return 0;
            default:
                System.err.println("[Erro ALU] Operação não suportada ou inválida na ALU: " + String.valueOf(operacao));
                return 0;
        }

        return 0;
    }
}