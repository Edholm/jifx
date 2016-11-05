package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.utils.Constants;
import pub.edholm.jifx.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * HSBK (Hue, Saturation, Brightness, Kelvin) is used to represent the color and color temperature of a light.
 * <p>
 * Created by Emil Edholm on 2016-11-04.
 */
public final class Hsbk implements Message {
    private final short hue, saturation, brightness, kelvin;

    public static final class Builder {
        private short hue = (short) 0xffff;
        private short saturation = (short) 0xffff;
        private short brightness = (short) 0xffff;
        private short kelvin = 5000;

        public Builder hue(int hue) {
            checkValueBounds(hue);
            this.hue = (short) hue;
            return this;
        }

        public Builder saturation(int saturation) {
            checkValueBounds(saturation);
            this.saturation = (short) saturation;
            return this;
        }

        public Builder brightness(int brightness) {
            checkValueBounds(brightness);
            this.brightness = (short) brightness;
            return this;
        }

        public Builder kelvin(int kelvin) {
            if (kelvin < 2500 || kelvin > 9000) {
                throw new IllegalArgumentException("Illegal kelvin value. Got: " + kelvin);
            }
            this.kelvin = (short) kelvin;
            return this;
        }

        public Hsbk build() {
            return new Hsbk(this);
        }

        private void checkValueBounds(int value) {
            if (value < 0 || value > 0xFFFF) {
                throw new IllegalArgumentException("Invalid value. Got: " + value);
            }
        }
    }

    private Hsbk(Builder b) {
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
        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_HSBK);
        bb.order(Constants.BYTE_ORDER);
        bb.putShort(hue);
        bb.putShort(saturation);
        bb.putShort(brightness);
        bb.putShort(kelvin);
        return bb.array();
    }

    @Override
    public int size() {
        return Constants.SIZE_HSBK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hsbk hsbk = (Hsbk) o;

        if (hue != hsbk.hue) return false;
        if (saturation != hsbk.saturation) return false;
        if (brightness != hsbk.brightness) return false;
        return kelvin == hsbk.kelvin;

    }

    @Override
    public int hashCode() {
        int result = (int) hue;
        result = 31 * result + (int) saturation;
        result = 31 * result + (int) brightness;
        result = 31 * result + (int) kelvin;
        return result;
    }

    @Override
    public String toString() {
        return "Hsbk{" +
                "hue=" + ByteUtils.toHexString(hue) +
                ", saturation=" + ByteUtils.toHexString(saturation) +
                ", brightness=" + ByteUtils.toHexString(brightness) +
                ", kelvin=" + kelvin +
                ", content=" + ByteUtils.toHexString(getContent()) +
                '}';
    }
}
