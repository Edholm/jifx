package messages.datatypes;

import org.junit.Test;
import pub.edholm.jifx.library.datatypes.PowerLevel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class PowerLevelTest {
    @Test
    public void valueOf() throws Exception {
        PowerLevel pl = new PowerLevel(true);
        PowerLevel valueOf = PowerLevel.valueOf(pl.getContent());
        assertThat(pl, is(valueOf));
    }

    @Test
    public void isPoweredOn() throws Exception {
        PowerLevel pl = new PowerLevel(true);
        assertThat(pl.isPoweredOn(), is(true));

        pl = new PowerLevel(false);
        assertThat(pl.isPoweredOn(), is(false));
    }

    @Test
    public void getContent() throws Exception {
        PowerLevel pl = new PowerLevel(true);

        byte ff = (byte) 0xff;
        byte[] expected = new byte[] {
                ff, ff
        };

        assertThat(pl.getContent(), is(expected));
    }

}