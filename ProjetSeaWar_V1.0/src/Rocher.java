
import java.awt.Color;
import java.util.*;

/**
 * 
 */
public class Rocher extends Case {

    public static final Color couleurVide = Color.YELLOW;
	
    public Rocher() {
        super(); // Il faut constructeur de case sans argument
    }
    
    public boolean getEstOccupe(){
        return true;
    }
}

