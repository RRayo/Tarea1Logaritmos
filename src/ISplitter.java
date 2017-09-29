
import java.util.Stack;

public interface ISplitter {
    void pickSeeds(Node n);
    Register pickNext(Node n, Node nn);
    void split(Node n,Stack<Long> nodes);
}
