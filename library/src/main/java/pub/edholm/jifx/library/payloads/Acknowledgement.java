package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class Acknowledgement extends AbstractMessage {
    private Acknowledgement(Header header) {
        super(header, new byte[0]);
    }

    public static class Builder extends AbstractBuilder<Acknowledgement, Builder> {

        public Builder() {
            super(MessageType.Acknowledgement, 0);
        }

        @Override
        public Acknowledgement build() {
            return new Acknowledgement(buildHeader());
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static Acknowledgement valueOf(byte[] content) {
        final Header h = Header.valueOf(content);
        if (h.getType() != MessageType.Acknowledgement){
            throw new MalformedMessageException("Supplied contents is not a valid Acknowledgement message");
        }
        return new Acknowledgement(h);
    }

    /** Get the device mac address that sent the acknowledgement */
    public byte[] getSenderMacAddress() {
        return getHeader().getTarget();
    }

    @Override
    public String toString() {
        return "Acknowledgement {"+
                "of: " + ByteUtils.toMacAddressString(Arrays.copyOfRange(getHeader().getTarget(), 0, 6)) +
                "}";
    }
}
