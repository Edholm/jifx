package pub.edholm.jifx.messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.datatypes.PowerLevel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class StateTest {
    @Test
    public void valueOf() throws Exception {
        Hsbk hsbk = new Hsbk.Builder().build();
        PowerLevel pl = new PowerLevel(true);

        State s = new State.Builder(hsbk, pl, "apa bepa").build();
        State valueOf = State.valueOf(s.getContent());
        assertThat(s, is(valueOf));
    }

    @Test
    public void getContent() throws Exception {
        Hsbk hsbk = new Hsbk.Builder().build();
        PowerLevel pl = new PowerLevel(true);
        State s = new State.Builder(hsbk, pl, "apa bepa").build();

        System.out.println(s);
    }

}