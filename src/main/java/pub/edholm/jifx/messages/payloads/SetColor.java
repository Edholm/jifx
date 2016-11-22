package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Sent by a client to change the light color.
 * <p>
 * Created by Emil Edholm on 2016-11-05.
 */
public class SetColor extends AbstractMessage implements Message {
    private final Hsbk color;
    private final int duration;

    private SetColor(Header header, byte[] payloadContent, Hsbk color, int duration) {
        super(header, payloadContent);
        this.color = color;
        this.duration = duration;
    }

    public static class Builder extends AbstractBuilder<SetColor, Builder> {
        private int duration = 1024;
        private Hsbk.Builder hsbkBuilder = new Hsbk.Builder();
        private Hsbk optionalHsbk;

        public Builder() {
            super(MessageType.SetColor, Constants.SIZE_SET_COLOR);
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return thisObject();
        }

        public Builder hsbk(Hsbk hsbk) {
            this.optionalHsbk = hsbk;
            return this;
        }

        /**
         * @see Hsbk.Builder#kelvin(int)
         */
        public Builder kelvin(int kelvin) {
            this.hsbkBuilder.kelvin(kelvin);
            return thisObject();
        }

        public Builder brightness(int brightness) {
            hsbkBuilder.brightness(brightness);
            return thisObject();
        }

        public Builder saturation(int saturation) {
            hsbkBuilder.saturation(saturation);
            return thisObject();
        }

        public Builder hue(int hue) {
            hsbkBuilder.hue(hue);
            return thisObject();
        }

        @Override
        public SetColor build() {
            final Header h = buildHeader();
            final Hsbk color = (optionalHsbk != null) ? optionalHsbk : hsbkBuilder.build();

            final ByteBuffer bb = ByteBuffer.allocate(5 + color.size());
            bb.order(Constants.BYTE_ORDER);
            bb.put((byte) 0); // reserved field
            bb.put(color.getContent());
            bb.putInt(duration);

            return new SetColor(h, bb.array(), color, duration);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public Hsbk getColor() {
        return color;
    }

    public int getDuration() {
        return duration;
    }

    public static SetColor valueOf(byte[] contents) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_SET_COLOR;
        if (contents.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("SetColor", SIZE, contents.length);
        }

        final Header h = Header.valueOf(contents);
        final byte[] payload = Arrays.copyOfRange(contents, Constants.SIZE_HEADER, SIZE);

        final ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(Constants.BYTE_ORDER);

        buffer.get(); // Discard reserved field
        final byte[] colorContents = new byte[Constants.SIZE_HSBK];
        buffer.get(colorContents, 0, colorContents.length);

        final Hsbk color = Hsbk.valueOf(colorContents);
        final int duration = buffer.getInt();

        return new SetColor(h, payload, color, duration);
    }

    @Override
    public String toString() {
        return "SetColor{" +
                "color=" + color +
                ", duration=" + duration +
                ", header=" + getHeader() +
                '}';
    }
}
