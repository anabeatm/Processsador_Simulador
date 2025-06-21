package src.modelo;

import src.util.FuncaoExtracaoBits;

public class Decodificador {
    // função principal vai transformar o codigo em binário em um objeto do tipo Intrucao





    public Instrucao decodificar(int binario){
        Instrucao instrucao = new Instrucao();
        int tipo = FuncaoExtracaoBits.extract_bits ((short)binario, 15, 1); // pegar o tipo (R ou I)
        if(tipo == 0){ // tipo 0
            int upcode = FuncaoExtracaoBits.extract_bits((short)binario, 9, 6);
            int registradorDestino = FuncaoExtracaoBits.extract_bits((short)binario, 6, 3);
            int registradorOperando1 = FuncaoExtracaoBits.extract_bits((short)binario, 3, 3);
            int registradorOperando2 = FuncaoExtracaoBits.extract_bits((short)binario, 0, 3);

            instrucao.setUpcode(upcode);
            instrucao.setRegistradorDestino(registradorDestino);
            instrucao.setRegistradorOperando1(registradorOperando1);
            instrucao.setRegistradorOperando2(registradorOperando2);
            instrucao.setTipoInstrucao(TipoInstrucao.R);


        } else if(tipo == 1){ // tipo I
            int upcode   = FuncaoExtracaoBits.extract_bits((short) binario, 13, 2);
            int registradorDestino = FuncaoExtracaoBits.extract_bits((short) binario, 10, 3);
            int imediato = FuncaoExtracaoBits.extract_bits((short) binario, 0, 10);

            instrucao.setUpcode(upcode);
            instrucao.setRegistradorDestino(registradorDestino);
            instrucao.setImediato(imediato);
            instrucao.setTipoInstrucao(TipoInstrucao.I);
    }
}
