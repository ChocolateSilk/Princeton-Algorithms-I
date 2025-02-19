import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }
        public int compareTo(SearchNode other) {
            return this.priority - other.priority;
        }
    }

    private boolean solvable;  
    private SearchNode solutionNode;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board cannot be null");
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        while (true) {
            if (expandSearch(pq)) {
                solvable = true;
                return; 
            }

            if (expandSearch(twinPQ)) {
                solvable = false;
                return;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
            return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? solutionNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;

        Stack<Board> path = new Stack<>();
        for (SearchNode node = solutionNode; node != null; node = node.previous) {
            path.push(node.board);
        }

        List<Board> result = new ArrayList<>();
        while (!path.isEmpty()) {
            result.add(path.pop());
        }
        return result;
    }
    private boolean expandSearch(MinPQ<SearchNode> pq) {
        if (pq.isEmpty()) return false;

        SearchNode current = pq.delMin();

        if (current.board.isGoal()) {
            solutionNode = current;
            return true;
        }

        for (Board neighbor : current.board.neighbors()) {
            if (current.previous == null || !neighbor.equals(current.previous.board)) {
                pq.insert(new SearchNode(neighbor, current.moves + 1, current));
            }
        }

        return false;
    }

    // test client (see below) 
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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