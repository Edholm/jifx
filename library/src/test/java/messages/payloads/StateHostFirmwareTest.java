package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateHostFirmware;
import pub.edholm.jifx.library.datatypes.Time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateHostFirmwareTest {
    @Test
    public void valueOf() throws Exception {
        final StateHostFirmware shf = new StateHostFirmware.Builder().buildTime(new Time(1480168243242000000L)).version(1337).build();
        final StateHostFirmware parsed = StateHostFirmware.valueOf(shf.getContent());
        System.out.println(shf);
        System.out.println(parsed);
        assertThat(parsed, is(shf));
    }

}