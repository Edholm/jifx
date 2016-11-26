package pub.edholm.jifx.messages.payloads;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class EchoTest {
    @Test
    public void valueOf() throws Exception {
        final EchoRequest req = new EchoRequest.Builder().randomPayload().sequence(0xeda).build();
        final EchoRequest parsedReq = EchoRequest.valueOf(req.getContent());
        System.out.println(req);
        System.out.println(parsedReq);
        assertThat(parsedReq, is(req));

        final EchoResponse resp = new EchoResponse.Builder().payload(req.getPayload()).source(0xeda).build();
        byte[] payload = req.getPayload();
        payload[0] = 0;
        payload[1] = 0;
        payload[2] = 0;
        final EchoResponse respParsed = EchoResponse.valueOf(resp.getContent());
        System.out.println(resp);
        System.out.println(respParsed);
        assertThat(respParsed, is(resp));
    }

}