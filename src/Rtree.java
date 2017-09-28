import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Double.MAX_VALUE;

public class Rtree {

    public static SerialGenerator sg = new SerialGenerator();
    public static String path = "/home/alejandro/tareas/8-semestre/ln/t1/";
    public static String DIR;
    public long treeId;
    public static long M;
    public static long m;

    static ISplitter splitter;

    public Rtree(){
        Node n = new Node();
        Node l = new Node(n);
        Node r = new Node(n);
        n.addNode(l);
        n.addNode(r);
        this.treeId = n.serialVersionUID;
        l.saveNode();
        r.saveNode();
        n.saveNode();
    }

    public static Node loadNode(long UID) {
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
    public static String search(Rectangle s, long nodeUID) {
        Queue<Long> nodesQueue = new LinkedList<>();
        nodesQueue.add(nodeUID);
        StringBuilder sb = new StringBuilder();
        while (!nodesQueue.isEmpty()) {
            long UID = nodesQueue.remove();
            Node n = Rtree.loadNode(UID);
            //TODO esto
        }

        /*
        StringBuilder sb = new StringBuilder();
        if (node.registers.isEmpty()) {//es una hoja, solo compara con su rectangulo
            if (node.MBR.overlaps(s)) {
                sb.append(node.serialVersionUID);
            }
        } else {
            for (long reg : node.registers) {
                Node n = Rtree.loadNode(reg);
                Rectangle r = n.MBR;
                if (r.overlaps(s)) {
                    sb.append("/");
                    sb.append(search(s,n.serialVersionUID));
                }
            }
        }
        return sb.toString();

        */
        return sb.toString();
    }


    public static void insertRectangle (long nodeUID, Rectangle s) {
        //TODO antes de agregar rectangulo hay que ver si no hace overflow. En caso de que lo haga hacer split
        Node node = Rtree.loadNode(nodeUID);
        if (node.registers.isEmpty()) {
            node.addRegister(new Register(s,node.serialVersionUID));
        } else { //se busca el el rectangulo que lo haria crecer menos

            double minAreaEnlarge = MAX_VALUE;
            double areaEnlarge = 0.0;
            long UID = 0L;

            for (Register reg: node.registers) {
                Rectangle r = reg.rectangle;
                areaEnlarge = r.areaEnlarge(s);
                if (areaEnlarge < minAreaEnlarge) {
                    minAreaEnlarge = areaEnlarge;
                    UID = reg.serialVersionUID;
                }
            }
            //busca recursivamente en el rectangulo que agranda menos el area
            insertRectangle(UID,s);

        }
    }

    public static double combinedArea (Rectangle r, Rectangle s) {
        Point newMinPoint = r.minPoint.compare(s.minPoint)? r.minPoint : s.minPoint;
        Point newMaxPoint = !r.maxPoint.compare(s.maxPoint)? r.maxPoint : s.maxPoint;
        return (newMaxPoint.x - newMinPoint.x)*(newMaxPoint.y - newMinPoint.y);
    }

    public static void split (Node n) {
        splitter.split(n);
    }


    public static boolean showdown (Node n1, Node n2, Rectangle r) {
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










}
