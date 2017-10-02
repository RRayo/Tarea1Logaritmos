import java.io.*;

public class SerialGenerator {

    /*
    long seedSerial;

    public SerialGenerator(){
        this.seedSerial = 1L;
    }

    public long generateSerial(){
        return this.seedSerial++;
    }*/

    /** File in which the next available id is stored. */
    public static final File FILE = new File(Rtree.DIR + "node");

    /** Return the next available id number. */
    public static long nextUID() {
    	ObjectInputStream in = null;
        try {
            long result;
            if (FILE.exists()) {
                in = new ObjectInputStream(new FileInputStream(FILE));
                result = in.readLong();
            } else {
                result = 0L;
            }
            ObjectOutputStream out
                    = new ObjectOutputStream(new FileOutputStream(FILE));
            out.writeLong(result + 1L);
            out.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return 0;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
