
public class Test {

	public static void main(String[] args) {		
		System.out.println("Point Test:");
		Point p0 = new Point(0,0);
		Point p1 = new Point(1,0);
		Point p2 = new Point(0,1);
		Point p3 = new Point(1,1);
		System.out.println("P0.x: " + p0.x + "  ,P0.y: " + p0.y);
		System.out.println("P1.x: " + p1.x + "  ,p1.y: " + p1.y);
		System.out.println("P2.x: " + p2.x + "  ,p2.y: " + p2.y);
		System.out.println("P3.x: " + p3.x + "  ,p3.y: " + p3.y);
		System.out.println("P0 <= P1: " + PointMethods.compare(p0, p1));
		System.out.println("P0 <= P2: " + PointMethods.compare(p0, p2));
		System.out.println("P0 <= P3: " + PointMethods.compare(p0, p3));
		System.out.println("P1 <= P2: " + PointMethods.compare(p1, p2));
		System.out.println("P2 <= P3: " + PointMethods.compare(p2, p3));
		System.out.println();
		System.out.println("Rectangle Test:");
		Rectangle r1 = new Rectangle(new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1));
		Rectangle r2 = new Rectangle(new Point(0.5,0.5), new Point(1.5,0.5), new Point(0.5,1.5), new Point(1.5,1.5));
		Rectangle r3 = new Rectangle(new Point(0,0), new Point(-1,0), new Point(0,-1), new Point(-1,-1));
		Rectangle r4 = new Rectangle(new Point(0,0), new Point(-1,0), new Point(0,1), new Point(-1,1));
		Rectangle r5 = new Rectangle(new Point(0,0), new Point(0.5,0), new Point(0,0.5), new Point(0.5,0.5));
		
		System.out.println("R1 intersecta R2: " + RectangleMethods.overlaps(r1, r2) + "  (R2 desplazado +0.5)"); //desplazado +0.5
		System.out.println("R1 intersecta R3: " + RectangleMethods.overlaps(r1, r3) + "  (se tocan en 1 punto)"); //se tocan en 1 punto
		System.out.println("R2 intersecta R3: " + RectangleMethods.overlaps(r2, r3) + "  (no intersectado)"); //no intersectado
		System.out.println(); //pruebas de simetria
		System.out.println("R2 intersecta R1: " + RectangleMethods.overlaps(r2, r1) + "  (inversa)");
		System.out.println("R3 intersecta R1: " + RectangleMethods.overlaps(r3, r1) + "  (inversa)");
		System.out.println("R3 intersecta R2: " + RectangleMethods.overlaps(r3, r2) + "  (inversa)");
		System.out.println();
		System.out.println("R1 intersecta R4: " + RectangleMethods.overlaps(r1, r4) + "  (se tocan en la arista)"); // se tocan en la arista
		System.out.println("R1 intersecta R5: " + RectangleMethods.overlaps(r1, r5) + "  (uno dentro del otro)"); // uno dentro del otro
		
	}

}
