package pub.edholm.jifx.library.payloads;

import pub.edholm.jifx.library.AbstractBuilder;
import pub.edholm.jifx.library.AbstractMessage;
import pub.edholm.jifx.library.MessageType;
import pub.edholm.jifx.library.datatypes.Time;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.headers.Header;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Emil Edholm on 2016-11-27.
 */
public class StateInfo extends AbstractMessage {
    private final Time time, uptime, downtime;

    private StateInfo(Header header, byte[] payloadContent, Builder b) {
        super(header, payloadContent);
        this.time = b.time;
        this.uptime = b.uptime;
        this.downtime = b.downtime;
    }

    public static class Builder extends AbstractBuilder<StateInfo, Builder> {
        private Time time, uptime, downtime;

        public Builder() {
            super(MessageType.StateInfo, Constants.SIZE_STATE_INFO);
        }

        public Builder time(Time time) {
            this.time = time;
            return this;
        }

        public Builder uptime(Time uptime) {
            this.uptime = uptime;
            return this;
        }

        public Builder downtime(Time donwtime) {
            this.downtime = donwtime;
            return this;
        }

        @Override
        public StateInfo build() {
            ByteBuffer buffer = ByteUtils.allocateByteBuffer(Constants.SIZE_STATE_INFO);
            buffer.put(time.getContent()).put(uptime.getContent()).put(downtime.getContent());
            return new StateInfo(buildHeader(), buffer.array(), this);
        }

        @Override
        protected Builder thisObject() {
            return this;
        }
    }

    public static StateInfo valueOf(byte[] content) {
        final int SIZE = Constants.SIZE_HEADER + Constants.SIZE_STATE_INFO;
        if (content.length < SIZE) {
            throw MalformedMessageException.createInvalidSize("StateInfo", SIZE, content.length);
        }

        final Header h = Header.valueOf(content);
        final byte[] payload = Arrays.copyOfRange(content, Constants.SIZE_HEADER, SIZE);

        Builder b = new Builder();
        b.time = Time.valueOf(Arrays.copyOfRange(payload, 0, Constants.SIZE_TIME));
        b.uptime = Time.valueOf(Arrays.copyOfRange(payload, Constants.SIZE_TIME, 2 * Constants.SIZE_TIME));
        b.downtime = Time.valueOf(Arrays.copyOfRange(payload, 2 * Constants.SIZE_TIME, Constants.SIZE_STATE_INFO));
        return new StateInfo(h, payload, b);
    }

    /**
     * current time (absolute time in nanoseconds since epoch)
     */
    public Time getTime() {
        return time;
    }

    /**
     * time since last power on (relative time in nanoseconds)
     */
    public Time getUptime() {
        return uptime;
    }

    /**
     * last power off period, 5 second accuracy (in nanoseconds)
     */
    public Time getDowntime() {
        return downtime;
    }

    @Override
    public String toString() {
        return "StateInfo{" +
                "time: " + time +
                ", uptime: " + uptime +
                ", downtime: " + downtime +
                '}';
    }
}
