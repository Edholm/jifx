package pub.edholm.jifx.library.headers;

import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public final class ProtocolHeader implements MessagePart {
    private final MessageType type;
    private final byte[] content;

    public ProtocolHeader(MessageType t) {
        this.type = t;
        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_PROTOCOL_HEADER);
        bb.putLong(0).putShort(this.type.type()).putShort((short) 0);
        this.content = bb.array();
    }

    public static ProtocolHeader valueOf(byte[] content) {
        if (content.length < Constants.SIZE_PROTOCOL_HEADER) {
            throw new MalformedMessageException(String.format("Content is too small. Got %d, expected: %d", content.length, Constants.SIZE_PROTOCOL_HEADER));
        }

        ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOf(content, content.length));
        bb.order(Constants.BYTE_ORDER);

        final short type = bb.get(8);
        return new ProtocolHeader(MessageType.valueOf(type));
    }

    public MessageType getType() {
        return type;
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
    public String toString() {
        return "ProtocolHeader {" +
                "type: " + type +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProtocolHeader that = (ProtocolHeader) o;

        return Arrays.equals(content, that.content);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }
}
