package pub.edholm.jifx.utils;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class ByteUtils {
    private static final String HEX_FORMAT = "%02x ";

    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : array) {
            sb.append(String.format(HEX_FORMAT, b));
        }

        return sb.toString();
    }

    public static <T> String toHexString(T s) {
        return "0x" + String.format(HEX_FORMAT, s);
    }
}
