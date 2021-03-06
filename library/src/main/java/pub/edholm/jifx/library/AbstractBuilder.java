package pub.edholm.jifx.library;

import pub.edholm.jifx.library.headers.Frame;
import pub.edholm.jifx.library.headers.FrameAddress;
import pub.edholm.jifx.library.headers.Header;

/**
 * Created by Emil Edholm on 2016-11-10.
 */
public abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> {
    // Frame
    protected boolean tagged = false;
    protected long source = 0;
    protected int payloadSize;

    // Frame address
    protected byte[] target = new byte[8];
    protected boolean ackRequired = false;
    protected boolean resRequired = false;
    protected int sequence = 0x0;

    // Protocol header
    protected MessageType messageType;

    /**
     * @see Frame.Builder#payloadSize(int)
     */
    public AbstractBuilder(MessageType messageType, int payloadSize) {
        this.messageType = messageType;
        this.payloadSize = payloadSize;
    }

    /**
     * @see Frame.Builder#tagged(boolean)
     */
    public B tagged(boolean tagged) {
        this.tagged = tagged;
        return thisObject();
    }

    /**
     * @see Frame.Builder#source(long)
     */
    public B source(long source) {
        this.source = source;
        return thisObject();
    }

    /**
     * @see FrameAddress.Builder#target(byte[])
     */
    public B target(byte[] target) {
        this.target = target;
        return thisObject();
    }

    /**
     * @see FrameAddress.Builder#ackRequired(boolean)
     */
    public B ackRequired(boolean ackRequired) {
        this.ackRequired = ackRequired;
        return thisObject();
    }

    /**
     * @see FrameAddress.Builder#resRequired(boolean)
     */
    public B resRequired(boolean resRequired) {
        this.resRequired = resRequired;
        return thisObject();
    }

    /**
     * @see FrameAddress.Builder#sequence(int)
     */
    public B sequence(int sequence) {
        this.sequence = sequence;
        return thisObject();
    }

    protected Header buildHeader() {
        return new Header.Builder(messageType, payloadSize)
                .ackRequired(ackRequired)
                .resRequired(resRequired)
                .sequence(sequence)
                .source(source)
                .tagged(tagged)
                .target(target).build();
    }

    public abstract T build();

    protected abstract B thisObject();
}
