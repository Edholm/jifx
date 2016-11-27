package messages.payloads;

import org.junit.Test;
import pub.edholm.jifx.library.payloads.StateVersion;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateVersionTest {
    @Test
    public void valueOf() throws Exception {
        final StateVersion sv = new StateVersion.Builder().product(31).vendor(1).version(0xffffffff).build();
        final StateVersion parsed = StateVersion.valueOf(sv.getContent());

        System.out.println(sv);
        System.out.println(parsed);

        assertThat(parsed, is(sv));
    }

    @Test
    public void testProductName() throws Exception {
        final StateVersion sv = new StateVersion.Builder().product(11).build();
        assertThat(sv.getProductName(), is("White 800 (High Voltage)"));

        final StateVersion sv2 = new StateVersion.Builder().product(121).build();
        assertThat(sv2.getProductName(), is("UNKNOWN"));
    }

    @Test
    public void testVendorName() throws Exception {
        final StateVersion sv = new StateVersion.Builder().vendor(1).build();
        assertThat(sv.getVendorName(), is("LIFX"));

        final StateVersion sv2 = new StateVersion.Builder().vendor(1337).build();
        assertThat(sv2.getVendorName(), is("UNKNOWN"));
    }

    @Test
    public void testFeatures() throws Exception {
        final StateVersion sv = new StateVersion.Builder().product(22).build();
        assertThat(sv.getProductName(), is("Color 1000"));

        Map<String, Boolean> features = sv.getFeatures();
        assertThat(features.get("color"), is(true));
        assertThat(features.get("infrared"), is(false));
        assertThat(features.get("multizone"), is(false));

        final StateVersion sv2 = new StateVersion.Builder().product(30).build();
        assertThat(sv2.getProductName(), is("LIFX+ BR30"));

        Map<String, Boolean> features2 = sv2.getFeatures();
        assertThat(features2.get("color"), is(true));
        assertThat(features2.get("infrared"), is(true));
        assertThat(features2.get("multizone"), is(false));
    }

    @Test
    public void unknownFeatures() throws Exception {
        final StateVersion sv = new StateVersion.Builder().product(111).build();
        assertNotNull(sv.getFeatures());
        assertThat(sv.getFeatures().size(), is(0));
    }

    @Test
    public void testLatestProduct() throws Exception {
        // Product 31 is the latest at the time of writing this.
        final StateVersion sv = new StateVersion.Builder().product(32).build();
        assertThat(sv.getProductName(), is("UNKNOWN"));

    }
}