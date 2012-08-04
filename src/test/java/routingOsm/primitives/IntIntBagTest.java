package routingOsm.primitives;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class IntIntBagTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IntIntBagTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IntIntBagTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBasic()
    {
        IntIntBag intIntBag = new IntIntBag();
        assertTrue( intIntBag.getSize() == 0 );

        // nothing in bag yet, expect 0x80000000
        assertTrue( intIntBag.get(1) == 0x80000000 );

        // nothing in bag, expect null
        assertTrue( intIntBag.getAll(1) == null);

        intIntBag.add(1, 111);
        intIntBag.add(2, 222);
        intIntBag.add(3, 3);
        intIntBag.add(4, 44);

        // test #getSize and #get
        assertTrue( intIntBag.getSize() == 4 );
        assertTrue( intIntBag.get(1) == 111);
        assertTrue( intIntBag.get(2) == 222);
        assertTrue( intIntBag.get(3) == 3);
        assertTrue( intIntBag.get(4) == 44);

        // test #getKeys
        IntArray keys = intIntBag.getKeys();
        assertTrue( keys.get(0) == 1 );
        assertTrue( keys.get(1) == 2 );
        assertTrue( keys.get(2) == 3 );
        assertTrue( keys.get(3) == 4 );

        // test #getValues
        IntArray values = intIntBag.getValues();
        assertTrue( values.get(0) == 111 );
        assertTrue( values.get(1) == 222 );
        assertTrue( values.get(2) == 3   );
        assertTrue( values.get(3) == 44  );

        // test #getAll
        assertTrue( intIntBag.getAll(1) [0] == 111 );
        assertTrue( intIntBag.getAll(2) [0] == 222 );

        intIntBag.sort();
        assertTrue( values.get(0) == 111 );

        intIntBag.add( 999, 99900 );
        intIntBag.add( 78, 7800 );
        assertTrue( values.get(4) == 99900);
        assertTrue( values.get(5) == 7800);

        // assert newly added keys
        assertTrue( keys.get(4) == 999 );
        assertTrue( keys.get(5) == 78  );

        intIntBag.sort();
        // keys are sorted now (4) is 78 (before it's 999)
        assertTrue( keys.get(4) == 78 );
        assertTrue( keys.get(5) == 999);

        intIntBag.clear();
        assertTrue( intIntBag.getSize() == 0 );

        // should get NULL value 0x8000 0000 as it's been cleared
        assertTrue( intIntBag.get(1) == 0x80000000 );



    }













































}
