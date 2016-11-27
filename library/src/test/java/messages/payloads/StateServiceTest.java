package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.datatypes.Service;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.payloads.StateService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class StateServiceTest {
    @Test(expected = MalformedMessageException.class)
    public void valueOfWithWrongContent() throws Exception {
        StateService.valueOf(new byte[]{0xa, 0xb, 0xc, 0xd});
    }

    @Test
    public void valueOf() throws Exception {
        StateService ss = new StateService.Builder(Service.UDP, 1337).source(0xedaade).build();
        StateService valueOf = StateService.valueOf(ss.getContent());
        assertThat(ss, is(valueOf));
    }

    @Test
    public void getContent() throws Exception {
        StateService ss = new StateService.Builder(Service.UDP, 0x100).build();
        byte[] expected = new byte[]{
                0x01, 0x00, 0x01, 0x00, 0x00
        };
        System.out.println(ss);
        assertThat(ss.getPayload(), is(expected));
    }

    @Test
    public void serviceUnavailable() throws Exception {
        StateService ss1 = new StateService.Builder(Service.UDP, 0).build();
        StateService ss2 = new StateService.Builder(Service.UDP, 1024).build();
        assertThat(ss1.isServiceAvailable(), is(true));
        assertThat(ss2.isServiceAvailable(), is(false));
    }
}