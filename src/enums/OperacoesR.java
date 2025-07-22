package src.enums;

public enum OperacoesR {
    ADD(0),
    SUB(1),
    MUL(2),
    DIV(3),
    CMP_EQUAL(4),
    CMP_NEQ(5),
    STORE(16),
    SYSCALL(63),
    LOAD(15),
    NOP_R(-1);

    private final int opcode;

    OperacoesR(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public static OperacoesR fromOpcode(int opcode) {
        for(OperacoesR op : values()) {
            if (op.getOpcode() == opcode) {
                return op;
            }
        }

        return NOP_R;
    }
}