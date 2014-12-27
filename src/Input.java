import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;



/**
 * Program for generating input for Assignment 4.
 * @author Matt Gigliotti
 *
 */
public final class Input {

    
    /**
     * 
     */
    private Input() {
        //dummy
        
    }
    
    /**
     * Generates input.
     * @param args arguments
     * @throws FileNotFoundException if file cannot be made
     */
    public static void main(String[] args) throws FileNotFoundException {

        final int mem = 2000;
        final int ids = 500;
        final int blockrange = 30;
        final int odds = 2;

        Random rand = new Random();

        PrintWriter in = new PrintWriter("in.txt");

        in.println(mem);
        int i = 1;
        while (i <= ids) {
            int coinflip = rand.nextInt(odds);
     
            if (coinflip == 0) {
                in.print("A ");
                in.print(1 + rand.nextInt(blockrange));
                i++;
            } else {
                in.print("D ");
                in.print(1 + rand.nextInt(i));
            }
            if (!(i > ids)) {
                in.println();
            }
        }
        
        
        
        in.close();
        
    }

}
