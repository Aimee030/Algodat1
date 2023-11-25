import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

class SparseVectorTest {

    //Vectors used by tests
    SparseVector v1;
    SparseVector v2;
    SparseVector v3;

    @BeforeEach
    @org.junit.jupiter.api.Test
        //initialise vectors before each test, two with length the same and not equal 0, one with length 0
    void setUp(){
        v1 = new SparseVector(12);
        v2 = new SparseVector(12);
        v3 = new SparseVector();
    }

    @org.junit.jupiter.api.Test
        //setElement and getElement are tested together
    void setAndGetElement() {

        //test get for not set but theoretically existing element
        Assertions.assertEquals(0.0, v1.getElement(5));

        //test set with get for set element
        v1.setElement(2, 2.0);
        Assertions.assertEquals(2.0, v1.getElement(2));

        //test set with get for set element for existing index
        v1.setElement(2, 5.0);
        Assertions.assertEquals(5.0, v1.getElement(2));

        //test set of elements with value 0.0 with nodeExists
        v1.setElement(11, 0.0);
        Assertions.assertEquals(false, v1.nodeExists(11));

        //test overwrite existing node with 0.0
        v1.setElement(2, 0.0);
        Assertions.assertEquals(false, v1.nodeExists(2));

        //test sorting by index by filling vector and comparing to sorted helpvector
        v2.setElement(4, 4.0);
        v2.setElement(1, 1.0);
        v2.setElement(10, 2.0);
        v2.setElement(11, 8.0);
        v2.setElement(6, 2.0);

        SparseVector hv = new SparseVector(12);
        hv.setElement(1, 1.0);
        hv.setElement(4, 4.0);
        hv.setElement(6, 2.0);
        hv.setElement(10, 2.0);
        hv.setElement(11, 8.0);

        Assertions.assertEquals(true, v2.equals(hv));

        //test set error first for too high index then for index < 0
        IndexOutOfBoundsException thrownSet = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.setElement(15, 4.0);});
        Assertions.assertEquals("Index out of Bounds", thrownSet.getMessage());
        IndexOutOfBoundsException thrownSet2 = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.setElement(-1, 4.0);});
        Assertions.assertEquals("Index out of Bounds", thrownSet2.getMessage());

        //test get error first for too high index then for index < 0
        IndexOutOfBoundsException thrownGet = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.getElement(15);});
        Assertions.assertEquals("Index out of Bounds", thrownGet.getMessage());
        IndexOutOfBoundsException thrownGet2 = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.getElement(-1);});
        Assertions.assertEquals("Index out of Bounds", thrownGet2.getMessage());
    }

    @org.junit.jupiter.api.Test
    void removeElement() {

        v2.setElement(0, 120.0);
        v2.setElement(1, 1.0);
        v2.setElement(6, 8.0);
        v2.setElement(8, 8.0);
        v2.setElement(10, 12.0);
        v2.setElement(11, 8.0);

        //test remove second, then first, then last element
        v2.removeElement(1);
        Assertions.assertEquals(0.0, v2.getElement(1));

        v2.removeElement(0);
        Assertions.assertEquals(0.0, v2.getElement(0));

        v2.removeElement(11);
        Assertions.assertEquals(0.0, v2.getElement(11));

        //test remove error first for too high index then for index < 0
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.removeElement(15);});
        Assertions.assertEquals("Index out of Bounds", thrown.getMessage());
        IndexOutOfBoundsException thrown2 = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> { v1.removeElement(-1);});
        Assertions.assertEquals("Index out of Bounds", thrown2.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getLength() {
        //test getLength for vector with length not equal and equal to 0
        assertEquals(12, v1.getLength());
        assertEquals(0, v3.getLength());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        //test for empty vectors first with different then same length
        Assertions.assertEquals(false, v1.equals(v3));
        Assertions.assertEquals(true, v1.equals(v2));

        //test for same length with more Nodes in this
        v1.setElement(5, 5.0);
        Assertions.assertEquals(false, v1.equals(v2));

        //test for same length with same Nodes
        v2.setElement(5, 5.0);
        Assertions.assertEquals(true, v1.equals(v2));

        //test for same length with more nodes in other
        v2.setElement(7, 7.0);
        Assertions.assertEquals(false, v1.equals(v2));

        //test for same length with same number of different Nodes
        v1.setElement(7, 2.0);
        Assertions.assertEquals(false, v1.equals(v2));
    }

    @org.junit.jupiter.api.Test
        //test add for two filled vectors and error if lengths are different
    void add() {
        v1.setElement(4, 4.0); //test removing if value becomes 0 after add
        v1.setElement(6, 6.0);
        v1.setElement(10, 10.0);
        v1.setElement(8, 8.0);
        v1.setElement(0, 120.0);

        v2.setElement(4, -4.0);
        v2.setElement(1, 1.0);
        v2.setElement(10, 2.0);
        v2.setElement(11, 8.0);
        v2.setElement(6, 2.0);

        v1.add(v2);

        //helpvector is the expected result
        SparseVector hv = new SparseVector(12);

        hv.setElement(0, 120.0);
        hv.setElement(1, 1.0);
        hv.setElement(6, 8.0);
        hv.setElement(8, 8.0);
        hv.setElement(10, 12.0);
        hv.setElement(11, 8.0);

        Assertions.assertEquals(true, v1.equals(hv));

        //test error if length is different
        ArithmeticException thrown = Assertions.assertThrows(ArithmeticException.class, () -> { v1.add(v3);});
        Assertions.assertEquals("Vectors are not the same length", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
        //test add with second, then first vector empty
    void addEmpty(){

        //v1 filled, v2 empty
        v1.setElement(0, 120.0);
        v1.setElement(1, 1.0);
        v1.setElement(6, 8.0);
        v1.setElement(8, 8.0);
        v1.setElement(10, 12.0);
        v1.setElement(11, 8.0);

        //helpvector is the expected result
        SparseVector hv = new SparseVector(12);

        hv.setElement(0, 120.0);
        hv.setElement(1, 1.0);
        hv.setElement(6, 8.0);
        hv.setElement(8, 8.0);
        hv.setElement(10, 12.0);
        hv.setElement(11, 8.0);

        v1.add(v2);

        Assertions.assertEquals(true, v1.equals(hv));

        v2.add(v1);
        Assertions.assertEquals(true, v2.equals(hv));


    }

}