package pub.edholm.jifx.exceptions;

/**
 * Thrown when unable to correctly parse message contents back into a Message
 * <br />
 * Created by Emil Edholm on 2016-11-22.
 * @see pub.edholm.jifx.messages.Message
 */
public class MessageParseException extends RuntimeException {
    public MessageParseException() {
    }

    public MessageParseException(String message) {
        super(message);
    }

    public MessageParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageParseException(Throwable cause) {
        super(cause);
    }

    public MessageParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
