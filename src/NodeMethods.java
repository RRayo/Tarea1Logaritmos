import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class NodeMethods {

    //Para ajustar el rectangulo luego de agregar nuevos hijos se toma el punto mas bajo de todos y el mas alto de todos y se crea un nuevo
    //rectangulo a partir de ellos
    /*
        (minX,maxY)
        *__________________*(maxX,maxY)
        |                  |
        |                  |
        |                  |
        *__________________*(maxX,minY)
        (minX,minY)
     */
    public static void adjust(Node node) {

        //System.out.println("Rectangulo: " + node.serialVersionUID + " area: " + RectangleMethods.getArea(node.MBR) +" registros: " + node.registers.size());
        if (((RectangleMethods.getArea(node.MBR) == 0.0) && node.registers.size() == 1) || ((RectangleMethods.getArea(node.MBR) == 0.0) && node.type.equals("R"))) {
            node.MBR = node.registers.get(0).rectangle;
            //System.out.println("NUeva area : " + RectangleMethods.getArea(node.MBR));
        } else {
            Point maxPoint = node.MBR.maxPoint;
            Point minPoint = node.MBR.minPoint;
            //System.out.println("Numero de registros: " + registers.size());

            for (Register reg : node.registers) {
                if (reg == null){
                    continue;
                }
                //System.out.println("    -registro: " + reg.serialVersionUID);




                /*
                assert reg != null;
                assert reg.rectangle != null;
                */
                /*
                try {
                    assert (reg != null);
                    assert (reg.rectangle != null);
                } catch (NullPointerException e ) {
                    System.out.println("El registro es nulo o su rectangulo es nulo");
                    System.exit(-1);
                }
                */

                if (RectangleMethods.getArea(reg.rectangle) == 0.0) {
                    continue;
                }

                Rectangle r = reg.rectangle;

                Point rLowerPoint = r.minPoint;
                Point rHigherPoint = r.maxPoint;

                //System.out.println("(" + rLowerPoint.x + "," + rLowerPoint.y + ")" + " |-| " + "(" + rHigherPoint.x + "," + rHigherPoint.y + ")");

                minPoint = PointMethods.compare(minPoint, rLowerPoint) ? minPoint : rLowerPoint;
                maxPoint = !PointMethods.compare(maxPoint, rHigherPoint) ? maxPoint : rHigherPoint;
            }

            //System.out.println("FInal: (" + minPoint.x + "," + minPoint.y + ")" + " |-| " + "(" + maxPoint.x + "," + maxPoint.y + ")");
            node.MBR = new Rectangle(minPoint, maxPoint);
        }
        saveNode(node);
    }


    //Guarda el nodo en un archivo identificado por su UID
    public static void saveNode(Node node) {
        try {
            String path = Rtree.path;
            String nodeName = "node" + node.serialVersionUID + ".ser";
            FileOutputStream fileOut = new FileOutputStream(path + nodeName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(node);
            out.close();
            fileOut.close();
            //System.out.println("Serialized " + nodeName + " is saved in" + path);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    public static void updateRegister(Node node, Register newReg) {
        //System.out.println("Nodo " + this.serialVersionUID + " numero de registros: " + this.registers.size());
        for (Register reg : node.registers) {
            //System.out.println("    -index: " + reg.serialVersionUID);
            if (reg.serialVersionUID.equals(newReg.serialVersionUID)) {
                node.registers.set(node.registers.indexOf(reg), newReg);
                break;
            }
        }
        saveNode(node);
    }

    public static void addRegister(Node node, Register reg, Stack<Long> nodes) {
        //System.out.println("Rectangle " + RectangleMethods.getArea(reg.rectangle)+ " added to node " + node.serialVersionUID);


        if(reg == null){
            System.out.println("Rectangle " + RectangleMethods.getArea(reg.rectangle)+ " added to node " + node.serialVersionUID);
            System.out.println("/////////////////////////////////////////////////REGISTRO NULO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            System.exit(-1);
        }
        node.registers.add(reg);
        saveNode(node);
        if (node.registers.size() > Rtree.M) {
            Rtree.split(node, nodes);
        }
        //nodes.add(this.serialVersionUID);
        Rtree.adjustTree(nodes);
    }
}
