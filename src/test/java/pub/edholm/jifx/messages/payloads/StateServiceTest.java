package pub.edholm.jifx.messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.messages.datatypes.Service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class StateServiceTest {
    @Test
    public void valueOf() throws Exception {
        StateService ss = new StateService(Service.UDP, 1337);
        StateService valueOf = StateService.valueOf(ss.getContent());
        assertThat(ss, is(valueOf));
    }

    @Test
    public void getContent() throws Exception {
        StateService ss = new StateService(Service.UDP, 0x100);
        byte[] expected = new byte[]{
                0x01, 0x00, 0x01, 0x00, 0x00
        };
        System.out.println(ss);
        assertThat(ss.getContent(), is(expected));
    }

    @Test
    public void serviceUnavailable() throws Exception {
        StateService ss1 = new StateService(Service.UDP, 0);
        StateService ss2 = new StateService(Service.UDP, 1024);
        assertThat(ss1.isServiceAvailable(), is(true));
        assertThat(ss2.isServiceAvailable(), is(false));
    }
}