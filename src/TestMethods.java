import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestMethods {
    public static Rectangle generateRectangle () {
        Point axisPoint = generateAxisPoint();
        return new Rectangle(axisPoint, generatePoint(axisPoint));
    }

    public static Point generateAxisPoint () {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Point(random.nextDouble(0, 500000), random.nextDouble(0, 500000));
    }

    public static Point generatePoint (Point axisPoint) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Point(axisPoint.x + random.nextDouble(0, 100), axisPoint.y + random.nextDouble(0, 100));
    }

    public static void insert100 () {
        for (int i = 0; i < 100; i++) {
            Rtree.insertRectangle(generateRectangle());
        }
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

    public static int treeHeigth () {
        Node node = Rtree.loadNode(Rtree.treeId);
        int height = 0;
        while (!node.type.equals("L")) {
            height++;
            node = Rtree.loadNode(node.registers.get(0).serialVersionUID);
        }
        return height;
    }
}
