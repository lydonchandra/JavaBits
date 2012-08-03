package routingOsm;

import routingOsm.model.OsmNode;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 1:44 AM
 */
public class Utils {
    public static final NumberFormat DECIMAL_FORMAT_0_DIGITS = new DecimalFormat("#,##0");

    public static final NumberFormat DECIMAL_FORMAT_2_DIGITS = new DecimalFormat("#,##0.00");

    public static final double RADIUS_EARTH_KM = 6371.0D;

    public static final double EPSG_900913_CONST = 111319.49077777777D;

    public static final double RAD_PI = 0.0174532925199433D;

    public static final double RAD_PI_HALF = 0.008726646259971648D;

    public static final String DF (long l) {
        return DECIMAL_FORMAT_0_DIGITS.format( l );
    }

    public static final String DF(double d) {
        return DECIMAL_FORMAT_2_DIGITS.format( d );
    }

    public static String KB() {
        long ram = getMemoryAvailable();
        ram >>>= 10;
        return DF(ram) + "k";
    }

    public static long getMemoryAvailable() {
        long free  = Runtime.getRuntime().freeMemory();
        long total = Runtime.getRuntime().totalMemory();
        long max   = Runtime.getRuntime().maxMemory();
        long available = max - total + free;
        return available;
    }

    public static int angleMathwise( OsmNode n1, OsmNode n2 ) {
        double dy = n2.getLat() - n1.getLat();
        double dx = n2.getLon() - n1.getLon();

        double degrees = StrictMath.toDegrees(
                                        StrictMath.atan2(dy, dx) );

        if( degrees < 0.0D )
                degrees += 360.0D;

        return (int)degrees;
    }

    public static int angleClockwise( OsmNode n1, OsmNode n2 ) {
        double dy = n2.getLat() - n1.getLat();
        double dx = n2.getLon() - n1.getLon();
        double degrees = StrictMath.toDegrees(
                                        StrictMath.atan2(dy,dx) );
        if( degrees < 0.0D )
                degrees += 360.0D;
        return (int)degrees;
    }

    public static double calcDistanceSphere( double lat1, double lon1,
                                             double lat2, double lon2 ) {

        double d = StrictMath.acos(
                            StrictMath.sin(lat1 * RAD_PI)
                            * StrictMath.sin(lat2 * RAD_PI)
                            + StrictMath.cos(lat1 * RAD_PI)
                            * StrictMath.cos(lat2 * RAD_PI)
                            * StrictMath.cos(
                                    (lon2-lon1) * RAD_PI)
                            ) * RADIUS_EARTH_KM;

        if( Double.isNaN(d))
                return 0.0D;

        return d;
    }

    public static double calcDistanceEuklid( double lat1, double lon1,
                                             double lat2, double lon2 ) {
        double x = (lon2-lon1)
                   * RAD_PI
                   * StrictMath.cos( (lat1 + lat2 )
                                     * RAD_PI_HALF );

        double y = (lat2-lat1) * RAD_PI;

        double d = StrictMath.hypot(x,y) * RADIUS_EARTH_KM;

        return d;
    }


    public static double calcDistanceManhatten( double lat1, double lon1,
                                                double lat2, double lon2 ) {
        return StrictMath.abs(lat2 - lat1)
               + StrictMath.abs(lon2 - lon1);
    }

    public static double EPSG_4326_TO_900913_Lon( double lon ) {
        double x = lon * EPSG_900913_CONST;
        return x;
    }

    public static double EPSG_900913_TO_4326_Lon( double lon ) {
        double x = lon / EPSG_900913_CONST;
        return x;
    }

    public static double EPSG_4326_TO_900913_Lat( double lat ) {
        double y = StrictMath.log(
                            StrictMath.tan(
                                    (90.0D + lat)
                                    * Math.PI / 360.0D) )
                   / RAD_PI;

        y *= EPSG_4326_TO_900913_Lon( EPSG_900913_CONST );
        return y;
    }

    public static double EPSG_900913_TO_4326_Lat( double lat ) {
        double y = lat / EPSG_900913_CONST;

        y = 57.295779513082323D
            * ( 2.0D * StrictMath.atan(
                            StrictMath.exp(y * Math.PI / 180.0D))
                - 1.570796326794897D);

        return y;
    }

    public static void copyFile( File from, File to )
    {
        try {
            InputStream in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);
            byte[] buf = new byte[4096];
            int len;

            while( (len = in.read(buf)) > 0)
                out.write( buf, 0, len );

            in.close();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] loadFileIntoByteArray( File dataFile )
        throws Exception
    {
        long fileSize = dataFile.length();
        if( fileSize > 2147483646L ) {
            throw new Exception("File too large");
        }

        byte[] memory = new byte[ (int)fileSize ];
        FileInputStream fis = new FileInputStream( dataFile );

        byte[] buf = new byte[4096];

        int pos = 0;
        for( int len = fis.read(buf)
             ; len > -1
             ; pos += buf.length ) {

            System.arraycopy( buf, 0, memory, pos, len );
            len = fis.read(buf);
        }
        fis.close();
        return memory;
    }

    public static void beep( int count ) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            for( int i=0; i<count; i++ ) {
                toolkit.beep();
                Thread.sleep(500L);
            }
        } catch( Exception e ) {
            System.err.println("Warning " + e.getMessage() );
        }
    }

}
