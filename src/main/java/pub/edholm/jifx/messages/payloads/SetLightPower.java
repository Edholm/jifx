package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class SetLightPower implements MessagePart {
    private final PowerLevel level;
    private final int duration;

    public SetLightPower(PowerLevel level, int duration) {
        this.level = level;
        this.duration = duration;
    }

    public static SetLightPower valueOf(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);

        byte[] powerLevelContent = new byte[Constants.SIZE_POWER_LEVEL];
        bb.get(powerLevelContent, 0, powerLevelContent.length);

        final int duration = bb.getInt();

        return new SetLightPower(PowerLevel.valueOf(powerLevelContent), duration);
    }

    @Override
    public int size() {
        return Constants.SIZE_SET_LIGHT_POWER;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_SET_LIGHT_POWER);
        bb.order(Constants.BYTE_ORDER);

        bb.put(level.getContent());
        bb.putInt(duration);

        return bb.array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetLightPower that = (SetLightPower) o;

        if (duration != that.duration) return false;
        return level.equals(that.level);

    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + duration;
        return result;
    }
}
