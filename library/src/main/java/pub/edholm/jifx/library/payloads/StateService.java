package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.datatypes.Port;
import pub.edholm.jifx.library.datatypes.Service;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.apache.commons.lang3.Validate.notNull;

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
    private final Port port;

    private StateService(Header header, byte[] payloadContent, Service s, Port port) {
        super(header, payloadContent);
        this.service = s;
        this.port = port;
    }

    public static class Builder extends AbstractBuilder<StateService, Builder> {
        private final Service service;
        private final Port port;

        public Builder(Service s, Port port) {
            super(MessageType.StateService, SIZE);
            this.service = notNull(s, "Service cannot be null");
            this.port = notNull(port, "Port cannot be null");
        }

        @Override
        public StateService build() {
            ByteBuffer bb = ByteUtils.allocateByteBuffer(SIZE);
            bb.put(service.getValue()).put(port.getContent());
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
        final Port port = Port.of(buffer.getInt());

        final Header h = Header.valueOf(content);
        return new StateService(h, payloadContent, Service.valueOf(service), port);
    }

    public Service getService() {
        return service;
    }

    public Port getPort() {
        return port;
    }

    public boolean isServiceAvailable() {
        return port.isReserved();
    }

    @Override
    public String toString() {
        return "StateService{" +
                "service=" + service +
                ", port=" + port +
                '}';
    }
}
