package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

/**
 * 
 */
public class Eau extends Case {
	public static final Color couleurVide = Color.CYAN;
    public static final Color couleurDep = Color.BLUE;
    public static final Color couleurTir = Color.GRAY;
    
    
    
	public Eau(Polygon p, int i, int j) {
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = new Position(i, j);
    }
    

    
    public void freeCase(){
        estOccupe = false;   
    }
    
    public void surbrillanceT(){
    	col = couleurTir;
    }
    
    public void surbrillanceD(){
    	col = couleurDep;
    }
    
    public void takeCase(Navire n){
        estOccupe = true;  
        takePosition = n;
    }

	

}
