package pub.edholm.jifx.messages.headers;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

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
public final class Frame implements MessagePart {
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
        private static final short TOTAL_HEADER_SIZE = Constants.SIZE_FRAME + Constants.SIZE_FRAME_ADDRESS + Constants.SIZE_PROTOCOL_HEADER;
        private short size = TOTAL_HEADER_SIZE;
        private short tagged = 0;
        private int source = 0;

        /**
         * The total size of the entire message going out the wire, including the payload
         * <br />
         * Defaults to combined size of all the header messages: 36 bytes.
         */
        public Builder size(int size) {
            if (size < 0 || size > 0xFFFF) {
                throw new IllegalArgumentException("Invalid size: Got: " + size);
            }
            this.size = (short) size;
            return this;
        }

        /**
         * Sets the size as the header size + the supplied payload size
         */
        public Builder payloadSize(int size) {
            if (size < 0 || size > 0xFFDB) {
                throw new IllegalArgumentException("Invalid size: Got: " + size);
            }
            this.size = (short) (TOTAL_HEADER_SIZE + size);
            return this;
        }

        /**
         * Indicates whether the Frame Address target field is being used to address an individual device or all devices
         * <br />
         * Set to true when broadcasting, else false.
         */
        public Builder tagged(boolean tagged) {
            this.tagged = (short) ((tagged) ? 1 << TAGGED_POSITION : 0);
            return this;
        }

        /**
         * The source identifier allows each client to provide an unique value, which will be included by the LIFX
         * device in any message that is sent in response to a message sent by the client. If the source identifier
         * is a non-zero value, then the LIFX device will send a unicast message to the IP address and port of the
         * client that sent the originating message. If the source identifier is a zero value, then the LIFX device
         * may send a broadcast message that can be received by all clients on the same sub-net.
         *
         * @see FrameAddress.Builder#ackRequired(boolean)
         * @see FrameAddress.Builder#resRequired(boolean)
         */
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
        ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_FRAME);
        buffer.putShort(size).putShort(otap).putInt(source);
        content = buffer.array();
    }

    public static Frame valueOf(byte[] contents) {
        if (contents.length < Constants.SIZE_FRAME) {
            throw new MalformedMessageException(String.format("Content is too small. Got %d, expected: %d", contents.length, Constants.SIZE_FRAME));
        }

        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_FRAME);
        bb.put(contents);

        final short size = bb.getShort(0);
        final short tagged = (short) ((bb.getShort(2) & 0x2000) >> TAGGED_POSITION);
        final int source = bb.getInt(4);

        return new Frame.Builder().size(size).source(source).tagged((tagged == 1)).build();
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    /**
     * Get the total size of the entire message, as specified by the user
     */
    public int getTotalSize() {
        return this.size;
    }

    public short getOrigin() {
        return origin;
    }

    public boolean isTagged() {
        return tagged > 0;
    }

    public short getAddressable() {
        return addressable;
    }

    public short getProtocol() {
        return protocol;
    }

    public int getSource() {
        return source;
    }

    /**
     * Get the size of *only* the Frame
     */
    @Override
    public int size() {
        return content.length;
    }

    @Override
    public String toString() {
        return "Frame {" +
                "size: " + Short.toUnsignedInt(size) + " (" + ByteUtils.toHexString(size) + ")" +
                ", origin: " + origin +
                ", tagged: " + ((tagged > 0) ? "true" : "false") +
                ", addressable: " + ((addressable > 0) ? "true" : "false") +
                ", protocol: " + protocol +
                ", source: " + ByteUtils.toHexString(source) +
                "}";
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
