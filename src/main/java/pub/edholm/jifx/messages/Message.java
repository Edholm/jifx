package pub.edholm.jifx.messages;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public interface Message {
    int size();
    byte[] getContent();
}
