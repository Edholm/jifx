package pub.edholm.jifx.messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.utils.Constants;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class SetColorTest {
    private final Hsbk hsbk = new Hsbk.Builder().hue(0x5555).kelvin(3500).build();

    @Test
    public void getContent() throws Exception {
        SetColor sc = new SetColor.Builder().hsbk(hsbk).build();

        byte FF = (byte) 0xff;
        byte[] scExpectedContent = new byte[]{
                0x00, 0x55, 0x55, FF, FF, FF, FF, (byte) 0xAC, 0x0D, 0x00, 0x04, 0x00, 0x00
        };
        System.out.println("SC: " + sc.toString());
        assertThat(sc.getPayload(), is(scExpectedContent));
    }

    @Test
    public void size() throws Exception {
        SetColor sc = new SetColor.Builder().hsbk(hsbk).build();
        assertThat(sc.size(), is(Constants.SIZE_HEADER + 13));
    }
}