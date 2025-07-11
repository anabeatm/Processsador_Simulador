package src.enums;

public enum Operacoes {
    ADD(0),
    SUB(1),
    MUL(2),
    DIV(3),
    CMP_EQ(4),
    CMP_NE(5),
    LOAD(15),
    STORE(16),
    JUMP(0),
    JUMP_COND(1),
    MOV(3),
    SYSCALL(63);

    private int upcode;

    Operacoes(int upcode) {
        this.upcode = upcode;
    }

    public int getUpcode() {
        return upcode;
    }
}
