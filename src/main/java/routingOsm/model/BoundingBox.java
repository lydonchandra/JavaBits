package routingOsm.model;

/**
 * User: lydonchandra
 * Date: 29/07/12
 * Time: 4:07 AM
 */
public class BoundingBox {
    private float lat1;
    private float lon1;
    private float lat2;
    private float lon2;

    public BoundingBox()
    {
        this( -90.0F, -180.0F, 90.0F, 180.0F );
    }

    public BoundingBox( float lat1, float lon1,
                        float lat2, float lon2 ) {
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.lat2 = lat2;
        this.lon2 = lon2;
    }

    public float getLat1() { return this.lat1; }
    public void setLat1(float lat1) { this.lat1 = lat1; }

    public float getLon1() { return this.lon1; }
    public void setLon1(float lon1) { this.lon1 = lon1; }

    public float getLat2() { return this.lat2; }
    public void setLat2(float lat2) { this.lat2 = lat2; }

    public float getLon2() { return this.lon2; }
    public void setLon2(float lon2) { this.lon2 = lon2; }

    /**
     * Check if this bbox contains given latlon
     * @param lat
     * @param lon
     * @return
     */
    public boolean contains( float lat, float lon ) {
        if( lat < this.lat1 ) return false;
        if( lon < this.lon1 ) return false;
        if( lat > this.lat2 ) return false;
        return lon <= this.lon2;
    }

    /**
     * Grow Bounding box to given lat & lon
     * ie // bbox is 0,0,0,0
     *    bbox = bbox.grow(10,10)
     *    // bbox is now 0,0,10,10
     * @param lat
     * @param lon
     * @return
     */
    public BoundingBox grow( float lat, float lon ) {
        if( lat < this.lat1 )
            this.lat1 = lat;

        if( lon < this.lon1 )
            this.lon1 = lon;

        if( lat > this.lat2 )
            this.lat2 = lat;

        if( lon > this.lon2 )
            this.lon2 = lon;

        return this;
    }

    /**
     * Grow Bounding Box by amount 'delta'
     * ie   // bbox is 0,0,0,0
     *      bbox = bbox.grow(10)
     *      // bbox is now -10, -10, 10, 10
     * @param delta
     * @return
     */
    public BoundingBox grow( float delta ) {
        this.lat1 -= delta;
        this.lon1 -= delta;
        this.lat2 += delta;
        this.lon2 += delta;
        return this;
    }

    public String toString() {
        return "[" + this.lat1 + "/" + this.lon1
                + ", " + this.lat2 + "/" + this.lon2 + "]";
    }

}
