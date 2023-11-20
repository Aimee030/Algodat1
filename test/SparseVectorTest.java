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
    void setElement() {
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> { v1.setElement(15, 4.0);});
        Assertions.assertEquals("Index out of Bounds", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getElement() {
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> { v1.getElement(15);});
        Assertions.assertEquals("Index out of Bounds", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void removeElement() {
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
    }

    @org.junit.jupiter.api.Test
    void add() {
        ArithmeticException thrown = Assertions.assertThrows(ArithmeticException.class, () -> { v1.add(v3);});
        Assertions.assertEquals("Vectors are not the same length", thrown.getMessage());
    }
}