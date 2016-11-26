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
public class StateWifiFirmware extends AbstractMessage {
    private final long build;
    private final int version;

    private StateWifiFirmware(Header header, byte[] payloadContent, Builder b) {
        super(header, payloadContent);
        this.build = b.build;
        this.version = b.version;
    }

    public static class Builder extends AbstractBuilder<StateWifiFirmware, Builder> {
        private long build;
        private int version;

        public Builder() {
            super(MessageType.StateWifiFirmware, Constants.SIZE_STATE_WIFI_FIRMWARE);
        }

        public Builder build(long build) {
            this.build = build;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        @Override
        public StateWifiFirmware build() {
            ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE_WIFI_FIRMWARE);
            buffer.putLong(build);
            buffer.putLong(0L);
            buffer.putInt(version);
            return new StateWifiFirmware(buildHeader(), buffer.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateWifiFirmware valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE_WIFI_FIRMWARE;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateWifiFirmware", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);

        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(Constants.BYTE_ORDER);

        final Builder b = new Builder();
        b.build = buffer.getLong();
        b.version = buffer.getInt(buffer.position() + Long.BYTES);
        return new StateWifiFirmware(h, payload, b);
    }

    public long getBuild() {
        return build;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "StateWifiFirmware{" +
                "build: " + ByteUtils.toHexString(build) +
                ", version: " + ByteUtils.toHexString(version) +
                '}';
    }
}
