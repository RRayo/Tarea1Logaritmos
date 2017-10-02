import java.util.*;

public class GreeneSplit extends LinearSplit {


    //private String chosenAxis;

    @Override
    public void split(Node n, Stack<Long> nodes) {
        ArrayList<Register> registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        RegisterTuple registerTuple = pickSeeds(registers);
        Node nn = new Node(n.type); // nuevo nodo, hermano del original

        registers.remove(registerTuple.reg1); // borra los registros obtenidos de la lista general
        registers.remove(registerTuple.reg2);

        NodeMethods.addRegister(n, registerTuple.reg1, new Stack<>()); // se agregan a los nuevos nodos los registros base obtenidos
        NodeMethods.addRegister(nn, registerTuple.reg2, new Stack<>());
        
        sortAxis(registers,this.chosenAxis);

        int size = registers.size();

        while (!registers.isEmpty()) {
            Register chosenRegister = pickNext(n, nn, registers);

            if (size > ((Rtree.M)/2 + 2)) { // la mitad superior de registros queda en el nodo n
                NodeMethods.addRegister(n, chosenRegister,new Stack<>());
            } else {// la mitad inferior de registros queda en el nodo nn
                NodeMethods.addRegister(nn, chosenRegister,new Stack<>());
            }
            size--;
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


    /**
     * Se ordenan los registros segun el valor de su punto menor segun su coordenada X o Y.
     * @param registers Lista de registros por ordenar.
     * @param chosenAxis Eje en el cual se ordenara ("X" o "Y").
     */
    public static void sortAxis (ArrayList<Register> registers, String chosenAxis) {
        if (chosenAxis.equals("X")) {
            Collections.sort(registers, new Comparator<Register>() {
                @Override
                public int compare(Register reg1, Register reg2)
                {
                    double compare = reg1.rectangle.minPoint.x - reg2.rectangle.minPoint.x;
                    if(Math.abs(compare) < 0.001 ) {
                        return 0;
                    } else if (compare < 0) {
                        return -1;
                    }
                    return 1;
                }
            });
        } else {
            Collections.sort(registers, new Comparator<Register>() {
                @Override
                public int compare(Register reg1, Register reg2)
                {
                    double compare = reg1.rectangle.minPoint.y - reg2.rectangle.minPoint.y;
                    if(Math.abs(compare) < 0.001 ) {
                        return 0;
                    } else if (compare < 0) {
                        return -1;
                    }
                    return 1;
                }
            });
        }

    }
}
