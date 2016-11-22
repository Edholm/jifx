package pub.edholm.jifx.exceptions;

/**
 * Thrown when a malformed message is detected.
 * <br />
 * Created by Emil Edholm on 2016-11-22.
 */
public class MalformedMessageException extends RuntimeException {
    public MalformedMessageException() {
    }

    public MalformedMessageException(String message) {
        super(message);
    }

    public MalformedMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedMessageException(Throwable cause) {
        super(cause);
    }

    public MalformedMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
