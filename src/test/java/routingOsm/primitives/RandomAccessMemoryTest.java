package routingOsm.primitives;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import routingOsm.AppTest;

/**
 * User: lydonchandra
 * Date: 4/08/12
 * Time: 12:18 AM
 */
public class RandomAccessMemoryTest
        extends TestCase
{
    public RandomAccessMemoryTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite(RandomAccessMemoryTest.class );
    }

    public void testBasicOperation() {
        // test constructor
        RandomAccess randomMemory = new RandomAccessMemory();
        assertTrue( randomMemory.getReadIndex() == 0L );

        randomMemory.setReadIndex(1L);
        assertTrue( randomMemory.getReadIndex() == 1L );

        randomMemory.clear();

        // test byte
        randomMemory.writeByte((byte)2);
        assertTrue( randomMemory.readByte() == (byte)2 );

        // test short
        randomMemory.writeShort( (short)65534 );
        assertTrue( randomMemory.readShort() == (short)65534 );

        // test int
        randomMemory.writeInt( (int)2000000 );
        assertTrue( randomMemory.readInt() == (int)2000000);

        // test long
        randomMemory.writeLong( 12345678912345678L );
        assertTrue( randomMemory.readLong() == 12345678912345678L );

        // test eof
        assertTrue( randomMemory.isEof() == true );

        randomMemory.close();
        assertTrue( randomMemory.getReadIndex() == 0L );
        assertTrue( randomMemory.getWriteIndex() == 0L );
    }

    public void testPerformance() {
        RandomAccess randomMemory = new RandomAccessMemory();

        final long MAX = 34359738368L;
        for( long i=0; i<MAX; i++ ) {
//            if( i %1000 == 0)
//                System.out.println(i);
            randomMemory.writeLong(i);
        }

    }
}
