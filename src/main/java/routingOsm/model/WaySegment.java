package routingOsm.model;

import routingOsm.Utils;
import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 11:11 PM
 */
public class WaySegment implements Var {

    private int id;
    private int sourceId;
    private int targetId;
    private Node[] nodes;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public int getSourceId() { return this.sourceId; }
    public void setSourceId(int sourceId) { this.sourceId = sourceId; }

    public int getTargetId() { return this.targetId; }
    public void setTargetId(int targetId) { this.targetId = targetId; }

    public Node[] getNodes() { return this.nodes; }
    public void setNodes( Node[] nodes) { this.nodes = nodes; }

    public WaySegment() {}

    public WaySegment( int id, int sourceId,
                       int targetId, Node[] nodes ) {
        this.id = id;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.nodes = nodes;
        if( (0 == id)
            || (0 == sourceId)
            || (0 == targetId)
            || (nodes.length < 2)) {

                throw new RuntimeException("Invalid WaySegment \n" + toString());
        }
    }

    public String toString() {
        String s = "Id:" + this.id
                    + " Source:" + this.sourceId
                    + " Target:" + this.targetId;

        if( this.nodes != null ) {
            s = s + " Nodes[" + this.nodes.length + "]:";
            for( int i=0; i < this.nodes.length; i++ ) {
                s = s + "\n[" + i + "]" + this.nodes[i].toString();
            }
        }
        return s;
    }

    public boolean isOverriableBy( Var that ) {
        return false;
    }

    public Var readFromStream( InStream inStream ) {
        this.id = inStream.readInt();
        this.sourceId = inStream.readInt();
        this.targetId = inStream.readInt();
        int nNodes = inStream.readInt();
        if( nNodes != 0 ) {
            this.nodes = new Node[nNodes];
            for( int i=0; i<nNodes; i++ ) {
                Node node = new Node();
                node.readFromStream(inStream);
                this.nodes[i] = node;
            }
        }
        return this;
    }

    @Override
    public boolean isOverridableBy(Var paramVar) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeToStream(OutStream outStream) {
        outStream.writeInt( this.id );
        outStream.writeInt( this.sourceId );
        outStream.writeInt( this.targetId );
        int nNodes = 0;
        if( this.nodes != null )
            nNodes = this.nodes.length;

        outStream.writeInt( nNodes );

        for( int i=0; i<nNodes; i++ ) {
            this.nodes[i].writeToStream( outStream );
        }
    }

    public double calcLengthKm() {
        double km = 0.0D;
        if( this.nodes.length > 1 ) {
            Node ndFrom = this.nodes[0];
            for( int i=1; i<this.nodes.length; i++ ) {
                Node ndTo = this.nodes[i];
                km += Utils.calcDistanceSphere( ndFrom.getLat(), ndFrom.getLon(),
                                                ndTo.getLat(), ndTo.getLon());
                ndFrom = ndTo;
            }
        }
        return km;
    }



































}
