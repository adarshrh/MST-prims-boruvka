/**
 * IDSA Short Project 9
 * Team Members:
 * Adarsh Raghupati  NetID: axh190002
 * Keerti Keerti     NetID: kxk190012
 */
package axh190002;


import java.util.NoSuchElementException;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;
    static int threshold = 2;
    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        pq = new Comparable[maxCapacity];
        size = 0;
    }

    /**
     * @param x Checks the size , if need be resizes and increases the size of pq and adds new element in the pq
     * @return true if added successfully else false
     */
    public boolean add(T x) {
        if(size == pq.length)       //if pq is completely filled
            resize();
        move(size,x);
        percolateUp(size);
        size++;
        return true;
    }

    /**
     * @param x method to add new element in pq, inturn calls add method
     * @return true is add is successful else false
     */
    public boolean offer(T x) {
        return add(x);
    }

    /**
     * @return polled element
     * @throws NoSuchElementException if pq is empty or poll returns null
     */
    public T remove() throws NoSuchElementException {
        T result = poll();
        if(result == null) {
            throw new NoSuchElementException("Priority queue is empty");
        } else {
            return result;
        }
    }

    /**
     * Prints all elements of pq.
     */
    public void printHeap(){
        for(int i=0;i<size;i++)
            System.out.print(pq[i]+" ");
    }

    /**
     * @return min element of the pq
     */
    public T poll() {
        if(isEmpty()){
            System.out.println("No more elements in the Binary Heap");
            return null;
        }
        else {
            Comparable x = pq[0];
            size--;
            move(0, pq[size]);
            percolateDown(0);
            return (T) x;
        }
    }

    /**
     * @return minimum element of the pq by calling peek
     */
    public T min() {
        return peek();
    }

    /**
     * @return minimum element of pq
     */
    public T peek() {
        if(isEmpty()){
            System.out.println("No more elements in the Binary Heap");
            return null;
        }else{
            return (T) pq[0];
        }
    }

    /**
     * @param i : index of the ele for which parent node is required
     * @return the parent element of the index passed
     */
    int parent(int i) {
        return (i-1)/2;
    }

    /**
     * @param i index of the element for which left child node is required
     * @return left child element of the index passed.
     */
    int leftChild(int i) {
        return 2*i + 1;
    }

    /** pq[index] may violate heap order with parent */
    void percolateUp(int index) {
        Comparable x = pq[index];
        while(index>0 && compare(pq[parent(index)],x)>0){
            move(index,pq[parent(index)]);
            index = parent(index);
        }
        move(index,x);
    }

    /** pq[index] may violate heap order with children */
    void percolateDown(int index) {
        Comparable x = pq[index];
        int lcIndex=leftChild(index);

        while (lcIndex < size){
            if(lcIndex<size-1 && pq[lcIndex].compareTo(pq[lcIndex+1])>0)
                lcIndex++;
            if(x.compareTo((T)pq[lcIndex])<=0)
                break;
            move(index,(T)pq[lcIndex]);
            index=lcIndex;
            lcIndex=leftChild(index);
        }
        move(index,x);
    }

    /** use this whenever an element moved/stored in heap. Will be overridden by IndexedHeap */
    void move(int dest, Comparable x) {
        pq[dest] = x;
    }

    /**
     * Compares the 2 elements and returns -1: a<b , 0: a=b and 1: a>b
     * @param a element a
     * @param b element b
     * @return -1: a<b , 0: a=b and 1: a>b
     */
    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
        for(int i=parent(size-1); i>=0; i--) {
            percolateDown(i);
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    // Resize array to double the current size

    /**
     * increase the size of the pq by the threshold value
     */
    void resize() {
       /* Comparable[] newcmp = new Comparable[size*threshold];
        for(int i=0;i<pq.length;i++){
            newcmp[i] = pq[i];
        }
        pq = newcmp;
        buildHeap();*/
        Comparable[] oldPQ = pq;
        pq = new Comparable[size * 2];
        for(int i = 0 ; i < oldPQ.length ; i++){
            pq[i] = oldPQ[i];
        }
        buildHeap();
    }

    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        IndexedHeap(int capacity) {
            super(capacity);
        }

        /** restore heap order property after the priority of x has decreased */
        void decreaseKey(T x) {
            percolateUp(x.getIndex());
        }

        @Override
        void move(int i, Comparable x) {
            super.move(i, x);
            ((T) x).putIndex(i);
        }
    }

    public static void main(String[] args) {
        BinaryHeap<Integer> h = new BinaryHeap(threshold);  // Creating a BinaryHeap with initial Threshold and resize it if need be.
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Enter 1: input element 2:Pop Minimum element 3: Print elements in BinaryHeap 4: exit");
            int choice = scan.nextInt();
            switch(choice){
                case 1: System.out.println("Enter the input element");
                    if(h.add(scan.nextInt()))
                        System.out.println("Successfully Inserted");
                    else
                        System.out.println("Insertion Unsuccessful");
                    break;
                case 2:
                    System.out.println(h.poll());
                    break;
                case 3: h.printHeap();
                    break;
                case 4: System.exit(0);
            }
        }
    }
}

