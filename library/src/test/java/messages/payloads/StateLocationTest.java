package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateLocation;
import pub.edholm.jifx.library.utils.ByteUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateLocationTest {
    @Test
    public void valueOf() throws Exception {
        final StateLocation sl = new StateLocation.Builder().id(ByteUtils.longToBytes(0xeda)).label("herpa gerpa").updatedAt(1480184158000000000L).build();
        final StateLocation parsed = StateLocation.valueOf(sl.getContent());
        System.out.println(sl);
        System.out.println(parsed);
        assertThat(parsed, is(sl));
    }

}