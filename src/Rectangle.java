import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Rectangle implements Serializable {
    ArrayList<Point> points = new ArrayList<Point>();
    double width;
    double height;

    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        this.points = new ArrayList<Point>(Arrays.asList(p1, p2, p3, p4));
        getDimensions();
    }

    public Point lowerPoint() {
        Point minPoint = null;
        for (Point p : points) {
            if (minPoint == null) {
                minPoint = p;
            } else {
                minPoint = minPoint.compare(p) ? minPoint : p;
            }
        }
        return minPoint;
    }

    public Point higherPoint() {
        Point maxPoint = null;
        for (Point p : points) {
            if (maxPoint == null) {
                maxPoint = p;
            } else {
                maxPoint = !maxPoint.compare(p) ? maxPoint : p;
            }
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
        Point minP = this.higherPoint();
        Point minR = r.higherPoint();
        return minP.x < minR.x + r.width && minP.x + width > minR.x && minP.y < minR.y + r.height && minP.y + height > minR.y;
    }
}
