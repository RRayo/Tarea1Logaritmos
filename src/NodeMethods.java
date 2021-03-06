import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

/**
 * Metodos para operar sobre Nodos.
 */
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

	/**
	 * Recalcula el MBR de un nodo recorriendo sus hijos y se queda con el rectangulo generado por los puntos mas ditantes.
	 * @param node Nodo al que se le quiere reajustar su MBR.
	 */
    public static void adjust(Node node) {

        if (((RectangleMethods.getArea(node.MBR) == 0.0) && node.registers.size() == 1) || ((RectangleMethods.getArea(node.MBR) == 0.0) && node.type.equals("R"))) {
            node.MBR = node.registers.get(0).rectangle;
        } else {
            Point maxPoint = node.MBR.maxPoint;
            Point minPoint = node.MBR.minPoint;

            for (Register reg : node.registers) {
                if (reg == null){
                    continue;
                }

                if (RectangleMethods.getArea(reg.rectangle) == 0.0) {
                    continue;
                }

                Rectangle r = reg.rectangle;

                Point rLowerPoint = r.minPoint;
                Point rHigherPoint = r.maxPoint;


                minPoint = PointMethods.compare(minPoint, rLowerPoint) ? minPoint : rLowerPoint;
                maxPoint = !PointMethods.compare(maxPoint, rHigherPoint) ? maxPoint : rHigherPoint;
            }

            node.MBR = new Rectangle(minPoint, maxPoint);
        }
        saveNode(node);
    }


    /**
     * Guarda el nodo en un archivo identificado por su UID
     * @param node Nodo que se desea guardar en disco.
     */
    public static void saveNode(Node node) {
        try {
            String path = Rtree.path;
            String nodeName = "node" + node.serialVersionUID + ".ser";
            FileOutputStream fileOut = new FileOutputStream(path + nodeName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(node);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    /**
     * Actualiza un registro de entre la lista de registros de un nodo si poseen la misma ID.
     * @param node Nodo que posee la lista de registros por actualizar.
     * @param newReg Registro que reemplazar� a un hijo del Nodo si comparten la misma ID.
     */
    public static void updateRegister(Node node, Register newReg) {
        for (Register reg : node.registers) {
            if (reg.serialVersionUID.equals(newReg.serialVersionUID)) {
                node.registers.set(node.registers.indexOf(reg), newReg);
                break;
            }
        }
        saveNode(node);
    }

    /**
     * Agrega un registro a un Nodo.
     * Si el nodo es nulo entrega un mensaje de error.
     * Si se llena el nodo se hace split.
     * @param node Nodo donde se agregara el registro.
     * @param reg Registro que se agregara a la lista dentro del nodo.
     * @param nodes
     */
    public static void addRegister(Node node, Register reg, Stack<Long> nodes) {

        node.registers.add(reg);
        adjust(node);
        saveNode(node);
        if (node.registers.size() > Rtree.M) {
            Rtree.split(node, nodes);
        }
        Rtree.adjustTree(nodes);
    }
}