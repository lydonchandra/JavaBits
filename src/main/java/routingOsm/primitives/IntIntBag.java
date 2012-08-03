package routingOsm.primitives;

/**
 * User: lydonchandra
 * Date: 30/07/12
 * Time: 9:02 PM
 */
public class IntIntBag {
    private IntArray values = new IntArray();
    private IntArray keys = new IntArray( this.values );
    public static final int NULLVALUE = -2147483648;
    private int size;

    public int getSize() { return this.size; }

    public void clear() {
        this.keys.clear();
        this.values.clear();
        this.size = 0;
    }

    public IntArray getKeys() {
        return this.keys;
    }

    public IntArray getValues() {
        return this.values;
    }

    public void add( int key, int value ) {
        this.keys.set( this.size, key );
        this.values.set( this.size, value );
        this.size += 1;
    }

    public int get( int key ) {
        int idx = this.keys.searchAny( key );
        if( -2147483648 == idx ) return -2147483648;
        return this.values.get(idx);
    }

    // get all values with key 'key'
    public int[] getAll( int key )
    {
        int[] idxs = this.keys.searchAll( key );
        if( null == idxs )
                return null;

        for( int i=0; i< idxs.length; i++ ) {
            idxs[i] = this.values.get( idxs[i] );
        }
        return idxs;
    }

    public void sort() {
        this.keys.sort();
    }
}
