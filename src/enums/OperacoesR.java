package src.enums;

public enum OperacoesR{
    // Opcodes para instruções Tipo R (formato 0) - Opcode de 6 bits (bits 9-14)
    ADD(0),          // 000000
    SUB(1),          // 000001
    MUL(2),          // 000010
    DIV(3),          // 000011
    CMP_EQUAL(4),    // 000100
    CMP_NEQ(5),        // 001111 - Carrega da memória para registrador
    STORE(16),       // 010000 - Armazena registrador na memória
    SYSCALL(63),
    LOAD(15),// 111111 - Chamada de sistema

    NOP_R(-1);       // No-Operation específica para Tipo R, para tratamento de erro

    private final int opcode;

    OperacoesR(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    /**
     * Retorna a OperacaoRType correspondente a um opcode.
     * @param opcode O valor do opcode a ser procurado.
     * @return A OperacaoRType correspondente, ou NOP_R se não for encontrada.
     */
    public static OperacoesR fromOpcode(int opcode) {
        for (OperacoesR op : OperacoesR.values()) {
            if (op.getOpcode() == opcode) {
                return op;
            }
        }
        return NOP_R; // Retorna NOP_R se o opcode não for encontrado
    }
}
