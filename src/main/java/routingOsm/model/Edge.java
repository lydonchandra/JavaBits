package routingOsm.model;

import routingOsm.primitives.Const;
import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 5:47 PM
 */
public class Edge implements Const {
    private int id;
    private int sourceId;
    private int targetId;
    private float km;
    private byte kmh;
    private byte noTurnBits;
    private int flags;
    private transient long readIndex;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public float getKm() { return this.km; }
    public void setKm(float km) { this.km = km; }

    public byte getKmh() { return this.kmh; }
    public void setKmh(byte kmh) { this.kmh = kmh; }

    public int getTargetId() { return this.targetId; }
    public void setTargetId(int targetId) { this.targetId = targetId;}

    public byte getNoTurnBits() { return this.noTurnBits; }
    public void setNoTurnBits(byte noTurnBits) { this.noTurnBits = noTurnBits; }

    public int getFlags() { return this.flags; }
    public void setFlags(int flags) { this.flags = flags; }


    public long getCacheReadIndex() {
        return this.readIndex;
    }

    public void setCacheReadIndex(long index) {
        this.readIndex = index;
    }

    public boolean isOverridableBy(Var that) {
        return true;
    }

    public Var readFromStream(InStream inStream) {
        this.id = inStream.readInt();
        this.sourceId = inStream.readInt();
        this.targetId = inStream.readInt();
        this.km = Float.intBitsToFloat( inStream.readInt() );
        this.kmh = inStream.readByte();
        this.noTurnBits = inStream.readByte();
        this.flags = inStream.readInt();
        return this;
    }

    public void writeToStream( OutStream outStream ) {
        outStream.writeInt( this.id );
        outStream.writeInt( this.sourceId );
        outStream.writeInt( this.targetId );
        outStream.writeInt( Float.floatToIntBits(this.km));
        outStream.writeByte( this.kmh );
        outStream.writeByte( this.noTurnBits );
        outStream.writeInt( this.flags );
    }

    public String toString() {
        String s = "Id:" + this.id
                + " SourceId:" + this.sourceId
                + " TargetId:" + this.targetId
                + " Km:" + this.km
                + " Kmh:" + this.kmh
                + " NoTurnBits:" + this.noTurnBits
                + " (" + Integer.toBinaryString(this.noTurnBits) + ")"
                + " Flags:" + this.flags
                + " (" + Integer.toBinaryString(this.flags) + ")";

        return s;
    }

}
