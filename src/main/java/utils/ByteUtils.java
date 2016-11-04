package utils;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class ByteUtils {

    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for(byte b : array) {
            sb.append(String.format("%02x ", b));
        }

        return sb.toString();
    }
}
