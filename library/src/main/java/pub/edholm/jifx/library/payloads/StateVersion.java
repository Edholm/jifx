package pub.edholm.jifx.library.payloads;

import com.fasterxml.jackson.jr.ob.JSON;
import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateVersion extends AbstractMessage {
    private final static URL productsUrl = StateVersion.class.getResource("/products/products.json");
    private List<Object> products;
    private Map<String, Object> productMap, vendorMap;

    private final int vendor, product, version;

    private StateVersion(Header header, byte[] payloadContent, Builder b) {
        super(header, payloadContent);
        this.vendor = b.vendor;
        this.product = b.product;
        this.version = b.version;
    }

    public static class Builder extends AbstractBuilder<StateVersion, Builder> {
        private int vendor = 1, product, version;

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

    private void initializeProducts() {
        if (products == null) {
            if (productsUrl == null) {
                System.err.println("Could not find products.json resource. Have you pulled the git submodule?");
                products = Collections.emptyList();
                return;
            }
            
            try {
                products = JSON.std.listFrom(productsUrl);
            } catch (IOException e) {
                e.printStackTrace();
                products = Collections.emptyList();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findVendor(int vendorId) {
        initializeProducts();
        for (Object vendorObj : products) {
            Map<String, Object> vendor = ((Map<String, Object>) vendorObj);
            int vid = ((int) vendor.get("vid"));
            if (vid == vendorId) {
                return vendor;
            }
        }
        return Collections.emptyMap();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findProduct(int productId) {
        vendorMap = findVendor(vendor);
        List<Object> products = (List<Object>) vendorMap.getOrDefault("products", Collections.emptyList());

        for (Object prodObj : products) {
            Map<String, Object> product = (Map<String, Object>) prodObj;
            int pid = (int) product.get("pid");
            if (productId == pid) {
                return product;
            }
        }

        return Collections.emptyMap();
    }

    public String getVendorName() {
        if (vendorMap == null) {
            vendorMap = findVendor(vendor);
        }
        return (String) vendorMap.getOrDefault("name", "UNKNOWN");
    }

    public int getVendor() {
        return vendor;
    }

    public int getProduct() {
        return product;
    }

    public String getProductName() {
        if (productMap == null) {
            productMap = findProduct(product);
        }
        return (String) productMap.getOrDefault("name", "UNKNOWN");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Boolean> getFeatures() {
        if (productMap == null) {
            productMap = findProduct(product);
        }
        return (Map<String, Boolean>) productMap.getOrDefault("features", Collections.emptyMap());
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "StateVersion{" +
                "vendor: " + Integer.toUnsignedString(vendor) + " (" + getVendorName() + ")" +
                ", product: " + Integer.toUnsignedString(product) + " (" + getProductName() + ")" +
                ", version: " + Integer.toUnsignedString(version) +
                ", features: " + getFeatures().toString() +
                '}';
    }
}
