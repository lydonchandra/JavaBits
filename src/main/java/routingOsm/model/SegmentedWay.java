package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarLong;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 11:36 PM
 *
 * SegmentedWay has an array of WaySegment.
 */
public class SegmentedWay extends Way {
    protected WaySegment[] waySegments;

    public void setSegments( WaySegment[] segments ) {
        this.waySegments = segments;
    }

    public WaySegment[] getSegments() {
        return this.waySegments;
    }

    public void clear() {
        super.clear();
        this.waySegments = null;
    }

    public SegmentedWay readFromWayStream( InStream inStream ) {
        clear();
        super.readFromStream( inStream );
        return this;
    }

    /**
     * Serialize SegmentedWay to stream
     * @param outStream
     */
    public void writeToStream( OutStream outStream ) {
        super.writeAttributesToStream( outStream );
        int nSegments = this.waySegments.length;
        VarLong.writeLongToStream( nSegments, outStream );
        for( int i=0; i<nSegments; i++ ) {
            this.waySegments[i].writeToStream( outStream );
        }
    }

    /**
     * De-serialize SegmentedWays from stream
     * @param inStream
     * @return
     */
    public Var readFromStream( InStream inStream ) {

        // deserialize parent's (Way) attributes
        super.readAttributesFromStream( inStream );

        int nSegments = (int)VarLong.readLongFromStream( inStream );
        this.waySegments = new WaySegment[nSegments];

        // deserialize WaySegment[]
        for( int i=0; i<nSegments; i++ ) {
            this.waySegments[i] = new WaySegment();
            this.waySegments[i].readFromStream( inStream );
        }
        return this;
    }

    public String toString() {
        String s = super.toString();
        int nSegments = 0;
        if( this.waySegments != null ) {

            nSegments = this.waySegments.length;
            s = s + "\nSegments[" + nSegments + "]:";
            for( int i=0; i<nSegments; i++ ) {
                s = s + "\n" + this.waySegments[i].toString() + ")";
            }
        }
        return s;
    }

}
