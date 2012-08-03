package routingOsm.primitives;

import java.io.*;

/**
 * User: lydonchandra
 * Date: 31/07/12
 * Time: 10:55 PM
 */
public class InStreamDisk implements InStream {
    private DataInputStream dis;
    private long readIndex;

    public InStreamDisk( File file ) {
        try {
            this.dis = new DataInputStream
                            ( new BufferedInputStream
                                    ( new FileInputStream(file), 32768));
            this.readIndex = 0L;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if( this.dis != null )
                    this.dis.close();
                    this.readIndex = 0L;

        } catch( Exception e) {
                throw new RuntimeException(e);
        }
    }

    public byte readByte() {
        try {
            this.readIndex += 1L;
            return this.dis.readByte();
        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public short readShort() {
        try {
            this.readIndex += 2L;
            return this.dis.readShort();
        } catch( Exception e) {
            throw new RuntimeException (e);
        }
    }

    public int readInt() {
        try {
            this.readIndex += 4L;
            return this.dis.readInt();
        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public long readLong() {
        try {
            this.readIndex += 8L;
            return this.dis.readLong();
        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEof() {
        try {
            return this.dis.available() == 0;
        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public long getReadIndex() {
        return this.readIndex;
    }

    public int readBytes( byte[] buffer ) {
        int nRead = 0;
        try {
            nRead = this.dis.read(buffer);
            if( nRead > 0 )
                this.readIndex += nRead;
            else
                nRead = 0;

        } catch( Exception e ) {
            throw new RuntimeException( e );
        }
        return nRead;
    }
}
