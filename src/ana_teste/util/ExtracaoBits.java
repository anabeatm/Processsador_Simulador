package src.ana_teste.util;

public class ExtracaoBits {
    public static short extract_bits (short value, int bstart, int blength) {
        short mask = (short)((1 << blength) - 1);
        return (short)((value >> bstart) & mask);
    }
}
