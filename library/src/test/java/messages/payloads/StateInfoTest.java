package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateInfo;
import pub.edholm.jifx.library.datatypes.Time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateInfoTest {
    @Test
    public void valueOf() throws Exception {
        final long time = 1480245682242000000L;
        final StateInfo si = new StateInfo.Builder().time(new Time(time)).uptime(new Time(1000000000L)).downtime(new Time(10004L)).build();
        final StateInfo parsed = StateInfo.valueOf(si.getContent());

        System.out.println(si);
        System.out.println(parsed);

        assertThat(parsed, is(si));
    }

}