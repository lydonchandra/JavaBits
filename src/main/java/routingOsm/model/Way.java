package routingOsm.model;

import routingOsm.Utils;
import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarLong;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 3:58 AM
 */
public class Way extends OsmWay {

    protected Node[] nodes;

    public void setNodes( Node[] nodes ) {
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return this.nodes;
    }

    public void clear() {
        super.clear();
        this.nodes = null;
    }

    public Way readFromOsmWayStream( InStream inStream ) {
        clear();
        super.readFromStream( inStream );
        return this;
    }

    public void writeToStream( OutStream outStream ) {

        // write super's attribures
        super.writeAttributesToStream( outStream );

        // write nodes length
        int nNodes = this.nodes.length;
        VarLong.writeLongToStream( nNodes, outStream );

        // write child nodes attributes
        for( int i=0; i<nNodes; i++ ) {
            this.nodes[i].writeToStream( outStream );
        }
    }

    public Var readFromStream( InStream inStream ) {

        // read super's attributes
        super.readAttributesFromStream( inStream );

        // read nodes length
        int nNodes = (int)VarLong.readLongFromStream( inStream );

        this.nodes = new Node[ nNodes ];

        // re-create child nodes
        for( int i=0; i < nNodes; i++ ) {
            this.nodes[i] = new Node();
            this.nodes[i].readFromStream( inStream );
        }
        return this;
    }

    public String toString() {
        String s = super.toString();
        int nNodes = 0;
        if( this.nodes != null )
            nNodes = this.nodes.length;

        s = s + "\nNodes[" + nNodes + "]";
        for( int i=0; i<nNodes; i++ ) {
            s = s + " (" + this.nodes[i].toString() + ")";
        }
        return s;
    }

    public double calcLengthKm() {
        double km = 0.0D;
        if( this.nodes.length > 1 ) {
            Node ndFrom = this.nodes[0];

            // sum distances from node[0] to node[1] to node[i]
            for( int i=1; i < this.nodes.length; i++ ) {
                Node ndTo = this.nodes[i];
                km += Utils.calcDistanceSphere(ndFrom.getLat(), ndFrom.getLon(),
                                               ndTo.getLat(), ndTo.getLon());

                ndFrom = ndTo;
            }
        }
        return km;
    }
}
