package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class StateInfrared extends AbstractInfrared {

    private StateInfrared(Header header, short brightness) {
        super(header, brightness);
    }

    public static class Builder extends AbstractBuilder<StateInfrared, Builder> {
        private final short brightness;

        public Builder(short brightness) {
            super(MessageType.StateInfrared, Constants.SIZE_INFRARED);
            this.brightness = brightness;
        }

        @Override
        public StateInfrared build() {
            return new StateInfrared(buildHeader(), brightness);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateInfrared valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_INFRARED;
        if (content.length != SIZE) {
            throw MalformedMessageException.createInvalidSize("StateInfrared", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        final short brightness = AbstractInfrared.parse(payload);

        return new StateInfrared(h, brightness);
    }

    @Override
    public String toString() {
        return "StateInfrared{" +
                "brightness=" + getBrightness() +
                ", header=" + getHeader() +
                '}';
    }
}
