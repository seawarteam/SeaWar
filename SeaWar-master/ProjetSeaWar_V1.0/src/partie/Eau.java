package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Eau extends Case implements Serializable {
	public static final Color couleurVide = Color.CYAN;
    public static final Color couleurDep = new Color(0, 0, 255, 60);
    public static final Color couleurOri = new Color(150,200,0);
    public static final Color couleurTir = Color.GRAY;
    public static final Color couleurTirOccupe = new Color(255,50,255);
    public static final Color couleurDeptemp = Color.black;
    
    
	public Eau(Polygon p, int i, int j,Observer obs) {
		super();
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j - ((int) i/2));
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
        takePosition = null;
    }
    
    public void ResetCouleur() {
    	col = couleurVide;
    	if(estOccupe) {
   			col = takePosition.couleur;
   		}
       	setChanged();
       	notifyObservers(this);
       	clearChanged();
    	
    	
 /*   	if(!estOccupe) {
			if(col != couleurVide){
				col = couleurVide;
		    	setChanged();
		    	notifyObservers(this);
		    	clearChanged();			}
    	} else {
    		if(!col.equals(takePosition.couleur)){
    			col = takePosition.couleur;
    	    	setChanged();
    	    	notifyObservers(this);
    	    	clearChanged();    		}
    	}
		*/
	}
    
    public void surbrillanceT(){
    	if(this.estOccupe) {
    		col = couleurTirOccupe;
    	} else {
    		col = couleurTir;
    	}
    	
    	setChanged();
    	notifyObservers(this);
    	clearChanged();    	
    }
    
    public void surbrillanceD(){
    	col = couleurDep;
    	setChanged();
    	notifyObservers(this);
    	clearChanged();    }
    
    public void surbrillanceO(){
    	col = couleurOri;
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
    }
    
    public void surbrillanceDeptemp() {
    	col = couleurDeptemp;
    	setChanged();
    	notifyObservers(this);
    	clearChanged();
    }
    
    public void takeCase(Navire n){
        estOccupe = true;  
        takePosition = n;
    }

	

}
