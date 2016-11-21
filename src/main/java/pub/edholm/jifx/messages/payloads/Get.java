package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;

/**
 * Created by Emil Edholm on 2016-11-21.
 */
public class Get extends AbstractMessage {
    private Get(Header header, byte[] payloadContent) {
        super(header, payloadContent);
    }

    public static class Builder extends AbstractBuilder<Get, Builder> {
        public Builder(MessageType getType) {
            super(getType, 0);
            if (!getType.toString().toLowerCase().startsWith("get")) {
                throw new IllegalArgumentException("Expected \"Get*\" message type. Got: " + getType);
            }
            this.resRequired(true);
            this.tagged(false);
        }

        @Override
        public Get build() {
            return new Get(buildHeader(), new byte[0]);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Get{messageType:" + getHeader().getType() + "}";
    }
}
