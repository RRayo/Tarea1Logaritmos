import java.util.ArrayList;

public class RectangleMethods {
	public static Point lowerPoint(ArrayList<Point> points) {
        Point minPoint = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        for (Point p : points) {
        	minPoint = PointMethods.compare(minPoint, p) ? minPoint : p;
        }
        return minPoint;
    }

    public static Point higherPoint(ArrayList<Point> points) {
        Point maxPoint = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for (Point p : points) {
        	maxPoint = !PointMethods.compare(maxPoint, p) ? maxPoint : p;
        }
        return maxPoint;
    }

    public static double getArea(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.y - p2.y);
    }
    
    public static double getArea(Rectangle r) {
        return Math.abs((r.maxPoint.x - r.minPoint.x) * (r.maxPoint.y - r.minPoint.y));
    }

    public static boolean overlaps (Rectangle r1, Rectangle r2) {
        return !(r1.minPoint.y > r2.maxPoint.y || r2.minPoint.y > r1.maxPoint.y || r2.maxPoint.x < r1.minPoint.x || r1.maxPoint.x < r2.minPoint.x);
        //return (maxP.x < maxR.x + r.width) && (maxP.x + width > maxR.x) && (maxP.y < maxR.y + r.height) && (maxP.y + height > maxR.y);
    }

    public static double areaEnlarge (Rectangle r1, Rectangle r2) {
        double area = getArea(r1);
        Point newMinPoint = PointMethods.compare(r1.minPoint, r2.minPoint)? r1.minPoint : r2.minPoint;
        Point newMaxPoint = !PointMethods.compare(r1.maxPoint, r2.maxPoint)? r1.maxPoint : r2.maxPoint;
        double newArea = (newMaxPoint.x - newMinPoint.x)*(newMaxPoint.y - newMinPoint.y);
	    if (area == 0.0) {
	        return area;
        } else {
            return newArea - area;
        }
    }
}
