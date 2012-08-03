package routingOsm.model;

import routingOsm.primitives.InStream;
import routingOsm.primitives.OutStream;
import routingOsm.primitives.Var;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 3:41 PM
 *
 * LatLon class stores latitude in a long value,
 *      latitude in high 32bits value
 *      longitude in low 32bits value
 *
 * latitude and longitude are mulitplied by 10,000,000.0 before stored.
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class LatLon
    implements Var
{

    public static final long NULL_COORDINATE = -9223372036854775808L;
    private static final long LONG_LOW_INT_MASK = 4294967295L;
    private static final long LONG_HIGH_INT_MASK = -4294967296L;

    // upper 32 bits has lat
    // lower 32 bits has lon
    protected long latLon;

    public LatLon() {
        this.latLon = -922337206854775808L;
    }

    public LatLon( double lat, double lon ) {
        setLatLon( lat, lon );
    }

    public long getLatLon() { return this.latLon; }

    public void setLatLon(long latLon) {
        this.latLon = latLon;
    }

    public void setLatLon( double lat, double lon ) {
        long llat = (int)(lat * 10000000.0D);

        long llon = (int)(lon * 10000000.0D)
                    & 0xFFFFFFFF;

        this.latLon = ( llat << 32 | llon );
    }

    public boolean equals( Object obj ) {
        if( !(obj instanceof LatLon))
            return false;

        LatLon that = (LatLon) obj;
        return this.latLon == that.latLon;
    }

    public void setLat( double lat ) {
        this.latLon &= 4294967295L;
        long llat = (int)(lat * 10000000.0D);

        this.latLon |= llat << 32;
    }

    public static double getLat( long latLon ) {
        int ilat = (int)(latLon >> 32);
        return ilat / 10000000.0D;
    }

    @XmlElement
    public double getLat() {
        int ilat = (int)(this.latLon >> 32);
        return ilat / 10000000.0D;
    }

    public void setLon( double lon ) {
        this.latLon &= -4294967296L;
        long llon = (int)(lon * 10000000.0D);
        // why AND with 0xFFFFFFFF ?
        this.latLon |= llon & 0xFFFFFFFF;
    }

    public static double getLon(long latLon) {
        int ilon = (int)(latLon & 0xFFFFFFFF);
        return ilon / 10000000.0D;
    }

    @XmlElement
    public double getLon() {
        int ilon = (int)(this.latLon & 0xFFFFFFFF);
        return ilon / 10000000.0D;
    }

    public Var readFromStream( InStream inStream ) {
        this.latLon = inStream.readLong();
        return this;
    }

    public void writeToStream( OutStream outStream ) {
        outStream.writeLong( this.latLon );
    }

    public String toString() {
        String s = "LatLon:" + getLat() + "|" + getLon();
        return s;
    }

    public boolean isOverridableBy( Var that ) {
        return true;
    }

}
