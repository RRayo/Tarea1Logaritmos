
public class PointMethods {
	/*
     * Compara si el punto actual es menor o igual en todas sus coordenadas respecto al nuevo punto p.
     */
    public static boolean compare(Point p1, Point p2){
        return p1.x <= p2.x && p1.y <= p2.y;
    }

}
