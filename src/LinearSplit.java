import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LinearSplit implements ISplitter{
	
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
		return null;
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

	@Override
	public void split(Node n, Queue<Long> nodes) {
		this.registers =  new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();
        this.pickSeeds(n);
        Node nn = new Node(false);
        
        n.addRegister(this.register1, new LinkedList<>());
        nn.addRegister(this.register2, new LinkedList<>());
        
        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            Register reg = this.registers.get(0);
            this.registers.remove(0);
            Rectangle r = reg.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                n.addRegister(reg,new LinkedList<>());
            } else {
                nn.addRegister(reg,new LinkedList<>());
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
