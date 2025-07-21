package src.modelo;

import src.enums.OperacoesR; // Atualizado
import src.enums.OperacoesI; // Atualizado
import src.enums.TipoInstrucao;

import src.util.FuncaoExtracaoBits;

public class Decodificador {

    public static Instrucao decodificar(short instrucao) {
        // O bit 15 determina o formato da instrução
        short format = FuncaoExtracaoBits.extractBits(instrucao, 15, 1);

        if (format == 0) { // Tipo R
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 9, 6);
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 6, 3);
            short rs = FuncaoExtracaoBits.extractBits(instrucao, 3, 3);
            short rt = FuncaoExtracaoBits.extractBits(instrucao, 0, 3);

            OperacoesR operacao = OperacoesR.fromOpcode(opcode);

            if (operacao == OperacoesR.NOP_R && opcode != OperacoesR.NOP_R.getOpcode()) {
                System.err.println("[Erro Decodificador] Opcode desconhecido para Tipo R: " + opcode +
                        " na instrução binária: " + Integer.toBinaryString(instrucao & 0xFFFF));
                return new Instrucao(instrucao, TipoInstrucao.R, OperacoesR.NOP_R, 0, 0, 0);
            }

            if (operacao == OperacoesR.SYSCALL) {
                return new Instrucao(instrucao, TipoInstrucao.R, operacao, 0, rs, 0);
            } else {
                return new Instrucao(instrucao, TipoInstrucao.R, operacao, rd, rs, rt);
            }

        } else { // Tipo I
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 13, 2);
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 10, 3);
            short immediate = FuncaoExtracaoBits.extractBits(instrucao, 0, 10);

            if ((immediate & 0x200) != 0) {
                immediate = (short) (immediate | 0xFC00);
            }

            OperacoesI operacao = OperacoesI.fromOpcode(opcode);

            if (operacao == OperacoesI.NOP_I && opcode != OperacoesI.NOP_I.getOpcode()) {
                System.err.println("[Erro Decodificador] Opcode desconhecido para Tipo I: " + opcode +
                        " na instrução binária: " + Integer.toBinaryString(instrucao & 0xFFFF));
                return new Instrucao(instrucao, TipoInstrucao.I, OperacoesI.NOP_I, 0, (short) 0);
            }

            return new Instrucao(instrucao, TipoInstrucao.I, operacao, rd, immediate);
        }
    }


}

