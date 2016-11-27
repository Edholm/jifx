package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.datatypes.PowerLevel;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.datatypes.Hsbk;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.datatypes.Label;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class State extends AbstractMessage {
    private final Hsbk color;
    private final PowerLevel power;
    private final Label label;

    private State(Header header, byte[] payloadContent, Hsbk color, PowerLevel power, Label label) {
        super(header, payloadContent);
        this.color = color;
        this.power = power;
        this.label = label;
    }

    public static class Builder extends AbstractBuilder<State, Builder> {
        private final Hsbk color;
        private final PowerLevel power;
        private final Label label;

        public Builder(Hsbk color, PowerLevel power, Label label) {
            super(MessageType.State, Constants.SIZE_STATE);
            this.color = color;
            this.power = power;
            this.label = label;
        }

        /* TODO: delegate Hsbk builder if needed */

        @Override
        public State build() {
            ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE);
            bb.put(color.getContent()).putShort((short) 0).put(power.getContent());
            bb.put(label.getContent()).putLong(0L);

            return new State(buildHeader(), bb.array(), color, power, label);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static State valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("State", SIZE, content.length);
        }

        ByteBuffer bb = ByteBuffer.wrap(content, Constants.SIZE_HEADER, Constants.SIZE_STATE);
        bb.order(Constants.BYTE_ORDER);

        final Header h = Header.valueOf(content);
        final byte[] payloadContent = Arrays.copyOfRange(content, Constants.SIZE_HEADER, content.length);

        final byte[] hsbkContents = new byte[Constants.SIZE_HSBK];
        bb.get(hsbkContents, 0, Constants.SIZE_HSBK);

        bb.getShort();
        final byte[] powerLevelContent = new byte[Constants.SIZE_POWER_LEVEL];
        bb.get(powerLevelContent, 0, Constants.SIZE_POWER_LEVEL);

        final byte[] labelContent = new byte[Constants.SIZE_LABEL];
        bb.get(labelContent, 0, Constants.SIZE_LABEL);

        final Hsbk hsbk = Hsbk.valueOf(hsbkContents);
        final PowerLevel powerLevel = PowerLevel.valueOf(powerLevelContent);
        final Label label = Label.valueOf(labelContent);

        return new State(h, payloadContent, hsbk, powerLevel, label);
    }

    public Hsbk getColor() {
        return color;
    }

    public PowerLevel getPower() {
        return power;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "State {" +
                "color: " + color +
                ", power: " + power +
                ", label: " + label +
                "}";
    }
}
