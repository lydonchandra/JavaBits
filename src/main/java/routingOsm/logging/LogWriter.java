package routingOsm.logging;

/**
 * User: lydonchandra
 * Date: 2/08/12
 * Time: 4:18 AM
 */
public abstract interface LogWriter {
    public abstract LogWriter setLogLevel( int paramInt );
    public abstract void log( String paramString, int paramInt );
    public abstract void close();
}
