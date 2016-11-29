package messages.datatypes;

import org.junit.Test;
import pub.edholm.jifx.library.datatypes.Hsbk;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class HsbkTest {
    @Test
    public void valueOf() throws Exception {
        Hsbk hsbk = new Hsbk.Builder().hue(0xa).brightness(0xb).saturation(0xc).kelvin(9000).build();
        Hsbk valueOf = Hsbk.valueOf(hsbk.getContent());
        assertThat(valueOf, is(hsbk));

        hsbk = new Hsbk.Builder().build();
        valueOf = Hsbk.valueOf(hsbk.getContent());
        assertThat(valueOf, is(hsbk));
    }

    @Test
    public void getContent() throws Exception {
        final Hsbk hsbk = new Hsbk.Builder().hue(0x5555).saturation(0xA0ED).brightness(0x1122).kelvin(3500).build();

        System.out.println("Hsbk: " + hsbk);
        byte FF = (byte) 0xff;
        byte[] expected = new byte[]{
                0x55, 0x55, (byte) 0xED, (byte) 0xA0, 0x22, 0x11, (byte) 0xAC, 0x0D
        };

        assertThat(hsbk.getContent(), is(expected));
    }

    @Test
    public void size() throws Exception {
        Hsbk hsbk = new Hsbk.Builder().build();
        assertThat(hsbk.size(), is(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKelvinLowerBounds() throws Exception {
        final Hsbk hsbk = new Hsbk.Builder().kelvin(2499).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKelvinUpperBounds() throws Exception {
        final Hsbk hsbk = new Hsbk.Builder().kelvin(9001).build();
    }

    @Test
    public void testKelvin9000Allowed() throws Exception {
        final Hsbk hsbk = new Hsbk.Builder().kelvin(9000).build();
    }

    @Test
    public void testKelvin2500Allowed() throws Exception {
        final Hsbk hsbk = new Hsbk.Builder().kelvin(2500).build();
    }
}