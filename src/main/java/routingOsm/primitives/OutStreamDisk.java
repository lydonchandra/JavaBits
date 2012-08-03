package routingOsm.primitives;

import java.io.*;

/**
 * User: lydonchandra
 * Date: 31/07/12
 * Time: 11:46 PM
 */
public class OutStreamDisk
        implements OutStream, Concatenable {

    private DataOutputStream dos;
    private long writeIndex;

    public OutStreamDisk( File file ) {
        try {
            this.dos = new DataOutputStream(
                                new BufferedOutputStream(
                                        new FileOutputStream(file), 32768));
            this.writeIndex = 0L;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if( this.dos != null )
                    this.dos.close();

            this.writeIndex = 0L;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void concatFile( File file ) {
        try {
            byte[] buffer = new byte[4096];
            DataInputStream dis = new DataInputStream(
                                            new BufferedInputStream(
                                                    new FileInputStream(file),
                                                    32768));

            for( int nRead = dis.read(buffer)
                 ; nRead >= 0
                 ; nRead = dis.read(buffer) ) {

                this.dos.write( buffer, 0, nRead );
                this.writeIndex += nRead;
            }
            dis.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeByte( byte b ) {
        try {
            this.writeIndex += 1L;
            this.dos.writeByte(b);

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public void writeShort( short s ) {
        try {
            this.writeIndex += 2L;
            this.dos.writeShort(s);

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public void writeInt( int i ) {
        try {
            this.writeIndex += 4L;
            this.dos.writeInt(i);

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public void writeLong( long l ) {
        try {
            this.writeIndex += 8L;
            this.dos.writeLong(l);
        }
        catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public long getWriteIndex() {
        return this.writeIndex;
    }

    public void writeBytes( byte[] bytes, int len ) {
        throw new UnsupportedOperationException( "writeBytes not yet implemented");
    }
}
