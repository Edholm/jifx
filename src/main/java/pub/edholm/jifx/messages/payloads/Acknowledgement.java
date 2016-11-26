package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;

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
                "from: " + ByteUtils.toMacAddressString(Arrays.copyOfRange(getHeader().getTarget(), 0, 6)) +
                "}";
    }
}
