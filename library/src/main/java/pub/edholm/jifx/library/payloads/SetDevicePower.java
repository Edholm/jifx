package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.datatypes.PowerLevel;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.headers.Header;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class SetDevicePower extends AbstractMessage {
    private final PowerLevel level;

    private SetDevicePower(Header header, PowerLevel level) {
        super(header, level.getContent());
        this.level = level;
    }

    public static class Builder extends AbstractBuilder<SetDevicePower, Builder> {
        private PowerLevel level = new PowerLevel(true);

        public Builder() {
            super(MessageType.SetDevicePower, Constants.SIZE_POWER_LEVEL);
        }

        /**
         * Defaults to powered on
         */
        public Builder level(boolean poweredOn) {
            this.level = new PowerLevel(poweredOn);
            return this;
        }

        @Override
        public SetDevicePower build() {
            return new SetDevicePower(buildHeader(), level);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static SetDevicePower valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_POWER_LEVEL;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("SetDevicePower", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        final PowerLevel level = PowerLevel.valueOf(payload);

        return new SetDevicePower(h, level);
    }

    public PowerLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "SetDevicePower{" +
                "level=" + level +
                '}';
    }
}
