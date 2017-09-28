public class TestRTree {
    public static void main(String[] args) {
        Rtree rtree1 = new Rtree("/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/");
//        Node node1 = Rtree.loadNode(42L);
//        System.out.println(node1.registers.size());
//          System.out.println(node1.MBR.maxPoint.x);
        System.out.println(Rtree.treeId);
        Rtree.insertRectangle(new Rectangle(new Point(0.0,0.0),new Point(1.0,0.0),new Point(1.0,0.0),new Point(1.0,1.0)));
        Node node1 = Rtree.loadNode(Rtree.treeId);
        System.out.println(node1.MBR.getArea());
    }
}
