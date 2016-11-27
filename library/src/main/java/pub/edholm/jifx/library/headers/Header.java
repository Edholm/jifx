package pub.edholm.jifx.library.headers;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * The header of a Lifx message without the payload part
 * Created by Emil Edholm on 2016-11-09.
 */
public class Header implements MessagePart {
    private final Frame frame;
    private final FrameAddress frameAddress;
    private final ProtocolHeader protocolHeader;

    private final byte[] contents;

    private Header(Frame f, FrameAddress fa, ProtocolHeader ph) {
        this.frame = f;
        this.frameAddress = fa;
        this.protocolHeader = ph;

        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_HEADER);
        bb.put(frame.getContent());
        bb.put(frameAddress.getContent());
        bb.put(protocolHeader.getContent());
        this.contents = bb.array();
    }

    private Header(Builder b) {
        this(b.buildFrame(), b.buildFrameAddress(), b.buildProtocolHeader());
    }

    public static class Builder extends AbstractBuilder<Header, Builder> {
        public Builder(MessageType messageType, int payloadSize) {
            super(messageType, payloadSize);
        }

        @Override
        public Header build() {
            return new Header(this);
        }

        private Frame buildFrame() {
            return new Frame.Builder()
                    .source(source)
                    .tagged(tagged)
                    .payloadSize(payloadSize).build();
        }

        private FrameAddress buildFrameAddress() {
            return new FrameAddress.Builder()
                    .ackRequired(ackRequired)
                    .resRequired(resRequired)
                    .sequence(sequence)
                    .target(target).build();
        }

        private ProtocolHeader buildProtocolHeader() {
            return new ProtocolHeader(messageType);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static Header valueOf(byte[] content) {
        if (content.length < Constants.SIZE_HEADER) {
            throw new MalformedMessageException(String.format("Missing info. Got: %d bytes, expected: %d", content.length, Constants.SIZE_HEADER));
        }

        final ByteBuffer buffer = ByteBuffer.wrap(content);
        buffer.order(Constants.BYTE_ORDER);

        final byte[] frameContents = new byte[Constants.SIZE_FRAME];
        final byte[] frameAddressContents = new byte[Constants.SIZE_FRAME_ADDRESS];
        final byte[] protocolHeaderContents = new byte[Constants.SIZE_PROTOCOL_HEADER];

        buffer.get(frameContents, 0, frameContents.length);
        buffer.get(frameAddressContents, 0, frameAddressContents.length);
        buffer.get(protocolHeaderContents, 0, protocolHeaderContents.length);

        final Frame f = Frame.valueOf(frameContents);
        final FrameAddress fa = FrameAddress.valueOf(frameAddressContents);
        final ProtocolHeader ph = ProtocolHeader.valueOf(protocolHeaderContents);

        return new Header(f, fa, ph);
    }

    public int getTotalSize() {
        return frame.getTotalSize();
    }

    public short getOrigin() {
        return frame.getOrigin();
    }

    public boolean isTagged() {
        return frame.isTagged();
    }

    public short getAddressable() {
        return frame.getAddressable();
    }

    public short getProtocol() {
        return frame.getProtocol();
    }

    public int getSource() {
        return frame.getSource();
    }

    public byte[] getTarget() {
        return Arrays.copyOf(frameAddress.getTarget(), frameAddress.getTarget().length);
    }

    public boolean isAckRequired() {
        return frameAddress.isAckRequired();
    }

    public boolean isResRequired() {
        return frameAddress.isResRequired();
    }

    public byte getSequence() {
        return frameAddress.getSequence();
    }

    public MessageType getType() {
        return protocolHeader.getType();
    }

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public byte[] getContent() {
        return Arrays.copyOf(contents, contents.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Header header = (Header) o;

        return frame.equals(header.frame)
                && frameAddress.equals(header.frameAddress)
                && protocolHeader.equals(header.protocolHeader);
    }

    @Override
    public int hashCode() {
        int result = frame.hashCode();
        result = 31 * result + frameAddress.hashCode();
        result = 31 * result + protocolHeader.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Header {" +
                "" + frame +
                ", " + frameAddress +
                ", " + protocolHeader +
                "}";
    }
}
