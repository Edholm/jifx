package pub.edholm.jifx.messages.payloads;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.AbstractBuilder;
import pub.edholm.jifx.messages.AbstractMessage;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.ExtendedLabel;
import pub.edholm.jifx.messages.datatypes.Label;
import pub.edholm.jifx.messages.datatypes.Time;
import pub.edholm.jifx.messages.headers.Header;
import pub.edholm.jifx.utils.Constants;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateLocation extends AbstractMessage {
    private final ExtendedLabel extendedLabel;

    private StateLocation(Header header, ExtendedLabel extendedLabel) {
        super(header, extendedLabel.getContent());
        this.extendedLabel = extendedLabel;
    }

    public static class Builder extends AbstractBuilder<StateLocation, Builder> {
        private byte[] id;
        private Label label;
        private Time updateAt;

        public Builder() {
            super(MessageType.StateLocation, Constants.SIZE_EXTENDED_LABEL);
        }

        public Builder id(byte[] id) {
            this.id = Arrays.copyOf(id, ExtendedLabel.SIZE_ID);
            return this;
        }

        public Builder label(String label) {
            this.label = Label.valueOf(label);
            return this;
        }

        public Builder updatedAt(long updatedAt) {
            this.updateAt = new Time(updatedAt);
            return this;
        }

        @Override
        public StateLocation build() {
            final ExtendedLabel extendedLabel = new ExtendedLabel(id, label, updateAt);
            return new StateLocation(buildHeader(), extendedLabel);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateLocation valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_EXTENDED_LABEL;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateLocation", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final ExtendedLabel el = ExtendedLabel.valueOf(Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE));
        return new StateLocation(h, el);
    }

    public ExtendedLabel getExtendedLabel() {
        return extendedLabel;
    }

    @Override
    public String toString() {
        return "StateLocation{" + extendedLabel + '}';
    }
}
