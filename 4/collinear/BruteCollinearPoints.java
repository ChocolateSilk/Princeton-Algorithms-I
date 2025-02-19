import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if(points == null) throw new IllegalArgumentException("no points given");
        for(Point p : points) {
            if(p == null) throw new IllegalArgumentException("contain null points");
        }
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for(int i = 1; i < sortedPoints.length; i++) {
            if(sortedPoints[i].compareTo(sortedPoints[i-1]) == 0) {
                throw new IllegalArgumentException("duplicated points");
            }
        
        for(int p = 0; p < sortedPoints.length; p++) {
            for(int q = p + 1; q < sortedPoints.length; q++) {
                for(int r = q + 1; r < sortedPoints.length; r++) {
                    for(int s = r + 1; s < sortedPoints.length; s++) {
                        
                        if(sortedPoints[p].compareTo(sortedPoints[q]) == sortedPoints[q].compareTo(sortedPoints[r]) && 
                        sortedPoints[r].compareTo(sortedPoints[q]) == sortedPoints[r].compareTo(sortedPoints[s])){
                            lineSegments.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                        
                    }
                }
            }
        }
        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }
    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }
 }