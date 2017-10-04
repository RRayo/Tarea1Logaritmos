import java.io.Serializable;
import java.util.ArrayList;


/**
 * Estructura Nodo para R-Trees, guarda un MBR, resgistros con los hijos, su tipo (Leaf, Root o Node) y un serial.
 *
 */
@SuppressWarnings("serial")
class Node implements Serializable {
    long serialVersionUID;
    Rectangle MBR;
    ArrayList<Register> registers = new ArrayList<Register>();
    protected String type; // Leaf, Root or Node


    /**
     * Constructor para Nodo.
     * @param type Tipo al que pertenece: Leaf, Root o Node.
     */
    Node(String type) {
        this.MBR = new Rectangle();
        this.serialVersionUID = SerialGenerator.nextUID();
        this.type = type;
    }
}













































