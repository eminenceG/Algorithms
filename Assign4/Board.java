import java.util.Iterator;
import java.util.Arrays;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.NullPointerException();
        this.blocks = new int[blocks.length][blocks.length];
        for (int k = 0; k < blocks.length; k++) {
            this.blocks[k] = Arrays.copyOf(blocks[k], blocks.length);
        }
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int result = 0;
        int dim = dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != (i * dim + j + 1))
                    result++;
            }
        }
        result -= 1;
        return result;
    }

    public int manhattan() {
        int result = 0;
        int dim = dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0)
                    continue;
                int righti = (blocks[i][j] - 1) / dim;
                int rightj = blocks[i][j] - 1 - righti * dim;
                result += Math.abs(righti - i) + Math.abs(rightj - j);
            }
        }
        return result;
    }
    public boolean isGoal() {
        int dim = dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((i * dim + j + 1) == dim * dim)
                    continue;
                if (blocks[i][j] != i * dim + j + 1)
                    return false;
            }
        }
        return true;
    }

    public Board twin() {

        int dim = dimension();
        int[][] twin = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                twin[i][j] = blocks[i][j];
            }
        }
        if (twin[0][0] != 0 && twin[0][1] != 0) {
            int t = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = t;    
        }
        else {
            int t = twin[1][0];
            twin[1][0] = twin[1][1];
            twin[1][1] = t;
        }
        Board twinBoard = new Board(twin);

        return twinBoard;
    }


    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        int dim = dimension();
        if (dim != ((Board) y).dimension())
            return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != ((Board) y).blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        return new Neighbors(blocks);
    }

    private class Neighbors implements Iterable<Board> {

        private int dim = dimension();
        private Board[] neighbors;
        private int nums;

        public Neighbors(int[][] blocks) {
            done:
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (blocks[i][j] == 0) {
                        if (i == 0 && j == 0) {
                            nums = 2;
                            neighbors = new Board[nums];
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(rightblocks);
                            neighbors[1] = new Board(downblocks);
                            break done;
                        }
                        else if (i == 0 && j == dim - 1) {
                            nums = 2;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(downblocks);
                            break done;
                        }

                        else if (i == dim - 1 && j == 0) {
                            nums = 2;
                            neighbors = new Board[nums];
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            neighbors[0] = new Board(rightblocks);
                            neighbors[1] = new Board(upblocks);
                            break done;
                        }

                        else if (i == dim - 1 && j == dim - 1) {
                            nums = 2;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(upblocks);
                            break done;
                        }
                        else if (i == 0) {
                            nums  = 3;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(rightblocks);
                            neighbors[2] = new Board(downblocks);
                            break done;
                        }
                        else if (i ==  dim -1) {
                            nums = 3;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(rightblocks);
                            neighbors[2] = new Board(upblocks);
                            break done;
                        }
                        else if (j == 0) {
                            nums = 3;   
                            neighbors = new Board[nums];
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(rightblocks);
                            neighbors[1] = new Board(upblocks);
                            neighbors[2] = new Board(downblocks);
                            break done;
                        }
                        else if (j == dim - 1) {
                            nums = 3;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(upblocks);
                            neighbors[2] = new Board(downblocks);
                            break done;
                        }
                        else {
                            nums = 4;
                            neighbors = new Board[nums];
                            int[][] leftblocks = leftChange(blocks, i, j);  
                            int[][] rightblocks = rightChange(blocks, i, j);  
                            int[][] upblocks = upChange(blocks, i, j);  
                            int[][] downblocks = downChange(blocks, i, j);  
                            neighbors[0] = new Board(leftblocks);
                            neighbors[1] = new Board(rightblocks);
                            neighbors[2] = new Board(upblocks);
                            neighbors[3] = new Board(downblocks);
                            break done;
                        }
                    }
                }
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }

        public class NeighborIterator implements Iterator<Board> {
            private int index;

            public NeighborIterator() {
                index = 0;
            }
            public boolean hasNext() {
                return index < nums;
            }

            public Board next() {
                if (neighbors[index] == null)
                    throw new java.util.NoSuchElementException();
                return neighbors[index++];
            }
        }
    }
    private int[][] leftChange(int[][] blocks, int i, int j) {
        int dim = blocks.length;
        int[][] leftBlocks = new int[dim][dim];
        for (int k = 0; k < dim; k++) {
            leftBlocks[k] = Arrays.copyOf(blocks[k], dim);
        }
        int left = blocks[i][j - 1];
        leftBlocks[i][j] = left;
        leftBlocks[i][j - 1] = 0;
        return leftBlocks;
    }
    private int[][] rightChange(int[][] blocks, int i, int j) {
        int dim = blocks.length;
        int[][] rightBlocks = new int[dim][dim];
        for (int k = 0; k < dim; k++) {
            rightBlocks[k] = Arrays.copyOf(blocks[k], dim);
        }
        int right = blocks[i][j + 1];
        rightBlocks[i][j] = right;
        rightBlocks[i][j + 1] = 0;
        return rightBlocks;
    }
    private int[][] upChange(int[][] blocks, int i, int j) {
        int dim = blocks.length;
        int[][] upBlocks = new int[dim][dim];
        for (int k = 0; k < dim; k++) {
            upBlocks[k] = Arrays.copyOf(blocks[k], dim);
        }
        int up = blocks[i - 1][j];
        upBlocks[i][j] = up;
        upBlocks[i - 1][j] = 0;
        return upBlocks;
    }
    private int[][] downChange(int[][] blocks, int i, int j) {
        int dim = blocks.length;
        int[][] downBlocks = new int[dim][dim];
        for (int k = 0; k < dim; k++) {
            downBlocks[k] = Arrays.copyOf(blocks[k], dim);
        }
        int down = blocks[i + 1][j];
        downBlocks[i][j] = down;
        downBlocks[i + 1][j] = 0;
        return downBlocks;
    }

    public String toString() {
        int dim = dimension();
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}