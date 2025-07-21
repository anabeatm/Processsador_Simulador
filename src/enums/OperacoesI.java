package src.enums;

public enum OperacoesI{
    // Opcodes para instruções Tipo I (formato 1) - Opcode de 2 bits (bits 13-14)
    JUMP(0),         // 00 - JUMP incondicional
    JUMP_COND(1),    // 01 - JUMP condicional
    MOV(3),          // 11 - Move imediato para registrador
    NOP_I(-1);       // No-Operation específica para Tipo I, para tratamento de erro

    private final int opcode;

    OperacoesI(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    /**
     * Retorna a OperacaoIType correspondente a um opcode.
     * @param opcode O valor do opcode a ser procurado.
     * @return A OperacaoIType correspondente, ou NOP_I se não for encontrada.
     */
    public static OperacoesI fromOpcode(int opcode) {
        for (OperacoesI op : OperacoesI.values()) {
            if (op.getOpcode() == opcode) {
                return op;
            }
        }
        return NOP_I; // Retorna NOP_I se o opcode não for encontrado
    }
}
