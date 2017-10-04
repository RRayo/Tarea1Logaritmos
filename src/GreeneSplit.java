import java.util.*;

public class GreeneSplit extends LinearSplit {

    @Override
    public void split(Node n, Stack<Long> nodes) {
        ArrayList<Register> registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        RegisterTuple regTuple = pickSeeds(registers);
        Node nn = new Node(n.type); // nuevo nodo, hermano del original

        registers.remove(regTuple.reg1); // borra los registros obtenidos de la lista general
        registers.remove(regTuple.reg2);


        Stack<Long> s = new Stack<>();
        s.push(n.serialVersionUID);

        Stack<Long> ss = new Stack<>();
        ss.push(nn.serialVersionUID);

        NodeMethods.addRegister(n, regTuple.reg1, s);
        NodeMethods.addRegister(nn, regTuple.reg2, ss);

        sortAxis(registers,this.chosenAxis);

        int size = registers.size();

        while (!registers.isEmpty()) {

            if(registers.size()==0) {
                break;
            }

            Register chosenRegister = pickNext(n, nn, registers);

            if (size > ((Rtree.M)/2 + 2)) { // la mitad superior de registros queda en el nodo n
                NodeMethods.addRegister(n, chosenRegister, s);
            } else {// la mitad inferior de registros queda en el nodo nn
                NodeMethods.addRegister(nn, chosenRegister, ss);
            }
            size--;
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
                if (nodes.size() >= 2 ) {
                    nodes.pop();
                }

                Node aux = Rtree.loadNode(nodes.peek());

                NodeMethods.updateRegister(aux,new Register(n.MBR, n.serialVersionUID));
                NodeMethods.addRegister( aux, new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
        //System.out.println("-------------------------------------------------------------------------------------------------------------------->Se realizo split exitosamente");

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