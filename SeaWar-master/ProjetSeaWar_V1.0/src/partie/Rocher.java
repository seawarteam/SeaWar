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
	
    public Rocher(Polygon p, int i, int j,Observer obs) {
    	super();
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j - ((int) i/2));
		addObserver(obs);
    }
    
    public boolean getEstOccupe(){
        return true;
    }

    public void ResetCouleur() {
    	
    		col = couleurVide;
        	setChanged();
        	notifyObservers(this);
        	clearChanged();    	
    	
    	
    	
		/*if(col != couleurVide){
			col = couleurVide;
	    	setChanged();
	    	notifyObservers(this);
	    	clearChanged();		
	    	
		}*/
	}
    
    public String toString() {
    	return "Terre";
    }
    
	public void getInfo() {
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
	}
	
	public void takeCase(Navire n){
		//Ne rien faire
	}

	public void surbrillanceT() {
		col = couleurTir;
	}
	
	public void surbrillanceO(){
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
	}
    public void surbrillanceDeptemp() {
    	col = Color.black;
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
    }

	
	public void surbrillanceD() {
		//Ne rien faire
	}

	
	
}

