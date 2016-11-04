package messages;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-03.
 */
public class FrameTest {
    @Test
    public void correctSize() throws Exception {
        Frame f = new Frame.Builder(0x0).build();
        assertThat(f.getContent().length, is(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeTooLarge() throws Exception {
        new Frame.Builder(0x10000).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sourceTooLarge() throws Exception {
        new Frame.Builder(0x0).source(0x100000000L).build();
    }

    @Test
    public void getContent() throws Exception {
        Frame f1 = new Frame.Builder(0xFFFF).tagged(true).source(0xFFFFFFFFL).build();
        Frame f2 = new Frame.Builder(0xFFFF).tagged(false).source(0xFFFFFFFFL).build();

        byte ff = (byte) 0xff;
        byte[] f1ExpectedContent = new byte[]{
                ff, ff, 0x0, 0x34, ff, ff, ff, ff
        };
        byte[] f2ExpectedContent = new byte[]{
                ff, ff, 0x0, 0x14, ff, ff, ff, ff
        };

        assertThat(f1.getContent(), is(f1ExpectedContent));
        assertThat(f2.getContent(), is(f2ExpectedContent));
    }
}