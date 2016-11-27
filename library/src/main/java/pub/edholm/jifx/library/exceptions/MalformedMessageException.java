package pub.edholm.jifx.library.exceptions;

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

    public static MalformedMessageException createInvalidSize(String className, int expected, int actual) {
        return new MalformedMessageException(String.format("Size does not match \"%s\". Expected %d bytes, got %d", className, expected, actual));
    }
}
