import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Node implements Serializable {
    long serialVersionUID;
    Rectangle MBR;
    ArrayList<Register> registers = new ArrayList<Register>();
    Node father;


    //constructor nodo raiz
    public Node() {
        this.serialVersionUID = SerialGenerator.nextUID();
        this.father = null;
    }


    //Nodo normal
    public Node(Node father) {
        this.serialVersionUID = SerialGenerator.nextUID();
        this.father = father;
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
    public void adjust() {
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

        this.MBR = new Rectangle(minPoint, new Point(minPoint.x,maxPoint.y) , new Point(maxPoint.x,minPoint.y),maxPoint);

        if(this.father != null){
            father.adjust();
        }
    }


    //Añade un rectangulo nuevo a la estructura
    /*public void addNode (Node n){
        this.registers.add(n.serialVersionUID);
        this.adjust();
    }*/


    public void addRegister(Register reg) {
        this.registers.add(reg);
        if (this.registers.size()>Rtree.M) {
            Rtree.split(this);
        }

    }


    //Guarda el nodo en un archivo identificado por su UID
    public void saveNode() {
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



}













































