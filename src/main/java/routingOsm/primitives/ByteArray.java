package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 2/08/12
 * Time: 4:37 AM
 */
public class ByteArray implements Swappable
{
    private static final long MAXMEM = 34359738368L; // 32 GiB
    private static final int TYPELEN = 1;
    private static final int BLOCKBITS = 20;
    private static final int BLOCKSIZE = 1048576; // 2^20 == 1024KiB == 1MiB
    private static final int BLOCKMASK = 1048575;
    private static final int SLOTSIZE = 32768;    // 2^15 == 32 KiB

    private long maxIdx = -1L;

    private Object[] slots = new Object[SLOTSIZE];
    private Swappable swappable;

    public ByteArray( Swappable swappable ) {
        this.swappable = swappable;
    }

    public ByteArray() {}

    /**
     * Swap i and j around
     * @param i
     * @param j
     */
    public void swap( int i, int j ) {
        byte tmp = get(i);
        set( i, get(j) );
        set( j, tmp );

        if( this.swappable != null )
              this.swappable.swap(i,j);
    }

    /**
     * Set value at slot[slotIdx].block[blockIdx]
     * where: slotIdx == idx >>> 20, bit 20 to 34 of idx (15 bits total)
     *        blockIdx == idx & 1048575, bit 0 to 19 of idx (20 bits total)
     * @param idx
     * @param value
     */
    public void set( long idx, byte value ) {

        // can't set negative index on array
        if( idx < 0L )
             throw new ArrayIndexOutOfBoundsException(
                                            String.valueOf(idx) );

        // update maxIdx var
        if( idx > this.maxIdx )
                this.maxIdx = idx;

        // logical/unsigned right shift idx 20 bits
        int slotIdx = (int) (idx >>> BLOCKBITS);

        // get byte data for slot[slotIdx]
        byte[] block = (byte[]) this.slots[slotIdx];

        // init block if null
        if( null == block ) {
            block = new byte[BLOCKSIZE];
            this.slots[slotIdx] = block;
        }

        // make sure index is within 0 and BLOCKMASK(1048575)
        idx &= BLOCKMASK;
        block[ (int)idx ] = value;
    }

    /**
     * Get value at slot[slotIdx].block[blockIdx]
     * where: slotIdx == idx >>> 20, bit 20 to 34 of idx (15 bits total)
     *        blockIdx == idx & 1048575, bit 0 to 19 of idx (20 bits total)
     * @param idx
     */
    public byte get( long idx ) {

        // array doesnt have negative index
        if( idx < 0L )
            throw new ArrayIndexOutOfBoundsException( String.valueOf(idx) );

        // logical/unsigned right shift 20 bits
        int slotIdx = (int)(idx >>> BLOCKBITS);

        // get actual data
        byte[] block = (byte[])this.slots[slotIdx];

        // block not init'ed, return 0
        if( null == block )
             return 0;

        // make sure index is within 0 and BLOCKMASK(1048575)
        idx &= BLOCKMASK;

        return block[ (int) idx ];
    }

    public long getSize() {
        return this.maxIdx + 1L;
    }

    public static void main(String[] args) {
        ByteArray byteArray = new ByteArray();
//        int slotIdx = 1 << 20;
//        int blockIdx =
        byteArray.set(0L, (byte)123);
        System.out.println(byteArray.get(0L));

        byteArray.set(1L, (byte)255);
        System.out.println( byteArray.get(1L));
    }

    public void clear() {
        for( int i=0; i<32768; i++ ) {
            this.slots[i] = null;
        }
        this.maxIdx = -1L;
    }
}
