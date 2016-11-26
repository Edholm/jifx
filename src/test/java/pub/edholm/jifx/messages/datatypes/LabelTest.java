package pub.edholm.jifx.messages.datatypes;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Emil Edholm on 2016-11-26.
 */
public class LabelTest {
    @Test
    public void valueOf() throws Exception {
        final Label label = Label.valueOf("Lorem ipsum dolor sit amet amet.");
        final Label parsed = Label.valueOf(label.getContent());
        assertThat(parsed, is(label));

        final Label label2 = Label.valueOf("Herp derp");
        final Label parsed2 = Label.valueOf(label2.getContent());
        assertThat(parsed2, is(label2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLargeInputInValueOf() throws Exception {
        Label.valueOf("Lorem ipsum dolor sit amet amet..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLargeInputInConstructor() throws Exception {
        new Label("Lorem ipsum dolor sit amet amet..");
    }

}