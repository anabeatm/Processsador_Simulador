package src.modelo;

import src.enums.OperacoesR;


public class ALU {
    public short executar(OperacoesR operacao, short operando1, short operando2, short rd) {
        switch (operacao) {
            case ADD:
                rd = (short) (operando1 + operando2);
                break;
            case SUB:
                return (short) (operando1 - operando2);
            case MUL:
                rd= (short) (operando1 * operando2);
                break;
            case DIV:
                if (operando2 == 0) {
                    System.err.println("[Erro ALU] Tentativa de divisão por zero!");
                    return 0; // Retorna 0 ou lança uma exceção, dependendo da política de erro
                }
                return (short) (operando1 / operando2);
            case CMP_EQUAL:
                return (short) ((operando1 == operando2) ? 1 : 0);
            case CMP_NEQ:
                return (short) ((operando1 != operando2) ? 1 : 0);
            // LOAD, STORE, SYSCALL e NOP_R não são operações da ALU, são tratadas pelo Processado

            case STORE:
            case SYSCALL:
            case NOP_R:
                System.err.println("[Erro ALU] Operação " + operacao + " não é uma operação de ALU.");
                return 0; // Retorna 0 ou lança uma exceção
            default:
                System.err.println("[Erro ALU] Operação não suportada ou inválida na ALU: " + operacao);
                return 0;
        }
        return 0;
    }

}
