import java.io.Serializable;

/**
 * Punto con coordenadas espaciales x e y, junto con un serial.
 *
 */
public class Point implements Serializable{

	private static final long serialVersionUID = 1L;
	double x;
    double y;

    /**
     * Punto con coordenadas x e y.
     * @param x  Coordenada x.
     * @param y  Coordenada y.
     */
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Punto default, con coordenadas (0,0).
     */
    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

}
