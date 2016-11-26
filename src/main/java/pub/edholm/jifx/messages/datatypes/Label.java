package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class Label implements MessagePart {
    private final String label;

    public Label(String label) {
        checkSize(label);
        this.label = label;
    }

    public static Label valueOf(byte[] content) {
        if (content.length < Constants.SIZE_LABEL) {
            throw MalformedMessageException.createInvalidSize("Label", Constants.SIZE_LABEL, content.length);
        }

        byte[] labelContent = Arrays.copyOfRange(content, 0, Constants.SIZE_LABEL);

        // The extra copyOf is to ensure that string only contains the correct data.
        final String label = new String(Arrays.copyOf(labelContent, indexOf(labelContent, (byte) 0)), StandardCharsets.UTF_8);
        return new Label(label);
    }

    public static Label valueOf(String s) {
        checkSize(s);
        return new Label(s);
    }

    private static int indexOf(byte[] content, byte value) {
        for (int i = 0; i < content.length; i++) {
            if (content[i] == value) {
                return i;
            }
        }
        return content.length;
    }

    private static void checkSize(String s) {
        if(s.getBytes().length > Constants.SIZE_LABEL) {
            throw new IllegalArgumentException(String.format("%s is too large. Expected ≤ %d bytes, got: %d", s, Constants.SIZE_LABEL, s.getBytes().length));
        }
    }


    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Label{'" + label + '\'' + '}';
    }

    @Override
    public int size() {
        return Constants.SIZE_LABEL;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_LABEL);
        buffer.put(label.getBytes());
        return buffer.array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label1 = (Label) o;

        return label != null ? label.equals(label1.label) : label1.label == null;
    }

    @Override
    public int hashCode() {
        return label != null ? label.hashCode() : 0;
    }
}
