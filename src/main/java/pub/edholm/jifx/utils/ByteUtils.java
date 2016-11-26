package pub.edholm.jifx.utils;

import java.nio.ByteBuffer;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public final class ByteUtils {
    private static final String HEX_FORMAT = "%02x";
    private static final String SEPARATOR = " ";

    private ByteUtils() {
        throw new AssertionError("You mustn't create an instance of this class");
    }

    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : array) {
            sb.append(String.format(HEX_FORMAT + SEPARATOR, b));
        }

        return sb.toString();
    }

    public static <T> String toHexString(T s) {
        return "0x" + String.format(HEX_FORMAT, s);
    }

    public static String toMacAddressString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            sb.append(String.format(HEX_FORMAT, b));
            if (i < bytes.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    public static byte[] emptyByteArray(int size) {
        return new byte[size];
    }

    public static byte[] longToBytes(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(l);
        return buffer.array();
    }

    public static ByteBuffer allocateByteBuffer(int capacity) {
        final ByteBuffer buffer = ByteBuffer.allocate(capacity);
        return buffer.order(Constants.BYTE_ORDER);
    }
}
