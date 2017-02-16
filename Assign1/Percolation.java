import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    
    private boolean[] openSites;
    private int gridN;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UFFull;
    
    public Percolation(int N)
    {
        openSites = new boolean[N * N];
        gridN = N;
        for (int i = 0; i < N * N; ++i)
        {
            openSites[i] = false;
        }
        UF = new WeightedQuickUnionUF(N * N + 2);
        UFFull = new WeightedQuickUnionUF(N * N + 1); //why    
    }
    
    private void indexChecker(int i, int j)
    {
        if (i < 1 || j < 1 || i > gridN || j > gridN)
            throw new java.lang.IndexOutOfBoundsException();
    }
    
    public void open(int i, int j)
    {
        indexChecker(i, j);
        
        int indexI = i - 1;
        int indexJ = j - 1;
        
        int osIndex = indexI * gridN + indexJ;
        if (openSites[osIndex])
            return;
        openSites[osIndex] = true;
        
        //index when 2 virtual sites are added
        int ufIndex = indexI * gridN + indexJ + 1;
        
        //if on the first row
        if (indexI == 0)
        {
            UF.union(0, ufIndex);
            UFFull.union(0, ufIndex);
        }
        
        // if on the last row
        if (indexI == gridN - 1)
        {
            UF.union(ufIndex, gridN * gridN + 1);
        }
        
        boolean bOpen = false;
        
        // union adjacent open sites
        int leftIndexI = indexI;
        int leftIndexJ = indexJ - 1;
        if (leftIndexJ >= 0)
        {
            bOpen = isOpen(leftIndexI + 1, leftIndexJ + 1);
            if (bOpen)
            {
                int leftUFIndex = leftIndexI * gridN + leftIndexJ + 1;
                UF.union(leftUFIndex, ufIndex);
                UFFull.union(leftUFIndex, ufIndex);
            }
        }
        
        int rightIndexI = indexI;
        int rightIndexJ = indexJ + 1;
        if (rightIndexJ < gridN)
        {
            bOpen = isOpen(rightIndexI + 1, rightIndexJ + 1);
            if (bOpen)
            {
                int rightUFIndex = rightIndexI * gridN + rightIndexJ + 1;
                UF.union(rightUFIndex, ufIndex);
                UFFull.union(rightUFIndex, ufIndex);
            }
        }
        
        int upIndexI = indexI - 1;
        int upIndexJ = indexJ;
        if (upIndexI >= 0)
        {
            bOpen = isOpen(upIndexI + 1, upIndexJ + 1);
            if (bOpen)
            {
                int upUFIndex = upIndexI * gridN + upIndexJ + 1;
                UF.union(upUFIndex, ufIndex);
                UFFull.union(upUFIndex, ufIndex);
            }
        }
        
        int downIndexI = indexI + 1;
        int downIndexJ = indexJ;
        if (downIndexI < gridN)
        {
            bOpen = isOpen(downIndexI + 1, downIndexJ + 1);
            if (bOpen)
            {
                int downUFIndex = downIndexI * gridN + downIndexJ + 1;
                UF.union(downUFIndex, ufIndex);
                UFFull.union(downUFIndex, ufIndex);
            }
        }
    }
    
    public boolean isOpen(int i, int j)
    {
        indexChecker(i, j);
        return (openSites[ (i - 1) * gridN + j - 1]);
    }
    
    public boolean isFull(int i, int j)
    {
        indexChecker(i, j);
        int indexI = i - 1;
        int indexJ = j - 1;
        
        int osIndex = indexI * gridN + indexJ;
        int ufIndex = osIndex + 1;
        
        boolean bOpen = isOpen(i, j);
        boolean isFull = UFFull.connected(0, ufIndex);
        return (bOpen && isFull);
    }
    public int numberOfOpenSites()
    {
        int sum = 0;
        for(boolean bOpen : openSites)
        {
           if(bOpen)
           {
               sum++;
           }
        }
        return (sum);
    }
    public boolean percolates()
    {
        if (gridN == 1)
            return (openSites[0]);
        return UF.connected(0, gridN * gridN + 1);
    }
    //public static void main(String[] args){}
}
