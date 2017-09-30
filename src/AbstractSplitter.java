import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public abstract class AbstractSplitter implements ISplitter{

    @Override
    public void split(Node n, Stack<Long> nodes) {

        nodes.pop();


        //System.out.println("-------------------------------------------------------------------------------------------------------------------->Se comenzo split");
        ArrayList<Register> registers = new ArrayList<>(n.registers);
        n.registers = new ArrayList<>();






        RegisterTuple regTuple = this.pickSeeds(n, registers);

        registers.remove(regTuple.reg1);
        registers.remove(regTuple.reg2);

        Node nn = new Node(n.type);

        NodeMethods.addRegister(n, regTuple.reg1, new Stack<>());
        NodeMethods.addRegister(nn, regTuple.reg2, new Stack<>());

        //System.out.println("AREA:" + RectangleMethods.getArea(nn.MBR) + "//" + RectangleMethods.getArea(regTuple.reg2.rectangle));

        /*if (n.type.equals("R") ){ //raiz hizo split
            n.type = "N";
            nn.type = "N";
            //se guarda n y nn y se añade al padre
            n.saveNode();
            nn.saveNode();
            Rtree.newRoot(n,nn);
        } */


        while (!registers.isEmpty() || n.registers.size() >= (Rtree.M-Rtree.m+1) || nn.registers.size() >= (Rtree.M-Rtree.m+1)) { // o grupo se lleno
            //System.out.println(registers.size());


            if(registers.size()==0) {
                //TODO aca register tiene valor 0???
                break;
            }
            Register chosenRegister = pickNext(n, nn, registers);
            Rectangle r = chosenRegister.rectangle;
            if (Rtree.showdown(n,nn,r)) {
                NodeMethods.addRegister(n, chosenRegister,new Stack<>());
            } else {
                NodeMethods.addRegister(nn, chosenRegister,new Stack<>());
            }
        }

        if (n.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {

                /*if(reg == null){
                    System.out.println("----------------------------------------REGISTRO NULO-------------------------");
                    System.exit(-1);
                }*/

                NodeMethods.addRegister(n, reg,new Stack<>());
            }
        } else if (nn.registers.size() >= (Rtree.M-Rtree.m+1)) {
            for (Register reg : registers) {


                /*if(reg == null){
                    System.out.println("----------------------------------------REGISTRO NULO-------------------------");
                    System.exit(-1);
                }*/

                NodeMethods.addRegister(nn, reg,new Stack<>());
            }
        }

        //se guarda cambios de n y nn
        NodeMethods.saveNode(n);
        NodeMethods.saveNode(nn);

        if(nodes.isEmpty()){ // es la raiz, se genera una nueva raiz
            n.type = "N";
            nn.type = "N";
            Rtree.newRoot(n,nn);
        } else { // se añade registro al padre
            try {
                //System.out.println(nodes.size());
                NodeMethods.addRegister( Rtree.loadNode(nodes.pop()), new Register(nn.MBR, nn.serialVersionUID), nodes);
                //Rtree.loadNode(nodes.pop()).addRegister(new Register(nn.MBR, nn.serialVersionUID), nodes);
            } catch (NullPointerException e) {
                System.out.println("Nodes vacio");
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------->Se realizo split exitosamente");
    }
}