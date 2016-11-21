package pub.edholm.jifx.utils;

import pub.edholm.jifx.messages.MessagePart;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public final class ByteUtils {
    private static final String HEX_FORMAT = "%02x ";

    private ByteUtils() {
        throw new AssertionError("You mustn't create an instance of this class");
    }

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
