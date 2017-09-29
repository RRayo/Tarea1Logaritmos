import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public abstract class AbstractSplitter implements ISplitter{

    private ArrayList<Register> registers = new ArrayList<>();
    private Register register1 = null;
    private Register register2 = null;

    @Override
    public void split(Node n, Stack<Long> nodes) {
        this.registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        this.pickSeeds(n);

        Node nn = new Node(n.type);

        n.addRegister(this.register1, new Stack<>());
        nn.addRegister(this.register2, new Stack<>());

        /*if (n.type.equals("R") ){ //raiz hizo split
            n.type = "N";
            nn.type = "N";

            //se guarda n y nn y se añade al padre
            n.saveNode();
            nn.saveNode();
            Rtree.newRoot(n,nn);

        } */


        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            Register chosenRegister = pickNext(n, nn);
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(chosenRegister,new Stack<>());
            } else {
                nn.addRegister(chosenRegister,new Stack<>());
            }
        }

        if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                n.addRegister(reg,new Stack<>());
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                nn.addRegister(reg,new Stack<>());
            }
        }

        //se guarda cambios de n y nn
        n.saveNode();
        nn.saveNode();

        if(nodes.isEmpty()){ // es la raiz, se genera una nueva raiz
            n.type = "N";
            nn.type = "N";
            Rtree.newRoot(n,nn);
        } else { // se añade registro al padre
            try {
                Rtree.loadNode(nodes.pop()).addRegister(new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
    }
}
