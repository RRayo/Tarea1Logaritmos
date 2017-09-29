
import java.util.ArrayList;
import java.util.Stack;

public interface ISplitter {
    RegisterTuple pickSeeds(Node n, ArrayList<Register> registers);
    Register pickNext(Node n, Node nn, ArrayList<Register> registers);
    void split(Node n,Stack<Long> nodes);
}
