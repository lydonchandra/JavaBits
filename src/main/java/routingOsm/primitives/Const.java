package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 5:45 PM
 */
public abstract interface Const extends Var {

    public abstract long getCacheReadIndex();

    public abstract void setCacheReadIndex( long paramLong );
}
