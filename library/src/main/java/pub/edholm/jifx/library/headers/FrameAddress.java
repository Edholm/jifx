package pub.edholm.jifx.library.headers;

import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

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
    private static final int ACK_REQUIRED_POSITION = 1;
    private static final int RES_REQUIRED_POSITION = 0;
    private final byte[] target;
    private final byte ackRequired;
    private final byte resRequired;
    private final byte sequence;

    private final byte[] content;

    public static final class Builder {
        private byte[] target = new byte[8];
        private byte ackRequired = 0x0;
        private byte resRequired = 0x0;
        private byte sequence = 0x0;

        /**
         * The target device address is 8 bytes long, when using the 6 byte MAC address then left-justify the value
         * and zero-fill the last two bytes. A target device address of all zeroes effectively addresses all devices
         * on the local network. The Frame tagged field must be set accordingly.
         *
         * @param target must be in little-endian order
         * @see Frame.Builder#tagged(boolean)
         */
        public Builder target(byte[] target) {
            this.target = Arrays.copyOf(target, 8);
            return this;
        }

        /**
         * Tell the device to that it must respond with Acknowledgement
         */
        public Builder ackRequired(boolean ackRequired) {
            this.ackRequired = (byte) ((ackRequired) ? 1 << ACK_REQUIRED_POSITION : 0);
            return this;
        }

        /**
         * Tell the device to that it must respond with a State message when appropriate
         */
        public Builder resRequired(boolean resRequired) {
            this.resRequired = (byte) ((resRequired) ? 1 : 0);
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
            this.sequence = (byte) (sequence & 0xff);
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

        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_FRAME_ADDRESS);
        bb.put(target);
        bb.put(new byte[]{0, 0, 0, 0, 0, 0});
        bb.put((byte) (ackRequired | resRequired));
        bb.put((byte) (sequence & 0xff)); // & with 0xff to get unsigned value
        this.content = bb.array();
    }

    public static FrameAddress valueOf(byte[] content) {
        if (content.length < Constants.SIZE_FRAME_ADDRESS) {
            throw new MalformedMessageException(String.format("Content is too small. Got %d, expected at least: %d", content.length, Constants.SIZE_FRAME_ADDRESS));
        }

        ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOf(content, content.length));
        bb.order(Constants.BYTE_ORDER);

        final byte[] target = new byte[8];
        bb.get(target, 0, 8);
        bb.position(bb.position() + 6); // Skip reserved fields
        final byte ackRes = bb.get();
        final byte seq = bb.get();

        final boolean res = (ackRes & 1) > 0;
        final boolean ack = ((ackRes & 2) >> 1) > 0;

        return new FrameAddress.Builder()
                .target(target)
                .sequence(seq)
                .ackRequired(ack)
                .resRequired(res).build();
    }

    public byte[] getTarget() {
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
        return "FrameAddress {" +
                "target: " + ByteUtils.toMacAddressString(Arrays.copyOfRange(target, 0, 6)) + // remove left-padding
                ", ackRequired: " + ((ackRequired > 0) ? "true" : "false") +
                ", resRequired: " + ((resRequired > 0) ? "true" : "false") +
                ", sequence: " + Short.toUnsignedInt(sequence) + String.format(" (0x%x)", sequence) +
                "}";
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
