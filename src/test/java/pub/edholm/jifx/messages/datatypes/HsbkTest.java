package pub.edholm.jifx.messages.datatypes;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class HsbkTest {
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

}