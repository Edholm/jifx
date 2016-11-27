package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.Acknowledgement;
import pub.edholm.jifx.library.utils.ByteUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class AcknowledgementTest {
    @Test
    public void valueOf() throws Exception {
        final Acknowledgement ack = new Acknowledgement.Builder().ackRequired(false).resRequired(false).target(ByteUtils.longToBytes(0xedaaa)).build();
        final Acknowledgement parsed = Acknowledgement.valueOf(ack.getContent());
        assertThat(parsed, is(ack));
    }

}