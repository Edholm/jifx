package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateHostInfo extends AbstractMessage {
    private final float signal;
    private final int tx, rx;

    private StateHostInfo(Header header, byte[] payloadContent, Builder builder) {
        super(header, payloadContent);
        this.signal = builder.signal;
        this.tx = builder.tx;
        this.rx = builder.rx;
    }

    public static class Builder extends AbstractBuilder<StateHostInfo, Builder> {
        private float signal = 0f;
        private int tx, rx;

        public Builder() {
            super(MessageType.StateHostInfo, Constants.SIZE_STATE_HOST_INFO);
        }

        public Builder signal(float signal) {
            this.signal = signal;
            return this;
        }

        public Builder tx(int tx) {
            this.tx = tx;
            return this;
        }

        public Builder rx(int rx) {
            this.rx = rx;
            return this;
        }

        @Override
        public StateHostInfo build() {
            ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE_HOST_INFO);
            buffer.putFloat(signal).putInt(tx).putInt(rx).putShort((short) 0);
            return new StateHostInfo(buildHeader(), buffer.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateHostInfo valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE_HOST_INFO;
        if(content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateHostInfo", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);

        final ByteBuffer buffer = ByteBuffer.wrap(content, Constants.SIZE_HEADER, Constants.SIZE_STATE_HOST_INFO);
        buffer.order(Constants.BYTE_ORDER);
        final float signal = buffer.getFloat();
        final int tx = buffer.getInt();
        final int rx = buffer.getInt();
        final Builder b = new Builder().signal(signal).tx(tx).rx(rx);

        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);

        return new StateHostInfo(h, payload, b);
    }

    public float getSignal() {
        return signal;
    }

    public int getTx() {
        return tx;
    }

    public int getRx() {
        return rx;
    }

    @Override
    public String toString() {
        return "StateHostInfo{" +
                "signal: " + signal + "mW" +
                ", tx: " + tx + " bytes" +
                ", rx: " + rx + " bytes" +
                '}';
    }
}
