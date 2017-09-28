import java.util.ArrayList;
import java.util.Queue;

public class LinearSplit implements ISplitter{
	
	private ArrayList<Register> registers = new ArrayList<>();
	private Register register1 = null;
    private Register register2 = null;
	
	
	@Override
	public void pickSeeds(Node n) {
		Point maxLowerPoint = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
		Point minHigherPoint = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
		// calcula separacion maxima del set
		for (Register reg : n.registers) {
			if (reg.rectangle.minPoint.y > maxLowerPoint.y) { // si el punto bajo del rectangulo es mayor al maximo punto bajo
				maxLowerPoint = reg.rectangle.minPoint;
			}
			if (reg.rectangle.maxPoint.y < minHigherPoint.y) { // si el punto alto del rectangulo es menor al minimo punto alto
				minHigherPoint = reg.rectangle.maxPoint;
			}
        }
		
		double maxSeparationSet = minHigherPoint.y - maxLowerPoint.y;
		

		Register r1 = null;
        Register r2 = null;
		double maxSeparation = 0;
		for (Register reg1 : n.registers) {
			for (Register reg2 : n.registers) {
				double separation = rectangleSeparationY(reg1.rectangle, reg2.rectangle)/maxSeparationSet;
				if (separation > maxSeparation) { 
					r1=reg1;
					r2=reg2;
				}
				
	        }
        }
		this.register1 = r1;
		this.register2 = r2;
		
		this.registers.remove(r1);
		this.registers.remove(r2);
	}

	@Override
	public Register pickNext() {
		return null;
	}

	
	private double rectangleSeparationY(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.y > r2.minPoint.y ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.y > r2.maxPoint.y ? r2.minPoint : r1.minPoint;
		return minHigherPoint.y - maxLowerPoint.y;
	}

	@Override
	public void split(Node n, Queue<Long> nodes) {
		// TODO Auto-generated method stub
		
	}

}
