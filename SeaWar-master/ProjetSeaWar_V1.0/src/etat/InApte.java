package etat;

import java.io.Serializable;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class InApte implements EtatDeplacement, Serializable {
	private static InApte i = new InApte();
	
	private InApte(){
		super();
	}
	
	public void setEtatCourant(Navire n) {
	}

	
	public void setEtatInapte(Navire n) {
	}
	
	public static EtatDeplacement getEtat(){
		return i;
	}
	
	/**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @return succès/echec
     */
    public boolean tir(Canons canon, Position pos) {
    	return false;
    }

	
    public boolean tir(Canons canon, Position pos, Navire previous, Navire current) {
		return false;
	}
	public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current){
		return false;
	}
	

}
