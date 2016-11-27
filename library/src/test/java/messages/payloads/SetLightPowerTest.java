package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.SetLightPower;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-22.
 */
public class SetLightPowerTest {
    @Test
    public void valueOf() throws Exception {
        final SetLightPower slp = new SetLightPower.Builder(false, 1337).source(0xedaeda).build();
        final SetLightPower parsed = SetLightPower.valueOf(slp.getContent());

        assertThat(parsed, is(slp));
    }

}