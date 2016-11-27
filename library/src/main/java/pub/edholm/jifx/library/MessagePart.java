package pub.edholm.jifx.library;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public interface MessagePart {
    int size();

    byte[] getContent();
}
