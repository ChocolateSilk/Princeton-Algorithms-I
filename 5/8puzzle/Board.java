import java.util.Arrays;
import java.util.Stack;

public class Board {

    private final int size;
    private final int[][] tiles;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = tiles;
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int[] flatten = flatten(tiles);
        int result = 0;
        for(int i = 0; i < size*size; i++) {
            if(flatten[i] != i) result++;
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int[] coordinates = manhattanHelper(i * size + j);
                    int x = coordinates[0];
                    int y = coordinates[1];
                    dist += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (this == y){
            return true;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();

        int blankRow = -1, blankCol = -1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (tiles[row][col] == 0) {
                    blankRow = row; 
                    blankCol = col; 
                    break;          
                }
            }
            if (blankRow != -1) break; 
        }
        int[][] directions = {
            {-1, 0}, 
            {1, 0},  
            {0, -1}, 
            {0, 1} 
        };
        for (int[] direction : directions) {
            int newRow = blankRow + direction[0];
            int newCol = blankCol + direction[1];
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                int[][] newTiles = copyTiles();
                newTiles[blankRow][blankCol] = tiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.push(new Board(newTiles));
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = copyTiles();
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size - 1; j ++){
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    int temp = newTiles[i][j];
                    newTiles[i][j] = newTiles[i][j + 1];
                    newTiles[i][j + 1] = temp;
                    return new Board(newTiles);
                }
            }
        }
        throw new IllegalArgumentException("No twin");
    }

    private int[] flatten(int[][] tiles) {
        int[] flatten = new int[size * size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                flatten[i * size + j] = tiles[i][j];
            }
        }
        return flatten;
    }
    private int[] manhattanHelper(int index) {
        int x = index / size;
        int y = index % size;

        return new int[]{x, y};
    }

    private int[][] copyTiles() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            copy[i] = tiles[i].clone();
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}