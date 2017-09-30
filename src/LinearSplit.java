import java.util.ArrayList;
import java.util.LinkedList;


public class LinearSplit extends AbstractSplitter {

    private String chosenAxis = null;


	@Override
	public RegisterTuple pickSeeds(Node n, ArrayList<Register> registers) {
		double maxLowerPointX = Double.NEGATIVE_INFINITY;
		double maxLowerPointY = Double.NEGATIVE_INFINITY;
		double minHigherPointX = Double.POSITIVE_INFINITY;
		double minHigherPointY = Double.POSITIVE_INFINITY;


		// calcula separacion maxima del set
		for (Register reg : registers) {
			//System.out.println("(" + reg.rectangle.minPoint.x + "," + reg.rectangle.minPoint.y + ")");
			//System.out.println("(" + reg.rectangle.maxPoint.x + "," + reg.rectangle.maxPoint.y + ")");
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
		for (Register reg1 : registers) {
			for (Register reg2 : registers) {
				double separationY = rectangleSeparationY(reg1.rectangle, reg2.rectangle)/maxSeparationSetY;
				double separationX = rectangleSeparationX(reg1.rectangle, reg2.rectangle)/maxSeparationSetX;

				//System.out.println(separationY + " compared to " + maxSeparation);

				//System.out.println(separationX + " compared to " + maxSeparation);


				if (separationY > maxSeparation) {
                    r1=reg1;
					r2=reg2;
					maxSeparation = separationY;
					chosenAxis = "Y";
				}
				
				if (separationX > maxSeparation) {
					r1=reg1;
					r2=reg2;
					maxSeparation = separationX;
					chosenAxis = "X";
				}
				
	        }
        }

        return new RegisterTuple(r1,r2);
	}

	@Override
	public Register pickNext(Node n, Node nn, ArrayList<Register> registers) {

		Register reg = registers.get(0);
		registers.remove(0);
		return reg;
	}

	
	private double rectangleSeparationY(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.y > r2.minPoint.y ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.y > r2.maxPoint.y ? r2.minPoint : r1.minPoint;
		return Math.abs(minHigherPoint.y - maxLowerPoint.y);
	}
	
	private double rectangleSeparationX(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.x > r2.minPoint.x ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.x > r2.maxPoint.x ? r2.minPoint : r1.minPoint;
		return Math.abs(minHigherPoint.x - maxLowerPoint.x);
	}


}
