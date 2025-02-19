import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf; //包含顶底节点，供percolates判断
    private WeightedQuickUnionUF fullUf; //不包含底部节点，否则isFull会有错误的判断，
    private int topVirtualNode;
    private int bottomVirtualNode;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be larger than 0");
        this.n = n;
        this.grid = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.fullUf = new WeightedQuickUnionUF(n * n + 1);
        this.topVirtualNode = n * n;
        this.bottomVirtualNode = n * n + 1;
        this.openSites = 0;
    }
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException("out of bound");
        }
    }

    private int to1D(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        grid[row - 1][col - 1] = true;
        openSites++;

        int current = to1D(row, col);

        if (row == 1){
            uf.union(current, topVirtualNode);
            fullUf.union(current, topVirtualNode);
        }
        if (row == n) {
            uf.union(current, bottomVirtualNode);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(current, to1D(row - 1, col));
            fullUf.union(current, to1D(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            uf.union(current, to1D(row + 1, col));
            fullUf.union(current, to1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(current, to1D(row, col - 1));
            fullUf.union(current, to1D(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(current, to1D(row, col + 1));
            fullUf.union(current, to1D(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full? 是否连通到顶部？
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && fullUf.find(to1D(row, col)) == fullUf.find(topVirtualNode);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topVirtualNode) == uf.find(bottomVirtualNode);
    }

    // test client (optional)
    public static void main(String[] args) {
        int input = StdIn.readInt();
        Percolation p = new Percolation(input);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            p.open(row, col);
            StdOut.println(p.isFull(row, col));
        }
        StdOut.println(p.percolates());
    }
}