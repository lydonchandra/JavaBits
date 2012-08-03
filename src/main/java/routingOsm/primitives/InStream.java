package routingOsm.primitives;

public abstract interface InStream {
    public abstract byte readByte();

    public abstract int readBytes( byte[] paramArrayOfByte );

    public abstract short readShort();

    public abstract long readLong();

    public abstract int readInt();

    public abstract boolean isEof();

    public abstract long getReadIndex();

    public abstract void close();
}
