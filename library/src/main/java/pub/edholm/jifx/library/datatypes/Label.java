package pub.edholm.jifx.library.datatypes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class Label implements MessagePart {
    private final String label;

    public Label(String label) {
        notNull(label, "Label cannot be null");
        isTrue(label.getBytes().length <= Constants.SIZE_LABEL, "Label size must be <= %d bytes", Constants.SIZE_LABEL);
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

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
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

        return new EqualsBuilder()
                .append(label, label1.label)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(label)
                .toHashCode();
    }
}
