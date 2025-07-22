package src.modelo;

import src.enums.OperacoesI;
import src.enums.OperacoesR;
import src.enums.TipoInstrucao;
import src.util.FuncaoExtracaoBits;

public class Decodificador {
    public Decodificador() {
    }

    public static Instrucao decodificar(short instrucao) {
        short format = FuncaoExtracaoBits.extractBits(instrucao, 15, 1);
        if (format == 0) {
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 9, 6);
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 6, 3);
            short rs = FuncaoExtracaoBits.extractBits(instrucao, 3, 3);
            short rt = FuncaoExtracaoBits.extractBits(instrucao, 0, 3);
            OperacoesR operacao = OperacoesR.fromOpcode(opcode);
            if (operacao == OperacoesR.NOP_R && opcode != OperacoesR.NOP_R.getOpcode()) {
                System.err.println("[Erro Decodificador] Opcode desconhecido para Tipo R: " + opcode + " na instrução binária: " + Integer.toBinaryString(instrucao & '\uffff'));
                return new Instrucao(instrucao, TipoInstrucao.R, OperacoesR.NOP_R, 0, 0, 0);
            } else {
                return operacao == OperacoesR.SYSCALL ? new Instrucao(instrucao, TipoInstrucao.R, operacao, 0, rs, 0) : new Instrucao(instrucao, TipoInstrucao.R, operacao, rd, rs, rt);
            }
        } else {
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 13, 2);
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 10, 3);
            short immediate = FuncaoExtracaoBits.extractBits(instrucao, 0, 10);
            if ((immediate & 512) != 0) {
                immediate = (short)(immediate | 'ﰀ');
            }

            OperacoesI operacao = OperacoesI.fromOpcode(opcode);
            if (operacao == OperacoesI.NOP_I && opcode != OperacoesI.NOP_I.getOpcode()) {
                System.err.println("[Erro Decodificador] Opcode desconhecido para Tipo I: " + opcode + " na instrução binária: " + Integer.toBinaryString(instrucao & '\uffff'));
                return new Instrucao(instrucao, TipoInstrucao.I, OperacoesI.NOP_I, 0, (short)0);
            } else {
                return new Instrucao(instrucao, TipoInstrucao.I, operacao, rd, immediate);
            }
        }
    }
}