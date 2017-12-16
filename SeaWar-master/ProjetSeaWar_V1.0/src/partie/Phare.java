package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

public class Phare extends Eau {

    public static final Color couleurVide = Color.WHITE;
    public static final Color couleurOccupe = Color.RED;
	
	
    public Phare(Polygon p, int i, int j,Observer obs) {
    	super(p, i, j,obs);
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j);
    }
    
	public void getInfo() {
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
	}
	
	public String toString() {
		return "Phare";
	}
    public void takeCase(Navire n){
        estOccupe = true;  
        takePosition = n;
        col = couleurOccupe;
    }
}
