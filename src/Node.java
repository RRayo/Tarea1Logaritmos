import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;


class Node implements Serializable {
    long serialVersionUID;
    Rectangle MBR;
    ArrayList<Register> registers = new ArrayList<Register>();
    private Boolean root;


    //constructor nodo
    Node(boolean root) {
        this.serialVersionUID = SerialGenerator.nextUID();
        this.root = root;
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
     */
    void adjust() {
        Point maxPoint = null;
        Point minPoint = null;

        for(Register reg: registers) {
            Rectangle r = reg.rectangle;
            Point rLowerPoint = r.minPoint;
            Point rHigherPoint = r.maxPoint;

            if (maxPoint == null || minPoint == null) {
                if (maxPoint == null) {
                    maxPoint = rHigherPoint;
                } else
                    minPoint = rLowerPoint;
            } else {
                minPoint = minPoint.compare(rLowerPoint) ? minPoint : rLowerPoint;
                maxPoint = !maxPoint.compare(rHigherPoint) ? maxPoint : rHigherPoint;
            }
        }

        if (minPoint != null) {
            this.MBR = new Rectangle(minPoint, new Point(minPoint.x,maxPoint.y) , new Point(maxPoint.x,minPoint.y),maxPoint);
        }
        this.saveNode();
    }


    void addRegister(Register reg, Queue<Long> nodes) {
        this.registers.add(reg);
        if (this.registers.size() > Rtree.M) {
            Rtree.split(this, nodes);
        }
        nodes.add(this.serialVersionUID);
        Rtree.adjustTree(nodes);
    }


    //Guarda el nodo en un archivo identificado por su UID
    void saveNode() {
        try {
            String path = System.getProperty("user.dir");
            String nodeName = "node" + serialVersionUID + ".ser";
            FileOutputStream fileOut = new FileOutputStream(path + nodeName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized " + nodeName + " is saved in" + path);
        } catch(IOException i) {
            i.printStackTrace();
        }
    }


    public Boolean getRoot() {
        return root;
    }
}













































