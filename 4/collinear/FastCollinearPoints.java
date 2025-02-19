import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
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
        }
        for (int i = 0; i < sortedPoints.length; i++) {
            Point origin = sortedPoints[i];
            Point[] otherPoints = sortedPoints.clone();
            Arrays.sort(otherPoints, origin.slopeOrder());

            int count = 1;
            for (int j = 1; j < otherPoints.length; j++) {
                if (j == otherPoints.length - 1 || origin.slopeTo(otherPoints[j]) != origin.slopeTo(otherPoints[j + 1])) {
                    if (count >= 3) {
                        Point[] collinear = new Point[count + 1];
                        collinear[0] = origin;

                        for (int k = 0; k < count; k++) {
                            collinear[k + 1] = otherPoints[j - count + 1 + k];
                        }
                        Arrays.sort(collinear);
                        if (origin.equals(collinear[0])) {
                            lineSegments.add(new LineSegment(collinear[0], collinear[collinear.length - 1]));
                        }
                    }
                    count = 1;
                } else count++;
        }
    }
}


    public int numberOfSegments() {
        return lineSegments.size();
    }
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }
    public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}