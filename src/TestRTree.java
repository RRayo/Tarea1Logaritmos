import java.util.ArrayList;
import java.util.List;

public class TestRTree {
    public static void main(String[] args) {


        //System.out.println(500000*500000);
    	String pathAle = "/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/";
    	String pathRayo = "C:/Users/Rayo1115/Desktop/T1LogTemp/";

        Rtree rtree1 = new Rtree(pathRayo);
        Node l = new Node("L");
        Node r = new Node("L");
        NodeMethods.saveNode(l);
        NodeMethods.saveNode(r);
        Rtree.newRoot(l,r);
        Rtree.splitter = new GreeneSplit();
//        Node node1 = Rtree.loadNode(42L);
//        System.out.println(node1.registers.size());
//          System.out.println(node1.MBR.maxPoint.x);
        //System.out.println(Rtree.treeId);
        //Rectangle rect = new Rectangle(new Point(0.0,0.0),new Point(1.0,0.0),new Point(1.0,0.0),new Point(1.0,1.0));
        /*Rectangle rect = TestMethods.generateRectangle();
        System.out.println(RectangleMethods.getArea(rect));


        Rtree.insertRectangle(rect);
        Node node1 = Rtree.loadNode(Rtree.treeId);
        System.out.println(RectangleMethods.getArea(node1.MBR));*/

        //TestMethods.insert100();
        //Rectangle rrr = TestMethods.generateRectangle();
        //System.out.println("Maxpoitn: " + rrr.maxPoint.x  + " : " + rrr.maxPoint.y + " | " + "Minpoint: " + rrr.minPoint.x  + ": " + rrr.minPoint.y + " | Area: " + RectangleMethods.getArea(rrr));
        //Rtree.insertRectangle(rrr);
        //Rtree.insertRectangle(TestMethods.generateRectangle());
        //Rtree.insertRectangle(TestMethods.generateRectangle());
        //
        //

        /*
        Point[] highPoints1 = {new Point(1,1), new Point(2,1), new Point(3,1), new Point(4,1)}; // alineados en x
        Point[] lowPoints1 = {new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0)};
        
        Point[] highPoints2 = {new Point(1,1), new Point(1,0), new Point(1,-1), new Point(1,-2)}; // alineados en y
        Point[] lowPoints2 = {new Point(0,0), new Point(0,-1), new Point(0,-2), new Point(0,-3)};
        
        Point[] highPoints3 = {new Point(1,1), new Point(2,0), new Point(3,-1), new Point(4,-2)}; // escalera descendiente
        Point[] lowPoints3 = {new Point(0,0), new Point(1,-1), new Point(2,-2), new Point(3,-3)};
        
        Point[] highPoints4 = {new Point(1,1), new Point(2,1), new Point(3,1), new Point(1,0), new Point(2,0), new Point(3,0)}; // 2x3
        Point[] lowPoints4 = {new Point(0,0), new Point(1,0), new Point(2,0), new Point(0,-1), new Point(1,-1), new Point(2,-1)};

        Point[] highPoints5 = {new Point(1,1), new Point(2,2), new Point(3,3), new Point(4,4)}; // unos dentro de los otros
        Point[] lowPoints5 = {new Point(0,0), new Point(0,0), new Point(0,0), new Point(0,0)};
        
//        Point[] highPoints2 = {new Point(,), new Point(,), new Point(,), new Point(,)};
//        Point[] lowPoints2 = {new Point(,), new Point(,), new Point(,), new Point(,)};
        
        TestMethods.insertRectangles(TestMethods.crearRectangulos(highPoints1, lowPoints1));

                */
        
        Point[] highPoints5 = {new Point(2,2), new Point(3,3), new Point(4,4), new Point(6,6)}; // unos dentro de los otros
        Point[] lowPoints5 = {new Point(1,1), new Point(1,1), new Point(1,1), new Point(2,2)};
        
        
        System.out.println("Root node: " + Rtree.treeId);
        List<Rectangle> r1 = TestMethods.crearRectangulos(highPoints5, lowPoints5);
        System.out.println("Rectangulos: " + r1.toString() + " size: " + r1.size());
        int a = 0;
        for (Rectangle s : r1) {
        	System.out.println("Rectangulo: " + (a++) + ", min (x,y) = (" + s.minPoint.x + "," + s.minPoint.y
        			+ ") , max (x,y) = (" + s.maxPoint.x + "," + s.maxPoint.y + ")");
        }
        TestMethods.insertRectangles(r1);

        System.out.println("Root node: " + Rtree.treeId);
        TestMethods.insertN(10000);
        TestMethods.insertN(100);
        Rtree.printTree();
        Rtree.printLevelOrder();
        System.out.println("Search r = (0,0)x(6,6):  " + Rtree.search(new Rectangle(new Point(0.1,0.1), new Point(6,6))));
        System.out.println("Search r = (1,1)x(2,2):  " + Rtree.search(new Rectangle(new Point(0,0), new Point(6,6))));
    }
}
