import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class QuadraticSplit implements ISplitter{


    private ArrayList<Register> registers = new ArrayList<>();
    private Register register1 = null;
    private Register register2 = null;


    @Override
    public void pickSeeds(Node n) {
        double mostWastefulArea = 0.0;
        Register mostWastefulRegister1 = null;
        Register mostWastefulRegister2 = null;
        for (Register reg1 : n.registers) {
            for (Register reg2 : n.registers) {
                if(reg1 != reg2) {
                    Rectangle r1 = reg1.rectangle;
                    Rectangle r2 = reg2.rectangle;
                    Double areaR1 = r1.getArea();
                    Double areaR2 = r2.getArea();
                    Double newArea = Rtree.combinedArea(r1,r2) - areaR1 - areaR2;
                    if(mostWastefulArea > newArea){
                        mostWastefulArea = newArea;
                        mostWastefulRegister1 = reg1;
                        mostWastefulRegister2 = reg2;
                    }
                }
            }
        }
        this.register1 = mostWastefulRegister1;
        this.register2 = mostWastefulRegister2;
        
        registers.remove(mostWastefulRegister1);
        registers.remove(mostWastefulRegister2);
    }

    @Override
    public Register pickNext() {
        Rectangle MBR1 = register1.rectangle;
        Rectangle MBR2 = register2.rectangle;
        Register chosenRegister = null;
        Double minIncrease = 0.0;

        for (Register reg : registers) {
            Rectangle r = reg.rectangle;
            Double areaDifference = Math.abs(Rtree.combinedArea(r, MBR1) - Rtree.combinedArea(r, MBR2));
            if (minIncrease > areaDifference) {
                minIncrease = areaDifference;
                chosenRegister = reg;
            }
        }
        registers.remove(chosenRegister);
        return chosenRegister;
    }

    @Override
    public void split(Node n,Queue<Long> nodes) {
        registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        pickSeeds(n);
        Node nn = new Node(false);
        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            Register chosenRegister = pickNext();
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(chosenRegister,new LinkedList<>());
            } else {
                nn.addRegister(chosenRegister,new LinkedList<>());
            }
        } if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
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


