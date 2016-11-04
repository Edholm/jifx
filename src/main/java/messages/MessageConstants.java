package messages;

import java.nio.ByteOrder;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
class MessageConstants {
    public static final int SIZE_PROTOCOL_HEADER = 12;
    public static final int SIZE_FRAME_ADDRESS = 16;
    public static final int SIZE_FRAME = 8;
    static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
}
