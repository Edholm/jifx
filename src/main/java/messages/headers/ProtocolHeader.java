package messages.headers;

import messages.MessageConstants;
import messages.MessageType;
import utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public final class ProtocolHeader {
    private final MessageType type;
    private final byte[] content;

    public ProtocolHeader(MessageType t) {
        this.type = t;
        ByteBuffer bb = ByteBuffer.allocate(MessageConstants.SIZE_PROTOCOL_HEADER);
        bb.order(MessageConstants.BYTE_ORDER);
        bb.putLong(0);
        bb.putShort(this.type.type());
        bb.putShort((short) 0);
        this.content = bb.array();
    }

    public MessageType getType() {
        return type;
    }

    public byte[] getContent() {
        return content;
    }

    public int size() {
        return content.length;
    }

    @Override
    public String toString() {
        return "ProtocolHeader{" +
                "type=" + type +
                ", content=" + ByteUtils.toHexString(content) +
                '}';
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
