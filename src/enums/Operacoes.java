package src.enums;

public enum Operacoes {

    ADD(0),
    SUB(1),
    MUL(2),
    DIV(3),
    CMP_EQUAL(4),
    CMP_NEQ(5),
    CMP_LESS(6),
    CMP_GREATER(7),
    CMP_LESS_EQ(8),
    CMP_GREATER_EQ(9),
    AND(10),
    OR(11),
    XOR(12),
    SHL(13),
    SHR(14),
    LOAD(15),
    STORE(16),
//    tipo I:
    JUMP(0),
    JUMP_COND(1),
    MOV(3),
    SYSCALL(63);

    private final int upcode;
     Operacoes(int upcode){
        this.upcode = upcode; // construtor para permitir argumento do tipo inteiro para diferenciar as operações
    }

    public int getUpcode(){
         return this.upcode;
    }



}
