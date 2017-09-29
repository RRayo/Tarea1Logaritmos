import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;


class Node implements Serializable {
    long serialVersionUID;
    Rectangle MBR;
    ArrayList<Register> registers = new ArrayList<Register>();
    protected String type; // Leaf, Root or Node


    //constructor nodo
    Node(String type) {
        this.MBR = new Rectangle();
        this.serialVersionUID = SerialGenerator.nextUID();
        this.type = type;
    }




    //Para ajustar el rectangulo luego de agregar nuevos hijos se toma el punto mas bajo de todos y el mas alto de todos y se crea un nuevo
    //rectangulo a partir de ellos
    /*
        (minX,maxY)
        *__________________*(maxX,maxY)
        |                  |
        |                  |
        |                  |
        *__________________*(maxX,minY)
        (minX,minY)

    void adjust() {
        Point maxPoint = this.MBR.maxPoint;
        Point minPoint = this.MBR.minPoint;
        //System.out.println("Numero de registros: " + registers.size());

        for(Register reg: registers) {
            Rectangle r = reg.rectangle;

            Point rLowerPoint = r.minPoint;
            Point rHigherPoint = r.maxPoint;

            minPoint = PointMethods.compare(minPoint, rLowerPoint) ? minPoint : rLowerPoint;
            maxPoint = !PointMethods.compare(maxPoint, rHigherPoint) ? maxPoint : rHigherPoint;
        }

        this.MBR = new Rectangle(minPoint, new Point(minPoint.x,maxPoint.y) , new Point(maxPoint.x,minPoint.y),maxPoint);
        this.saveNode();
    }
*/
/*
    void addRegister(Register reg, Stack<Long> nodes) {
        this.registers.add(reg);
        this.saveNode();
        if (this.registers.size() > Rtree.M) {
            Rtree.split(this, nodes);
        }
        //nodes.add(this.serialVersionUID);
        Rtree.adjustTree(nodes);
    }
*/
/*
    //Guarda el nodo en un archivo identificado por su UID
    void saveNode() {
        try {
            String path = Rtree.path;
            String nodeName = "node" + serialVersionUID + ".ser";
            FileOutputStream fileOut = new FileOutputStream(path + nodeName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            //System.out.println("Serialized " + nodeName + " is saved in" + path);
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

*/
    /*public String getRoot() {
        return type;
    }*/

    /*
    public void updateRegister(Register newReg) {
        //System.out.println("Nodo " + this.serialVersionUID + " numero de registros: " + this.registers.size());
        for (Register reg : registers) {
            //System.out.println("    -index: " + reg.serialVersionUID);
            if (reg.serialVersionUID.equals(newReg.serialVersionUID)) {
                this.registers.set(registers.indexOf(reg), newReg);
                break;
            }
        }
        this.saveNode();
    }
    */
}













































