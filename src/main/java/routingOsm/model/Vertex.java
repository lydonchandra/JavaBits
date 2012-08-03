package routingOsm.model;

import routingOsm.primitives.Const;
import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 6:32 PM
 */
public class Vertex extends LatLon
        implements Const {

    private transient long cacheIndex;
    private transient boolean isShared;
    private transient boolean isConst;
    private int id;
    private int refCounter;
    private ViaNodeRestriction[] viaNodeRestrictions;
    private long osmId;
    private byte clazz;

    public void setId( int id ) { this.id = id; }
    public int getId() { return this.refCounter; }

    public int getRefCounter() { return this.refCounter; }
    public int incRefCounter() { return ++this.refCounter; }

    public void setOsmId(long osmId) { this.osmId = osmId; }
    public long getOsmId() { return this.osmId; }

    public byte getClazz() { return this.clazz; }
    public void setClazz(byte clazz) { this.clazz = clazz; }

    public ViaNodeRestriction[] getViaNodeRestrictions() {
        return this.viaNodeRestrictions;
    }

    public boolean isShared() { return this.isShared; }
    public void setShared(boolean isShared) { this.isShared = isShared; }


    /**
     * Init this.viaNodeRestrictions based on given restrictions[]
     * @param restrictions Array of Restriction
     */
    public void setRestrictions(Restriction[] restrictions) {
        if( this.isConst )
            throw new UnsupportedOperationException("Vertex-Restrictions cannot be reset after being used in Serialization.");

        if( null == restrictions )
                return;

        int nVnr = restrictions.length;
        this.viaNodeRestrictions = new ViaNodeRestriction[nVnr];

        for( int i=0; i < nVnr; i++ ) {
            ViaNodeRestriction vnr = new ViaNodeRestriction();
            vnr.setFlags(
                    (byte) restrictions[i].getFlags());

            this.viaNodeRestrictions[i] = vnr;
        }
    }

    public void clear() {
        this.id = 0;
        this.refCounter = 0;
        this.latLon = -9223372036854775808L;
        this.viaNodeRestrictions = null;
        this.isConst = false;
        this.isShared = false;
        this.cacheIndex = 0L;
        this.osmId = 0L;
    }

    public long getCacheReadIndex() {
        return this.cacheIndex;
    }

    public void setCacheReadIndex(long index) {
        this.cacheIndex = index;
    }

    public boolean isOverridableBy( Var that ) {
        return true;
    }

    /**
     * De-serialize Vertex from stream
     * @param inStream
     * @return
     */
    public Var readFromStream( InStream inStream ) {
        // set to constant as we've deserialized it from stream
        this.isConst = true;
        this.id = inStream.readInt();
        this.clazz = inStream.readByte();
        this.refCounter = inStream.readByte();
        this.latLon = inStream.readLong();
        this.osmId = inStream.readLong();
        int nVnr = inStream.readByte();
        if( nVnr == 0 ) {
            this.viaNodeRestrictions = null;
        } else {
            this.viaNodeRestrictions = new ViaNodeRestriction[nVnr];
            for( int i=0; i < nVnr; i++ ) {
                ViaNodeRestriction vnr = new ViaNodeRestriction();
                vnr.setFrom( inStream.readInt() );
                vnr.setTo( inStream.readInt() );
                vnr.setFlags( inStream.readByte() );
                this.viaNodeRestrictions[i] = vnr;
            }
        }
        return this;
    }

    /**
     * Serialize Vertex object TO stream
     * by writing id, clazz, refCounter, latLon, osmId and viaNodeRestrictions to stream.
     * @param outStream
     */
    public void writeToStream(OutStream outStream) {
        this.isConst = true;
        outStream.writeInt( this.id );
        outStream.writeByte( this.clazz );
        outStream.writeByte( (byte)this.refCounter );
        outStream.writeLong( this.latLon );
        outStream.writeLong( this.osmId );

        int nVnr = 0;
        if( this.viaNodeRestrictions != null )
                nVnr = this.viaNodeRestrictions.length;

        outStream.writeByte( (byte)nVnr );

        for( int i=0; i<nVnr; i++ ) {
            ViaNodeRestriction vnr = this.viaNodeRestrictions[i];
            outStream.writeInt( vnr.from );
            outStream.writeInt( vnr.to );
            outStream.writeByte( vnr.flags );
        }
    }

    public String toString() {
        String s = "Id:" + this.id
                    + " Clazz:" + this.clazz
                    + " LatLon:" + getLat()
                    + "|" + getLon()
                    + " RefCounter:" + this.refCounter
                    + " OsmId:" + this.osmId;

        if( this.viaNodeRestrictions != null ) {
            int nVnr = this.viaNodeRestrictions.length;
            s = s + " ViaNodeRestrictions[" + nVnr + "]:";
            for( int i=0; i < nVnr; i++ ) {
                ViaNodeRestriction vnr = this.viaNodeRestrictions[i];
                s = s + " [" + i + "]" + vnr.toString();
            }
        }
        return s;
    }






    public class ViaNodeRestriction
    {
        private int from;
        private int to;
        private byte flags;

        public ViaNodeRestriction() {}

        public int getFrom() { return this.from; }
        public void setFrom(int from) { this.from = from; }

        public int getTo() { return this.to; }
        public void setTo(int to) { this.to = to; }

        public byte getFlags() { return this.flags; }
        public void setFlags(byte flags) { this.flags = flags; }

        public String toString() {
            return "From:" + this.from
                    + " To:" + this.to
                    + " Flags:" + this.flags
                    + "(" + Restriction.restrictionFlagsToString( this.flags) + ")";
        }
    }

}
