/* A randomized queue is similar to a stack or queue, except that the item 
 * removed is chosen uniformlt at random from items in the data structure.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] q; //queue elements
    private int N = 0;    //number of elements in queue
    private int first = 0; //index of first element of queue
    private int last = 0; //index of next available slot        
     
    // cast needed since no generic array creation in Java
    @SuppressWarnings("unchecked")
    
    public RandomizedQueue()       // construct an empty randomized queue
    {
        q = (Item[]) new Object[2];
    }
    
    public boolean isEmpty()       // is the queue empty?
    {
        return (N == 0);
    }
    public int size()              // return the number of items on the queue
    {
        return N;
    }
    
    // resize the underlying array
    @SuppressWarnings("unchecked")
    private void resize(int max)
    {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
        {
            temp[i] = q[(first + i) % q.length];
            q = temp;
            first = 0;
            last = N;
        }
    }
    
    public void enqueue(Item item) // add the item
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        // double size of array if necessary and recopy to front of array
        if (N == q.length)
            resize(2 * q.length);
        q[last++] = item; //add item
        if (last == q.length)
            last = 0;
        N++;
    }
    
    public Item dequeue()          // remove and return a random item
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int index = (first + StdRandom.uniform(N)) % q.length;
        Item item = q[index];
        q[index] = q[first];
        q[first] = null;
        N--;
        first++;
        if (first == q.length)
            first = 0; // wrap-around
        if (N > 0 && N == q.length / 4)
            resize(q.length / 2);
        return item;
    }
    
    public Item sample()           // return (but do not remove) a random item  
    {
        if (isEmpty())
            throw new java.lang.UnsupportedOperationException();
        int index = (first + StdRandom.uniform(N)) % q.length;
        Item result = q[index];
        return result;
    }
   
    public Iterator<Item> iterator() //return an independent iterator over items in random order
    {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item>
    {
        private int[] order;
        private int index;
        
        public RandomIterator()
        {
            order = new int[N];
            for (int i = 0; i < N; ++i)
                order[i] = (first + i) % q.length;
            StdRandom.shuffle(order);
            index = 0;
        }
        
        public boolean hasNext()
        {
            return index < order.length;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = q[order[index++]];
            return item;
        }
    }
    public static void main(String[] args)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        for (int i = 0; i < 10; ++i)
        {
            q.enqueue(String.valueOf(i));
            if (i % 2 == 0)
                q.dequeue();
        }
        Iterator<String> iter = q.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
  }
    }
} 
