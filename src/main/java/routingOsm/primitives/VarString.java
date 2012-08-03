package routingOsm.primitives;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 1:48 AM
 */
public class VarString implements Var {
    private static Charset CHARSET_UTF8 = Charset.forName("UTF-8");
    private byte[] bytes;

    public VarString() {
        this.bytes = new byte[0];
    }

    public VarString(String s) {
        if(null == s)
            this.bytes = new byte[0];
        else
            this.bytes = s.getBytes(CHARSET_UTF8);
    }

    public byte[] getUTF8Bytes() {
        return this.bytes;
    }

    public String toString() {
        if( null == this.bytes ) {
            return "";
        }
        return new String( this.bytes, CHARSET_UTF8 );
    }

    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    public boolean equals(Object obj) {
        if( !(obj instanceof VarString) )
            return false;

        if( obj == this )
            return true;

        VarString that = (VarString)obj;

        return Arrays.equals( this.bytes, that.bytes );
    }

    public Var readFromStream(InStream inStream) {
        int len = (int)VarLong.readLongFromStream(inStream);
        this.bytes = new byte[len];
        for(int i=0; i<len; i++ ) {
            this.bytes[i] = inStream.readByte();
        }
        return this;
    }

    public void writeToStream(OutStream outStream) {
        int len = this.bytes.length;
        VarLong.writeLongToStream(len, outStream);
        for( int i=0; i<len; i++ ) {
            outStream.writeByte( this.bytes[i] );
        }
    }

    public boolean isOverridableBy(Var that) {
        return false;
    }
}
