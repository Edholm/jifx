package pub.edholm.jifx.messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.messages.MessageType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Emil Edholm on 2016-11-21.
 */
public class GetTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNonGetMessageType() throws Exception {
        new Get.Builder(MessageType.SetColor);
    }

    @Test
    public void valueOf() throws Exception {
        final Get g = new Get.Builder(MessageType.Get).target(0xadde).source(0xeda).build();
        final Get other = Get.valueOf(g.getContent());
        assertThat(other, is(g));
    }
}