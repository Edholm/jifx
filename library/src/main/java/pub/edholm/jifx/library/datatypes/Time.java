package pub.edholm.jifx.library.datatypes;

import pub.edholm.jifx.library.utils.Constants;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.utils.ByteUtils;
import pub.edholm.jifx.library.MessagePart;

import java.nio.ByteBuffer;
import java.time.Duration;
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
        ByteBuffer bb = ByteUtils.allocateByteBuffer(Constants.SIZE_TIME);
        bb.putLong(this.time);
        content = bb.array();
    }

    public static Time valueOf(byte[] content) {
        if (content.length < Constants.SIZE_TIME) {
            throw MalformedMessageException.createInvalidSize("Time", Constants.SIZE_TIME, content.length);
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

    public Duration getDuration() {
        return Duration.ofNanos(time);
    }

    @Override
    public int size() {
        return content.length;
    }

    @Override
    public byte[] getContent() {
        return Arrays.copyOf(content, content.length);
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
                "time: " + Long.toUnsignedString(time) + "ns" +
                ", instant: " + getInstant().toString() +
                ", duration: " + getDuration().toString() +
                '}';
    }
}
