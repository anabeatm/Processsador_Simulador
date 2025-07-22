package src.modelo; // Mantido o package 'simulador'

import src.enums.OperacoesI;
import src.enums.OperacoesR;
import src.enums.TipoInstrucao;
import src.util.FuncaoExtracaoBits;

public class Decodificador {

    public static Instrucao decodificar(short instrucao) {
        // Extrai o bit 15 para determinar o formato (0 para R, 1 para I)
        short format = FuncaoExtracaoBits.extractBits(instrucao, 15, 1);
        System.out.println("[DEBUG Decodificador] Instrução Bruta (0x" + Integer.toHexString(instrucao & 0xFFFF) + ") - Formato (bit 15): " + format + " (0=Tipo R, 1=Tipo I)");

        if (format == 0) { // Tipo R
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 9, 6);
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 6, 3);
            short rs = FuncaoExtracaoBits.extractBits(instrucao, 3, 3);
            short rt = FuncaoExtracaoBits.extractBits(instrucao, 0, 3);

            OperacoesR operacao = OperacoesR.fromOpcode(opcode);

            System.out.println("[DEBUG Decodificador - Tipo R] Opcode: " + opcode + " | RD: " + rd + " | RS: " + rs + " | RT: " + rt);
            if (operacao == OperacoesR.NOP_R && opcode != OperacoesR.NOP_R.getOpcode()) {
                System.err.println("[ERRO DECODIFICADOR] Opcode R desconhecido: " + opcode + " (Instrução: 0x" + Integer.toHexString(instrucao & 0xFFFF) + ")");
            }

            // CORREÇÃO AQUI: Agora usa o único construtor Tipo R disponível
            if (operacao == OperacoesR.SYSCALL) {
                // Para SYSCALL, rs é o serviço, rd e rt são 0
                return new Instrucao(instrucao, TipoInstrucao.R, operacao, 0, rs, 0);
            }

            return new Instrucao(instrucao, TipoInstrucao.R, operacao, rd, rs, rt);

        } else { // Tipo I
            short opcode = FuncaoExtracaoBits.extractBits(instrucao, 13, 2); // Opcode de 2 bits
            short rd = FuncaoExtracaoBits.extractBits(instrucao, 10, 3);
            short immediate = FuncaoExtracaoBits.extractBits(instrucao, 0, 10);

            // Antes da extensão de sinal para o imediato
            System.out.println("[DEBUG Decodificador - Tipo I] Opcode: " + opcode + " | RD: " + rd + " | Imediato (sem sinal): " + immediate);

            // Extensão de sinal para o imediato de 10 bits para short (16 bits)
            if ((immediate & 0x200) != 0) {
                immediate = (short) (immediate | 0xFC00);
            }
            System.out.println("[DEBUG Decodificador - Tipo I] Imediato (com extensão de sinal): " + immediate);

            OperacoesI operacao = OperacoesI.fromOpcode(opcode);

            if (operacao == OperacoesI.NOP_I && opcode != OperacoesI.NOP_I.getOpcode()) {
                System.err.println("[ERRO DECODIFICADOR] Opcode I desconhecido: " + opcode + " (Instrução: 0x" + Integer.toHexString(instrucao & 0xFFFF) + ")");
            }

            return new Instrucao(instrucao, TipoInstrucao.I, operacao, rd, immediate);
        }
    }
}