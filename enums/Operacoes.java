package enums;

public enum Operacoes {
    ADD(0),
    SUB(1),
    MUL(2),
    DIV(3),
    CMP_EQUAL(4),
    CMP_NEQ(5),
    LOAD(6),
    STORE(7),
    JUMP(8),
    JUMP_COND(9),
    MOV(10),
    SYSCALL(63);

    private final int upcode;
     Operacoes(int upcode){
        this.upcode = upcode;
    }



}
