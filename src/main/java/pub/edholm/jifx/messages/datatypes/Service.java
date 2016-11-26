package pub.edholm.jifx.messages.datatypes;

/**
 * Describes the services exposed by the device.
 * Created by Emil Edholm on 2016-11-05.
 *
 * @see <a href="https://lan.developer.lifx.com/docs/device-messages#section-service">API docs</a>
 */
public enum Service {
    UDP(1),
    Reserved(5), // Undocumented service type
    Unknown(-1);

    private final byte value;

    Service(int value) {
        this.value = (byte) value;
    }

    public static Service valueOf(byte value) {
        for (Service s : Service.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return Unknown;
    }

    public byte getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }
}
