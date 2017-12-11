
import java.util.*;

public class Plateau {
    
    private Set<Case> cases;
    private int largeur;
    private int longueur;
    
    public Plateau(int l, int L) {
        cases = new HashSet<Case>();
        largeur = l;
        longueur = L;
        for (int i = 0 ; i < l ; i++) {
            for (int j = 0 ; j < L ; j++) { 
                cases.add(new Eau(i, j));
            }
        }           
              
        //TO DO : pouvoir retourner un set d'obstacles (tout les obstacles prÃ©sents) 
        //--> faire une liste "fixe" avec les rochers et une autre "changeante" avec les bateaux.
            
    }


}
