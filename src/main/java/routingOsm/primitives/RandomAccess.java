package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 2/08/12
 * Time: 4:31 AM
 */
public abstract interface RandomAccess
        extends InStream, OutStream
{
    public abstract void setWriteIndexEof();

    public abstract void setWriteIndex(long paramLong);

    public abstract void setReadIndex(long paramLong);

    public abstract void clear();
}
