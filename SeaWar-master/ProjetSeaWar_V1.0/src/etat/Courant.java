package etat;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class Courant implements EtatDeplacement{
private static Courant c = new Courant();
	
	private Courant(){
		super();
	}
	public static EtatDeplacement getEtat(){
		return c;
	}
	public void setEtatCourant(Navire n) {
	}

	
	public void setEtatInapte(Navire n) {
		n.setEtat(InApte.getEtat());
		
	}
	
	/**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @return succès/echec
     */
	public boolean tir(Canons canon, Position pos, Navire previous, Navire current) {
		
		if(current.getADejaTire()){
			return false;//TODO: message d'erreur : "le navire à déjà tirer pendant le tour"
		}
    	return canon.tire(pos);
    }
	
	
	 /**
     * Mets à jour les variables d'instances lors d'un déplacement
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
     */
    public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current) {
    	current.setDep(current.getDep()- nbCase);
    	current.setDir(dir);
    	current.setPos(pos);
    	current.setaEteDeplace(true);
    	return true;
    }
    
    public String toString() {
    	return "Courant";
    }

	
}
