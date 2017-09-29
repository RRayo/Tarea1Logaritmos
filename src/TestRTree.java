public class TestRTree {
    public static void main(String[] args) {
        //System.out.println(500000*500000);
        Rtree rtree1 = new Rtree("/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/");
        Node l = new Node("L");
        Node r = new Node("L");
        NodeMethods.saveNode(l);
        NodeMethods.saveNode(r);
        Rtree.newRoot(l,r);
        Rtree.splitter = new LinearSplit();
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
        TestMethods.insert100();
        TestMethods.insert100();
        Rtree.printTree();
    }
}
