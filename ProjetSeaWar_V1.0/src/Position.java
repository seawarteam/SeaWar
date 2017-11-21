
import java.util.*;

/**
 * 
 */
public class Position {

    /**
     * Default constructor
     */
    public Position(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * 
     */
    public int x;

    /**
     * 
     */
    public int y;

    /**
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param int 
     * @param int 
     * @return
     */
    public int set(int a, int b) {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public String toString() {
        return "("+this.x+";"+this.y+")";
    }

}