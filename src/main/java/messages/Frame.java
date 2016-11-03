package messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Details the first part of the header: Frame.
 * <br />
 * The Frame is 8 byte (64 bits) in length.
 * <br />
 * Created by Emil Edholm on 2016-11-01.
 */
public final class Frame {
    /**
     * Total size of the Frame in in bytes
     */
    private static final int TOTAL_SIZE = 8;
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
            if(size < 0 || size > 0xFFFF) {
                throw new IllegalArgumentException("Size does not fit a uint16: Got: " + size);
            }
            this.size = (short) size;
        }

        public Builder tagged(boolean tagged) {
            this.tagged = (short) ((tagged) ? 1 << TAGGED_POSITION : 0);
            return this;
        }

        public Builder source(long source) {
            if (source < 0 || source >  0xFFFFFFFFL) {
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
        ByteBuffer buffer = ByteBuffer.allocate(TOTAL_SIZE);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(size).putShort(otap).putInt(source);
        content = buffer.array();
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String toString() {
        String contentHex = "";
        for(byte b : content) {
            contentHex += String.format("0x%x ", b);
        }
        return "Frame{" +
                "origin=" + origin +
                ", addressable=" + addressable +
                ", protocol=" + protocol +
                ", size=" + size +
                ", tagged=" + tagged +
                ", source=" + source +
                ", content=" + contentHex +
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
