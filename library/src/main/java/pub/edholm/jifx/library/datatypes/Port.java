package pub.edholm.jifx.library.datatypes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.utils.ByteUtils;

import java.nio.ByteBuffer;

import static org.apache.commons.lang3.Validate.inclusiveBetween;

/**
 * Created by Emil Edholm on 2016-11-29.
 */
public final class Port implements MessagePart, Comparable<Port> {
    private static final Logger logger = LoggerFactory.getLogger(Port.class);

    private final int port;

    private Port(int port) {
        this.port = port;
    }

    public static Port of(int port) {
        inclusiveBetween(0, 0xFFFF, port, "%d is not a valid port number", port);
        if (port <= 1024) {
            logger.info(String.format("Port %d may require elevated privileges depending on use", port));
        }
        return new Port(port);
    }

    public int number() {
        return port;
    }

    /** Returns true if the port number is 0 */
    public boolean isReserved() {
        return port == 0;
    }

    @Override
    public int size() {
        return Integer.BYTES;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer buffer = ByteUtils.allocateByteBuffer(size());
        buffer.putInt(port);
        return buffer.array();
    }

    @Override
    public int compareTo(Port o) {
        return Integer.compare(port, o.port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Port port1 = (Port) o;

        return new EqualsBuilder()
                .append(port, port1.port)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(port)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Port{" + port + '}';
    }

}
