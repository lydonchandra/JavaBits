package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarString;
import routingOsm.routing.Graph;

import javax.xml.bind.annotation.XmlElement;

/**
 * User: lydonchandra
 * Date: 30/07/12
 * Time: 12:11 AM
 */
public final class RoutingResultSegment
    implements Var
{
    private int edgeId;
    private int sourceId;
    private int targetId;
    private float h;
    private float km;
    private int flags;
    private byte noTurnBits;
    private Coords coords;
    private VarString name;

    public RoutingResultSegment() {
        this.coords = new Coords();
        this.name = new VarString("");
    }

    public void setEdgeId( int edgeId ) { this.edgeId = edgeId; }

    public Coords getCoords() { return this.coords; }
    public void setCoords(Coords coords) { this.coords = coords; }

    public VarString getName() { return this.name; }
    public void setName(VarString name) { this.name = name; }

    @XmlElement(required=true)
    public String getStreetName() { return this.name.toString(); }

    @XmlElement(required=true, name="coords")
    public LatLon[] getLatLons() { return this.coords.getLatLons(); }

    @XmlElement(required=true)
    public int getSourceId() { return this.sourceId; }

    @XmlElement(required=true)
    public int getTargetId() { return this.targetId; }
    public void setTargetId(int targetId) { this.targetId = targetId; }

    @XmlElement(required=true, name="time")
    public float getH() { return this.h; }
    public void setH(float h) { this.h = h; }

    @XmlElement(required=true, name="length")
    public float getKm() { return this.km; }
    public void setKm(float km) { this.km = km; }

    @XmlElement(required = true)
    public int getFlags() { return this.flags; }
    public void setFlags( int flags ) { this.flags = flags; }

    @XmlElement(required=true)
    public byte getNoTurnBits() { return this.noTurnBits; }
    public void setNoTurnBits( byte noTurnBits ) { this.noTurnBits = noTurnBits; }

//    @XmlElement(required=true, name="segmentId")
//    public int getId() { return Graph.toSementId(this.edgeId); }
//
//    @XmlElement(required=true, name="wrongWay")
//    public boolean isWrongWay() { return Graph.isWrongWay(this.edgeId); }
//
//    @XmlElement(required=true, name="oneWay")
//    public boolean isOneWay() { return Graph.isOneWay(this.edgeId); }
//
//    @XmlElement(required=true, name="reverse")
//    public boolean isReverse() { return Graph.isReverse(this.edgeId); }
//
//    public String toString() {
//        String s = "Id:" + getId()
//                + " Source: " + this.sourceId
//                + " Target:" + this.targetId
//                + " Flags" + this.flags
//                + " NoTurnBits:" + this.noTurnBits
//                + " OneWay:" + isOneWay()
//                + " WrongWay:" + isWrongWay()
//                + " Reverse:" + isReverse()
//                + " h:" + this.h
//                + " Km:" + this.km
//                + " Name:" + this.name
//                + " Geometry:" + this.coords;
//        return s;
//    }

    public boolean isOverridableBy( Var that ) {
        return true;
    }

    public Var readFromStream(InStream inStream) {
        this.edgeId = inStream.readInt();
        this.sourceId = inStream.readInt();
        this.targetId = inStream.readInt();
        this.flags = inStream.readInt();
        this.noTurnBits = inStream.readByte();

        this.h = Float.intBitsToFloat(inStream.readInt());
        this.km = Float.intBitsToFloat( inStream.readInt());

        this.name.readFromStream(inStream);
        this.coords.readFromStream( inStream );
        return this;
    }

    public void writeToStream( OutStream outStream ) {
        outStream.writeInt( this.edgeId );
        outStream.writeInt( this.sourceId );
        outStream.writeInt( this.targetId );
        outStream.writeInt( this.flags );
        outStream.writeByte( this.noTurnBits );

        outStream.writeInt( Float.floatToIntBits(this.h) );
        outStream.writeInt( Float.floatToIntBits(this.km));

        this.name.writeToStream( outStream );
        this.coords.writeToStream( outStream );
    }



}
