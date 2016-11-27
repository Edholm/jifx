package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

/**
 * Created by Emil Edholm on 2016-11-21.
 */
public class Get extends AbstractMessage {
    private Get(Header header) {
        super(header, new byte[0]);
    }

    public static class Builder extends AbstractBuilder<Get, Builder> {
        public Builder() {
            this(MessageType.Get);
        }

        public Builder(MessageType getType) {
            super(getType, 0);
            if (!getType.toString().toLowerCase().startsWith("get")) {
                throw new IllegalArgumentException("Expected \"Get*\" message type. Got: " + getType);
            }
            if(getType == MessageType.GetService) {
                this.resRequired(false);
                this.tagged(true);
                this.target(ByteUtils.emptyByteArray(8));
            } else {
                this.resRequired(true);
                this.tagged(false);
            }
        }

        @Override
        public Get build() {
            return new Get(buildHeader());
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static Get valueOf(byte[] content) {
        if (content.length < Constants.SIZE_HEADER) {
            throw MalformedMessageException.createInvalidSize("Get", Constants.SIZE_HEADER, content.length);
        }

        Header h = Header.valueOf(content);
        return new Get(h);
    }

    @Override
    public String toString() {
        return "Get{messageType=" + getHeader().getType() + "}";
    }
}
