
import java.util.*;

/**
 * 
 */
public class Navire {

    /**
     * Default constructor
     */
    public Navire() {
    }

    /**
     * 
     */
    public String nom;

    /**
     * 
     */
    public int pv;

    /**
     * 
     */
    public int depMax;

    /**
     * 
     */
    public String nomJ;

    /**
     * 
     */
    public Orientation dir;

    /**
     * 
     */
    public Position pos;



    /**
     * 
     */
    public Set<Canons> canons;

    /**
     * @param pos  
     * @return
     */
    public boolean tirPrimaire(Position pos ) {
        // TODO implement here
        return false;
    }

    /**
     * @param pos 
     * @return
     */
    public boolean tirSecondaire(Position pos) {
        // TODO implement here
        return false;
    }

    /**
     * @param pos 
     * @return
     */
    public boolean deplacement(Position pos) {
        // TODO implement here
        return false;
    }

    /**
     * @param degats
     */
    public void toucher(int degats) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List getInfo() {
        // TODO implement here
        return null;
    }

}