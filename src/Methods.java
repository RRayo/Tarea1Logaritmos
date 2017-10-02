import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Methods {

    public static SerialGenerator sg = new SerialGenerator();
    public static String path = "/home/alejandro/tareas/8-semestre/ln/t1/";

    /**
     * Carga un Nodo desde un archivo.
     * @param UID Codificacion del nodo que se cargara.
     * @return Retorna el Nodo como objeto.
     */
    public static Node loadNode(long UID) {
        String nodeName = "node" + UID + ".ser";
        ObjectInputStream in = null;
        
        try {
            in = new ObjectInputStream(new FileInputStream(path + nodeName));
            return (Node) (in.readObject());
        } catch (Exception e) {
            System.out.println("No se encontro el nodo: " + UID);
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
     * Devuelve un string con todos los ids que matchearon al rectangulo dentro del nodo.
     * @param s Rectangulo que se buscara en el nodo.
     * @param nodeUID ID del nodo que se cargara en memoria principal.
     * @return String con todos los UID que metcheen dentro del nodo.
     */
    public static String search(Rectangle s, long nodeUID) {
        Node node = loadNode(nodeUID);
        StringBuilder sb = new StringBuilder();
        if (node.registers.isEmpty()) {//es una hoja, solo compara con su rectangulo
            if (RectangleMethods.overlaps(node.MBR, s)) {
                sb.append(node.serialVersionUID);
            }
        } else {
            for (Register reg : node.registers) {
                Rectangle r = reg.rectangle;
                if (RectangleMethods.overlaps(r, s)) {
                    sb.append("/");
                    sb.append(search(s,reg.serialVersionUID));
                }
            }
        }
        return sb.toString();
    }
}
