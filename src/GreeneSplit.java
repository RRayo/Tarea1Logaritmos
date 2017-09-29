import java.util.*;

public class GreeneSplit extends LinearSplit {

    private ArrayList<Register> registers = new ArrayList<>();
    private Register register1 = null;
    private Register register2 = null;
    private String chosenAxis;

    @Override
    public void split(Node n, Stack<Long> nodes) {
        this.registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        this.pickSeeds(n);
        Node nn = new Node(n.type);


        sortAxis(registers,chosenAxis);

        int size = registers.size();

        while (!registers.isEmpty()) {
            Register chosenRegister = pickNext(n, nn);
            Rectangle r = chosenRegister.rectangle;
            if (size > ((Rtree.M)/2 + 2)) {
                n.addRegister(chosenRegister,new Stack<>());
            } else {
                nn.addRegister(chosenRegister,new Stack<>());
            }
            size--;
        }

        if (n.type.equals("R") ){ //raiz hizo split
            n.type = "N";
            nn.type = "N";

            //se guarda n y nn y se añade al padre
            n.saveNode();
            nn.saveNode();
            Rtree.newRoot(n,nn);

        }
    }


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
