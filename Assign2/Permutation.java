import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class Permutation
{
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String s;
        String out;
        while (!StdIn.isEmpty())
        {
            s = StdIn.readString();
            q.enqueue(s);
        }
        
        for (int i = 0; i < k; i++)
        {
            out = q.dequeue();
            StdOut.println(out);
        }
    }
}