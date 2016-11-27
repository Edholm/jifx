package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.headers.Header;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.datatypes.Label;

import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class BasicLabel extends AbstractMessage {
    private final Label label;

    private BasicLabel(Header header, Label label) {
        super(header, label.getContent());
        this.label = label;
    }

    public enum Type {
        SetLabel(MessageType.SetLabel),
        StateLabel(MessageType.StateLabel);

        private final MessageType messageType;

        Type(MessageType type) {
            this.messageType = type;
        }
    }

    public static class Builder extends AbstractBuilder<BasicLabel, Builder> {
        private Label label;

        public Builder(Type type) {
            super(type.messageType, Constants.SIZE_LABEL);
        }

        public Builder label(String label) {
            this.label = Label.valueOf(label);
            return this;
        }

        @Override
        public BasicLabel build() {
            return new BasicLabel(buildHeader(), label);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static BasicLabel valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_LABEL;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("BasicLabel", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);
        final Label label = Label.valueOf(payload);

        return new BasicLabel(h, label);
    }

    public Label getLabel() {
        return label;
    }

    public String asString() {
        return label.toString();
    }

    @Override
    public String toString() {
        return getHeader().getType() + "{" +
                "label: " + label +
                '}';
    }
}
