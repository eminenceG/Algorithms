import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twinPq;
    private Board board;
    private ArrayList<Board> result;
    private boolean solvablity;

    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.NullPointerException();
        board = initial;
        result = new ArrayList<>();
        solve();
    }

    public boolean isSolvable() {
        return solvablity;
    }

    public int moves() {
        return result.size() - 1;
    }
    
    public Iterable<Board> solution() {
        return result;
    }

    private void solve() {
        Board twinBoard = board.twin();
        if (twinBoard.isGoal()) {
            solvablity = false;
            return;
        }

        if (board.isGoal()) {
            result.add(board);
            return;
        }

        pq = new MinPQ<SearchNode>();
        twinPq = new MinPQ<SearchNode>();
        SearchNode node = new SearchNode(null, board);
        SearchNode twinNode = new SearchNode(null, twinBoard);
        pq.insert(node); 
        twinPq.insert(twinNode);

        while (!board.isGoal() && !twinBoard.isGoal()) {
            node = pq.delMin();
            board = node.getBoard();                    
            for (Board i : board.neighbors())
                if (node.prev == null || !i.equals(node.prev.getBoard()))
                    pq.insert(new SearchNode(node, i));

            twinNode = twinPq.delMin();
            twinBoard = twinNode.getBoard();
            for (Board i : twinBoard.neighbors())
                if (twinNode.prev == null || !i.equals(twinNode.prev.getBoard()))
                    twinPq.insert(new SearchNode(twinNode, i));
        }
        if (twinBoard.isGoal()) {
            solvablity = false;
            return;
        }
        while (node != null) {
            result.add(0, node.getBoard());
            node = node.prev;
        }
        solvablity = true;
        return;
    }


    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode prev;
        private Board b;
        private int moves;
        private int priority;

        public SearchNode(SearchNode prev, Board b) {
            if (prev == null)
                moves = 0;
            else
                moves = prev.getMoves() + 1;
            this.b = b;
            this.prev = prev;
            priority = moves + b.manhattan();
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }

        public int getMoves() {
            return moves; 
        }

        public Board getBoard() {
            return b;
        }
    }

    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}