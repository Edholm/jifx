package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateVersion extends AbstractMessage {
    private final int vendor, product, version;

    private StateVersion(Header header, byte[] payloadContent, Builder b) {
        super(header, payloadContent);
        this.vendor = b.vendor;
        this.product = b.product;
        this.version = b.version;
    }

    public static class Builder extends AbstractBuilder<StateVersion, Builder> {
        private int vendor, product, version;

        public Builder() {
            super(MessageType.StateVersion, Constants.SIZE_STATE_VERSION);
        }

        public Builder vendor(int vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder product(int product) {
            this.product = product;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        @Override
        public StateVersion build() {
            ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE_VERSION);
            buffer.putInt(vendor).putInt(product).putInt(version);
            return new StateVersion(buildHeader(), buffer.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateVersion valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE_VERSION;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateVersion", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);

        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(Constants.BYTE_ORDER);

        Builder b = new Builder();
        b.vendor = buffer.getInt();
        b.product = buffer.getInt();
        b.version = buffer.getInt();
        return new StateVersion(h, payload, b);
    }

    public int getVendor() {
        return vendor;
    }

    public int getProduct() {
        return product;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "StateVersion{" +
                "vendor: " + Integer.toUnsignedString(vendor) + " (" + ByteUtils.toHexString(vendor) + ")" +
                ", product: " + Integer.toUnsignedString(product) + " (" + ByteUtils.toHexString(product) + ")" +
                ", version: " + Integer.toUnsignedString(version) + " (" + ByteUtils.toHexString(version) + ")" +
                '}';
    }
}
