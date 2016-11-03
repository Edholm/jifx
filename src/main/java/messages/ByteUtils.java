package messages;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class ByteUtils {

    public static byte setBit(byte b, int position) {
        return (byte) (b | ( 1 << position));
    }
    public static byte clearBit(byte b, byte position) {
        return (byte) (b & ~(1 << position));
    }
}
