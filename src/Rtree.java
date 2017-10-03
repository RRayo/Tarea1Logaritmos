import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.Double.MAX_VALUE;

/**
 * Estructura de arbol para almacenar rectangulos en 2 dimensiones.
 */
public class Rtree {

    public static SerialGenerator sg = new SerialGenerator();
    static String DIR;
    static long treeId;
    static long M = 3L;
    static long m = 1L;
    public static String path  = "/home/alejandro/tareas/8-semestre/ln/t1/Tarea1Logaritmos/temp/";

    public static ISplitter splitter;

    //TODO tamaño 0

    /**
     * Contructor para el Rtree.
     * @param temp Directorio donde se guardaran las estructuras generadas.
     */
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

    /**
     * Genera una nueva raiz del arbol y sus hijos iniciales con .
     * @param l Nodo izquierdo de la raiz.
     * @param r Nodo derecho de la raiz.
     */
    public static void newRoot(Node l, Node r) {
        Node n = new Node("R");
        NodeMethods.saveNode(n);

        Stack<Long> ls = new Stack<>();
        ls.push(n.serialVersionUID);
        ls.push(l.serialVersionUID);

        Stack<Long> rs = new Stack<>();
        rs.push(n.serialVersionUID);
        rs.push(r.serialVersionUID);

        NodeMethods.addRegister(n,new Register(l.MBR,l.serialVersionUID), ls);
        NodeMethods.addRegister(n,new Register(r.MBR,r.serialVersionUID), rs);
        treeId = n.serialVersionUID;
        NodeMethods.saveNode(n);
    }

    /**
     * Carga un Nodo desde un archivo.
     * @param UID Codificacion del nodo que se cargara.
     * @return Retorna el Nodo como objeto.
     */
    static Node loadNode(long UID) {
        String nodeName = "node" + UID + ".ser";
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(path + nodeName));
            return (Node) (in.readObject());
        } catch (Exception e) {
            //System.out.println("No se encontro el nodo: " + UID);
            e.printStackTrace();
            System.exit(1);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Busca en el Rtree todos los rectangulos que intersecten al rectangulo entregado.
     * @param s Rectangulo que se busca en el arbol.
     * @return String con todos los ids que matchearon.
     */
    public static String search(Rectangle s) {

        Stack<Long> nodesStack = new Stack<Long>();
        nodesStack.add(treeId);
        StringBuilder sb = new StringBuilder();

        while (!nodesStack.isEmpty()) {
            Node node = Rtree.loadNode(nodesStack.pop());

            assert node != null;
            
            double area = RectangleMethods.getArea(node.MBR);
            if (area < 0.0001 && area > -0.0001) {
            	continue;
            }

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


    /**
     * Inserta un rectangulo en el Rtree bajando hasta posicionarse en una hoja.
     * @param s Rectangulo por insertar al arbol.
     */
    public static void insertRectangle (Rectangle s) {
        Stack<Long> nodesStack = new Stack<>(); //va guardando el reocrrido
        nodesStack.add(treeId);
        Node node = Rtree.loadNode(treeId);

        while (node != null) {

            if (node.type.equals("L")) { //es hoja
               //System.out.println("insert on node: " + node.serialVersionUID);
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
                //System.out.println("Tamaño stack: " + nodesStack.size());
                node = Rtree.loadNode(UID);
            }
        }

    }


    /**
     * Split de un nodo segun el splitter seleccionado.
     * @param n Nodo donde se hara split.
     * @param nodes Lista de nodos.
     */
    static void split(Node n, Stack<Long> nodes) {
        splitter.split(n, nodes);
    }


    /**
     * Metodo para decidir en que nodo se guardara un rectangulo.
     * Considera los casos en que haya empates y los resuelve.
     * @param n1 Nodo 1.
     * @param n2 Nodo 2.
     * @param r Rectangulo que se almacenara en uno de los nodos.
     * @return Retorna true si el rectangulo quedara en el Nodo 1.
     */
    static boolean showdown(Node n1, Node n2, Rectangle r) {
        Double areaGrow1 = RectangleMethods.combinedArea(n1.MBR,r);
        Double areaGrow2 = RectangleMethods.combinedArea(n2.MBR,r);

        if (Math.abs(areaGrow1 - areaGrow2) < 0.001) {
            if (Math.abs(RectangleMethods.getArea(n1.MBR) - RectangleMethods.getArea(n2.MBR)) < 0.001) {
                return n1.registers.size() <= n2.registers.size();
            } else return RectangleMethods.getArea(n1.MBR) < RectangleMethods.getArea(n2.MBR);
        } else if (areaGrow1 < areaGrow2) {
            return true;
        }
        return false;
    }


    /**
     * Reajusta los nodos en el Stack recalculando su MBR.
     * @param nodes Stack con los nodos que fueron afectados al insertar un rectangulo en el arbol y necesitan recomputar su MBR.
     */
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

    /**
     * Imprime el arbol actual.
     */
    public static void printTree() {
        Stack<Long> nodesStack = new Stack<>(); //va guardando el recorrido
        nodesStack.add(treeId);
        while (!nodesStack.isEmpty()) {
            Node node = Rtree.loadNode(nodesStack.pop());
            System.out.println("Nodo: " + node.serialVersionUID + " tipo: " + node.type + " tamaño rectangulo: " + RectangleMethods.getArea(node.MBR) + " numero de hijos: " + node.registers.size());

            if (!node.type.equals("L")){ // si no es una hoja agrega los registros al stack
                for (Register UID : node.registers) {
                    if(UID.serialVersionUID!=node.serialVersionUID){
                        //System.out.println("node: " + UID.serialVersionUID);
                        nodesStack.add(UID.serialVersionUID);
                    }

                }
            }
        }

    }
    
    public static void printLevelOrder() {
        Queue<Long> queue = new LinkedList<Long>();
        queue.add(treeId);
        while (!queue.isEmpty()) 
        {
 
            /* poll() removes the present head.
            For more information on poll() visit 
            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
            Node tempNode = Rtree.loadNode(queue.poll());
            System.out.print(tempNode.serialVersionUID + " ");
            
            if (!tempNode.type.equals("L")){ // si no es una hoja agrega los registros al stack
                for (Register UID : tempNode.registers) {
                    if(UID.serialVersionUID!=tempNode.serialVersionUID){
                        //System.out.println("node: " + UID.serialVersionUID);
                    	queue.add(UID.serialVersionUID);
                    }

                }
            }
        }
        System.out.println(System.lineSeparator());
        
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