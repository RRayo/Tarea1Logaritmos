import java.util.ArrayList;


public class LinearSplit extends AbstractSplitter {

    protected String chosenAxis = null;


	@Override
	public RegisterTuple pickSeeds(ArrayList<Register> registers) {
		double maxLowerPointX = Double.NEGATIVE_INFINITY;
		double maxLowerPointY = Double.NEGATIVE_INFINITY;
		double minHigherPointX = Double.POSITIVE_INFINITY;
		double minHigherPointY = Double.POSITIVE_INFINITY;


		// calcula separacion maxima del set
		for (Register reg : registers) {
			maxLowerPointX = reg.rectangle.minPoint.x > maxLowerPointX ? reg.rectangle.minPoint.x : maxLowerPointX;
			maxLowerPointY = reg.rectangle.minPoint.y > maxLowerPointY ? reg.rectangle.minPoint.y : maxLowerPointY;
			minHigherPointX = reg.rectangle.maxPoint.x < minHigherPointX ? reg.rectangle.maxPoint.x : minHigherPointX;
			minHigherPointY = reg.rectangle.maxPoint.y < minHigherPointY ? reg.rectangle.maxPoint.y : minHigherPointY;
        }
		
		double maxSeparationSetX = Math.abs(maxLowerPointX - minHigherPointX);
		double maxSeparationSetY = Math.abs(maxLowerPointY - minHigherPointY);
		

		Register r1 = null;
        Register r2 = null;

		double maxSeparation = 0;
		for (Register reg1 : registers) {
			for (Register reg2 : registers) {
				if (reg1.serialVersionUID == reg2.serialVersionUID) {
					continue;
				}
				double separationY = rectangleSeparationY(reg1.rectangle, reg2.rectangle)/maxSeparationSetY;
				double separationX = rectangleSeparationX(reg1.rectangle, reg2.rectangle)/maxSeparationSetX;

				if (separationY >= maxSeparation) {
                    r1=reg1;
					r2=reg2;
					maxSeparation = separationY;
					chosenAxis = "Y";
				}
				
				if (separationX >= maxSeparation) {
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

	/**
	 * Se calcula la separacion en el eje Y de 2 rectangulos.
	 * @param r1 Rectangulo 1.
	 * @param r2 Rectangulo 2.
	 * @return Separacion en el eje Y.
	 */
	private double rectangleSeparationY(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.y > r2.minPoint.y ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.y > r2.maxPoint.y ? r2.minPoint : r1.minPoint;
		return Math.abs(minHigherPoint.y - maxLowerPoint.y);
	}
	
	/**
	 * Se calcula la separacion en el eje X de 2 rectangulos.
	 * @param r1 Rectangulo 1.
	 * @param r2 Rectangulo 2.
	 * @return Separacion en el eje X.
	 */
	private double rectangleSeparationX(Rectangle r1, Rectangle r2) {
		Point maxLowerPoint = r1.minPoint.x > r2.minPoint.x ? r1.minPoint : r2.minPoint;
		Point minHigherPoint = r1.maxPoint.x > r2.maxPoint.x ? r2.minPoint : r1.minPoint;
		return Math.abs(minHigherPoint.x - maxLowerPoint.x);
	}


}