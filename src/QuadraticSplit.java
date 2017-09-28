import java.util.ArrayList;

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
    public void split(Node n,Node father) {
        registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        pickSeeds(n);
        Node nn = new Node(false);
        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            Register chosenRegister = pickNext();
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(chosenRegister);
            } else {
                nn.addRegister(chosenRegister);
            }
        } if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                n.addRegister(reg);
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {
                nn.addRegister(reg);
            }
        }

        //se guarda nn y se añade al padre
        nn.saveNode();
        father.addRegister(new Register(nn.MBR,nn.serialVersionUID));
    }
}


