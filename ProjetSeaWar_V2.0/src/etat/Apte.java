package etat;

import java.io.Serializable;
import java.util.Set;

import partie.Canons;
import partie.Navire;

public class Apte implements EtatDeplacement, Serializable {
	private static Apte a = new Apte();
	
	private Apte(){
		super();
	}
	
	public void setEtatCourant(Navire n) {
		n.setEtat(Courant.getEtat());
		
	}

	public void setEtatInapte(Navire n) {
		n.setEtat(InApte.getEtat());
	}



	public static EtatDeplacement getEtat() {
		return a;
	}

	/**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @param previous : le navire actuellement à l'état Courant (qui doit passer à l'état inapte)
     * @param current : le navire selectionné (qui doit passer à l'état Courant)
     * @param rochers : les rochers
     * 
     * @return succès/echec
     */
    public boolean tir(Canons canon, Position pos, Navire previous, Navire current, Set<Position> rochers) {
    	boolean ok;
    	if(previous != null) {
    		if(previous.getaEteDeplace()){
    			if(! current.getADejaTire()){
    				ok = canon.tire(pos, rochers);
    				if(ok) {
    					setEtatInapte(previous);
    					setEtatCourant(current);
    				}
    			} else {
    				return false;//TODO: message d'erreur : "le navire à déjà tirer pendant le tour"
    			}
    		} else {
    			return false;//TODO: message d'erreur : "Le navire précédant n'a pas été déplacé"
    		}
    	} else {
    		ok = canon.tire(pos, rochers);
			if(ok) {
				setEtatCourant(current);
			}
    	}
    	
    	return ok;
    }

    /**
     * Mets à jour les variables d'instances lors d'un déplacement
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
     */
    public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current) {
    	if(previous != null) {
    		if(previous.getaEteDeplace()) {
    			setEtatInapte(previous);
    		} else {
    			return false;
    		}	
    	}
    	setEtatCourant(current);
    	current.setDep(current.getDep()- nbCase);
    	current.setDir(dir);
    	current.setPos(pos);
    	current.setaEteDeplace(true);
    	return true;
    }
    
    
    public String toString() {
    	return "Apte";
    }

}
