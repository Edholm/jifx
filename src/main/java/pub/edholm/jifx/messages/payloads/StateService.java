package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.Service;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;

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

    private StateService(Header header, byte[] payloadContent, Builder b) {
        super(header, payloadContent);
        this.service = b.service;
        this.port = b.port;
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

            return new StateService(buildHeader(), bb.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateService valueOf(byte[] content) {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        buffer.order(Constants.BYTE_ORDER);
        buffer.put(content);

        final byte service = buffer.get(0);
        final int port = buffer.getInt(1);

        return new StateService.Builder(Service.valueOf(service), port).build();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateService that = (StateService) o;
        return port == that.port && service == that.service;

    }

    @Override
    public int hashCode() {
        int result = service.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "StateService{" +
                "service=" + service +
                ", port=" + port +
                ", content=" + ByteUtils.toHexString(getContent()) +
                '}';
    }
}
