public class Point {
    double x;
    double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /*
     * Compara si el punto actual es menor o igual en todas sus coordenadas respecto al nuevo punto p.
     */
    public boolean compare(Point p){
        return this.x <= p.x && this.y <= p.y;
    }
}
