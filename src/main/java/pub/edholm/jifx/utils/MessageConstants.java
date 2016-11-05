package pub.edholm.jifx.utils;

import java.nio.ByteOrder;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public class MessageConstants {
    public static final int SIZE_HSBK = 8;
    public static final int SIZE_PROTOCOL_HEADER = 12;
    public static final int SIZE_FRAME_ADDRESS = 16;
    public static final int SIZE_FRAME = 8;
    public static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
}
