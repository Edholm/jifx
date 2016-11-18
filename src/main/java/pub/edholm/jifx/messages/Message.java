package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.headers.Header;

/**
 * Created by Emil Edholm on 2016-11-08.
 */
public interface Message extends MessagePart {

    Header getHeader();

    /**
     * Total size of the whole message in bytes
     */
    int size();

    byte[] getPayload();

    byte[] getContent();

    @Override
    String toString();
}
