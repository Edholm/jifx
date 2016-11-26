package pub.edholm.jifx.messages.payloads;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateHostInfoTest {
    @Test
    public void valueOf() throws Exception {
        final StateHostInfo shi = new StateHostInfo.Builder().signal(0.542f).rx(4211).tx(1999).build();
        final StateHostInfo parsed = StateHostInfo.valueOf(shi.getContent());
        System.out.println(shi);
        System.out.println(parsed);

        assertThat(parsed, is(shi));
    }

}