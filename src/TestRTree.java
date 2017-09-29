public class TestRTree {
    public static void main(String[] args) {
        Rtree rtree1 = new Rtree("/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/");
        Node l = new Node("L");
        Node r = new Node("L");
        l.saveNode();
        r.saveNode();
        Rtree.newRoot(l,r);
//        Node node1 = Rtree.loadNode(42L);
//        System.out.println(node1.registers.size());
//          System.out.println(node1.MBR.maxPoint.x);
        System.out.println(Rtree.treeId);
        Rectangle rect = new Rectangle(new Point(0.0,0.0),new Point(1.0,0.0),new Point(1.0,0.0),new Point(1.0,1.0));
        System.out.println(RectangleMethods.getArea(rect));
        Rtree.insertRectangle(rect);
        Node node1 = Rtree.loadNode(Rtree.treeId);
        System.out.println(RectangleMethods.getArea(node1.MBR));
        Rtree.printTree();
    }
}
