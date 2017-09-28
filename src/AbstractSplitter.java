import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractSplitter implements ISplitter{

    private ArrayList<Register> registers = new ArrayList<>();
    private Register register1 = null;
    private Register register2 = null;

    @Override
    public void split(Node n, Queue<Long> nodes) {
        this.registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        this.pickSeeds(n);
        Node nn = new Node(false);

        n.addRegister(this.register1, new LinkedList<>());
        nn.addRegister(this.register2, new LinkedList<>());

        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            Register chosenRegister = pickNext(n, nn);
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(chosenRegister,new LinkedList<>());
            } else {
                nn.addRegister(chosenRegister,new LinkedList<>());
            }
        }

        if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                n.addRegister(reg,new LinkedList<>());
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                nn.addRegister(reg,new LinkedList<>());
            }
        }

        //se guarda n y nn y se añade al padre
        n.saveNode();
        nn.saveNode();

        if(nodes.isEmpty()){ // es la raiz, se genera una nueva raiz
            Node root = new Node(true);
            root.addRegister(new Register(nn.MBR,nn.serialVersionUID),nodes);
            root.addRegister(new Register(n.MBR,n.serialVersionUID),nodes);
            root.saveNode();
        } else {
            try {
                Rtree.loadNode(nodes.remove()).addRegister(new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
    }
}
