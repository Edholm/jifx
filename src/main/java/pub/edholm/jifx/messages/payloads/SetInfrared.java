package pub.edholm.jifx.messages.payloads;

/**
 * Created by Emil Edholm on 2016-11-06.
 */
public class SetInfrared extends AbstractInfrared {
    public SetInfrared(short brightness) {
        super(brightness);
    }

    public static SetInfrared valueOf(byte[] content) {
        return new SetInfrared(AbstractInfrared.parse(content));
    }

    @Override
    public String toString() {
        return "SetInfrared{" +
                "brightness=" + getBrightness() +
                '}';
    }
}
