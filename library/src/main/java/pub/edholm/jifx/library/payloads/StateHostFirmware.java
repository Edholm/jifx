package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.datatypes.Time;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateHostFirmware extends AbstractMessage {
    private final Time buildTime;
    private final int version;

    private StateHostFirmware(Header header, byte[] payloadContent, Builder builder) {
        super(header, payloadContent);
        this.buildTime = builder.buildTime;
        this.version = builder.version;
    }

    public static class Builder extends AbstractBuilder<StateHostFirmware, Builder> {
        private Time buildTime;
        private int version;

        public Builder() {
            super(MessageType.StateHostFirmware, Constants.SIZE_STATE_HOST_FIRMWARE);
        }

        public Builder buildTime(Time buildTime) {
            this.buildTime = buildTime;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        @Override
        public StateHostFirmware build() {
            ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE_HOST_FIRMWARE);
            buffer.put(buildTime.getContent()).putLong(0L).putInt(version);
            return new StateHostFirmware(buildHeader(), buffer.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateHostFirmware valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE_HOST_FIRMWARE;
        if(content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateHostFirmware", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);

        final ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(Constants.BYTE_ORDER);
        final Time buildTime = Time.valueOf(payload);
        buffer.position(buffer.position() + 16);
        final int version = buffer.getInt();

        final Builder b = new Builder().version(version).buildTime(buildTime);
        return new StateHostFirmware(h, payload, b);
    }

    public Time getBuildTime() {
        return buildTime;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "StateHostFirmware{" +
                "build: " + buildTime +
                ", version: " + ByteUtils.toHexString(version) +
                '}';
    }
}
