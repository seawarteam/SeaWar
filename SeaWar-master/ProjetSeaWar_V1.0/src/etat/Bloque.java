package etat;

import java.io.Serializable;
import java.util.Set;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class Bloque implements EtatDeplacement, Serializable{

	private static Bloque b = new Bloque();
	
	private Bloque() {
		
	}

	public void setEtatCourant(Navire n) {
		
	}

	public void setEtatInapte(Navire n) {
		n.setEtat(InApte.getEtat());
		
	}

	public boolean tir(Canons canon, Position pos, Navire previous,	Navire current, Set<Position> rochers) {
		System.err.println("Bloqué ! => le navire ne peux pas tirer");
		return false;
	}

	public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current) {
		if(previous != null) {
    		if(previous.getaEteDeplace()) {
    			setEtatInapte(previous);
    		} else {
    			return false;
    		}	
    	}
		current.setDep(0);
    	current.setDir(dir);
    	current.setPos(pos);
    	current.setaEteDeplace(true);
    	setEtatInapte(current);
		return false;
	}

	public static Bloque getEtat() {
		return b ;
	}
	
	public String toString() {
    	return "Bloqué";
    }


}
