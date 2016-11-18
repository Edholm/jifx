package pub.edholm.jifx.messages.headers;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

/**
 * The header of a Lifx message without the payload part
 * Created by Emil Edholm on 2016-11-09.
 */
public class Header implements MessagePart {
    private final Frame frame;
    private final FrameAddress frameAddress;
    private final ProtocolHeader protocolHeader;

    private final byte[] contents;

    private Header(Builder b) {
        this.frame = b.buildFrame();
        this.frameAddress = b.buildFrameAddress();
        this.protocolHeader = b.buildProtocolHeader();

        ByteBuffer bb = ByteBuffer.allocate(frame.size() + frameAddress.size() + protocolHeader.size());
        bb.order(Constants.BYTE_ORDER);
        bb.put(frame.getContent());
        bb.put(frameAddress.getContent());
        bb.put(protocolHeader.getContent());
        this.contents = bb.array();
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
        throw new AssertionError("Not implemented");
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

    public long getTarget() {
        return frameAddress.getTarget();
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
        return contents;
    }
}
