package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

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

    public static SetLightPower valueOf(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);

        byte[] powerLevelContent = new byte[Constants.SIZE_POWER_LEVEL];
        bb.get(powerLevelContent, 0, powerLevelContent.length);

        final int duration = bb.getInt();

        return new SetLightPower.Builder(PowerLevel.valueOf(powerLevelContent).isPoweredOn(), duration).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetLightPower that = (SetLightPower) o;

        return duration == that.duration && level.equals(that.level);
    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        return "SetLightPower{" +
                "level=" + level +
                ", duration=" + duration +
                '}';
    }
}
