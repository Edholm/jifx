package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageConstants;

import java.nio.ByteBuffer;

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

    public boolean isOn() {
        return level > 0;
    }

    public boolean isOff() {
        return !isOn();
    }

    @Override
    public int size() {
        return content.length;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}
