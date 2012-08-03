package routingOsm.primitives;

import java.io.ByteArrayInputStream;

/**
 * User: lydonchandra
 * Date: 2/08/12
 * Time: 4:34 AM
 */
public final class RandomAccessMemory
    implements RandomAccess
{
    private ByteArray byteArray = new ByteArray();
    private long writeIndex;
    private long readIndex;
    private byte[] buffer = new byte[1024];

    /**
     * Empty byteArray, set write & read index to 0
     */
    public void clear() {
        this.byteArray.clear();
        this.writeIndex = 0L;
        this.readIndex = 0L;
    }

    /**
     * Set read index to index
     * @param index
     */
    public void setReadIndex( long index ) {
        this.readIndex = index;
    }

    public long getReadIndex() {
        return this.readIndex;
    }

    public long getWriteIndex() {
        return this.writeIndex;
    }

    public void setWriteIndex( long index ) {
        this.writeIndex = index;
    }

    /**
     * Set write index to byte array size
     */
    public void setWriteIndexEof() {
        this.writeIndex = this.byteArray.getSize();
    }

    /**
     * Write byte to byte array,
     * and increment writeIndex
     * @param b
     */
    public void writeByte( byte b ) {
        this.byteArray.set( this.writeIndex++, b );
    }

    /**
     * Read byte from byteArray,
     * increment readIndex
     * @return
     */
    public byte readByte() {
        return this.byteArray.get( this.readIndex++ );
    }

    /**
     * Write long into byteBarray, in turn calls writeByte 8x
     * @param l
     */
    public void writeLong(long l) {
        Bits.putLong( this.buffer, 0, l );
        for( int i=0; i<8; i++ ) {
            writeByte(this.buffer[i]);
        }
    }

    /**
     * Read long from byte array, in turn calls readByte 8x
     * @return
     */
    public long readLong() {
        for( int i=0; i<8; i++ ) {
            this.buffer[i] = readByte();
        }
        return Bits.getLong( this.buffer, 0 );
    }

    /**
     * Write int to byteArray, in turn calls writeByte 4x
     * @param i
     */
    public void writeInt( int i ) {
        Bits.putInt( this.buffer, 0, i );
        for( int j=0; j<4; j++ ) {
            writeByte( this.buffer[j] );
        }
    }

    /**
     * Read int by calling readByte 4x
     * @return
     */
    public int readInt() {
        for( int i=0; i<4; i++ ) {
            this.buffer[i] = readByte();
        }
        return Bits.getInt( this.buffer, 0);
    }

    /**
     * Write short by calling writeByte 2x
     * @param s
     */
    public void writeShort( short s ) {
        Bits.putShort( this.buffer, 0, s );
        for( int j=0; j<2; j++ ) {
            writeByte( this.buffer[j]);
        }
    }

    /**
     * Read short by calling readByte 2x
     * @return
     */
    public short readShort() {
        for( int i=0; i<2; i++ ) {
            this.buffer[i] = readByte();
        }
        return Bits.getShort( this.buffer, 0 );
    }

    /**
     * EOF when readIndex >= byte array size
     */
    public boolean isEof() {
        return this.readIndex >= this.byteArray.getSize();
    }

    /**
     *  Clear byte array, read and write index
     */
    public void close() {
        clear();
        this.byteArray = null;
    }

    /**
     *
     * @param buffer
     * @return
     */
    public int readBytes( byte[] buffer ) {
        throw new UnsupportedOperationException("readBytes not yet implemented");
    }

    /**
     * Write bytes by calling writeByte X times
     * @param bytes
     * @param len
     */
    public void writeBytes( byte[] bytes, int len ) {
        for( int i=0; i < len; i++ ) {
            writeByte( bytes[i] );
        }
    }


}
