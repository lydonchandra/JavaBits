package routingOsm.primitives;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class IntArrayTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IntArrayTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IntArrayTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBasic()
    {
        IntArray intArray = new IntArray();
        assertTrue( intArray.getSize() == 0 );

        intArray.push(1);
        intArray.push(1000);
        intArray.push(22222);
        intArray.set(3, 33);
        intArray.set(4, 33);
        assertTrue( intArray.getSize() == 5 );
        assertTrue( intArray.get(0) == 1 );
        assertTrue( intArray.get(1) == 1000 );
        assertTrue( intArray.get(3) == 33 );

        // test #contains
        assertTrue( intArray.contains(1000) == true );

        // test #searchAll
        assertTrue( intArray.searchAll(33).length == 2 );
        assertTrue( intArray.searchAll(33)[0] == 3 );
        assertTrue( intArray.searchAll(33)[1] == 4 );

        // 66 doesn't exit, expect null
        assertTrue( intArray.searchAll(66) == null );

        // test #searchAny
        assertTrue( intArray.searchAny(33) == 3 );

        // test #sort
        intArray.sort();
        assertTrue( intArray.get(0) == 1);
        assertTrue( intArray.get(1) == 33);
        assertTrue( intArray.get(2) == 33);
        assertTrue( intArray.get(3) == 1000);
        assertTrue( intArray.get(4) == 22222);



        // test #clear
        intArray.clear();
        assertTrue( intArray.contains(1000) == false );
        assertTrue( intArray.getSize() == 0 );

    }

}
