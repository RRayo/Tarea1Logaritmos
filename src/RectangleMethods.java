import java.util.ArrayList;

/**
 * Metodos para operar sobre rectangulos.
 *
 */
public class RectangleMethods {

	/**
	 * De una lista de puntos devuelve el que tenga el punto (x,y) mas a la izquierda y abajo.
	 * @param points Lista de puntos que describen un rectangulo.
	 * @return Retorna el punto mas a la izquierda y abajo del rectangulo.
	 */
	public static Point lowerPoint(ArrayList<Point> points) {
        Point minPoint = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        for (Point p : points) {
        	minPoint = PointMethods.compare(minPoint, p) ? minPoint : p;
        }
        return minPoint;
    }

	/**
	 * De una lista de puntos devuelve el que tenga el punto (x,y) mas a la derecha y arriba.
	 * @param points Lista de puntos que describen un rectangulo.
	 * @return Retorna el punto mas a la derecha y arriba del rectangulo.
	 */
    public static Point higherPoint(ArrayList<Point> points) {
        Point maxPoint = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for (Point p : points) {
        	maxPoint = !PointMethods.compare(maxPoint, p) ? maxPoint : p;
        }
        return maxPoint;
    }

    /**
     * Calcula el area de un rectangulo dados los puntos que lo describen.
     * @param p1 Punto 1.
     * @param p2 Punto 2.
     * @return Retorna el area formada por los puntos.
     */
    public static double getArea(Point p1, Point p2) {
        return Math.abs((p1.x - p2.x) *(p1.y - p2.y));
    }
    
    /**
     * Calcula el area de un rectangulo.
     * @param r Rectangulo con el area por calcular.
     * @return Retorna el area del rectangulo ingresado.
     */
    public static double getArea(Rectangle r) {
        return Math.abs((r.maxPoint.x - r.minPoint.x) * (r.maxPoint.y - r.minPoint.y));
    }

    /**
     * Comprueba si 2 rectangulos se sobrelapan.
     * @param r1 Rectangulo 1.
     * @param r2 Rectangulo 2.
     * @return Retorna true si los rectangulos se sobrelapan.
     */
    public static boolean overlaps (Rectangle r1, Rectangle r2) {
        return !(r1.minPoint.y > r2.maxPoint.y || r2.minPoint.y > r1.maxPoint.y || r2.maxPoint.x < r1.minPoint.x || r1.maxPoint.x < r2.minPoint.x);
        //return (maxP.x < maxR.x + r.width) && (maxP.x + width > maxR.x) && (maxP.y < maxR.y + r.height) && (maxP.y + height > maxR.y);
    }

    /**
     * Calcula la diferencia del area que cubre ambos rectangulos menos el area del primero.
     * Si el area del primer rectangulo es cero retorna el area del segundo.
     * @param r1 Primer rectangulo.
     * @param r2 Segundo rectangulo.
     * @return
     */
    public static double areaEnlarge (Rectangle r1, Rectangle r2) {
        double area = getArea(r1);
        if (area == 0.0) { //TODO revisar posicion
	        return getArea(r2);
        }
        Point newMinPoint = PointMethods.compare(r1.minPoint, r2.minPoint)? r1.minPoint : r2.minPoint;
        Point newMaxPoint = !PointMethods.compare(r1.maxPoint, r2.maxPoint)? r1.maxPoint : r2.maxPoint;
        double newArea = getArea(newMaxPoint, newMinPoint);
        return newArea - area;
    }
}
