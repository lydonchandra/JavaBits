package routingOsm.model;

import java.util.List;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 5:30 PM
 */
public class Restriction {

    public static final int NO_TURN = 1;
    public static final int ONLY_TURN = 2;
    public static final int LEFT_TURN = 4;
    public static final int RIGHT_TURN = 8;
    public static final int STRAIGHT_ON = 16;
    public static final int U_TURN = 32;

    private long from;
    private long via;
    private long to;
    private int flags;

    public Restriction( long from, long via, long to, int flags ) {
        this.from = from;
        this.via = via;
        this.to = to;
        this.flags = flags;
    }

    public long getFrom() {
        return this.from;
    }

    public void setFrom(long from) { this.from = from; }

    public long getVia() { return this.via; }
    public void setVia(long via) { this.via = via; }

    public long getTo() { return this.to; }
    public void setTo(long to) { this.to = to; }

    public int getFlags() { return this.flags; }
    public void setFlags(int flags) { this.flags = flags; }

    public String toString() {
        return "From:" + this.from + " Via:" + this.via
                + " To:" + this.to + " Flags:" + this.flags
                + "(" + restrictionFlagsToString(this.flags) + ")";
    }


    public static Restriction nodeRestrictionFromOsmRelation( OsmRelation osmRelation)
    {
        if( (osmRelation.getClazz() != -1)
            || (osmRelation.getFlags() == 0)) {

            return null;
        }

        List members = osmRelation.getMembers();
        int nMembers = members.size();
        if( nMembers < 3 )
            return null;

        long from = 0L;
        long via = 0L;
        long to = 0L;

        for( int i=0; i<nMembers; i++ ) {
            OsmRelation.Member member = (OsmRelation.Member)members.get(i);
            if( 1 == member.getRole()
                 && 2 == member.getType() ) {

                from = member.getRef();
            }
            else if( 3 == member.getRole()
                     && 1 == member.getType() ) {

                via = member.getRef();
            }
            else if( 2 == member.getRole()
                     && 2 == member.getType() ) {

                to = member.getRef();
            }
        }

        if( from != 0L
            && via != 0L
            && to != 0L ) {

                return new Restriction( from, via, to, osmRelation.getFlags() );
        }
        return null;
    }


    public static String restrictionFlagsToString( int type ) {
        if( type == 5 ) return "NLT";
        if( type == 9 ) return "NRT";
        if( type == 17) return "NSO";
        if( type == 33) return "NUT";
        if( type == 6 ) return "OLT";
        if( type == 10) return "ORT";
        if( type == 18) return "OSO";
        return "";
    }


}
