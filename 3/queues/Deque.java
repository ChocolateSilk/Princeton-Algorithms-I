import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int size;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (isEmpty()) {
            first = new Node<Item>();
            first.item = item;
            first.next = null;
            last = first;
        } else {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("cannot add null item");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("null deque");
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        size --;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("null deque");
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        }
        else {
            Node<Item> current = first;
            while (current.next != last) {
                current = current.next;
            }
            current.next = null;
            last = current;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }
    
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            this.current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        StdOut.println("Deque Empty?: " + deque.isEmpty());
        StdOut.println("Deque Initial size: " + deque.size());

        StdOut.println("\n== enqueue ==");
        deque.addFirst("A");
        StdOut.println("After addFirst(A): first = " + deque.first + ", last = " + deque.last);
        deque.addLast("B");
        deque.addFirst("C");
        deque.addLast("D");

        StdOut.println("Deque size: " + deque.size());
        StdOut.println("Deque empty?: " + deque.isEmpty());

        StdOut.println("\n== Iterate through deque ==");
        for (String item : deque) {
            StdOut.print(item + " ");
        }
        StdOut.println();

        StdOut.println("\n== dequeue ==");
        StdOut.println("Remove first: " + deque.removeFirst());
        StdOut.println("Remove last: " + deque.removeLast());
        StdOut.println("Deque size: " + deque.size());

        StdOut.println("\n== Iterate through deque one more time ==");
        for (String item : deque) {
            StdOut.print(item + " ");
        }
        StdOut.println();

        StdOut.println("\n== Empty deque ==");
        if (!deque.isEmpty()) {
            deque.removeFirst();
            deque.removeLast();
        }
        StdOut.println("Deque empty?: " + deque.isEmpty());
        StdOut.println("Deque size: " + deque.size());
        }
    }