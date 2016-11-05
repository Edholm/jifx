package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.headers.ProtocolHeader;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public class ProtocolHeaderTest {
    @Test
    public void valueOf() throws Exception {
        ProtocolHeader protocolHeader = new ProtocolHeader(MessageType.SetColor);
        ProtocolHeader valueOf = ProtocolHeader.valueOf(protocolHeader.getContent());
        assertThat(protocolHeader, is(valueOf));
    }

    @Test
    public void size() throws Exception {
        ProtocolHeader ph = new ProtocolHeader(MessageType.Acknowledgement);
        assertThat(ph.getContent().length, is(12));
    }

    @Test
    public void getContent() throws Exception {
        ProtocolHeader ph1 = new ProtocolHeader(MessageType.Acknowledgement);
        ProtocolHeader ph2 = new ProtocolHeader(MessageType.SetColor);

        byte[] ph1Expected = new byte[]{
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2d, 0x00, 0x00, 0x00
        };
        byte[] ph2Expected = new byte[]{
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x00, 0x00, 0x00
        };

        System.out.println("PH1: " + ph1);
        System.out.println("PH2: " + ph2);
        assertThat(ph1.getContent(), is(ph1Expected));
        assertThat(ph2.getContent(), is(ph2Expected));
    }

}