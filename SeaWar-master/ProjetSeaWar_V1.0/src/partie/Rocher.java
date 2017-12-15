package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

/**
 * 
 */
public class Rocher extends Case {

    public static final Color couleurVide = Color.YELLOW;
	
    public Rocher(Polygon p, int i, int j) {
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = new Position(i, j);
    }
    
    public boolean getEstOccupe(){
        return true;
    }

	
	public void takeCase(Navire n){
		//Ne rien faire
	}

	public void surbrillanceT() {
		//Ne rien faire
	}

	@Override
	public void surbrillanceD() {
		//Ne rien faire
	}
}

