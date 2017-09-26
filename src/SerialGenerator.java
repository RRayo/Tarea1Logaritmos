public class SerialGenerator {
    long seedSerial;

    public SerialGenerator(){
        this.seedSerial = 1L;
    }

    public long generateSerial(){
        return this.seedSerial++;
    }
}
