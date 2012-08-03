package routingOsm.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * User: lydonchandra
 * Date: 30/07/12
 * Time: 12:05 AM
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder={"errorLevel", "errorMessage", "route"} )
public class RoutingResult {
    public static final int ERR_ROUTE_FOUND = 0;
    public static final int ERR_ROUTE_NOT_FOUND = -1;
    public static final int ERR_TOO_MANY_REQUEST = 1;
    public static final int ERR_EXCEPTION = 2;

    private int errorLevel;
    private String errorMessage;

    @XmlElement( name="segment" )
    private RoutingResultSegment[] route;

    public int getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel( int errorLevel ) {
        this.errorLevel = errorLevel;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }

    public RoutingResultSegment[] getRoute() {
        return this.route;
    }

    public void setRoute( RoutingResultSegment[] route ) {
        this.route = route;
    }
}
