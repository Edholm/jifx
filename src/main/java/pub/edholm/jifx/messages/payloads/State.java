package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class State implements Message {
    private final Hsbk color;
    private final PowerLevel power;
    private final String label;
    private final byte[] content;

    public State(Hsbk color, PowerLevel power, String label) throws UnsupportedEncodingException {
        this.color = color;
        this.power = power;
        this.label = label;

        ByteBuffer bb = ByteBuffer.allocate(Constants.SIZE_STATE);
        bb.order(Constants.BYTE_ORDER);
        bb.put(color.getContent());
        bb.putShort((short) 0);
        bb.put(power.getContent());

        ByteBuffer labelBuffer = ByteBuffer.allocate(32);
        labelBuffer.order(Constants.BYTE_ORDER);
        labelBuffer.put(label.getBytes("UTF-8"));

        bb.put(labelBuffer.array());
        bb.putLong(0);

        this.content = bb.array();
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

        return new State(hsbk, powerLevel, label);
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
    public int size() {
        return Constants.SIZE_STATE;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (color != null ? !color.equals(state.color) : state.color != null) return false;
        if (power != null ? !power.equals(state.power) : state.power != null) return false;
        if (label != null ? !label.equals(state.label) : state.label != null) return false;
        return Arrays.equals(content, state.content);

    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "color=" + color +
                ", power=" + power +
                ", label='" + label + '\'' +
                ", content=" + ByteUtils.toHexString(content) +
                '}';
    }
}
