package pub.edholm.jifx.library.datatypes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.nio.ByteBuffer;

import static org.apache.commons.lang3.Validate.inclusiveBetween;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * HSBK (Hue, Saturation, Brightness, Kelvin) is used to represent the color and color temperature of a light.
 * <p>
 * Created by Emil Edholm on 2016-11-04.
 */
public final class Hsbk implements MessagePart {
    private final short hue, saturation, brightness, kelvin;

    public static final class Builder {
        private short hue = (short) 0xffff;
        private short saturation = (short) 0xffff;
        private short brightness = (short) 0xffff;
        private short kelvin = 5000;

        public Builder hue(int hue) {
            this.hue = (short) (hue);
            return this;
        }

        public Builder saturation(int saturation) {
            this.saturation = (short) saturation;
            return this;
        }

        public Builder brightness(int brightness) {
            this.brightness = (short) brightness;
            return this;
        }

        /**
         * Expects a value between 2500 and 9000
         */
        public Builder kelvin(int kelvin) {
            inclusiveBetween(2500, 9000, kelvin, "Kelvin value must be between %d and %d", 2500, 9000);
            this.kelvin = (short) kelvin;
            return this;
        }

        public Hsbk build() {
            return new Hsbk(this);
        }
    }

    private Hsbk(Builder b) {
        notNull(b);
        this.hue = b.hue;
        this.saturation = b.saturation;
        this.brightness = b.brightness;
        this.kelvin = b.kelvin;
    }

    public short getHue() {
        return hue;
    }

    public short getSaturation() {
        return saturation;
    }

    public short getBrightness() {
        return brightness;
    }

    public short getKelvin() {
        return kelvin;
    }

    public static Hsbk valueOf(byte[] content) {
        if (content.length != Constants.SIZE_HSBK) {
            throw MalformedMessageException.createInvalidSize("Hsbk", Constants.SIZE_HSBK, content.length);
        }

        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);
        final short hue = bb.getShort();
        final short saturation = bb.getShort();
        final short brightness = bb.getShort();
        final short kelvin = bb.getShort();

        return new Hsbk.Builder()
                .hue(hue)
                .saturation(saturation)
                .brightness(brightness)
                .kelvin(kelvin).build();
    }

    @Override
    public byte[] getContent() {
        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_HSBK);
        bb.putShort(hue).putShort(saturation).putShort(brightness).putShort(kelvin);
        return bb.array();
    }

    @Override
    public int size() {
        return Constants.SIZE_HSBK;
    }

    @Override
    public String toString() {
        return "Hsbk{" +
                "hue: " + ByteUtils.toHexString(hue) +
                ", saturation: " + ByteUtils.toHexString(saturation) +
                ", brightness: " + ByteUtils.toHexString(brightness) +
                ", kelvin: " + kelvin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Hsbk hsbk = (Hsbk) o;

        return new EqualsBuilder()
                .append(hue, hsbk.hue)
                .append(saturation, hsbk.saturation)
                .append(brightness, hsbk.brightness)
                .append(kelvin, hsbk.kelvin)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(hue)
                .append(saturation)
                .append(brightness)
                .append(kelvin)
                .toHashCode();
    }
}
