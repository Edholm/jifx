package pub.edholm.jifx.messages;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.exceptions.MessageParseException;
import pub.edholm.jifx.messages.headers.ProtocolHeader;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-21.
 */
public class MessageParser {
    private static final String VALUEOF_METHOD_NAME = "valueOf";
    private static final Class VALUEOF_PARAMETER_CLASS = byte[].class;

    private MessageParser() {
        throw new AssertionError("Mustn't instantiate this class");
    }

    /**
     * Try to parse a byte array into a Message
     *
     * @throws MalformedMessageException
     * @throws MessageParseException
     */
    public static Message parse(byte[] contents) {
        if (contents.length < Constants.SIZE_HEADER) {
            throw new MalformedMessageException("Contents too small to contain Lifx header");
        }

        final int protocolHeaderPosition = Constants.SIZE_FRAME + Constants.SIZE_FRAME_ADDRESS;
        final byte[] protocolHeaderContents = Arrays.copyOfRange(contents, protocolHeaderPosition, protocolHeaderPosition + Constants.SIZE_PROTOCOL_HEADER);
        final ProtocolHeader protocolHeader = ProtocolHeader.valueOf(protocolHeaderContents);

        final Class<? extends Message> klass = protocolHeader.getType().getImplementationClass();
        if (klass == null) {
            System.err.println("Unknown contents: " + ByteUtils.toHexString(contents));
            throw new MessageParseException("Cannot parse contents due to unknown implementation class. Message type: " + protocolHeader.getType());
        }

        Method valueOfMethod;
        try {
            valueOfMethod = klass.getMethod(VALUEOF_METHOD_NAME, VALUEOF_PARAMETER_CLASS);
            return (Message) valueOfMethod.invoke(null, (Object) contents);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError(VALUEOF_METHOD_NAME + " method isn't implemented in " + klass);
        } catch (InvocationTargetException e) {
            throw new MessageParseException(String.format("Unable to parse contents into a message. Content length: %d bytes", contents.length), e.getCause());
        }
    }
}
