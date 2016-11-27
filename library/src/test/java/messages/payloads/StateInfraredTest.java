package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.payloads.StateInfrared;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-22.
 */
public class StateInfraredTest {
    @Test(expected = MalformedMessageException.class)
    public void valueOfWithWrongContents() throws Exception {
        StateInfrared.valueOf(new byte[] {0x01, 0x05, 0x08, 0x10, 0x0a});
    }

    @Test
    public void valueOf() throws Exception {
        final StateInfrared si = new StateInfrared.Builder((short) 118).source(0xedaeda).build();
        final StateInfrared parsed = StateInfrared.valueOf(si.getContent());
        assertThat(parsed, is(si));
    }
}