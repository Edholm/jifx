package pub.edholm.jifx.messages.payloads;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class StateInfrared extends AbstractInfrared {
    public StateInfrared(short brightness) {
        super(brightness);
    }

    public static StateInfrared valueOf(byte[] content) {
        return new StateInfrared(AbstractInfrared.parse(content));
    }

    @Override
    public String toString() {
        return "StateInfrared{" +
                "brightness=" + getBrightness() +
                '}';
    }
}
