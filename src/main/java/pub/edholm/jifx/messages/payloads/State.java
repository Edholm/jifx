package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class State extends AbstractMessage {
    private final Hsbk color;
    private final PowerLevel power;
    private final String label;

    private State(Header header, byte[] payloadContent, Hsbk color, PowerLevel power, String label) {
        super(header, payloadContent);
        this.color = color;
        this.power = power;
        this.label = label;
    }

    public static class Builder extends AbstractBuilder<State, Builder> {
        private final Hsbk color;
        private final PowerLevel power;
        private final String label;

        public Builder(Hsbk color, PowerLevel power, String label) {
            super(MessageType.State, Constants.SIZE_STATE);
            this.color = color;
            this.power = power;
            this.label = label;
        }

        /* TODO: delegate Hsbk builder if needed */

        @Override
        public State build() {
            ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_STATE);
            bb.order(Constants.BYTE_ORDER);
            bb.put(color.getContent());
            bb.putShort((short) 0);
            bb.put(power.getContent());

            ByteBuffer labelBuffer = ByteBuffer.allocate(32);
            labelBuffer.order(Constants.BYTE_ORDER);
            labelBuffer.put(label.getBytes(StandardCharsets.UTF_8));

            bb.put(labelBuffer.array());
            bb.putLong(0);

            return new State(buildHeader(), bb.array(), color, power, label);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static State valueOf(byte[] content) throws UnsupportedEncodingException {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(Constants.BYTE_ORDER);

        byte[] hsbkContents = new byte[Constants.SIZE_HSBK];
        bb.get(hsbkContents, 0, Constants.SIZE_HSBK);

        bb.getShort();
        byte[] powerLevelContent = new byte[Constants.SIZE_POWER_LEVEL];
        bb.get(powerLevelContent, 0, Constants.SIZE_POWER_LEVEL);

        byte[] labelContent = new byte[32];
        bb.get(labelContent, 0, 32);

        Hsbk hsbk = Hsbk.valueOf(hsbkContents);
        PowerLevel powerLevel = PowerLevel.valueOf(powerLevelContent);
        String label = new String(Arrays.copyOf(labelContent, indexOf(labelContent, (byte) 0)), "UTF-8");

        return new State.Builder(hsbk, powerLevel, label).build();
    }

    private static int indexOf(byte[] content, byte value) {
        for (int i = 0; i < content.length; i++) {
            if (content[i] == value) {
                return i;
            }
        }
        return content.length;
    }

    public Hsbk getColor() {
        return color;
    }

    public PowerLevel getPower() {
        return power;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "State{" +
                "color=" + color +
                ", power=" + power +
                ", label='" + label + '\'' +
                ", content=" + ByteUtils.toHexString(getPayload()) +
                ", header=" + getHeader() +
                '}';
    }
}
