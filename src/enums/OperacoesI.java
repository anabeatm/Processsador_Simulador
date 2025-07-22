package src.enums;

public enum OperacoesI {
    JUMP(0),
    JUMP_COND(1),
    MOV(3),
    NOP_I(-1);

    private final int opcode;

    OperacoesI(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public static OperacoesI fromOpcode(int opcode) {
        for(OperacoesI op : values()) {
            if (op.getOpcode() == opcode) {
                return op;
            }
        }

        return NOP_I;
    }
}