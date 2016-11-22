package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.Service;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Response to GetService message.
 * Provides the device Service and port. If the Service is temporarily unavailable, then the port value will be 0.
 * <p>
 * Created by Emil Edholm on 2016-11-05.
 *
 * @see <a href="https://lan.developer.lifx.com/docs/device-messages#section-stateservice-3">API docs</a>
 */
public class StateService extends AbstractMessage {
    private static final int SIZE = 5;
    private final Service service;
    private final int port;

    private StateService(Header header, byte[] payloadContent, Service s, int port) {
        super(header, payloadContent);
        this.service = s;
        this.port = port;
    }

    public static class Builder extends AbstractBuilder<StateService, Builder> {
        private final Service service;
        private final int port;

        public Builder(Service s, int port) {
            super(MessageType.StateService, SIZE);
            this.service = s;
            this.port = port;
        }

        @Override
        public StateService build() {
            ByteBuffer bb = ByteBuffer.allocate(SIZE);
            bb.order(Constants.BYTE_ORDER);
            bb.put(service.getValue());
            bb.putInt(port);

            return new StateService(buildHeader(), bb.array(), service, port);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateService valueOf(byte[] content) {
        if (content.length < Constants.SIZE_HEADER + SIZE) {
            throw MalformedMessageException.createInvalidSize("StateService", Constants.SIZE_HEADER + SIZE, content.length);
        }

        ByteBuffer buffer = ByteBuffer.wrap(content, Constants.SIZE_HEADER, SIZE);
        buffer.order(Constants.BYTE_ORDER);

        final byte[] payloadContent = Arrays.copyOfRange(content, Constants.SIZE_HEADER, Constants.SIZE_HEADER + SIZE);
        final byte service = buffer.get();
        final int port = buffer.getInt();

        final Header h = Header.valueOf(content);
        return new StateService(h, payloadContent, Service.valueOf(service), port);
    }

    public Service getService() {
        return service;
    }

    public int getPort() {
        return port;
    }

    public boolean isServiceAvailable() {
        return port == 0;
    }

    @Override
    public String toString() {
        return "StateService{" +
                "service=" + service +
                ", port=" + port +
                ", payload=" + ByteUtils.toHexString(getPayload()) +
                ", header=" + getHeader() +
                '}';
    }
}
