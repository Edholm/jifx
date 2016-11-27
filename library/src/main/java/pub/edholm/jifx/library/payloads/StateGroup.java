package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.datatypes.ExtendedLabel;
import pub.edholm.jifx.library.datatypes.Time;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.datatypes.Label;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateGroup extends AbstractMessage {
    private final ExtendedLabel extendedLabel;

    private StateGroup(Header header, ExtendedLabel extendedLabel) {
        super(header, extendedLabel.getContent());
        this.extendedLabel = extendedLabel;
    }

    public static class Builder extends AbstractBuilder<StateGroup, Builder> {
        private byte[] id;
        private Label label;
        private Time updateAt;

        public Builder() {
            super(MessageType.StateGroup, Constants.SIZE_EXTENDED_LABEL);
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
        public StateGroup build() {
            final ExtendedLabel extendedLabel = new ExtendedLabel(id, label, updateAt);
            return new StateGroup(buildHeader(), extendedLabel);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateGroup valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_EXTENDED_LABEL;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateGroup", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final ExtendedLabel el = ExtendedLabel.valueOf(Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE));
        return new StateGroup(h, el);
    }

    public ExtendedLabel getExtendedLabel() {
        return extendedLabel;
    }

    @Override
    public String toString() {
        return "StateGroup{" + extendedLabel + '}';
    }
}
