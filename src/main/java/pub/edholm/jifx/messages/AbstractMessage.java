package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

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
        return payloadContent;
    }

    @Override
    public int size() {
        return header.getTotalSize();
    }

    @Override
    public byte[] getContent() {
        ByteBuffer buffer = ByteBuffer.allocate(header.getTotalSize());
        buffer.order(Constants.BYTE_ORDER);
        buffer.put(header.getContent());
        buffer.put(payloadContent);
        return buffer.array();
    }

}
