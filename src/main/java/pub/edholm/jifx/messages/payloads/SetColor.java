package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageConstants;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Sent by a client to change the light state.
 *
 * Created by Emil Edholm on 2016-11-05.
 */
public class SetColor implements Message {
    private final Hsbk color;
    private final int duration;
    private final byte[] content;

    public SetColor(Hsbk color, int duration) {
        this.color = color;
        this.duration = duration;

        ByteBuffer bb = ByteBuffer.allocate(5 + color.size());
        bb.order(MessageConstants.BYTE_ORDER);
        bb.put((byte) 0); // reserved field
        bb.put(color.getContent());
        bb.putInt(duration);
        content = bb.array();
    }

    public Hsbk getColor() {
        return color;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public int size() {
        return content.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetColor setColor = (SetColor) o;

        return Arrays.equals(content, setColor.content);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    @Override
    public String toString() {
        return "SetColor{" +
                "color=" + color +
                ", duration=" + duration +
                ", content=" + ByteUtils.toHexString(content) +
                '}';
    }
}
