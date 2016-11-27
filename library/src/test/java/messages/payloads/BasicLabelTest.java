package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.BasicLabel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class BasicLabelTest {
    @Test
    public void valueOf() throws Exception {
        final BasicLabel bl1 = new BasicLabel.Builder(BasicLabel.Type.SetLabel).label("asdfqwert!#¤%").build();
        final BasicLabel bl2 = new BasicLabel.Builder(BasicLabel.Type.StateLabel).label("åpoiuylkjhg").source(0xeda).build();

        final BasicLabel parsed1 = BasicLabel.valueOf(bl1.getContent());
        final BasicLabel parsed2 = BasicLabel.valueOf(bl2.getContent());

        System.out.println(bl1);
        System.out.println(parsed1);

        System.out.println(bl2);
        System.out.println(parsed2);

        assertThat(parsed1, is(bl1));
        assertThat(parsed2, is(bl2));
    }
}