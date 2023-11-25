public class SparseVector {

    int length;
    Node head;

    public SparseVector() {
        new SparseVector(0);
    }

    public SparseVector(int n) {
        this.length = n;
    }

    void setElement(int index, double value) throws IndexOutOfBoundsException {
        if(index > this.length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        if(value == 0){
            removeElement(index);
            return;
        }
        if(nodeExists(index)){
            setValue(index, value);
            return;
        }
        Node node = new Node(value, index);
        if(this.head != null) {
            Node entry = this.head;

            if(node.index < entry.index) {
                this.head = node;
                head.setNext(entry);
            } else {
                while(entry.next != null && entry.next.index < node.index) {
                    entry = entry.next;
                }
                node.setNext(entry.next);
                entry.setNext(node);
            }
        } else this.head = node;
    }

    double getElement(int index) throws IndexOutOfBoundsException{
        double res = 0.0;
        if(index > this.length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        Node entry = this.head;
        while(entry != null) {
            if (entry.index == index) {
                res = entry.value;
                break;
            }
            entry = entry.next;
        }
        return res;
    }

    void removeElement(int index) throws IndexOutOfBoundsException {
        if(index > this.length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        Node entry = this.head;
        if(entry.index == index){
            this.head = entry.next;
            return;
        }
        Node curr = entry;
        Node next = entry.next;
        while(next != null) {
            if (next.index == index) {
                curr.setNext(next.next);
                break;
            }
            curr = next;
            next = next.next;
        }
    }

    int getLength() {
        return this.length;
    }


    boolean equals(SparseVector other) {
        if(this.length != other.length) {
            return false;
        }
        Node me = this.head;
        Node not_me = other.head;
        while(me != null && not_me != null) {
            if(me.value != not_me.value || me.index != not_me.index) {
                return false;
            }
            me = me.next;
            not_me = not_me.next;
        }
        if(me != null || not_me != null) {
            return false;
        }
        return true;
    }

    void add(SparseVector other) throws ArithmeticException {
        if(this.length != other.length) {
            throw new ArithmeticException("Vectors are not the same length");
        }
        Node me = this.head;
        Node not_me = other.head;
        while(me != null && not_me != null) {
            while(me.index < not_me.index && me != null) {
                me = me.next;
            }
            if(me.index > not_me.index) {
                this.setElement(not_me.index, not_me.value);
                not_me = not_me.next;
            } else if(me.index == not_me.index) {
                me.value += not_me.value;
                if(me.value == 0) {
                    this.removeElement(me.index);
                }
                me = me.next;
                not_me = not_me.next;
            }
        }
        while(me == null && not_me != null) {
            this.setElement(not_me.index, not_me.value);
            not_me = not_me.next;
        }
    }

    boolean nodeExists(int index){
        Node entry = this.head;
        while(entry != null){
            if(entry.index == index) return true;
            entry = entry.next;
        }
        return false;
    }

    void setValue(int index, double value){
        Node entry = this.head;
        while(entry != null){
            if(entry.index == index){
                entry.value = value;
                return;
            }
        }
    }

}

class Node{

    double value;
    int index;
    Node next;

    Node(double wert, int i){
        this.value = wert;
        this.index = i;
    }

    void setNext(Node next) {
        this.next = next;
    }

}
