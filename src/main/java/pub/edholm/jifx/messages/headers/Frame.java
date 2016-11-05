package pub.edholm.jifx.messages.headers;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageConstants;
import pub.edholm.jifx.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * The Frame section contains information about the following:
 * Size of the entire message
 * LIFX Protocol number: must be 1024 (decimal)
 * Use of the Frame Address target field
 * Source identifier
 * <p>
 * The Frame is 8 bytes long (64 bits)
 * Created by Emil Edholm on 2016-11-01.
 */
public final class Frame implements Message {
    /**
     * Total size of the Frame in in bytes
     */
    private static final int ADDRESSABLE_POSITION = 12;
    private static final int TAGGED_POSITION = 13;
    private static final int ORIGIN_POSITION = 14;
    private final byte[] content;

    private final short size;
    private final short origin = 0;
    private final short tagged;
    private final short addressable = 1 << ADDRESSABLE_POSITION;
    private final short protocol = 1024;
    private final int source;

    public static class Builder {
        private final short size;
        private short tagged = 0;
        private int source = 0;

        public Builder(int size) {
            if (size < 0 || size > 0xFFFF) {
                throw new IllegalArgumentException("Size does not fit a uint16: Got: " + size);
            }
            this.size = (short) size;
        }

        public Builder tagged(boolean tagged) {
            this.tagged = (short) ((tagged) ? 1 << TAGGED_POSITION : 0);
            return this;
        }

        public Builder source(long source) {
            if (source < 0 || source > 0xFFFFFFFFL) {
                throw new IllegalArgumentException("Source does not fit a uint32: Got: " + source);
            }
            this.source = (int) source;
            return this;
        }

        public Frame build() {
            return new Frame(this);
        }
    }

    private Frame(Builder b) {
        this.size = b.size;
        this.source = b.source;
        this.tagged = b.tagged;


        short otap = (short) (origin | tagged | addressable | protocol);
        ByteBuffer buffer = ByteBuffer.allocate(MessageConstants.SIZE_FRAME);
        buffer.order(MessageConstants.BYTE_ORDER);
        buffer.putShort(size).putShort(otap).putInt(source);
        content = buffer.array();
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    /** Get the total size of the entire message, as specified by the user */
    public int getTotalSize() {
        return this.size;
    }

    /** Get the size of *only* the Frame */
    @Override
    public int size() {
        return content.length;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "origin=" + ByteUtils.toHexString(origin) +
                ", addressable=" + ((addressable > 0) ? "true" : "false") +
                ", protocol=" + protocol +
                ", size=" + ByteUtils.toHexString(size) +
                ", tagged=" + ((tagged > 0) ? "true" : "false") +
                ", source=" + ByteUtils.toHexString(source) +
                ", content=" + ByteUtils.toHexString(content) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        return Arrays.equals(content, frame.content);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }
}
