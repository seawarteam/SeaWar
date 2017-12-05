
import java.util.*;

/**
 * 
 */
public class Eau extends Case {

    private Boolean estOccupe;
    
    public static final Color couleurVide = Color.CYAN;
    public static final Color couleurDep = Color.BLUE;
    public static final Color couleurTir = Color.GRAY;
    
    public Eau() {
        super(); // Il faut qu'il y ait un constructeur de case sans argument
        estOccupe = false;
    }
    
    public Boolean getEstOccupe(){
        return estOccupe;  
    }
    
    public void freeCase(){
        estOccupe = false;   
    }
    
    public void takeCase(){
        estOccupe = true;   
    }

}
