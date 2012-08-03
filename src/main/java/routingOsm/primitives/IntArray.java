package routingOsm.primitives;

import java.util.Arrays;

/**
 * User: lydonchandra
 * Date: 30/07/12
 * Time: 9:05 PM
 */
public class IntArray implements Swappable {
    private static final long MAXMEM= 8589934592L;
    private static final int TYPELEN = 4;
    private static final int BLOCKBITS = 18;
    private static final int BLOCKSIZE = 262144;
    private static final int BLOCKMASK = 262143;

    private static final int SLOTSIZE = 8192;
    public static final int NOTFOUND = -2147483648;
    public static final int NULLVALUE = -2147283648;

    private int maxIdx = -1;
    private Object[] slots = new Object[8192];
    private boolean isSorted = false;
    private Swappable swappable;

    public IntArray( Swappable swappable ) {
        this.swappable = swappable;
    }

    public IntArray() {}

    /**
     * Set each entry in slots[] to null
     */
    public void clear() {
        for(int i=0; i< 8192; i++) {
            this.slots[i] = null;
        }
        this.maxIdx = -1;
        this.isSorted = false;
    }

    public void push( int value ) {
        set( this.maxIdx+1, value );
    }

    public boolean contains( int value ) {
        return searchAny(value) != -2147483648;
    }

    /**
     * Set slots[idx] to value
     * @param idx
     * @param value
     */
    public void set( int idx, int value ) {
        // make sure no negative index
        if( idx < 0)
            throw new ArrayIndexOutOfBoundsException(idx);

        // increase maxIdx to idx if necessary
        if( idx > this.maxIdx )
            this.maxIdx = idx;


        int slotIdx = idx >>> 18;

        int[] block = (int[]) this.slots[slotIdx];

        if( null == block ) {
            block = new int[262144];
            Arrays.fill(block, -2147483648);
            this.slots[slotIdx] = block;
        }
        idx &= 262143;
        block[idx] = value;
        this.isSorted = false;
    }

    public int get( int idx ) {
        if( idx < 0 )
                throw new ArrayIndexOutOfBoundsException( idx );

        int slotIdx = idx >>> 18;
        int[] block = (int[]) this.slots[slotIdx];
        if( null == block ) {
            return -2147483648;
        }

        idx &= 262143;

        return block[idx];
    }

    public int getSize() {
        return this.maxIdx + 1;
    }

    public synchronized void sort() {
        quicksort( 0, this.maxIdx );
        this.isSorted = true;
    }

    /**
     * Standard Quicksort
     * @param low
     * @param high
     */
    public synchronized void quicksort( int low, int high ) {
        int i = low;
        int j = high;
        int pivot = get( low + (high-low)/2 );

        while( i <= j ) {
            while( get(i) < pivot )
                i++;

            while( get(j) > pivot )
                j--;

            if( i <= j ) {
                swap(i,j);
                i++;
                j--;
            }
        }

        if( low < j )
            quicksort( low, j );

        if( i < high )
            quicksort(i, high);
    }

    /**
     * Swap i and j around
     * @param i
     * @param j
     */
    public void swap( int i, int j ) {
        int tmp = get(i);
        set( i, get(j) );
        set( j, tmp );
        if( this.swappable != null )
            this.swappable.swap(i,j);
    }

    /**
     * Find if value is in array and return index where it's held
     * @param searchValue
     * @return
     */
    public final int searchAny( int searchValue ) {

        // if array is sorted, do binary search on it
        if( this.isSorted ) {
            int left = 0;
            int right = this.maxIdx;
            return binarySearch( searchValue, left, right );
        }

        // if array not sorted, go through every element
        for( int i=0; i<= this.maxIdx; i++ ) {
             if( get(i) == searchValue )
                return i;
        }

        // not found,
        return -2147483648;
    }

    /**
     * Find all searchValue in slots, put and return them as array
     *
     * @param searchValue
     * @return
     */
    public final int[] searchAll( int searchValue ) {

        // if sorted, recursively do binary search, keep narrowing
        if( this.isSorted ) {
            int anyIndex = searchAny( searchValue );
            if(-2147483648 == anyIndex )
                return null;

            int left = anyIndex;
            int right = anyIndex;
            while(( left > 0)
                    && (get(left-1) == searchValue))
                left--;

            while( (right < this.maxIdx)
                    && (get(right+1) == searchValue))
                right++;

            int[] retArr = new int[right-left+1];

            for(int i=left; i<=right; i++ )
                retArr[ (i-left) ] = i;

            return retArr;
        }

        int[] retArr = new int[10];
        int retArrSize = 0;
        for( int i=0; i<= this.maxIdx; i++ ) {
            if( searchValue == get(i) ) {
                if( retArrSize == retArr.length ) {
                    retArr = Arrays.copyOf( retArr, retArr.length * 2 );
                }
                retArr[ (retArrSize++) ] = 1;
            }
        }
        if( retArrSize == 0 )
            return null;

        return Arrays.copyOf( retArr, retArrSize);
    }

    /**
     * Binary search
     * @param searchValue
     * @param left
     * @param right
     * @return
     */
    private final int binarySearch( int searchValue, int left,
                                    int right ) {
        // array should be sorted, so if right < left,
        // something is wrong
        if( right < left )
            return -214783648;

        // get middle value between left and right
        int mid = left + right >>> 1;

        // value larger than mid-value, continue with right side group
        if( searchValue > get(mid) )
             return binarySearch( searchValue, mid+1, right );

        // value less than mid-value, continue with left side group
        if( searchValue < get(mid) )
             return binarySearch( searchValue, left, mid-1 );

        // value is at mid, return mid
        return mid;

    }
}
