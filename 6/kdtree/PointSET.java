import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private int size;
    private SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        size = 0;
        set = new SET<>();
    }
    // is the set empty? 
    public boolean isEmpty() {
        return size == 0;
    }
    // number of points in the set 
    public int size(){
        return size;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!set.contains(p)) {
            set.add(p);
            size ++;
        } 
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }
    // draw all points to standard draw 
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new ArrayList<>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Query point cannot be null");
        if (set.isEmpty()) return null;
    
        double shortestDist = Double.POSITIVE_INFINITY;
        Point2D result = null;
    
        for (Point2D point : set) {
            double dist = point.distanceSquaredTo(p);
            if (dist < shortestDist) {
                shortestDist = dist;
                result = point;
            }
        }
    
        return result;
    }
    public static void main(String[] args){

    }                  // unit testing of the methods (optional) 
 }