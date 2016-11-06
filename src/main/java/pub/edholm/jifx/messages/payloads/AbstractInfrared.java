package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
abstract class AbstractInfrared implements Message {
    private final short brightness;

    public AbstractInfrared(short brightness) {
        this.brightness = brightness;
    }

    public short getBrightness() {
        return brightness;
    }

    protected static short parse(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);
        return bb.getShort();
    }

    @Override
    public int size() {
        return Constants.SIZE_INFRARED;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_INFRARED);
        bb.order(Constants.BYTE_ORDER);
        bb.putShort(brightness);

        return bb.array();
    }

    @Override
    abstract public String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractInfrared that = (AbstractInfrared) o;

        return brightness == that.brightness;

    }

    @Override
    public int hashCode() {
        return (int) brightness;
    }
}
