package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateGroup;
import pub.edholm.jifx.library.utils.ByteUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateGroupTest {
    @Test
    public void valueOf() throws Exception {
        final StateGroup sg = new StateGroup.Builder().id(ByteUtils.longToBytes(0xeda)).label("derp slerp").updatedAt(1480178158000000000L).build();
        final StateGroup parsed = StateGroup.valueOf(sg.getContent());
        System.out.println(sg);
        System.out.println(parsed);
        assertThat(parsed, is(sg));
    }

}