package pub.edholm.jifx.messages;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public enum MessageType {
    /* Device pub.edholm.jifx.messages */
    GetService(2),
    StateService(3),
    GetHostInfo(12),
    StateHostInfo(13),
    GetHostFirmware(14),
    StateHostFirmware(15),
    GetWifiInfo(16),
    StateWifiInfo(17),
    GetWifiFirmware(18),
    StateWifiFirmware(19),
    GetDevicePower(20),
    SetDevicePower(21),
    StateDevicePower(22),
    GetLabel(23),
    SetLabel(24),
    StateLabel(25),
    GetVersion(32),
    StateVersion(33),
    GetInfo(34),
    StateInfo(35),
    Acknowledgement(45),
    GetLocation(48),
    StateLocation(50),
    GetGroup(51),
    StateGroup(53),
    EchoRequest(58),
    EchoResponse(59),

    /* Light pub.edholm.jifx.messages */
    Get(101),
    SetColor(102),
    State(107),
    GetLightPower(116),
    SetLightPower(117),
    StateLightPower(118),
    GetInfrared(120),
    StateInfrared(121),
    SetInfrared(122),

    /* Multi-zone pub.edholm.jifx.messages */
    SetColorZones(501),
    GetColorZones(502),
    StateZone(503),
    StateMultiZone(506),

    Unknown(-1);

    private final short type;

    MessageType(int type) {
        this.type = (short) type;
    }

    public short type() {
        return type;
    }

    public static MessageType valueOf(short type) {
        for (MessageType device : MessageType.values()) {
            if (device.type == type) {
                return device;
            }
        }
        return MessageType.Unknown;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + type + ")";
    }
}
