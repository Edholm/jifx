package pub.edholm.jifx.utils;

import org.junit.Test;
import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageType;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.headers.Frame;
import pub.edholm.jifx.messages.headers.FrameAddress;
import pub.edholm.jifx.messages.headers.ProtocolHeader;
import pub.edholm.jifx.messages.payloads.SetColor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class ByteUtilsTest {
    private final Message m1 = new Frame.Builder(49).tagged(true).build();
    private final Message m2 = new FrameAddress.Builder().build();
    private final Message m3 = new ProtocolHeader(MessageType.SetColor);
    private final Message m4 = new SetColor(new Hsbk.Builder().hue(0x5555).kelvin(3500).build(), 1024);

    @Test
    public void totalSize() throws Exception {
        int expectedSize = m1.size() + m2.size() + m3.size() + m4.size();
        List<Message> messages = Arrays.asList(m1, m2, m3, m4);
        int calculatedSize = ByteUtils.totalSize(messages);

        System.out.println("Expected: " + expectedSize + ", Calculated: " + calculatedSize);
        assertThat(calculatedSize, is(expectedSize));
    }

    @Test
    public void combineContents() throws Exception {
        byte FF = (byte) 0xff;
        byte AC = (byte) 0xac;
        byte[] expected = new byte[]{
                0x31, 0x00, 0x00, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x00, 0x00, 0x00, 0x00, 0x55, 0x55, FF, FF, FF, FF, AC, 0x0D, 0x00, 0x04, 0x00, 0x00
        };
        byte[] calculated = ByteUtils.combineContents(Arrays.asList(m1, m2, m3, m4));
        System.out.println("Calculated: " + ByteUtils.toHexString(calculated));
        System.out.println("Expected  : " + ByteUtils.toHexString(expected));

        assertThat(calculated, is(expected));
    }

}