package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 2:59 AM
 */
public class VarLong implements Var {

    private long longValue;

    public VarLong() {
        this.longValue = 0L;
    }

    public VarLong( long longValue ) {
        this.longValue = longValue;
    }

    public long getLong() {
        return this.longValue;
    }

    public void setLong(long longValue) {
        this.longValue = longValue;
    }

    public String toString() {
        return String.valueOf( this.longValue );
    }

    public boolean equals(Object obj) {
        if( !(obj instanceof VarLong))
            return false;

        VarLong that = (VarLong)obj;
        return this.longValue == that.longValue;
    }

    public int hashCode() {
        // >>> logical right shift
        return (int)(this.longValue ^ this.longValue >>> 32 );
    }

    public boolean isOverridableBy( Var that ) {
        return false;
    }

    public static long readLongFromStream( InStream inStream ) {
        int idx = 0;
        long longValue = 0L;
        byte b = 0;
        do {
            b = inStream.readByte();
            long lowByte = b & 0x7F;
            lowByte <<= idx * 7;
            longValue |= lowByte;
            idx++;
        }
        while( b < 0 );

        return longValue;
    }

    public Var readFromStream( InStream inStream )
    {
        this.longValue = readLongFromStream( inStream );
        return this;
    }

    public static void writeLongToStream( long longValue, OutStream outStream ) {
        long highBits = longValue;
        do {
            long lowBits = highBits & 0x7F;
            byte b = (byte)(int)lowBits;
            highBits >>>= 7;
            if( highBits > 0L ) {
                b = (byte)( b | 0x80 );
            }
            outStream.writeByte(b);
        }
        while( highBits > 0L );
    }

    public void writeToStream(OutStream outStream) {
        writeLongToStream( this.longValue, outStream );
    }
}
