package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class EchoResponse extends AbstractMessage {
    private EchoResponse(Header header, byte[] payloadContent) {
        super(header, payloadContent);
    }

    public static class Builder extends AbstractBuilder<EchoResponse, Builder> {
        private byte[] payload;

        public Builder() {
            super(MessageType.EchoResponse, Constants.SIZE_ECHO);
        }

        public Builder payload(byte[] payload) {
            this.payload = Arrays.copyOf(payload, Constants.SIZE_ECHO);
            return this;
        }

        @Override
        public EchoResponse build() {
            return new EchoResponse(buildHeader(), payload);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static EchoResponse valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_ECHO;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("EchoResponse", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        return new EchoResponse(h, payload);
    }

    /**
     * True if the supplied EchoRequest has the same payload as this EchoResponse
     */
    public boolean hasSamePayload(EchoRequest request) {
        return Arrays.equals(getPayload(), request.getPayload());
    }

    @Override
    public String toString() {
        return "EchoResponse{payload: " + ByteUtils.toHexString(getPayload()) + "}";
    }
}
