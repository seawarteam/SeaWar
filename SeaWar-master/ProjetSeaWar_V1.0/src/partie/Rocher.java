package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

/**
 * 
 */
public class Rocher extends Case {

    public static final Color couleurVide = Color.YELLOW;
    public static final Color couleurTir = Color.GRAY;
	
    public Rocher(Polygon p, int i, int j) {
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j);
    }
    
    public boolean getEstOccupe(){
        return true;
    }

    public void ResetCouleur() {
		if(col != couleurVide){
			col = couleurVide;
			notifyObservers(this);			
		}
	}
    
	public void takeCase(Navire n){
		//Ne rien faire
	}

	public void surbrillanceT() {
		col = couleurTir;
	}
	
	public void surbrillanceO(){
		//Ne rien faire
	}

	
	public void surbrillanceD() {
		//Ne rien faire
	}

	
	
}

