package pub.edholm.jifx.messages.payloads;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateVersionTest {
    @Test
    public void valueOf() throws Exception {
        final StateVersion sv = new StateVersion.Builder().product(1).vendor(2).version(0xffffffff).build();
        final StateVersion parsed = StateVersion.valueOf(sv.getContent());

        System.out.println(sv);
        System.out.println(parsed);

        assertThat(parsed, is(sv));
    }

}