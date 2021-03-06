package pub.edholm.jifx.library.datatypes;

import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.MessagePart;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class PowerLevel implements MessagePart {
    private final short level;
    private final byte[] content;

    public PowerLevel(boolean on) {
        this.level = (short) ((on) ? 0xffff : 0);
        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_POWER_LEVEL);
        bb.putShort(this.level);
        content = bb.array();
    }

    public static PowerLevel valueOf(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);
        final short level = bb.getShort();
        return new PowerLevel(level != 0);
    }

    public boolean isPoweredOn() {
        return level != 0;
    }

    public boolean isPoweredOff() {
        return !isPoweredOn();
    }

    @Override
    public int size() {
        return content.length;
    }

    @Override
    public byte[] getContent() {
        return Arrays.copyOf(content, content.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PowerLevel that = (PowerLevel) o;

        return Arrays.equals(content, that.content);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    @Override
    public String toString() {
        return "PowerLevel{" +
                "poweredOn: " + isPoweredOn() +
                '}';
    }
}
