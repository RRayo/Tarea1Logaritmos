import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Stack;

import static java.lang.Double.MAX_VALUE;

public class Rtree {

    public static SerialGenerator sg = new SerialGenerator();
    static String DIR;
    static long treeId;
    static long M = 100L;
    static long m = 2L;
    public static String path  = "/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/";

    public static ISplitter splitter;

    //TODO tamaño 0

    public Rtree(String temp){
        path = temp;
        /*Node n = new Node("R");
        Node l = new Node("L");
        Node r = new Node("L");
        n.saveNode();
        l.saveNode();
        r.saveNode();
        n.addRegister(new Register(l.MBR,l.serialVersionUID), new LinkedList<>());
        n.addRegister(new Register(r.MBR,r.serialVersionUID), new LinkedList<>());
        treeId = n.serialVersionUID;
        n.saveNode();*/
    }

    public static void newRoot(Node l, Node r) {
        Node n = new Node("R");
        NodeMethods.saveNode(n);
        NodeMethods.addRegister(n,new Register(l.MBR,l.serialVersionUID), new Stack<>());
        NodeMethods.addRegister(n,new Register(r.MBR,r.serialVersionUID), new Stack<>());
        treeId = n.serialVersionUID;
        NodeMethods.saveNode(n);
    }

    static Node loadNode(long UID) {
        String nodeName = "node" + UID + ".ser";
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + nodeName));
            return (Node) (in.readObject());
        } catch (Exception e) {
            //System.out.println("No se encontro el nodo: " + UID);
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }


    //Devuelve un string con todos los ids que matchearon
    public static String search(Rectangle s) {

        Stack<Long> nodesStack = new Stack<Long>();
        nodesStack.add(treeId);
        StringBuilder sb = new StringBuilder();

        while (!nodesStack.isEmpty()) {
            Node node = Rtree.loadNode(nodesStack.pop());

            assert node != null;

            if (node.type.equals("L")) {//es una hoja, solo compara con su rectangulo
                if (RectangleMethods.overlaps(node.MBR, s)) {
                    sb.append(node.serialVersionUID);
                    sb.append(",");
                }
            } else {
                for (Register reg : node.registers) {
                    Rectangle r = reg.rectangle;
                    if (RectangleMethods.overlaps(r, s)) {
                        nodesStack.add(reg.serialVersionUID);
                    }
                }
            }
        }
        return sb.toString();
    }


    public static void insertRectangle (Rectangle s) {
        Stack<Long> nodesStack = new Stack<>(); //va guardando el reocrrido
        nodesStack.add(treeId);
        Node node = Rtree.loadNode(treeId);

        while (node != null) {
            if (node.type.equals("L")) { //es hoja
                NodeMethods.addRegister(node, new Register(s, node.serialVersionUID), nodesStack);
                node = null;

            } else { //se busca el el rectangulo que lo haria crecer menos

                double minAreaEnlarge = MAX_VALUE;
                double areaEnlarge;
                long UID = 0L;

                for (Register reg : node.registers) {
                    Rectangle r = reg.rectangle;
                    areaEnlarge = RectangleMethods.areaEnlarge(r, s);
                    if (areaEnlarge < minAreaEnlarge) {
                        minAreaEnlarge = areaEnlarge;
                        UID = reg.serialVersionUID;
                    }
                }
                nodesStack.add(UID);
                node = Rtree.loadNode(UID);
            }
        }

    }



    static double combinedArea(Rectangle r, Rectangle s) {
        Point newMinPoint = PointMethods.compare(r.minPoint, s.minPoint)? r.minPoint : s.minPoint;
        Point newMaxPoint = !PointMethods.compare(r.maxPoint, s.maxPoint)? r.maxPoint : s.maxPoint;
        return (newMaxPoint.x - newMinPoint.x)*(newMaxPoint.y - newMinPoint.y);
    }

    static void split(Node n, Stack<Long> nodes) {
        splitter.split(n, nodes);
    }


    static boolean showdown(Node n1, Node n2, Rectangle r) {
        Double areaGrow1 = combinedArea(n1.MBR,r);
        Double areaGrow2 = combinedArea(n2.MBR,r);

        if (Math.abs(areaGrow1 - areaGrow2) < 0.001) {
            if (Math.abs(RectangleMethods.getArea(n1.MBR) - RectangleMethods.getArea(n2.MBR)) < 0.001) {
                return n1.registers.size() <= n2.registers.size();
            } else return RectangleMethods.getArea(n1.MBR) < RectangleMethods.getArea(n2.MBR);
        } else if (areaGrow1 < areaGrow2) {
            return true;
        }
        return false;
    }


    static void adjustTree(Stack<Long> nodes) {
        while (!nodes.isEmpty()) {
            Node node = Rtree.loadNode(nodes.pop());
            if (node != null) {
                NodeMethods.adjust(node);
                Register newReg = new Register(node.MBR,node.serialVersionUID);
                if (!nodes.isEmpty()) { //actualizar en padre
                    Node father = Rtree.loadNode(nodes.peek());
                    //System.out.println("nodo padre" + father.serialVersionUID + " nodo hijo :" + newReg.serialVersionUID);
                    NodeMethods.updateRegister(father, newReg);
                }
            }
        }
    }

    public static void printTree() {
        Stack<Long> nodesStack = new Stack<>(); //va guardando el reocrrido
        nodesStack.add(treeId);
        while (!nodesStack.isEmpty()) {
            Node node = Rtree.loadNode(nodesStack.pop());
            System.out.println("Nodo: " + node.serialVersionUID + " tipo: " + node.type + " tamaño rectangulo: " + RectangleMethods.getArea(node.MBR) + " numero de hijos: " + node.registers.size());

            if (!node.type.equals("L")){
                for (Register UID : node.registers) {
                    if(UID.serialVersionUID!=node.serialVersionUID){
                        //System.out.println("node: " + UID.serialVersionUID);
                        nodesStack.add(UID.serialVersionUID);
                    }

                }
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