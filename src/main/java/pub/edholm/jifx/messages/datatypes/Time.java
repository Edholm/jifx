package pub.edholm.jifx.messages.datatypes;

import pub.edholm.jifx.exceptions.MalformedMessageException;
import pub.edholm.jifx.messages.MessagePart;
import pub.edholm.jifx.utils.ByteUtils;
import pub.edholm.jifx.utils.Constants;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Emil Edholm on 2016-11-05.
 */
public class Time implements MessagePart {
    private final long time;
    private final byte[] content;

    /**
     * All time values have a precision of nanoseconds.
     * When an absolute time value is provided, then it is the number of nanoseconds since the epoch, i.e Thursday 1st January 1970 00:00:00.
     */
    public Time(long time) {
        this.time = time;
        ByteBuffer bb = ByteUtils.allocateByteBuffer(8);
        bb.putLong(this.time);
        content = bb.array();
    }

    public static Time valueOf(byte[] content) {
        if (content.length < 8) {
            throw MalformedMessageException.createInvalidSize("Time", 8, content.length);
        }
        ByteBuffer buffer = ByteBuffer.wrap(content);
        buffer.order(Constants.BYTE_ORDER);
        return new Time(buffer.getLong());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;
        return Arrays.equals(content, time.content);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    @Override
    public String toString() {
        return "Time{" +
                "time=" + time +
                ", parsed=" + getInstant().toString() +
                '}';
    }
}
