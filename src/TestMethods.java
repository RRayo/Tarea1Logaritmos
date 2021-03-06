import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestMethods {
    static Rectangle generateRectangle() {
        Point axisPoint = generateAxisPoint();
        return new Rectangle(axisPoint, generatePoint(axisPoint));
    }

    private static Point generateAxisPoint() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Point(random.nextDouble(0, 500000), random.nextDouble(0, 500000));
    }

    private static Point generatePoint(Point axisPoint) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Point(axisPoint.x + random.nextDouble(0, 100), axisPoint.y + random.nextDouble(0, 100));
    }

    static void insertN(long n) {
        for (int i = 0; i < n; i++) {
            Rtree.insertRectangle(generateRectangle());
        }
    }

    public static int searchN(long n) {
        int totalDiskAccess = 0;
        for (int i = 0; i < n; i++) {
            totalDiskAccess = Integer.parseInt(Rtree.search(generateRectangle()).split("-")[1]);
        }
        return totalDiskAccess;
    }
    
    public static List<Rectangle> crearRectangulos (Point[] highPoints, Point[] lowPoints) {
    	List<Rectangle> r = new ArrayList<Rectangle>();
    	for(int i = 0; i<(Math.min(highPoints.length, lowPoints.length)); i++){
    		r.add(new Rectangle(lowPoints[i], highPoints[i]));
    	}
		return r;
    }
    
    public static void insertRectangles (List<Rectangle> rectangles) {
        for (Rectangle r : rectangles) {
            Rtree.insertRectangle(r);
        }
    }

    static int treeHeigth() {
        Node node = Rtree.loadNode(Rtree.treeId);
        int height = 0;
        assert node != null;
        while (!node.type.equals("L")) {
            height++;
            node = Rtree.loadNode(node.registers.get(0).serialVersionUID);
        }
        return height;
    }
    
    
}
