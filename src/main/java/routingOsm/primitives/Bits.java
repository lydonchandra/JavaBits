package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 2/08/12
 * Time: 10:11 PM
 */
public class Bits {
    public static short getShort( byte[] b, int off ) {
        return (short)(
                    ( (b[(off+1)]
                       & 0xFF) << 0 )
                    + ( b[(off+0)] << 8 )
                );
    }

    /**
     * Get 'int' value representing byte array b
     * @param b
     * @param off
     * @return
     */
    public static int getInt( byte[] b, int off ) {
        return ((b[(off+3)] & 0xFF) << 0)
               + ((b[(off+2)] & 0xFF) << 8)
               + ((b[(off+1)] & 0xFF) << 16)
               + ((b[(off+0)] << 24));
    }

    /**
     * Get a 'float' value representing byte array 'b'
     * @param b
     * @param off
     * @return
     */
    public static float getFloat( byte[] b, int off ) {
        int i = ( (b[(off+3)] & 0xFF) << 0 ) +
                ( (b[(off+2)] & 0xFF) << 8 ) +
                ( (b[(off+1)] & 0xFF) << 16) +
                ( (b[(off+0)] << 24 ));

        return Float.intBitsToFloat( i );
    }

    /**
     * Get a 'long' value representing byte array 'b'
     * @param b
     * @param off
     * @return
     */
    public static long getLong( byte[] b, int off ) {
        long longVal = 0L;

        // + has higher operator precedence than <<
        // use brackets!!
        longVal +=
            ( (long)(b[(off+7)] & 0xFF) << 0) +
            ( (long)(b[(off+6)] & 0xFF) << 8) +
            ( (long)(b[(off+5)] & 0xFF) << 16) +
            ( (long)(b[(off+4)] & 0xFF) << 24) +
            ( (long)(b[(off+3)] & 0xFF) << 32) +
            ( (long)(b[(off+2)] & 0xFF) << 40) +
            ( (long)(b[(off+1)] & 0xFF) << 48) +
            ( (long)(b[(off+0)] << 56));

        return longVal;
    }

    /**
     * Put short 'val' into b array
     * @param b
     * @param off
     * @param val
     */
    public static void putShort( byte[] b, int off, short val ) {
        b[ (off+1) ] = (byte)(val >>> 0);
        b[ (off+0) ] = (byte)(val >>> 8);
    }

    /**
     * Put int 'val' into b array
     * @param b
     * @param off
     * @param val
     */
    public static void putInt( byte[] b, int off, int val ) {
        b[ (off+3) ] = (byte)(val >>> 0);
        b[ (off+2) ] = (byte)(val >>> 8);
        b[ (off+1) ] = (byte)(val >>> 16);
        b[ (off+0) ] = (byte)(val >>> 24);
    }

    /**
     * Put float 'val' into b array
     * @param b
     * @param off
     * @param val
     */
    public static void putFloat( byte[] b, int off, float val ) {
        int i = Float.floatToIntBits(val);

        b[(off+3)] = (byte)(i >>> 0);
        b[(off+2)] = (byte)(i >>> 8);
        b[(off+1)] = (byte)(i >>> 16);
        b[(off+0)] = (byte)(i >>> 24);
    }

    /**
     * Put long 'val' into b array
     * @param b
     * @param off
     * @param val
     */
    public static void putLong( byte[] b, int off, long val ) {
        b[(off+7)] = (byte)( val >>> 0 );
        b[(off+6)] = (byte)( val >>> 8 );
        b[(off+5)] = (byte)( val >>> 16);
        b[(off+4)] = (byte)( val >>> 24);
        b[(off+3)] = (byte)( val >>> 32);
        b[(off+2)] = (byte)( val >>> 40);
        b[(off+1)] = (byte)( val >>> 48);
        b[(off+0)] = (byte)( val >>> 56);
    }
}
