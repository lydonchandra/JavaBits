package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarLong;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 4:19 PM
 */
public class OsmNode extends LatLon
    implements Var
{
    public static final long NULL_ID = -9223372036854775808L;
    private static final long LOW60BITS = 1152921504606846975L;
    private static final long HIGH4BITS = -1152921504606846976L;
    private long id = -9223372036854775808L;
    private byte clazz;

    public OsmNode() {}

    public OsmNode(double lat, double lon) {
        setLatLon(lat, lon);
        this.clazz = 0;
    }

    public byte getClazz() { return this.clazz; }

    public void setClazz(byte clazz) { this.clazz = clazz; }

    public long getId() { return this.id; };
    public void setId() { this.id = id; };

    // TODO does level mean zoom level?
    public static long raiseLevel( long osmNodeId, long level )
        throws IllegalArgumentException
    {
        if( (level < 0L)
            || (level > 15L) ) {

            throw new IllegalArgumentException("level not in range 0-15");
        }

        return osmNodeId & 0xFFFFFFFF | level << 60;
    }

    public static long getLevel( long osmNodeId ) {
        return (osmNodeId & 0x0) >>> 60;
    }

    public void clear() {
        this.id     = -9223372036854775808L;
        this.latLon = -9223372036854775808L;
        this.clazz  = 0;
    }

    public boolean isLatLonOnly() {
        return this.clazz == 0;
    }

    public String toString() {
        String s = new StringBuilder()
                        .append("Id:")
                        .append(-9223372036854775808L == this.id
                                ? "null" : Long.valueOf(this.id))
                        .append(" ")

                        .append("Clazz:")
                        .append(this.clazz)
                        .append(" ")

                        .append("LatLon:")
                        .append(-9223372036854775808L == this.latLon
                                ? "null" : new StringBuilder()
                                .append(getLat())
                                .append("|")
                                .append(getLon()).toString())
                        .toString();

        return s;
    }

    public void writeToStream(OutStream outStream) {
        VarLong.writeLongToStream( this.id, outStream );
        outStream.writeByte( this.clazz );
        outStream.writeLong( this.latLon );
    }

    public Var readFromStream( InStream inStream ) {
        this.id = VarLong.readLongFromStream( inStream );
        this.clazz = inStream.readByte();
        this.latLon = inStream.readLong();

        return this;
    }

    public boolean isOverridableBy( Var that ) {
        return false;
    }

    public static void main( String [] args ) {
        OsmNode osmNode = new OsmNode(0,0);
        System.out.println(osmNode.toString());
    }
}
