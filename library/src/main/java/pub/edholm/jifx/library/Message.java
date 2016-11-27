package pub.edholm.jifx.library;

import pub.edholm.jifx.library.headers.Header;

/**
 * Created by Emil Edholm on 2016-11-08.
 */
public interface Message extends MessagePart {

    Header getHeader();

    /**
     * Total size of the whole message in bytes
     */
    @Override
    int size();

    byte[] getPayload();

    @Override
    String toString();
}
