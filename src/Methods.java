import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Methods {

    public static SerialGenerator sg = new SerialGenerator();
    public static String path = "/home/alejandro/tareas/8-semestre/ln/t1/";

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
