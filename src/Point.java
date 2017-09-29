import java.io.Serializable;

public class Point implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double x;
    double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

//    /*
//     * Compara si el punto actual es menor o igual en todas sus coordenadas respecto al nuevo punto p.
//     */
//    public boolean compare(Point p){
//        return this.x <= p.x && this.y <= p.y;
//    }
}
