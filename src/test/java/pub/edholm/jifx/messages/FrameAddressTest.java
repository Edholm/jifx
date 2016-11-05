package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.headers.FrameAddress;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public class FrameAddressTest {
    @Test
    public void valueOf() throws Exception {
        FrameAddress frameAddress = new FrameAddress.Builder()
                .target(0xfedaf)
                .sequence(0xed)
                .ackRequired(false)
                .resRequired(true).build();

        FrameAddress valueOf = FrameAddress.valueOf(frameAddress.getContent());
        assertThat(frameAddress, is(valueOf));
    }

    @Test
    public void correctSize() throws Exception {
        FrameAddress fa = new FrameAddress.Builder().build();
        assertThat(fa.getContent().length, is(16));

    }

    @Test(expected = IllegalArgumentException.class)
    public void sequenceToLarge() throws Exception {
        new FrameAddress.Builder().sequence(0x100).build();
        new FrameAddress.Builder().sequence(-1).build();
    }

    @Test
    public void getContent() throws Exception {
        FrameAddress fa1 = new FrameAddress.Builder().build();
        FrameAddress fa2 = new FrameAddress.Builder().ackRequired(true).resRequired(true).target(0xFFFFFFFFFFFFFFFFL).sequence(0xff).build();

        System.out.println("FA1: " + fa1);
        System.out.println("FA2: " + fa2);
        byte[] fa1Expected = new byte[]{
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0
        };

        byte ff = (byte) 0xff;
        byte[] fa2Expected = new byte[]{
                ff, ff, ff, ff, ff, ff, ff, ff, ff, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        assertThat(fa1.getContent(), is(fa1Expected));
        assertThat(fa2.getContent(), is(fa2Expected));
    }

}