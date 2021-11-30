package cz.uhk.pro2;
import org.apache.derby.tools.ij;

public class RunDbConsole {
    public static void main(String[] args) {
        try {
            //connect 'jdbc:derby:ChatClientDB;create=true';
           // connect 'jdbc:derby:ChatClientDB';
            ij.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
