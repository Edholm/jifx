package pub.edholm.jifx.messages.payloads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-22.
 */
public class SetInfraredTest {
    @Test
    public void valueOf() throws Exception {
        final SetInfrared setInfrared = new SetInfrared.Builder((short) 123).build();
        final SetInfrared parsed = SetInfrared.valueOf(setInfrared.getContent());
        assertThat(parsed, is(setInfrared));
    }

}