package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.utils.MessageConstants;
import pub.edholm.jifx.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class PowerLevel implements Message {
    private final short level;
    private final byte[] content;

    public PowerLevel(boolean on) {
        this.level = (short) ((on) ? 0xffff : 0);
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(MessageConstants.BYTE_ORDER);
        bb.putShort(this.level);
        content = bb.array();
    }

    public boolean isPoweredOn() {
        return level > 0;
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
        return content;
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
                "poweredOn=" + isPoweredOn() +
                ", level=" + ByteUtils.toHexString(level) +
                '}';
    }
}
