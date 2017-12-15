package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.util.*;

/**
 * 
 */
public abstract class Case {

	public boolean estOccupe;
	public Navire takePosition;
	public Polygon poly;
	public Color col;
	public Position position;
		
	

    public Case(Polygon p, Color c, int i, int j) {
    	poly = p;
    	estOccupe = false;
    	takePosition = null;
		col = c;
		position = new Position(i, j);
    }
    
    public Case(){
    	super();
    }
    
    public abstract void takeCase(Navire n);
    public abstract void surbrillanceT();
    public abstract void surbrillanceD();
    
    public List getInfo() {
        // TODO implement here
        return null;
    }
    
    public boolean getEstOccupe() {
    	return estOccupe;
    }
    
    public void setEstOccupe(boolean b) {
    	estOccupe = b;
    }
    
    public void setTakePosition(Navire n) {
    	takePosition = n;
    }
    

}