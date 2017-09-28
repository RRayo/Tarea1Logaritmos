import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Double.MAX_VALUE;

public class Rtree {

    public static SerialGenerator sg = new SerialGenerator();
    static String DIR;
    static long treeId;
    static long M = 100L;
    static long m = 2L;
    public static String path  = "/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/";

    private static ISplitter splitter;

    //TODO tamaño 0

    public Rtree(String temp){
        path = temp;
        Node n = new Node(true);
        Node l = new Node(false);
        Node r = new Node(false);
        n.saveNode();
        l.saveNode();
        r.saveNode();
        n.addRegister(new Register(l.MBR,l.serialVersionUID), new LinkedList<>());
        n.addRegister(new Register(r.MBR,r.serialVersionUID), new LinkedList<>());
        treeId = n.serialVersionUID;
        n.saveNode();
    }

    static Node loadNode(long UID) {
        String nodeName = "node" + UID + ".ser";
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + nodeName));
            return (Node) (in.readObject());
        } catch (Exception e) {
            System.out.println("No se encontro el nodo: " + UID);
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }


    //Devuelve un string con todos los ids que matchearon
    public static String search(Rectangle s) {

        Queue<Long> nodesQueue = new LinkedList<>();
        nodesQueue.add(treeId);
        StringBuilder sb = new StringBuilder();

        while (!nodesQueue.isEmpty()) {
            Node node = Rtree.loadNode(nodesQueue.remove());

            assert node != null;

            if (node.registers.isEmpty()) {//es una hoja, solo compara con su rectangulo
                if (node.MBR.overlaps(s)) {
                    sb.append(node.serialVersionUID);
                    sb.append(",");
                }
            } else {
                for (Register reg : node.registers) {
                    Rectangle r = reg.rectangle;
                    if (r.overlaps(s)) {
                        nodesQueue.add(reg.serialVersionUID);
                    }
                }
            }
        }
        return sb.toString();
    }


    public static void insertRectangle (Rectangle s) {
        Queue<Long> nodesQueue = new LinkedList<>(); //va guardando el reocrrido
        nodesQueue.add(treeId);
        Node node = Rtree.loadNode(treeId);

        while (node != null) {
            if (node.registers.isEmpty()) { //es hoja
                node.addRegister(new Register(s, node.serialVersionUID), nodesQueue);
                node = null;

            } else { //se busca el el rectangulo que lo haria crecer menos

                double minAreaEnlarge = MAX_VALUE;
                double areaEnlarge;
                long UID = 0L;

                for (Register reg : node.registers) {
                    Rectangle r = reg.rectangle;
                    areaEnlarge = r.areaEnlarge(s);
                    if (areaEnlarge < minAreaEnlarge) {
                        minAreaEnlarge = areaEnlarge;
                        UID = reg.serialVersionUID;
                    }
                }
                nodesQueue.add(UID);
                node = Rtree.loadNode(UID);
            }
        }
    }



    static double combinedArea(Rectangle r, Rectangle s) {
        Point newMinPoint = r.minPoint.compare(s.minPoint)? r.minPoint : s.minPoint;
        Point newMaxPoint = !r.maxPoint.compare(s.maxPoint)? r.maxPoint : s.maxPoint;
        return (newMaxPoint.x - newMinPoint.x)*(newMaxPoint.y - newMinPoint.y);
    }

    static void split(Node n, Queue<Long> nodes) {
        splitter.split(n, nodes);
    }


    static boolean showdown(Node n1, Node n2, Rectangle r) {
        Double areaGrow1 = combinedArea(n1.MBR,r);
        Double areaGrow2 = combinedArea(n2.MBR,r);

        if (Math.abs(areaGrow1 - areaGrow2) < 0.001) {
            if (Math.abs(n1.MBR.getArea() - n2.MBR.getArea()) < 0.001) {
                return n1.registers.size() <= n2.registers.size();
            } else return n1.MBR.getArea() < n2.MBR.getArea();
        } else if (areaGrow1 < areaGrow2) {
            return true;
        }
        return false;
    }


    static void adjustTree(Queue<Long> nodes) {
        while (!nodes.isEmpty()) {
            Node node = Rtree.loadNode(nodes.remove());
            if (node != null) {
                node.adjust();
            }
        }
    }
}


/*showdown sin simplificar

    static boolean showdown(Node n1, Node n2, Rectangle r) {
        Double areaGrow1 = combinedArea(n1.MBR,r);
        Double areaGrow2 = combinedArea(n2.MBR,r);

        if (Math.abs(areaGrow1 - areaGrow2) < 0.001) {
            if (Math.abs(n1.MBR.getArea() - n2.MBR.getArea()) < 0.001) {
                if (n1.registers.size() <= n2.registers.size()){
                    return true;
                } else {
                    return false;
                }
            } else if (n1.MBR.getArea() < n2.MBR.getArea() ) {
                return true;
            } else {
                return false;
            }
        } else if (areaGrow1 < areaGrow2) {
            return true;
        }
        return false;
    }
 */