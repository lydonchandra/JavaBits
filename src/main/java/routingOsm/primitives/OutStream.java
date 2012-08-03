package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 1:31 AM
 */
public abstract interface OutStream {
    public abstract void writeByte( byte paramByte );

    public abstract void writeBytes( byte[] paramArrayOfByte, int paramInt );

    public abstract void writeShort( short paramShort );

    public abstract void writeLong( long paramLong );

    public abstract void writeInt( int paramInt );

    public abstract void close();

    public abstract long getWriteIndex();

}
