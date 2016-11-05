package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.messages.Message;
import pub.edholm.jifx.messages.MessageConstants;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class Time implements Message {
    private final long time;
    private final byte[] content;

    /**
     * All time values have a precision of nanoseconds.
     * When an absolute time value is provided, then it is the number of nanoseconds since the epoch, i.e Thursday 1st January 1970 00:00:00.
     */
    public Time(long time) {
        this.time = time;
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(MessageConstants.BYTE_ORDER);
        bb.putLong(this.time);
        content = bb.array();
    }

    /**
     * Nanoseconds since the epoch
     */
    public long getTime() {
        return time;
    }

    public Instant getInstant() {
        return Instant.ofEpochMilli(TimeUnit.NANOSECONDS.toMillis(time));
    }

    @Override
    public int size() {
        return content.length;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}
