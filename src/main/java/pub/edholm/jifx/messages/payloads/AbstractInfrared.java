package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
abstract class AbstractInfrared extends AbstractMessage {
    private final short brightness;

    protected AbstractInfrared(Header header, short brightness) {
        super(header, AbstractInfrared.toByteArray(brightness));
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

    private static byte[] toByteArray(short brightness) {
        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_INFRARED);
        bb.order(Constants.BYTE_ORDER);
        bb.putShort(brightness);

        return bb.array();
    }

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
