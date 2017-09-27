import java.util.ArrayList;

import static java.lang.Double.MAX_VALUE;

public class QuadraticSplit implements ISplitter{


    ArrayList<Register> registers = new ArrayList<Register>();
    ArrayList<Long> registerGroup1 = new ArrayList<Long>();
    ArrayList<Long> registerGroup2 = new ArrayList<Long>();
    Register register1;
    Register register2;
    long M;
    long m;

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
        if (mostWastefulRegister1.equals(null) || mostWastefulRegister2.equals(null)) {

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
    public void split(Node n) {
        registers = n.registers;
        n.registers = new ArrayList<Register>();
        pickSeeds(n);
        Node nn = new Node(n.father);
        while (!registers.isEmpty() || n.registers.size() >= (M-m+1) || nn.registers.size() >= (M-m+1)) { // o grupo se lleno
            Register chosenRegister = pickNext();
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(chosenRegister);//TODO arregar agregar nodo
            } else {
                nn.addRegister(chosenRegister);
            }
        } if (n.registers.size() >= (M-m+1)) {
            for (Register reg : registers) {
                n.addRegister(reg);
            }
        } else if (nn.registers.size() >= (M-m+1)) {
            for (Register reg : registers) {
                nn.addRegister(reg);
            }
        }
    }
}


