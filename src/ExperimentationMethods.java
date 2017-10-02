import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ExperimentationMethods {
    private static StringBuilder filesData(String path, StringBuilder sb) {
        ArrayList<Double> results = new ArrayList<>();
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        long numberOfFiles = 0L;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                numberOfFiles++;
                double bytes = child.length();
                double percentageOfUse = (bytes/4096);
                results.add(percentageOfUse);
                child.delete();
            }
            double[] arr = results.stream().mapToDouble(i -> i).toArray();
            Statistics stats = new Statistics(arr);
            sb.append("Number of nodes: ").append(numberOfFiles).append("\n");
            sb.append("Mean: ").append(stats.getMean()).append("\n");
            sb.append("Median: ").append(stats.median()).append("\n");
            sb.append("Variance: ").append(stats.getVariance()).append("\n");
            sb.append("Standard deviation: ").append(stats.getStdDev()).append("\n");
        } else {
            System.out.println("Could not opern directory");
            System.exit(-1);
        }
        return sb;
    }

    private static void resultsFile(String name, String path, StringBuilder sb) {
        List<String> lines = Collections.singletonList(sb.toString());
        Path file = Paths.get(path + name);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println("Could not write on file" + name);
            e.printStackTrace();
        }
    }

    static void insertExperiment(long N, String path, String name) {

        long tStart = System.currentTimeMillis();

        Rtree rtree1 = new Rtree(path);
        Node l = new Node("L");
        Node r = new Node("L");
        NodeMethods.saveNode(l);
        NodeMethods.saveNode(r);
        Rtree.newRoot(l,r);
        Rtree.splitter = new LinearSplit();
        TestMethods.insertN(N);

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;

        int treeHeigth = TestMethods.treeHeigth();

        StringBuilder sb = new StringBuilder();
        sb.append("Built tree: ").append(Rtree.treeId).append("\n");
        sb.append("Time elapsed: ").append(elapsedSeconds).append("\n");
        sb.append("Tree heigth: ").append(treeHeigth).append("\n");
        ExperimentationMethods.resultsFile(name, path, ExperimentationMethods.filesData(path,sb));
    }
}
