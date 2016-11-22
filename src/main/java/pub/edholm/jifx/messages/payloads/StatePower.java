package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class StatePower extends AbstractMessage {
    private final PowerLevel level;

    private StatePower(Header header, byte[] payloadContent, PowerLevel level) {
        super(header, payloadContent);
        this.level = level;
    }

    public static class Builder extends AbstractBuilder<StatePower, Builder> {
        private PowerLevel level;

        public Builder(boolean isDevicePower, boolean poweredOn) {
            super((isDevicePower) ? MessageType.StateDevicePower : MessageType.StateLightPower, Constants.SIZE_POWER_LEVEL);
            this.level = new PowerLevel(poweredOn);
        }

        @Override
        public StatePower build() {
            return new StatePower(buildHeader(), level.getContent(), level);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StatePower valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_POWER_LEVEL;
        if (content.length != SIZE) {
            throw MalformedMessageException.createInvalidSize("StatePower", SIZE, content.length);
        }

        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        final Header h = Header.valueOf(content);

        final PowerLevel level = PowerLevel.valueOf(payload);
        return new StatePower(h, payload, level);
    }

    public PowerLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "StatePower{" +
                "level=" + level +
                ", header=" + getHeader() +
                '}';
    }
}
