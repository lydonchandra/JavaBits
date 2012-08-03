package routingOsm.routing;

import routingOsm.logging.Log;
import routingOsm.primitives.AscPosMarker;
import routingOsm.primitives.IntIntBag;

import java.util.RandomAccess;

/**
 * User: lydonchandra
 * Date: 30/07/12
 * Time: 12:30 AM
 */
public class Graph {
    public static final int SUPPORT_NOTHING = 0;
    public static final int SUPPORT_LATLON = 1;
    public static final int SUPPORT_EDGEFLAGS = 2;
    public static final int SUPPORT_SEGMENTS = 4;
    public static final int SUPPORT_REVERSE = 8;
    public static final int SUPPORT_RASTER = 16;
    public static final int SUPPORT_BARRIERS = 32;

    private int supports = 0;
    private int[] edgeEntries;
    private int[] edgeIds;
    private int[] edgeSourceIds;
    private int[] edgeTargetIds;

    private float[] edgeCostsKm;
    private float[] edgeCostsH;

    private byte[] edgeNoTurnBits;
    private int[] edgeFlags;
    private int[] edgeIdxsR;
    private int[] edgeEntriesR;
    private byte[] edgeNoTurnBitsR;

    private IntIntBag rasterBag;
    private float[] lats;
    private float[] lons;
    private byte [] vertexClazzes;

    private float minLat;
    private float minLon;
    private float maxLat;
    private float maxLon;

    private RandomAccess raGraph;
    private AscPosMarker nameIndex;
    private AscPosMarker geomIndex;

    private long fptrNames;
    private long fptrGeoms;

    private int nSegments;
    private Log log;


}
