import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Rectangle implements Serializable {
    ArrayList<Point> points = new ArrayList<Point>();
    double width;
    double height;

    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        this.points = new ArrayList<Point>(Arrays.asList(p1, p2, p3, p4));
        this.getDimensions();
    }

    public Point lowerPoint() {
        Point minPoint = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        for (Point p : points) {
        	minPoint = minPoint.compare(p) ? minPoint : p;
        }
        return minPoint;
    }

    public Point higherPoint() {
        Point maxPoint = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for (Point p : points) {
        	maxPoint = !maxPoint.compare(p) ? maxPoint : p;
        }
        return maxPoint;
    }

    public void getDimensions() {
        Point maxPoint = this.lowerPoint();
        Point minPoint = this.higherPoint();
        this.width = maxPoint.x - minPoint.x;
        this.height = maxPoint.y - minPoint.y;

    }

    public boolean overlaps (Rectangle r) {
        Point minP = this.lowerPoint();
        Point maxP = this.higherPoint();
        Point minR = r.lowerPoint();
        Point maxR = r.higherPoint();
        return !(minP.y > maxR.y || minR.y > maxP.y || maxR.x < minP.x || maxP.x < minR.x);
        //return (maxP.x < maxR.x + r.width) && (maxP.x + width > maxR.x) && (maxP.y < maxR.y + r.height) && (maxP.y + height > maxR.y);
    }
}
