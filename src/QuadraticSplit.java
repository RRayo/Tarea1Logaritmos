
/*
import java.util.ArrayList;


public class QuadraticSplit extends AbstractSplitter {


    private ArrayList<Register> registers = new ArrayList<>();
    private Register register1 = null;
    private Register register2 = null;


    @Override
    public void pickSeeds(Node n) {
        double mostWastefulArea = 0.0;
        Register mostWastefulRegister1 = null;
        Register mostWastefulRegister2 = null;
        for (Register reg1 : this.registers) {
            for (Register reg2 : this.registers) {
                if(reg1 != reg2) {
                    Rectangle r1 = reg1.rectangle;
                    Rectangle r2 = reg2.rectangle;
                    Double areaR1 = RectangleMethods.getArea(r1.maxPoint,r1.minPoint);
                    Double areaR2 = RectangleMethods.getArea(r2.maxPoint,r2.minPoint);
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
    public Register pickNext(Node n, Node nn) {
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


}

*/
