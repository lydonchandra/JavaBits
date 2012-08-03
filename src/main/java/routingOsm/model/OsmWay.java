package routingOsm.model;

import routingOsm.converter.OsmDataException;
import routingOsm.primitives.*;

import java.util.Arrays;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 1:37 AM
 */
public class OsmWay implements Var {
    public static final int INTERN_FLAG_NULL = 0;
    public static final int INTERN_FLAG_ONEWAY = 1;
    public static final int INTERN_FLAG_SHUTTLE_TRAIN = 2;

    private int internFlags;
    private int flags;

    private long id;
    private byte clazz;
    private VarString name;

    private int kmh;

    private static final int INITIAL_REF_ARRAY_SIZE = 10;
    private long[] refs;
    private int refsSize;

    public OsmWay() {
        clear();
    }

    public OsmWay( long id, byte clazz, String name,
                   int flags, int kmh, int priority ) {

        this.id = id;
        this.clazz = clazz;
        this.name = new VarString(name);
        this.flags = flags;
        this.kmh = kmh;
        this.refs = null;
        this.refsSize = 0;
    }

    public OsmWay copy() {
        OsmWay way = new OsmWay();

        way.id = this.id;
        way.name = this.name;
        way.clazz = this.clazz;
        way.flags = this.flags;
        way.internFlags = this.internFlags;
        way.kmh = this.kmh;
        way.refs = Arrays.copyOf( this.refs, this.refsSize );
        way.refsSize = this.refsSize;

        return way;
    }

    public void clear() {
        this.id = 0L;
        this.name = new VarString();
        this.clazz = 0;
        this.flags = 0;
        this.internFlags = 0;
        this.kmh = 0;
        this.refsSize = 0;
    }

    public int getRefsSize() {
        return this.refsSize;
    }

    public long getRef(int idx) {
        return this.refs[idx];
    }

    public byte getClazz() { return this.clazz; }
    public void setClazz(byte clazz) { this.clazz = clazz; }

    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }

    public int getKmh() { return this.kmh; }
    public void setKmh(int kmh) { this.kmh = kmh; }

    public VarString getName() { return this.name; }
    public void setName( VarString name ) { this.name = name; }

    public int getFlags() { return this.flags; }
    public void setFlags( int flags ) { this.flags = flags; }

    public int getInternFlags() { return this.internFlags; }

    public OsmWay addRef(long refId) throws OsmDataException {
        if( null == this.refs ) {
            this.refs = new long[10];
        }
        if( (this.refsSize > 0)
            && (refId == this
                            .refs[ (this.refsSize-1) ]
               ))
        {
            throw new OsmDataException("Redundant Ref " + refId + " in way " + this.id );
        }

        if( this.refsSize == this.refs.length ) {
            this.refs = Arrays.copyOf( this.refs, this.refs.length * 2 );
        }
        this.refs[
                (this.refsSize++) ] = refId;

        return this;
    }

    public boolean isOneWay() {
        return (this.internFlags & 0x1) != 0;
    }

    public void setOneWay( boolean isOneWay ) {
        this.internFlags &= -2;
        if( isOneWay )
            this.internFlags |= 1;
    }

    public boolean isShuttleTrain() {
        return (this.internFlags & 0x2) != 0;
    }

    public void setShuttleTrain( boolean isShuttleTrain ) {
        this.internFlags &= -3;
        if( isShuttleTrain )
                this.internFlags |= 2;
    }

    public final void reverse() {
        int x = 0;
        for( int y = this.refsSize-1; x < y; y-- ) {
            long tmp = this.refs[x];
            this.refs[x] = this.refs[y];
            this.refs[y] = tmp;
            x++;
        }
    }

    public String toString() {
        String w = "Id:" + this.id + " Name:'" + this.name + "'" +
                   " Class:" + this.clazz + " Flags:" + this.flags +
                   " Kmh:" + this.kmh + "\nRefs[" + this.refsSize + "]:";

        for( int i=0; i<this.refsSize; i++ ) {
            if( i > 0 ) {
                w = w+",";
            }
            w = w + this.refs[i];
        }
        return w;
    }

    protected final void writeAttributesToStream( OutStream outStream ) {
        outStream.writeLong( this.id );
        outStream.writeByte( this.clazz );

        VarLong.writeLongToStream( this.flags, outStream );
        VarLong.writeLongToStream( this.internFlags, outStream );
        VarLong.writeLongToStream( this.kmh, outStream );
        this.name.writeToStream(outStream);
    }

    protected final void readAttributesFromStream(InStream inStream) {
        clear();

        this.id = inStream.readLong();
        this.clazz = inStream.readByte();

        this.flags = (int)VarLong.readLongFromStream( inStream );
        this.internFlags = (int)VarLong.readLongFromStream( inStream );
        this.kmh = (int) VarLong.readLongFromStream( inStream );

        this.name.readFromStream( inStream );
    }

    public boolean isOverridableBy(Var that) {
        return false;
    }

    public Var readFromStream(InStream inStream) {
        readAttributesFromStream( inStream );
        this.refsSize = (int)VarLong.readLongFromStream( inStream );

        this.refs = new long[ this.refsSize ];
        for( int i = 0; i < this.refsSize; i++ ) {
            this.refs[i] = inStream.readLong();
        }
        return this;
    }

    public void writeToStream( OutStream outStream ) {
        writeAttributesToStream(outStream);
        VarLong.writeLongToStream( this.refsSize, outStream );
        for( int i=0; i<this.refsSize; i++ ) {
            outStream.writeLong( this.refs[i] );
        }
    }

}
