package messages.datatypes;

import org.junit.Test;
import pub.edholm.jifx.library.datatypes.ExtendedLabel;
import pub.edholm.jifx.library.datatypes.Label;
import pub.edholm.jifx.library.datatypes.Time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class ExtendedLabelTest {
    @Test
    public void valueOf() throws Exception {
        final byte[] id = new byte[]{0xa, 0xb, 0xc};
        final ExtendedLabel el = new ExtendedLabel(id, Label.valueOf("derp herp"), new Time(1480178158000000L));
        final ExtendedLabel parsed = ExtendedLabel.valueOf(el.getContent());
        assertThat(parsed, is(el));
    }

}