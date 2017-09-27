
public interface ISplitter {
    void pickSeeds(Node n);
    Register pickNext();
    void split(Node n);
}
