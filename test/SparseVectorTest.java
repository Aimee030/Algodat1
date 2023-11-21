import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

class SparseVectorTest {

    SparseVector v1;
    SparseVector v2;
    SparseVector v3;

    @BeforeEach
    @org.junit.jupiter.api.Test
    void setUp(){
        v1 = new SparseVector(12);
        v2 = new SparseVector(12);
        v3 = new SparseVector();
    }

    @org.junit.jupiter.api.Test
    void setAndGetElement() {
        v1.setElement(2, 2.0);
        Assertions.assertEquals(2.0, v1.getElement(2));

        Assertions.assertEquals(0.0, v1.getElement(5));

        IndexOutOfBoundsException thrownSet = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> { v1.setElement(15, 4.0);});
        Assertions.assertEquals("Index out of Bounds", thrownSet.getMessage());

        IndexOutOfBoundsException thrownGet = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> { v1.getElement(15);});
        Assertions.assertEquals("Index out of Bounds", thrownGet.getMessage());
    }

    @org.junit.jupiter.api.Test
    void removeElement() {
        v2.setElement(5, 5.0);
        v2.removeElement(5);
        Assertions.assertEquals(0.0, v2.getElement(5));

        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> { v1.removeElement(15);});
        Assertions.assertEquals("Index out of Bounds", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getLength() {
        assertEquals(12, v1.getLength());
        assertEquals(0, v3.getLength());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        Assertions.assertEquals(false, v1.equals(v3));
        Assertions.assertEquals(true, v1.equals(v2));

        v1.setElement(5, 5.0);
        Assertions.assertEquals(false, v1.equals(v2));

        v2.setElement(5, 5.0);
        Assertions.assertEquals(true, v1.equals(v2));

        v2.setElement(7, 7.0);
        Assertions.assertEquals(false, v1.equals(v2));
    }

    @org.junit.jupiter.api.Test
    void add() {
        v1.setElement(4, 4.0);
        v1.setElement(6, 6.0);
        v1.setElement(10, 10.0);
        v1.setElement(8, 8.0);
        v1.setElement(0, 120.0);

        v2.setElement(4, -4.0);
        v2.setElement(1, 1.0);
        v2.setElement(10, 2.0);
        v2.setElement(11, 8.0);

        v1.add(v2);

        SparseVector hv = new SparseVector(12);

        hv.setElement(0, 120.0);
        hv.setElement(1, 1.0);
        hv.setElement(6, 6.0);
        hv.setElement(8, 8.0);
        hv.setElement(10, 12.0);
        hv.setElement(11, 8.0);

        Assertions.assertEquals(true, v1.equals(hv));

        ArithmeticException thrown = Assertions.assertThrows(ArithmeticException.class, () -> { v1.add(v3);});
        Assertions.assertEquals("Vectors are not the same length", thrown.getMessage());
    }
}