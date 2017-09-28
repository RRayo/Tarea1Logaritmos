import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LinearSplit extends AbstractSplitter {
	
	private ArrayList<Register> registers = new ArrayList<>();
	private Register register1 = null;
    private Register register2 = null;
	
	
	@Override
	public void pickSeeds(Node n) {
		double maxLowerPointX = Double.NEGATIVE_INFINITY;
		double maxLowerPointY = Double.NEGATIVE_INFINITY;
		double minHigherPointX = Double.POSITIVE_INFINITY;
		double minHigherPointY = Double.POSITIVE_INFINITY;
		// calcula separacion maxima del set
		for (Register reg : n.registers) {
			maxLowerPointX = reg.rectangle.minPoint.x > maxLowerPointX ? reg.rectangle.minPoint.x : maxLowerPointX;
			maxLowerPointY = reg.rectangle.minPoint.y > maxLowerPointY ? reg.rectangle.minPoint.y : maxLowerPointY;
			minHigherPointX = reg.rectangle.maxPoint.x < minHigherPointX ? reg.rectangle.maxPoint.x : minHigherPointX;
			minHigherPointY = reg.rectangle.maxPoint.y < minHigherPointY ? reg.rectangle.maxPoint.y : minHigherPointY;
        }
		
		double maxSeparationSetX = maxLowerPointX - minHigherPointX;
		double maxSeparationSetY = maxLowerPointY - minHigherPointY;
		

		Register r1 = null;
        Register r2 = null;
		double maxSeparation = 0;
		for (Register reg1 : this.registers) {
			for (Register reg2 : this.registers) {
				double separationY = rectangleSeparationY(reg1.rectangle, reg2.rectangle)/maxSeparationSetY;
				double separationX = rectangleSeparationX(reg1.rectangle, reg2.rectangle)/maxSeparationSetX;
				
				if (separationY > maxSeparation) { 
					r1=reg1;
					r2=reg2;
					maxSeparation = separationY;
				}
				
				if (separationX > maxSeparation) { 
					r1=reg1;
					r2=reg2;
					maxSeparation = separationX;
				}
				
	        }
        }
		this.register1 = r1;
		this.register2 = r2;
		
		this.registers.remove(r1);
		this.registers.remove(r2);
	}

	@Override
	public Register pickNext(Node n, Node nn) {
		Register reg = this.registers.get(0);
		this.registers.remove(0);
		return reg;
	}

	
	private double rectangleSeparationY(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.y > r2.minPoint.y ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.y > r2.maxPoint.y ? r2.minPoint : r1.minPoint;
		return minHigherPoint.y - maxLowerPoint.y;
	}
	
	private double rectangleSeparationX(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.x > r2.minPoint.x ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.x > r2.maxPoint.x ? r2.minPoint : r1.minPoint;
		return minHigherPoint.x - maxLowerPoint.x;
	}


}
