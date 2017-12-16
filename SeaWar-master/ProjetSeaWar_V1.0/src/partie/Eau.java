package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

/**
 * 
 */
public class Eau extends Case {
	public static final Color couleurVide = Color.CYAN;
    public static final Color couleurDep = new Color(0, 0, 255, 60);
    public static final Color couleurOri = Color.orange;
    public static final Color couleurTir = Color.GRAY;
    
    
    
	public Eau(Polygon p, int i, int j,Observer obs) {
		super();
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j);
		addObserver(obs);
    }
    
	public void getInfo() {
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
	}
	
	public String toString() {
		return "Eau";
	}
    
    public void freeCase(){
        estOccupe = false;   
    }
    
    public void ResetCouleur() {
    	if(!estOccupe) {
			if(col != couleurVide){
				col = couleurVide;
				notifyObservers(this);			
			}
    	} else {
    		if(!col.equals(takePosition.couleur)){
    			col = takePosition.couleur;
    			notifyObservers(this);
    		}
    	}
		
	}
    
    public void surbrillanceT(){
    	col = couleurTir;
    	notifyObservers(this);
    }
    
    public void surbrillanceD(){
    	col = couleurDep;
    	notifyObservers(this);
    }
    
    public void surbrillanceO(){
    	col = couleurOri;
    	notifyObservers(this);
    }
    
    public void takeCase(Navire n){
        estOccupe = true;  
        takePosition = n;
    }

	

}
