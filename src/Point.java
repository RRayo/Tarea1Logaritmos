public class Point {
    double x;
    double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean compare(Point p){
        return this.x <= p.x && this.y <= p.y;
    }
}
