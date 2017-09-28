import java.util.Queue;

public interface ISplitter {
    void pickSeeds(Node n);
    Register pickNext();
    void split(Node n,Queue<Long> nodes);
}
