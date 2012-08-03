package routingOsm.converter;

/**
 * User: lydonchandra
 * Date: 28/07/12
 * Time: 3:54 AM
 */
public class OsmDataException extends Exception {
    private static final long serialVersionUID = -7197469703994047333L;

    public OsmDataException(String errMsg) {
        super(errMsg);
    }
}
