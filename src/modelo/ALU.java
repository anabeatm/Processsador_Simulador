//package src.modelo;
//import src.enums.*;
//




// NÃO UTILIZANDO

//public class ALU {
//
////    public int executar(int operacao, int operando1, int operando2) {
////        Operacoes op = null;
////        for (Operacoes valor : Operacoes.values()) { // for onde 'valor' recebe cada valor presente na classe Operacoes
////            if (valor.getUpcode() == operacao) { // se o valor
////                op = valor;
////                break;
////            }
////        }
////
////        if (op == null) {
////            throw new IllegalArgumentException("Operação da ALU inválida: " + operacao);
////        }
////
////        switch (op) {
////            case ADD:
////                return operando1 + operando2;
////            case SUB:
////                return operando1 - operando2;
////            case MUL:
////                return operando1 * operando2;
////            case DIV:
////                if (operando2 == 0) {
////                    throw new ArithmeticException("ERRO!!!! Divisão por zero!");
////                }
////                return operando1 / operando2;
////            case CMP_EQUAL: // Compara se são iguais (retorna 1 se sim, 0 se não)
////                return (operando1 == operando2) ? 1 : 0;
////            case CMP_NEQ: // Compara se são diferentes (retorna 1 se sim, 0 se não)
////                return (operando1 != operando2) ? 1 : 0;
////            // Para LOAD/STORE, a ALU pode ser usada para calcular o endereço efetivo (base + offset)
////            case LOAD:
////            case STORE:
////                return operando1 + operando2; // Base + Offset (imediato)
////            case MOV:
////                return  operando2;
////            default:
////                throw new UnsupportedOperationException("Operação ALU não implementada: " + op);
////        }
////    }
//
//    public int executar(int operacao, int operando1, int operando2, TipoInstrucao tipo) {
//        Operacoes op = null;
//        for (Operacoes valor : Operacoes.values()) {
//            if (valor.getUpcode() == operacao) {
//                op = valor;
//                break;
//            }
//        }
//
//        if (op == null) {
//            throw new IllegalArgumentException("Operação da ALU inválida: " + operacao);
//        }
//
//        // Aqui precisamos garantir que estamos interpretando a operação no contexto do tipo
//        if (tipo == TipoInstrucao.I) {
//            if (operacao == 3) { // 3 no tipo I é MOV
//                return operando2; // MOV sobrescreve o destino com o imediato
//            }
//            if (operacao == 0) { // 0 no tipo I é JUMP
//                // Se quiser implementar JUMP, pode retornar um sinal especial, por exemplo
//            }
//        }
//
//        // Se não for tipo I, segue com as operações normais (tipo R)
//        switch (op) {
//            case ADD:
//                return operando1 + operando2;
//            case SUB:
//                return operando1 - operando2;
//            case MUL:
//                return operando1 * operando2;
//            case DIV:
//                if (operando2 == 0) {
//                    throw new ArithmeticException("ERRO!!!! Divisão por zero!");
//                }
//                return operando1 / operando2;
//            case CMP_EQUAL:
//                return (operando1 == operando2) ? 1 : 0;
//            case CMP_NEQ:
//                return (operando1 != operando2) ? 1 : 0;
//            case LOAD:
//            case STORE:
//                return operando1 + operando2;
//            default:
//                throw new UnsupportedOperationException("Operação ALU não implementada: " + op);
//        }
//    }
//
//
//}
//
//
//
//
//
//
