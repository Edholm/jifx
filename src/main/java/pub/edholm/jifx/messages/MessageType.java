package pub.edholm.jifx.messages;

import pub.edholm.jifx.messages.payloads.BasicLabel;

/**
 * Created by Emil Edholm on 2016-11-04.
 */
public enum MessageType {
    /* Device pub.edholm.jifx.messages */
    GetService(2, pub.edholm.jifx.messages.payloads.Get.class),
    StateService(3, pub.edholm.jifx.messages.payloads.StateService.class),
    GetHostInfo(12, pub.edholm.jifx.messages.payloads.Get.class),
    StateHostInfo(13, pub.edholm.jifx.messages.payloads.StateHostInfo.class),
    GetHostFirmware(14, pub.edholm.jifx.messages.payloads.Get.class),
    StateHostFirmware(15, pub.edholm.jifx.messages.payloads.StateHostFirmware.class),
    GetWifiInfo(16, pub.edholm.jifx.messages.payloads.Get.class),
    StateWifiInfo(17, pub.edholm.jifx.messages.payloads.StateWifiInfo.class),
    GetWifiFirmware(18, pub.edholm.jifx.messages.payloads.Get.class),
    StateWifiFirmware(19, pub.edholm.jifx.messages.payloads.StateWifiFirmware.class),
    GetDevicePower(20, pub.edholm.jifx.messages.payloads.Get.class),
    SetDevicePower(21, pub.edholm.jifx.messages.payloads.SetDevicePower.class),
    StateDevicePower(22, pub.edholm.jifx.messages.payloads.StatePower.class),
    GetLabel(23, pub.edholm.jifx.messages.payloads.Get.class),
    SetLabel(24, BasicLabel.class),
    StateLabel(25, BasicLabel.class),
    GetVersion(32, pub.edholm.jifx.messages.payloads.Get.class),
    StateVersion(33, null),
    GetInfo(34, pub.edholm.jifx.messages.payloads.Get.class),
    StateInfo(35, null),
    Acknowledgement(45, pub.edholm.jifx.messages.payloads.Acknowledgement.class),
    GetLocation(48, pub.edholm.jifx.messages.payloads.Get.class),
    StateLocation(50, pub.edholm.jifx.messages.payloads.StateLocation.class),
    GetGroup(51, pub.edholm.jifx.messages.payloads.Get.class),
    StateGroup(53, pub.edholm.jifx.messages.payloads.StateGroup.class),
    EchoRequest(58, pub.edholm.jifx.messages.payloads.EchoRequest.class),
    EchoResponse(59, pub.edholm.jifx.messages.payloads.EchoResponse.class),

    /* Light pub.edholm.jifx.messages */
    Get(101, pub.edholm.jifx.messages.payloads.Get.class),
    SetColor(102, pub.edholm.jifx.messages.payloads.SetColor.class),
    State(107, pub.edholm.jifx.messages.payloads.State.class),
    GetLightPower(116, pub.edholm.jifx.messages.payloads.Get.class),
    SetLightPower(117, pub.edholm.jifx.messages.payloads.SetLightPower.class),
    StateLightPower(118, pub.edholm.jifx.messages.payloads.StatePower.class),
    GetInfrared(120, pub.edholm.jifx.messages.payloads.Get.class),
    StateInfrared(121, pub.edholm.jifx.messages.payloads.StateInfrared.class),
    SetInfrared(122, pub.edholm.jifx.messages.payloads.SetInfrared.class),

    /* Multi-zone pub.edholm.jifx.messages */
    SetColorZones(501, null),
    GetColorZones(502, pub.edholm.jifx.messages.payloads.Get.class),
    StateZone(503, null),
    StateMultiZone(506, null),

    Unknown(-1, null);

    private final short type;
    private final Class<? extends Message> implementationClass;

    MessageType(int type, Class<? extends Message> implementationClass) {
        this.type = (short) type;
        this.implementationClass = implementationClass;
    }

    public short type() {
        return type;
    }

    public Class<? extends Message> getImplementationClass() {
        return implementationClass;
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
