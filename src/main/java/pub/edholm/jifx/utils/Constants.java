package pub.edholm.jifx.utils;

import java.nio.ByteOrder;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public class Constants {

    private Constants() {
        throw new AssertionError("You mustn't create an instance of this class");
    }

    public static final int PORT = 56700;

    public static final int SIZE_ECHO = 64;
    public static final int SIZE_STATE_SIGNAL_INFO = 14;
    public static final int SIZE_STATE_HOST_FIRMWARE = 20;
    public static final int SIZE_INFRARED = 2;
    public static final int SIZE_SET_LIGHT_POWER = 6;
    public static final int SIZE_STATE = 56;
    public static final int SIZE_POWER_LEVEL = 2;
    public static final int SIZE_HSBK = 8;
    public static final int SIZE_SET_COLOR = 5 + SIZE_HSBK;
    public static final int SIZE_PROTOCOL_HEADER = 12;
    public static final int SIZE_FRAME_ADDRESS = 16;
    public static final int SIZE_FRAME = 8;
    public static final int SIZE_HEADER = SIZE_FRAME + SIZE_FRAME_ADDRESS + SIZE_PROTOCOL_HEADER;
    public static final int SIZE_LABEL = 32;
    public static final int SIZE_EXTENDED_LABEL = SIZE_LABEL + 16 + 8;
    public static final int SIZE_TIME = 8;

    public static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
}
