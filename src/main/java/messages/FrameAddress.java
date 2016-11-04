package messages;

import utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * The Frame Address section contains the following routing information:
 * Target device address
 * Acknowledgement message is required flag
 * State response message is required flag
 * Message sequence number
 * <p>
 * The FrameAddress is 16 byte long (128 bits)
 * Created by Emil Edholm on 2016-11-04.
 */
public final class FrameAddress {
    public static final int ACK_REQUIRED_POSITION = 9;
    public static final int RES_REQUIRED_POSITION = 8;
    private final long target;
    private final int ackRequired;
    private final int resRequired;
    private final byte sequence;

    private final byte[] content;

    public static final class Builder {
        private long target = 0x0;
        private int ackRequired = 0x0;
        private int resRequired = 0x0;
        private byte sequence = 0x0;

        public Builder target(long target) {
            this.target = target;
            return this;
        }

        public Builder ackRequired(boolean ackRequired) {
            this.ackRequired = (ackRequired) ? 1 << ACK_REQUIRED_POSITION : 0;
            return this;
        }

        public Builder resRequired(boolean resRequired) {
            this.resRequired = (resRequired) ? 1 << RES_REQUIRED_POSITION : 0;
            return this;
        }

        public Builder sequence(int sequence) {
            if (sequence < 0 || sequence > 0xFF) {
                throw new IllegalArgumentException("Sequence larger than is possible. Got: " + sequence);
            }
            this.sequence = (byte) sequence;
            return this;
        }

        public FrameAddress build() {
            return new FrameAddress(this);
        }
    }

    private FrameAddress(Builder b) {
        this.target = b.target;
        this.ackRequired = b.ackRequired;
        this.resRequired = b.resRequired;
        this.sequence = b.sequence;

        ByteBuffer bb = ByteBuffer.allocate(MessageConstants.SIZE_FRAME_ADDRESS);
        bb.order(MessageConstants.BYTE_ORDER);
        bb.putLong(target);
        // & with 0xff to get unsigned value
        bb.putLong(ackRequired | resRequired | (sequence & 0xff));
        this.content = bb.array();
    }

    public byte[] getContent() {
        return content;
    }

    public int size() {
        return content.length;
    }

    @Override
    public String toString() {
        return "FrameAddress{" +
                "target=" + String.format("0x%x", target) +
                ", ackRequired=" + ((ackRequired > 0) ? "true" : "false") +
                ", resRequired=" + ((resRequired > 0) ? "true" : "false") +
                ", sequence=" + String.format("0x%x", sequence) +
                ", content=" + ByteUtils.toHexString(content) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrameAddress that = (FrameAddress) o;

        return Arrays.equals(content, that.content);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }
}
