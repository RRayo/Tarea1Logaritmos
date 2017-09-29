import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RtreeTest {

    @Test
    public void RtreeCreationAndInsert() {
        Rtree rtree1 = new Rtree("/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/");
        Node l = new Node("L");
        Node r = new Node("L");
        NodeMethods.saveNode(l);
        NodeMethods.saveNode(r);
        Rtree.newRoot(l,r);
        Rectangle rect = TestMethods.generateRectangle();
        Rtree.insertRectangle(rect);
        Node node1 = Rtree.loadNode(Rtree.treeId);
        // assert statements
        assertEquals(Rtree.combinedArea(node1.MBR,rect), RectangleMethods.getArea(node1.MBR), 0.001, "Expanded area is not equal");
        assertEquals(1,TestMethods.treeHeigth(), "Tree should have height 1 not" +TestMethods.treeHeigth() );
        //Rtree.printTree();
    }

}