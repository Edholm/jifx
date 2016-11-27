package messages;

import org.junit.Test;
import pub.edholm.jifx.library.Message;
import pub.edholm.jifx.library.MessageParser;
import pub.edholm.jifx.library.datatypes.Hsbk;
import pub.edholm.jifx.library.datatypes.Label;
import pub.edholm.jifx.library.datatypes.PowerLevel;
import pub.edholm.jifx.library.datatypes.Service;
import pub.edholm.jifx.library.payloads.*;
import pub.edholm.jifx.library.utils.Constants;

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
            new State.Builder(new Hsbk.Builder().build(), new PowerLevel(true), Label.valueOf("Label schmäjbel")).build(),
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