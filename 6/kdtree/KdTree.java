import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private boolean isVertical;
        public Node(Point2D point, boolean isVertical) {
            this.point = point;
            this.isVertical = isVertical;
        }
    }

    private Node root;
    private int size;
    public KdTree() {
        this.size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insertHelper(root, p, true);
    }
    private Node insertHelper(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            size ++;
            return new Node(p, isVertical);
        }
        if (node.point.equals(p)) return node;
        if (isVertical) {
            if (p.x() < node.point.x()) {
                node.left = insertHelper(node.left, p, !isVertical);
            } else {
                node.right = insertHelper(node.right, p, !isVertical);
            }
        } else {
            if (p.y() < node.point.y()) {
                node.left = insertHelper(node.left, p, !isVertical);
            } else {
                node.right = insertHelper(node.right, p, !isVertical);
            }
        }

        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return containsHelper(root, p);
    }
    private boolean containsHelper(Node node, Point2D p) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;
        if (node.isVertical) {
            if (p.x() < node.point.x()) {
                return containsHelper(node.left, p);
            }
            else {
                return containsHelper(node.right, p);
            }
        }
        else {
            if (p.y() < node.point.y()) {
                return containsHelper(node.left, p);
            }
            else {
                return containsHelper(node.right, p);
            }
        }
}
    public void draw() {
        draw(root, 0, 0, 1, 1); // 从根节点开始绘制，初始区域为整个单位正方形
    }

    private void draw(Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) return;

        // 绘制当前节点
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());

        // 设置分割线的颜色和方向
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(node.point.x(), ymin, node.point.x(), ymax); // 垂直分割线
            // 递归绘制左右子树
            draw(node.left, xmin, ymin, node.point.x(), ymax);
            draw(node.right, node.point.x(), ymin, xmax, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(xmin, node.point.y(), xmax, node.point.y()); // 水平分割线
            // 递归绘制左右子树
            draw(node.left, xmin, ymin, xmax, node.point.y());
            draw(node.right, xmin, node.point.y(), xmax, ymax);
        }
    }



    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) return;

        // 检查当前节点是否在矩形范围内
        if (rect.contains(node.point)) {
            result.add(node.point);
        }

        // 判断是否需要递归检查左右子树
        if (node.isVertical) {
            if (rect.xmin() <= node.point.x()) {
                range(node.left, rect, result); // 左子树可能有点在范围内
            }
            if (rect.xmax() >= node.point.x()) {
                range(node.right, rect, result); // 右子树可能有点在范围内
            }
        } else {
            if (rect.ymin() <= node.point.y()) {
                range(node.left, rect, result); // 下方子树可能有点在范围内
            }
            if (rect.ymax() >= node.point.y()) {
                range(node.right, rect, result); // 上方子树可能有点在范围内
            }
        }
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        if (root == null) return null;
        return thePoint(root, p, root).point;
    }

    private Node thePoint(Node current, Point2D p, Node nearest) {

        if (current == null)
            return nearest;

        if (current.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
            nearest = current;

        Node left = thePoint(current.left, p, nearest);
        Node right = thePoint(current.right, p, nearest);

        if (left.point.distanceSquaredTo(p) < right.point.distanceSquaredTo(p)) {
            if (left.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
                nearest = left;
        } else {
            if (right.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
                nearest = right;
        }
        return nearest;

    }

}
