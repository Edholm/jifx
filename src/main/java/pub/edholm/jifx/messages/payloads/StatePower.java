package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.utils.Constants;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class StatePower implements MessagePart {
    private final PowerLevel level;

    public StatePower(PowerLevel level) {
        this.level = level;
    }

    public static StatePower valueOf(byte[] content) {
        final PowerLevel level = PowerLevel.valueOf(content);
        return new StatePower(level);
    }

    public PowerLevel getLevel() {
        return level;
    }

    @Override
    public int size() {
        return Constants.SIZE_POWER_LEVEL;
    }

    @Override
    public byte[] getContent() {
        return level.getContent();
    }

    @Override
    public String toString() {
        return "StatePower{" +
                "level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatePower that = (StatePower) o;

        return level.equals(that.level);

    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
