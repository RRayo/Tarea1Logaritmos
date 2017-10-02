/**
 * Metodos para operar sobre puntos.
 */
public class PointMethods {
	
	/**
	 * Compara si el punto p1 es menor o igual en todas sus coordenadas respecto al punto p2.
	 * @param p1 Punto a comparar.
	 * @param p2 Punto con el que se compara el primero.
	 * @return Retorna true si el el punto p1 es menor en ambas coordenadas.
	 */
    public static boolean compare(Point p1, Point p2){
        return p1.x <= p2.x && p1.y <= p2.y;
    }

}
