package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateWifiFirmware;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateWifiFirmwareTest {
    @Test
    public void valueOf() throws Exception {
        final StateWifiFirmware swf = new StateWifiFirmware.Builder().build(0xeda).version(8).source(0xaef).build();
        final StateWifiFirmware parsed = StateWifiFirmware.valueOf(swf.getContent());
        assertThat(parsed, is(swf));
    }
}