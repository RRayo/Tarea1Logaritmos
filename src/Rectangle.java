import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Rectangulo con 2 puntos que lo definen en el espacio, el de la esquina superior derecha y la esquina inferior izquierda.
 * 
 */
@SuppressWarnings("serial")
public class Rectangle implements Serializable {
    //ArrayList<Point> points = new ArrayList<Point>();
    Point minPoint;
    Point maxPoint;

    /**
     * Rectangulo default, con puntos (0,0)
     */
    public Rectangle() {
        this.minPoint = new Point();
        this.maxPoint = new Point();
    }

    /**
     * Rectangulo definido por 4 puntos, donde se eligen el maximo superior y minimo inferior.
     * @param p1 Punto 1.
     * @param p2 Punto 2.
     * @param p3 Punto 3.
     * @param p4 Punto 4.
     */
    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(p1, p2, p3, p4));
        this.minPoint = RectangleMethods.lowerPoint(points);
        this.maxPoint = RectangleMethods.higherPoint(points);
    }

    /**
     * Rectangulo definido por 2 puntos.
     * @param minPoint Punto inferior izquierdo.
     * @param maxPoint Punto superior derecho.
     */
    public Rectangle(Point minPoint, Point maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

}
