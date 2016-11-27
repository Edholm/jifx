package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateWifiInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class StateWifiInfoTest {
    @Test
    public void valueOf() throws Exception {
        final StateWifiInfo swi = new StateWifiInfo.Builder().rx(188).tx(199).signal(1.5f).build();
        final StateWifiInfo parsed = StateWifiInfo.valueOf(swi.getContent());

        System.out.println(swi);
        System.out.println(parsed);
        assertThat(parsed, is(swi));
    }

}