package src.modelo;

import src.modelo.Instrucao;
import src.enums.*;
import src.util.FuncaoExtracaoBits;

public class Decodificador {

    public static Instrucao decodificar(short binario) {
        short bitFormato = FuncaoExtracaoBits.extract_bits(binario, 15, 1);
      TipoInstrucao tipoInstrucao = (bitFormato == 0) ? TipoInstrucao.R : TipoInstrucao.I;

        if (tipoInstrucao == TipoInstrucao.R) {
            int opcode = FuncaoExtracaoBits.extract_bits(binario, 9, 6);
            int rd = FuncaoExtracaoBits.extract_bits(binario, 6, 3);
            int rs = FuncaoExtracaoBits.extract_bits(binario, 3, 3);
            int rt = FuncaoExtracaoBits.extract_bits(binario, 0, 3);
            return new Instrucao(tipoInstrucao, opcode, rd, rs, rt, 0);
        } else {
            int opcode = FuncaoExtracaoBits.extract_bits(binario, 13, 2);
            int rd = FuncaoExtracaoBits.extract_bits(binario, 10, 3); // Corrigido (bits 13:11)
            int imediato = FuncaoExtracaoBits.extract_bits(binario, 0, 10);
            return new Instrucao(tipoInstrucao, opcode, rd, 0, 0, imediato);
        }
    }
}
