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

        ArrayList<Register> registers = new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        RegisterTuple regTuple = this.pickSeeds(n, registers);
        registers.remove(regTuple.reg1);
        registers.remove(regTuple.reg2);
        Node nn = new Node(n.type);

        Stack<Long> s = new Stack<>();
        s.push(n.serialVersionUID);

        Stack<Long> ss = new Stack<>();
        ss.push(nn.serialVersionUID);

        NodeMethods.addRegister(n, regTuple.reg1, s);
        NodeMethods.addRegister(nn, regTuple.reg2, ss);

        //System.out.println("Split de nodo: " + n.serialVersionUID);

        while (!registers.isEmpty() // si la lista esta vacia
                // si se sobrepasa el limite de algun nodo para que el otro quede con al menos m registros
                && n.registers.size() < (Rtree.M-Rtree.m+1)
                && nn.registers.size() < (Rtree.M-Rtree.m+1))  {

         if(registers.size()==0) {
             break;
            }
            Register chosenRegister = pickNext(n, nn, registers);
            Rectangle r = chosenRegister.rectangle;

            // se selecciona el nodo donde se colocara

            Stack<Long> insertNodes = stackClone(nodes);

            if (Rtree.showdown(n,nn,r)) {
                insertNodes.push(n.serialVersionUID);
                NodeMethods.addRegister(n, chosenRegister,insertNodes);
            } else {
                insertNodes.push(nn.serialVersionUID);
                NodeMethods.addRegister(nn, chosenRegister,insertNodes);
            }
        }

        // si agun nodo se lleno con M-m+1 registros, los que quedan iran al otro
        if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                NodeMethods.addRegister(n, reg, s);
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                NodeMethods.addRegister(nn, reg, ss);
            }
        }

        //se guarda cambios de n y nn
        NodeMethods.saveNode(n);
        NodeMethods.saveNode(nn);



        if(n.type.equals("R")){ // es la raiz, se genera una nueva raiz
            //System.out.println("SPLIT RAIZ");
            n.type = "N";
            nn.type = "N";
            NodeMethods.saveNode(n);
            NodeMethods.saveNode(nn);
            Rtree.newRoot(n,nn);
        } else { // se agrega registro al padre
            try {
                nodes.pop();

                Node aux = Rtree.loadNode(nodes.peek());

                NodeMethods.addRegister( aux, new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
        //System.out.println("-------------------------------------------------------------------------------------------------------------------->Se realizo split exitosamente");
    }

    private static Stack<Long> stackClone(Stack<Long> stack) {
        if (stack.isEmpty()) {
            return stack;
        } else {
            Stack<Long> aux = (Stack<Long>)stack.clone();
            aux.pop();
            return aux;
        }
    }
}
