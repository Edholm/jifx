package pub.edholm.jifx.messages.payloads;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class SetDevicePowerTest {
    @Test
    public void valueOf() throws Exception {
        final SetDevicePower sdp = new SetDevicePower.Builder().level(false).build();
        final SetDevicePower parsed = SetDevicePower.valueOf(sdp.getContent());
        assertThat(parsed, is(sdp));
    }

}