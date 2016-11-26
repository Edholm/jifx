package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public abstract class AbstractMessage implements Message {
    private final Header header;
    private final byte[] payloadContent;

    protected AbstractMessage(Header header, byte[] payloadContent) {
        this.header = header;
        this.payloadContent = payloadContent;

    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public byte[] getPayload() {
        return Arrays.copyOf(payloadContent, payloadContent.length);
    }

    @Override
    public int size() {
        return header.getTotalSize();
    }

    @Override
    public byte[] getContent() {
        ByteBuffer buffer = ByteUtils.allocateByteBuffer(header.getTotalSize());
        buffer.put(header.getContent()).put(payloadContent);
        return buffer.array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMessage that = (AbstractMessage) o;

        return header.equals(that.header)
                && Arrays.equals(payloadContent, that.payloadContent);

    }

    @Override
    public int hashCode() {
        int result = header.hashCode();
        result = 31 * result + Arrays.hashCode(payloadContent);
        return result;
    }
}
