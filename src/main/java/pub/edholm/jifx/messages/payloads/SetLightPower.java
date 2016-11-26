package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class SetLightPower extends AbstractMessage {
    private final PowerLevel level;
    private final int duration;

    private SetLightPower(Header header, byte[] payloadContent, PowerLevel level, int duration) {
        super(header, payloadContent);
        this.level = level;
        this.duration = duration;
    }

    public static class Builder extends AbstractBuilder<SetLightPower, Builder> {
        private final PowerLevel powerLevel;
        private final int duration;

        public Builder(boolean powerOn, int duration) {
            super(MessageType.SetLightPower, Constants.SIZE_SET_LIGHT_POWER);
            this.powerLevel = new PowerLevel(powerOn);
            this.duration = duration;
        }

        @Override
        public SetLightPower build() {
            ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_SET_LIGHT_POWER);
            bb.order(Constants.BYTE_ORDER);

            bb.put(powerLevel.getContent());
            bb.putInt(duration);
            byte[] content = bb.array();

            return new SetLightPower(buildHeader(), content, powerLevel, duration);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public PowerLevel getLevel() {
        return level;
    }

    public int getDuration() {
        return duration;
    }

    public static SetLightPower valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_SET_LIGHT_POWER;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("SetLightPower", SIZE, content.length);
        }

        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        final Header h = Header.valueOf(content);

        ByteBuffer bb = ByteBuffer.wrap(payload);
        bb.order(Constants.BYTE_ORDER);

        byte[] powerLevelContent = new byte[Constants.SIZE_POWER_LEVEL];
        bb.get(powerLevelContent, 0, powerLevelContent.length);

        final int duration = bb.getInt();

        return new SetLightPower(h, payload, PowerLevel.valueOf(powerLevelContent), duration);
    }

    @Override
    public String toString() {
        return "SetLightPower{" +
                "level=" + level +
                ", duration=" + duration +
                '}';
    }
}
