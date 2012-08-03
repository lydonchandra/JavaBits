package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarLong;

import java.util.Arrays;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 4:31 PM
 *
 * Coords object stores coords array of long coord values.
 * long coord value is a long representative of LatLon
 */
public class Coords
    implements Var
{
    private long[] coords;
    private int size;

    public void clear() {
        this.coords = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * Get coords array,
     * and sync size with coords.length
     * @return
     */
    public long[] getCoords() {
        if( null == this.coords )
                return null;

        if( this.size != this.coords.length ) {
            this.coords = Arrays.copyOf( this.coords, this.size );
        }
        return this.coords;
    }

    /**
     * Reverse elements inside coords,
     * front element will be at rear,
     * rear element will be at front
     */
    public final void reverse() {
        int x = 0;
        for( int y=this.size-1; x<y; y-- ) {
            long tmp = this.coords[x];
            this.coords[x] = this.coords[y];
            this.coords[y] = tmp;
            x++;
        }
    }

    /**
     * Return LatLon[] as this.coords representation
     * @return
     */
    public LatLon[] getLatLons() {
        if( null == this.coords )
            return null;

        LatLon[] latLons = new LatLon[this.size];
        for( int i=0; i<this.size; i++ ) {
            latLons[i] = new LatLon();
            latLons[i].setLatLon( this.coords[i] );
        }
        return latLons;
    }

    /**
     * Set this.coords to given coords
     * @param coords
     */
    public void setCoords( long[] coords ) {
        this.coords = coords;
        if( null == coords )
            this.size = 0;
        else
            this.size = this.coords.length;
    }

    /**
     * Set this.coords to given LatLon[]
     * Given LatLon[] will be converted to long and then inserted into this.coords.
     * if latLons is null, empty this.coords
     * @param latLons
     */
    public void setCoords( LatLon[] latLons ) {
        if( null == latLons ) {
            this.coords = null;
            this.size = 0;
            return;
        }

        // copy latLons[] contents into this.coords
        this.size = latLons.length;
        this.coords = new long[this.size];
        for( int i=0; i<this.size; i++ ) {
            // we want getLatLon() which returns long
            this.coords[i] = latLons[i].getLatLon();
        }
    }

    public void addCoord( LatLon latLon ) {
        addCoord( latLon.getLatLon() );
    }

    public void addCoord( long coord ) {
        // allocate coords[] if it hasn't
        if( null == this.coords)
                this.coords = new long[10];

        // coords array is full, expand capacity 2x
        if( this.coords.length == this.size ) {
                this.coords = Arrays.copyOf( this.coords,
                                             this.coords.length * 2);
        }

        // insert given coord at [this.size] and THEN increment this.size
        this.coords[ (this.size++) ] = coord;
    }

    /**
     * Write/Serialize this Coords object to stream
     * @param outStream
     */
    public void writeToStream( OutStream outStream ) {
        // serialize this.size
        VarLong.writeLongToStream( this.size, outStream );

        // serialize each coord
        for( int i=0; i<this.size; i++ ) {
            outStream.writeLong( this.coords[i] );
        }
    }

    /**
     * De-serialize coords from stream
     * @param inStream
     * @return
     */
    public Var readFromStream(InStream inStream ) {
        // read size/length first
        this.size = (int)VarLong.readLongFromStream( inStream );
        this.coords = new long[ this.size ];

        // read coords[] object from stream
        for( int i=0; i<this.size; i++ ) {
            this.coords[i] = inStream.readLong();
        }
        return this;
    }

    public boolean isOverridableBy(Var var) {
        if( null == this.coords )
                return false;

        if( (var instanceof Coords) ) {
            Coords that = (Coords)var;
            if( null == that.coords )
                 return true;

            return that.coords.length <= this.coords.length;
        }
        return false;
    }

    public String toString() {
        String s = "";
        LatLon latLon = new LatLon();
        for( int i=0; i<this.size; i++ ) {

            // first element don't need comma infrontof it
            if( i > 0 )
                s = s + ",";

            // init latLon and call its toString()
            latLon.setLatLon( this.coords[i] );
            s = s + latLon.toString();
        }
        return s;
    }










}
