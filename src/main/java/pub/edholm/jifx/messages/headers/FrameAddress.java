package pub.edholm.jifx.messages.headers;

import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * The Frame Address section contains the following routing information:
 * Target device address
 * Acknowledgement message is required flag
 * State response message is required flag
 * MessagePart sequence number
 * <p>
 * The FrameAddress is 16 byte long (128 bits)
 * Created by Emil Edholm on 2016-11-04.
 */
public final class FrameAddress implements MessagePart {
    private static final int ACK_REQUIRED_POSITION = 9;
    private static final int RES_REQUIRED_POSITION = 8;
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

        /**
         * The target device address is 8 bytes long, when using the 6 byte MAC address then left-justify the value
         * and zero-fill the last two bytes. A target device address of all zeroes effectively addresses all devices
         * on the local network. The Frame tagged field must be set accordingly.
         *
         * @see Frame.Builder#tagged(boolean)
         */
        public Builder target(long target) {
            this.target = target;
            return this;
        }

        /**
         * Tell the device to that it must respond with Acknowledgement
         */
        public Builder ackRequired(boolean ackRequired) {
            this.ackRequired = (ackRequired) ? 1 << ACK_REQUIRED_POSITION : 0;
            return this;
        }

        /**
         * Tell the device to that it must respond with a State message when appropriate
         */
        public Builder resRequired(boolean resRequired) {
            this.resRequired = (resRequired) ? 1 << RES_REQUIRED_POSITION : 0;
            return this;
        }

        /**
         * The sequence number allows the client to provide a unique value, which will be included by the LIFX device
         * in any message that is sent in response to a message sent by the client. This allows the client to
         * distinguish between different messages sent with the same source identifier in the Frame.
         *
         * @see FrameAddress.Builder#ackRequired(boolean)
         * @see FrameAddress.Builder#resRequired(boolean)
         */
        public Builder sequence(int sequence) {
            if (sequence < 0 || sequence > 0xFF) {
                throw new IllegalArgumentException("Sequence larger than is possible. Got: " + ByteUtils.toHexString(sequence));
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

        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_FRAME_ADDRESS);
        bb.order(Constants.BYTE_ORDER);
        bb.putLong(target);
        // & with 0xff to get unsigned value
        bb.putLong(ackRequired | resRequired | (sequence & 0xff));
        this.content = bb.array();
    }

    public static FrameAddress valueOf(byte[] content) {
        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_FRAME_ADDRESS);
        bb.order(Constants.BYTE_ORDER);
        bb.put(content);
        final long target = bb.getLong(0);
        final long ars = bb.getLong(8);
        final int ackReq = (int) ((ars & 0x200) >> ACK_REQUIRED_POSITION);
        final int resReq = (int) ((ars & 0x100) >> RES_REQUIRED_POSITION);
        final int seq = (int) (ars & 0xff);

        return new FrameAddress.Builder()
                .target(target)
                .sequence(seq)
                .ackRequired(ackReq == 1)
                .resRequired(resReq == 1).build();
    }

    public long getTarget() {
        return target;
    }

    public boolean isAckRequired() {
        return ackRequired > 0;
    }

    public boolean isResRequired() {
        return resRequired > 0;
    }

    public byte getSequence() {
        return sequence;
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
