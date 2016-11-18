package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class SetInfrared extends AbstractInfrared {

    private SetInfrared(Header header, short brightness) {
        super(header, brightness);
    }

    public static class Builder extends AbstractBuilder<SetInfrared, Builder> {
        private final short brightness;

        public Builder(short brightness) {
            super(MessageType.SetInfrared, Constants.SIZE_INFRARED);
            this.brightness = brightness;
        }

        @Override
        public SetInfrared build() {
            return new SetInfrared(buildHeader(), brightness);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static SetInfrared valueOf(byte[] content) {
        return new SetInfrared.Builder(AbstractInfrared.parse(content)).build();
    }

    @Override
    public String toString() {
        return "SetInfrared{" +
                "brightness=" + getBrightness() +
                '}';
    }
}
