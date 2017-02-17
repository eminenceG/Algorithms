import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item>
{
    private int N; // number of elements in queue
    private Node first; // beginning of queue;
    private Node last; // end of queue
    
    private class Node
    {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    public Deque()                    // construct an empty deque
    {
        first = null;
        last = null;
        N = 0;
    }
    public boolean isEmpty()          // is the deque empty
    {
        return(N == 0);
    }
    public int size()                 // return the number of items on the deque
    {
        return N;
    }
    public void addFirst(Item item)   // add the item to the front
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        boolean empty = isEmpty();
        Node oldFirst = first;
        
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        
        if (empty) { last = first; }
        else { oldFirst.prev = first; }
        ++N;
    }
    
    public void addLast(Item item)    // add the item to the end
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        boolean empty = isEmpty();
        Node oldLast = last;
        
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        
        if (empty) {first = last; }
        else { oldLast.next = last; }
        ++N;
    }
    public Item removeFirst()         // remove and teturn the item from the front
    {
        boolean empty = isEmpty();
        if (empty)
            throw new java.util.NoSuchElementException();
        
        Item item = first.item;
        first = first.next;
        if (N == 1) { last = null; }
        if (first != null) { first.prev = null; }
        --N;
        return item;
    }
    
    public Item removeLast()          // remove and return the item from the end
    {
        boolean empty = isEmpty();
        if (empty)
            throw new java.util.NoSuchElementException();
        
        Item item = last.item;
        last = last.prev;
        last.next = null;
        
        --N;
        
        return item;
        
    }
    
    public Iterator<Item> iterator()  // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {
            return current != null;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] atgs) //optional
    {
        Deque<String> q = new Deque<String>();
        for (int i = 0; i < 10; ++i)
        {
            if (i % 2 == 0) { q.addLast(String.valueOf(i)); }
            else if (i % 3 == 0) { q.removeLast(); }
        }
        for(String item : q) { StdOut.println(item); }
    }
}