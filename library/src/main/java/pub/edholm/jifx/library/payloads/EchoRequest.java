package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class EchoRequest extends AbstractMessage {
    private EchoRequest(Header header, byte[] payloadContent) {
        super(header, payloadContent);
    }

    public static class Builder extends AbstractBuilder<EchoRequest, Builder> {
        private byte[] payload;

        public Builder() {
            super(MessageType.EchoRequest, Constants.SIZE_ECHO);
            resRequired(true);
        }

        public Builder payload(byte[] payload) {
            this.payload = Arrays.copyOf(payload, Constants.SIZE_ECHO);
            return this;
        }

        public Builder randomPayload() {
            this.payload = new byte[Constants.SIZE_ECHO];
            final Random r = new Random();
            r.nextBytes(payload);
            return this;
        }

        @Override
        public EchoRequest build() {
            return new EchoRequest(buildHeader(), payload);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static EchoRequest valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_ECHO;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("EchoRequest", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        return new EchoRequest(h, payload);
    }

    @Override
    public String toString() {
        return "EchoRequest{payload: " + ByteUtils.toHexString(getPayload()) + "}";
    }
}
