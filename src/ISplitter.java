import java.util.Queue;

public interface ISplitter {
    void pickSeeds(Node n);
    Register pickNext(Node n, Node nn);
    void split(Node n,Queue<Long> nodes);
}
