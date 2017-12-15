package etat;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class Apte implements EtatDeplacement {
	private static Apte a = new Apte();
	
	private Apte(){
		super();
	}
	
	public void setEtatCourant(Navire n) {
		n.setEtat(Courant.getEtat());
		
	}

	public void setEtatInapte(Navire n) {
	}



	public EtatDeplacement getEtat() {
		return a;
	}

	/**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @return succès/echec
     */
    public boolean tir(Canons canon, Position pos, Navire previous, Navire current) {
    	if(previous != null) {
    		if(previous.getaEteDeplace()){
    			setEtatInapte(previous);
    			if(current.getADejaTire()){
    				return false;//TODO: message d'erreur : "le navire à déjà tirer pendant le tour"
    			}
    		} else {
    			return false;//TODO: message d'erreur : "Le navire précédant n'a pas été déplacé"
    		}
    	}
    	setEtatCourant(current);
    	return canon.tire(pos);
    }

    /**
     * Mets à jour les variables d'instances lors d'un déplacement
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
     */
    public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current) {
    	if(previous != null) {
    		setEtatInapte(previous);
    	}
    	setEtatCourant(current);
    	current.setDep(current.getDep()- nbCase);
    	current.setDir(dir);
    	current.setPos(pos);
    	return true;
    }
    
    


}
