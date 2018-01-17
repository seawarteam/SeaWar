package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public abstract class Case extends Observable implements Serializable{


	private static final long serialVersionUID = -6618483486525611426L;
	public boolean estOccupe;
	public Navire takePosition;
	public Polygon poly;
	public Color col;
	public Position position;
		
	

    public Case(Polygon p, Color c, int i, int j,Observer obs) {
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = c;
		position = Position.getPosition(i, j);
		addObserver(obs);
    }
    
    public Case(){
    	super();
    }
    public abstract void freeCase();
    public abstract void takeCase(Navire n);
    public abstract void surbrillanceT();
    public abstract void surbrillanceD();
    public abstract void surbrillanceO();
    public abstract void ResetCouleur();
    public abstract void surbrillanceDeptemp();
    
    public abstract void getInfo();
    
    public boolean getEstOccupe() {
    	return estOccupe;
    }
    
    public void setEstOccupe(boolean b) {
    	estOccupe = b;
    }
    
    public Navire getTakePosition(){
    	return takePosition;
    }
    
    public Polygon getPoly(){
    	return poly;
    }
    
    public Position getPosition(){
    	return position;
    }
    
    public void setTakePosition(Navire n) {
    	takePosition = n;
    }
	
    public void setColor(Color colorBase) {
        col = colorBase;
        setChanged();
    	notifyObservers(this);
    	clearChanged();  
    }
}
