package pub.edholm.jifx.messages;

import org.junit.Test;
import pub.edholm.jifx.messages.datatypes.Hsbk;
import pub.edholm.jifx.messages.datatypes.PowerLevel;
import pub.edholm.jifx.messages.datatypes.Service;
import pub.edholm.jifx.messages.payloads.*;
import pub.edholm.jifx.utils.Constants;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Emil Edholm on 2016-11-22.
 */
public class MessageParserTest {
    List<? extends Message> messages = Arrays.asList(
            new Get.Builder().build(),
            new SetColor.Builder().build(),
            new SetInfrared.Builder((short) 125).build(),
            new SetLightPower.Builder(true, 1025).build(),
            new State.Builder(new Hsbk.Builder().build(), new PowerLevel(true), "Label schmäjbel").build(),
            new StateInfrared.Builder((short) 1111).build(),
            new StatePower.Builder(true, true).build(),
            new StateService.Builder(Service.UDP, Constants.PORT).build());

    @Test
    public void parse() throws Exception {
        for (Message m : messages) {
            assertThat(MessageParser.parse(m.getContent()), is(m));
        }
    }
}