package pub.edholm.jifx.messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.exceptions.MalformedMessageException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Emil Edholm on 2016-11-22.
 */
public class StatePowerTest {
    @Test(expected = MalformedMessageException.class)
    public void valueOfWithWrongContents() throws Exception {
       StatePower.valueOf(new byte[]{0x01, 0xa, 0xb, 0xc});
    }

    @Test
    public void valueOf() throws Exception {
        final StatePower sp = new StatePower.Builder(true, true).source(0xedaedd).build();
        final StatePower parsed = StatePower.valueOf(sp.getContent());
        assertThat(parsed, is(sp));
    }

}