import java.util.ArrayList;
import java.util.Stack;

/**
 * Spliter abstracto que implementa el metodo split para las distintas heuristicas.
 *
 */
public abstract class AbstractSplitter implements ISplitter{

	/**
	 * Metodo para realizar split en un nodo.
	 * @param n Nodo donde se realizara split.
	 * @param nodes
	 */
    @Override
    public void split(Node n, Stack<Long> nodes) {
        ArrayList<Register> registers = new ArrayList<>(n.registers); //guarda nodos en una lista
        n.registers = new ArrayList<>(); //vacia la lista del nodo
        RegisterTuple regTuple = this.pickSeeds(registers); // obtiene tupla con los registros base de los nuevos nodos
        registers.remove(regTuple.reg1); // borra los registros obtenidos de la lista general
        registers.remove(regTuple.reg2);
        Node nn = new Node(n.type); // nuevo nodo, hermano del original
        NodeMethods.addRegister(n, regTuple.reg1, new Stack<>()); // se agregan a los nuevos nodos los registros base obtenidos
        NodeMethods.addRegister(nn, regTuple.reg2, new Stack<>());

        while (!registers.isEmpty() // si la lista esta vacia
        		// si se sobrepasa el limite de algun nodo para que el otro quede con al menos m registros
        		&& n.registers.size() < (Rtree.M-Rtree.m+1) 
        		&& nn.registers.size() < (Rtree.M-Rtree.m+1)) {

         /*if(registers.size()==0) {
                //TODO aca register tiene valor 0???
                break;
            }*/
        	
            Register chosenRegister = pickNext(n, nn, registers); // se obtiene el siguiente registro
            Rectangle r = chosenRegister.rectangle;
            
            // se selecciona el nodo donde se colocara
            if (Rtree.showdown(n,nn,r)) {
                NodeMethods.addRegister(n, chosenRegister,new Stack<>());
            } else {
                NodeMethods.addRegister(nn, chosenRegister,new Stack<>());
            }
        }

        // si agun nodo se lleno con M-m+1 registros, los que quedan iran al otro
        if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                NodeMethods.addRegister(n, reg,new Stack<>());
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                NodeMethods.addRegister(nn, reg,new Stack<>());
            }
        }

        //se guarda cambios de n y nn
        NodeMethods.saveNode(n);
        NodeMethods.saveNode(nn);

        if(nodes.isEmpty()){ // es la raiz, se genera una nueva raiz
            n.type = "N";
            nn.type = "N";
            Rtree.newRoot(n,nn);
        } else { // se agrega registro al padre
            try {
                nodes.pop();
                NodeMethods.addRegister( Rtree.loadNode(nodes.pop()), new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------->Se realizo split exitosamente");
    }
}
