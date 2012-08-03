package routingOsm.primitives;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BitsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BitsTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BitsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void test_getShort()
    {
        assertTrue( true );
        byte b[] = { 0 , 0 };
        assertTrue( Bits.getShort(b, 0) == 0 );

        byte c[] = { 0, 1 };
        assertTrue( Bits.getShort(c, 0) == 1);

        byte d[] = { 0, (byte)255 };
        assertTrue( Bits.getShort(d, 0) == 255);

        byte e[] = { 1, 0 };
        assertTrue( Bits.getShort(e, 0) == 256 );

        byte f[] = { 1, 1, 1 };
        assertTrue( Bits.getShort(f, 0) == 257 );

        byte g[] = { 1, 0, 1 };
        assertTrue( Bits.getShort(g, 1) == 1 );
    }
}
