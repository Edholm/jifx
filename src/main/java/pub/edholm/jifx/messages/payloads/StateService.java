package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.messages.datatypes.Service;
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
public class StateService implements MessagePart {
    private static final int SIZE = 5;
    private final Service service;
    private final int port;

    public StateService(Service s, int port) {
        this.service = s;
        this.port = port;
    }

    public static StateService valueOf(byte[] content) {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        buffer.order(Constants.BYTE_ORDER);
        buffer.put(content);

        final byte service = buffer.get(0);
        final int port = buffer.getInt(1);

        return new StateService(Service.valueOf(service), port);
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
    public int size() {
        return SIZE;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer bb = ByteBuffer.allocate(SIZE);
        bb.order(Constants.BYTE_ORDER);
        bb.put(service.getValue());
        bb.putInt(port);
        return bb.array();
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
