
import java.util.*;

/**
 * 
 */
public class Plateau {
    
    private Set<Case> cases;
    private int largeur;
    private int longueur;
    
    public Plateau(int l, int L) {
        cases = new HashSet<Case>;
        largeur = l;
        longueur = L;
        for (int i = 0 ; i < l ; i++) {
            for (int j = 0 ; j < L ; j++) { 
                cases.add(new Eau(i, j));
                
              
        
            
    }


}
