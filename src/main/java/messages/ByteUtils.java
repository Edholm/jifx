package messages;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class ByteUtils {

    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for(byte b : array) {
            sb.append(String.format("0x%x ", b));
        }

        return sb.toString();
    }
}
