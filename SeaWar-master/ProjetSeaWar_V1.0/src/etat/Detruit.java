package etat;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class Detruit implements EtatDeplacement {
	private static Detruit d = new Detruit();
	
	private Detruit(){
		super();
	}
	
	public void setEtatCourant(Navire n) {
	}

	
	public void setEtatInapte(Navire n) {
	}
	
	public static EtatDeplacement getEtat(){
		return d;
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
		System.out.println("Detruit : deplacement");
		return false;
	}
	
	public String toString() {
    	return "Détruit";
    }


}
