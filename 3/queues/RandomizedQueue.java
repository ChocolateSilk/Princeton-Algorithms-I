import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;
    private int size;
    private Item[] q; 
    private int first;
    private int last; 

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
        first = 0;
        last = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last  = size;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("null item");
        if (size == q.length) resize(2*q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("empty queue");
        int r = StdRandom.uniformInt(size);
        Item item = q[r];
        q[r] = q[--size];
        q[size] = null;
        if (size > 0 && size == q.length / 4) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("empty queue");
        int r = StdRandom.uniformInt(size);
        Item item = q[r];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        private Item[] iterArray;
        private int current;

        public ArrayIterator() {
            iterArray = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iterArray[i] = q[i];
            }
            StdRandom.shuffle(iterArray);
            current = 0;
        }
        public boolean hasNext() {
            return current < iterArray.length;
        }
        public void remove() {
            throw new UnsupportedOperationException("method forbidden");
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no more elements");
            return iterArray[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rQ = new RandomizedQueue<>();
        StdOut.println("rQ empty?: " + rQ.isEmpty());
        StdOut.println("rQ initial size: " + rQ.size());

        StdOut.println("\n== enqueue ==");
        rQ.enqueue("A");
        rQ.enqueue("B");
        rQ.enqueue("C");
        rQ.enqueue("D");
        StdOut.println("rQ empty? " + rQ.isEmpty());
        StdOut.println("rQ size: " + rQ.size());

        StdOut.println("\n== Iterate through ==");
        for (String item : rQ) {
            StdOut.print(item + " ");
        }
        StdOut.println();

        rQ.dequeue();
        rQ.dequeue();

        StdOut.println("\n== Iterate through one more time ==");
        for (String item : rQ) {
            StdOut.print(item + " ");
        }
        StdOut.println();

        StdOut.println("rQ empty?: " + rQ.isEmpty());
        StdOut.println("rQ size: " + rQ.size());

    }

}
