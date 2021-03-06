package pub.edholm.jifx.library.datatypes;

import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.MessagePart;
import pub.edholm.jifx.library.payloads.StateGroup;
import pub.edholm.jifx.library.payloads.StateLocation;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * A label that contains a 16 byte id, a label and a timestamp. For use with StateGroup and StateLocation
 * <br />
 * Created by Emil Edholm on 2016-11-26.
 *
 * @see StateGroup
 * @see StateLocation
 */
public class ExtendedLabel implements MessagePart {
    public static final int SIZE_ID = 16;
    private final byte[] id;
    private final Label label;
    private final Time updatedAt;

    public ExtendedLabel(byte[] id, Label label, Time updatedAt) {
        this.id = Arrays.copyOf(notNull(id, "ID cannot be null"), SIZE_ID);
        this.label = notNull(label, "Label cannot be null");
        this.updatedAt = notNull(updatedAt, "UpdatedAt cannot be null");
    }

    public static ExtendedLabel valueOf(byte[] content) {
        if (content.length < Constants.SIZE_EXTENDED_LABEL) {
            throw MalformedMessageException.createInvalidSize("ExtendedLabel", Constants.SIZE_EXTENDED_LABEL, content.length);
        }

        ByteBuffer buffer = ByteBuffer.wrap(content);
        buffer.order(Constants.BYTE_ORDER);

        final byte[] idContent = new byte[SIZE_ID];
        final byte[] labelContent = new byte[Constants.SIZE_LABEL];
        final byte[] timeContent = new byte[Constants.SIZE_TIME];

        buffer.get(idContent, 0, SIZE_ID);
        buffer.get(labelContent, 0, labelContent.length);
        buffer.get(timeContent, 0, timeContent.length);

        return new ExtendedLabel(idContent, Label.valueOf(labelContent), Time.valueOf(timeContent));
    }

    public byte[] getId() {
        return Arrays.copyOf(id, id.length);
    }

    public Label getLabel() {
        return label;
    }

    public Time getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public int size() {
        return Constants.SIZE_EXTENDED_LABEL;
    }

    @Override
    public byte[] getContent() {
        ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_EXTENDED_LABEL);
        buffer.put(id);
        buffer.put(label.getContent());
        buffer.put(updatedAt.getContent());
        return buffer.array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtendedLabel that = (ExtendedLabel) o;

        if (!Arrays.equals(id, that.id)) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        return updatedAt != null ? updatedAt.equals(that.updatedAt) : that.updatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(id);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExtendedLabel{" +
                "id: " + ByteUtils.toHexString(id) +
                ", label: " + label +
                ", updatedAt: " + updatedAt +
                '}';
    }

}
