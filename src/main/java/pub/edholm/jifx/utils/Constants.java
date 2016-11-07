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

    public static final int SIZE_INFRARED = 2;
    public static final int SIZE_SET_LIGHT_POWER = 6;
    public static final int SIZE_STATE = 56;
    public static final int SIZE_POWER_LEVEL = 2;
    public static final int SIZE_HSBK = 8;
    public static final int SIZE_PROTOCOL_HEADER = 12;
    public static final int SIZE_FRAME_ADDRESS = 16;
    public static final int SIZE_FRAME = 8;
    public static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
}
