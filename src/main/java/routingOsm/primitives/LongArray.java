package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 4/08/12
 * Time: 7:29 PM
 */
public class LongArray
        implements Swappable {

    private static final long MAXMEM = 17179869184L; // 16 GiB
    private static final int TYPELEN = 4;
    private static final int BLOCKBITS = 17;
    private static final int BLOCKSIZE = 131072; // 128 KiB
    private static final int BLOCKMASK = 131071;
    private static final int SLOTSIZE = 32768;   // 32 KiB
    private static final int NOT_FOUND = -2147483648; // 0x8000 0000
    private static final long NULLVALUE = -9223372036854775808L;  // 0x8000 0000 0000 0000
    private int maxIdx = -1;
    private Object[] slots = new Object[SLOTSIZE];
    private boolean isSorted = false;
    private Swappable swappable;

    public LongArray( Swappable swappable ) {
        this.swappable = swappable;
    }

    public LongArray() {}


    @Override
    public void swap(int paramInt1, int paramInt2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
