package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class StateInfrared extends AbstractInfrared {

    public StateInfrared(Header header, short brightness) {
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
        return new StateInfrared.Builder(AbstractInfrared.parse(content)).build();
    }

    @Override
    public String toString() {
        return "StateInfrared{" +
                "brightness=" + getBrightness() +
                '}';
    }
}
