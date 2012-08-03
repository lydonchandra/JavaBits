package routingOsm.model;

import com.sun.xml.internal.xsom.impl.RestrictionSimpleTypeImpl;
import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 5:26 PM
 */
public class Node extends OsmNode {
    private Restriction[] restrictions;

    public Node() {}

    public Node( double lat, double lon ) {
        super(lat, lon);
    }

    public void setRestrictions( Restriction[] restrictions ) {
        this.restrictions = restrictions;
    }

    public Restriction[] getRestrictions() {
        return this.restrictions;
    }

    public void clear() {
        super.clear();
        this.restrictions = null;
    }

    public String toString() {
        String s = super.toString();
        if( this.restrictions != null ) {
            s = s + " Restrictions[" + this.restrictions.length + "]:";

            for( int i=0
                 ; i< this.restrictions.length
                 ; i++ ) {

                s = s + " (" + this.restrictions[i].toString() + ")";
            }
        }
        return s;
    }

    public boolean isLatLonOnly() {
        throw new RuntimeException( getClass().getName() + " cannot be simple ");
    }

    public void writeToStream( OutStream outStream ) {
        int nRestrictions = 0;
        if( this.restrictions != null )
            nRestrictions = this.restrictions.length;

        super.writeToStream( outStream );

        outStream.writeByte( (byte)nRestrictions );

        if( nRestrictions > 0 ) {
            for( int i=0; i<nRestrictions; i++ ) {
                Restriction r = this.restrictions[i];
                outStream.writeLong( r.getFrom() );
                outStream.writeLong(r.getTo());
                outStream.writeInt ( r.getFlags() );
            }
        }
    }

    public Var readFromStream(InStream inStream) {
        super.readFromStream( inStream );

        int nRestrictions = inStream.readByte();
        if( nRestrictions > 0 ) {
            this.restrictions = new Restriction[ nRestrictions ];

            for( int i=0; i<nRestrictions; i++ ) {
                long from = inStream.readLong();
                long to = inStream.readLong();
                int flags = inStream.readInt();

                this.restrictions[i] = new Restriction( from, getId(), to, flags );
            }
        }
        return this;
    }
}
