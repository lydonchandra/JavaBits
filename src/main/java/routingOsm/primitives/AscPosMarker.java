package routingOsm.primitives;

import java.io.File;


/**
 * User: lydonchandra
 * Date: 31/07/12
 * Time: 10:52 PM
 */
public class AscPosMarker {
    private static final int BLOCK_SIZE = 1048576;
    private File tmpFile;
    private OutStream tmpOutStream;
    private long[] deltas;
    private int deltasIdx;
    private int blockSize;
    private long size;
    private long valGuard;
    private RandomAccess raData;
    private long raDataOffset;

    /**
     * Create a new temp file for output
     * @param tmpFile
     */
    public void openCreate(File tmpFile) {
        this.tmpFile = tmpFile;
        this.tmpOutStream = new OutStreamDisk( this.tmpFile );
        this.valGuard = -1L;
        this.size = 0L;
        this.blockSize = 0;
        this.deltas = new long[1024];
        this.deltasIdx = 0;
    }

    public AscPosMarker openUse( RandomAccess ra, boolean inMem ) {
        int deltasLength = ra.readInt();
        this.deltas = new long[deltasLength];
        for( int i=0; i<deltasLength; i++ ) {

        }
        return this;
    }

}
