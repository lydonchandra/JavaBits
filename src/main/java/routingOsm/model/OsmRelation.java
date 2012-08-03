package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;
import routingOsm.primitives.VarLong;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 5:56 PM
 */
public class OsmRelation
        implements Var {

    public static final byte MEMBER_TYPE_UNKNOWN = 0;
    public static final byte MEMBER_TYPE_NODE = 1;
    public static final byte MEMBER_TYPE_WAY = 2;
    public static final byte MEMBER_TYPE_RELATION = 3;

    public static final int MEMBER_ROLE_UNKNOWN = 0;
    public static final int MEMBER_ROLE_FROM = 1;
    public static final int MEMBER_ROLE_TO = 2;
    public static final int MEMBER_ROLE_VIA = 3;

    private long id;
    private byte clazz;
    private int flags;
    private List<Member> members = new ArrayList();

    public long getId()        { return this.id; }
    public void setId(long id) { this.id = id; }

    public byte getClazz()           { return this.clazz; }
    public void setClazz(byte clazz) { this.clazz = clazz; }

    public int getFlags()           { return this.flags; }
    public void setFlags(int flags) { this.flags = flags; }

    public List<Member> getMembers() { return this.members; }

    protected void addMember( byte type, long refId, int role ) {
        this.members.add(
                    new Member(type, refId, role) );
    }

    public void addMember( String strType, long lngRef, String strRole ) {
        byte memberType = 0;
        if( "node".equals(strType) )
            memberType = 1;
        else if( "way".equals(strType) )
            memberType = 2;
        else if( "relation".equals(strType))
            memberType = 3;

        int memberRole = 0;
        if("from".equals(strRole))
            memberRole = 1;
        else if( "to".equals(strRole) )
            memberRole = 2;
        else if( "via".equals(strRole) )
            memberRole = 3;

        addMember(memberType, lngRef, memberRole );
    }

    public void clear() {
        this.id = 0L;
        this.clazz = 0;
        this.flags = 0;
        this.members.clear();
    }

    public String toString() {
        String r = "Id:" + this.id + " Class:" + this.clazz
                    + " Flags:" + this.flags
                    + " Members[" + this.members.size() + "]:";

        for( int i=0; i<this.members.size(); i++) {
            r = r + "\n"
                + ( (Member)this.members.get(i) ).toString();
        }
        return r;
    }

    public boolean isOverridableBy( Var that ) {
        return false;
    }

    public Var readFromStream( InStream inStream ) {
        clear();
        this.id = VarLong.readLongFromStream( inStream );
        this.clazz = inStream.readByte();

        this.flags = (int)VarLong.readLongFromStream(inStream);
        int membersSize = inStream.readInt();
        for( int i=0; i < membersSize; i++ ) {
            this.members.add(
                    (Member)new Member()
                                    .readFromStream(inStream) );
        }
        return this;
    }

    public void writeToStream( OutStream outStream ) {
        VarLong.writeLongToStream( this.id, outStream );
        outStream.writeByte( this.clazz );

        VarLong.writeLongToStream( this.flags, outStream );
        int membersSize = this.members.size();
        outStream.writeInt( membersSize );

        for( int i=0; i<membersSize; i++ ) {
            Member member = (Member)this.members.get(i);
            member.writeToStream( outStream );
        }
    }



    public static class Member
        implements Var
    {
        private byte type;
        private long ref;
        private int role;

        private Member() {}

        public byte getType() { return this.type; }
        public long getRef() { return this.ref; }
        public int getRole() { return this.role; }

        private Member( byte type, long ref, int role ) {
            this.type = type;
            this.ref = ref;
            this.role = role;
        }

        public String toString() {
            return "Type:" + this.type
                    + " Ref:" + this.ref
                    + " Role:'" + this.role + "'";
        }

        public boolean isOverridableBy( Var that ) {
            return false;
        }

        public Var readFromStream(InStream inStream) {
            this.type = inStream.readByte();
            this.ref  = inStream.readLong();
            this.role = (int) VarLong.readLongFromStream( inStream );
            return this;
        }

        public void writeToStream(OutStream outStream) {
            outStream.writeByte( this.type );
            outStream.writeLong( this.ref  );
            VarLong.writeLongToStream( this.role, outStream );
        }
    }
}
