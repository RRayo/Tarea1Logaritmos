import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExperimentationMethods {
    public static StringBuilder filesData (String path, StringBuilder sb) {
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        long numberOfFiles = 0L;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                numberOfFiles++;
                double bytes = child.length();
                double percentageOfUse = (bytes/4096);
                sb.append(percentageOfUse);
                sb.append("\n");
            }
            sb.append("Number of files: ");
            sb.append(numberOfFiles);
        } else {
            System.out.println("Could not opern directory");
            System.exit(-1);
        }
        return sb;
    }

    public static void resultsFile (String name, String path, StringBuilder sb) {
        List<String> lines = Collections.singletonList(sb.toString());
        Path file = Paths.get(path + name);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println("Could not write on file" + name);
            e.printStackTrace();
        }
    }
}
