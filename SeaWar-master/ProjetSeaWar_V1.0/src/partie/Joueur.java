package partie;

import java.util.*;

import etat.Courant;

/**
 * 
 */
public class Joueur {
	public static void main(String [] args){
		Position.initTabPosition(10, 10);
	}
	
	private String nom;
	private List<Navire> navires;
	private Navire currentN;// Navire selectionnÃ© dans l'interface
	
    /**
     * Default constructor
     */
    public Joueur(String s) {
    	this.nom=s;
    	navires = new ArrayList<Navire>();
    }
    
    public Vector<Navire> ajoutDefaultNavire(Observer obs){
    	Vector<Navire> navs = new Vector<Navire>(2);
    	navs.add(new Navire("Fregate", 50, 7, this.nom, Orientation.N, Position.getPosition(0, 0),obs));
    	navs.add(new Navire("Amiral", 100, 3, this.nom, Orientation.N, Position.getPosition(0, 1),obs));
    	navires.add(navs.get(0));
    	navires.add(navs.get(1));
    	return navs;
	}

    
    public boolean equals(Object obj) {
    	if(this == obj) {
    		return true;
    	}
    	if (!(obj instanceof Joueur)){
    		return false;
    	}
    	Joueur j = (Joueur) obj;
    	return this.nom.equals(j.nom);
    }
    
    public String toString() {
    	String navs="\n\t";
    	for(Navire nav : navires) {
    		navs = navs+nav.toString();
    		navs = navs+ "\n\t ";
    	}
    	return "Joueur\tnom = "+this.nom+"\tNavires :"+navs+"\n";
    }


    
    //getter
    public String getNom() {
    	return this.nom;
    }
    
    
    public void addNavire(Navire nav) {
    	this.navires.add(nav);
    }
    
    public boolean deplacement(Position p, Orientation dir, int nbCase){
    	return currentN.deplacement(p, dir, nbCase, getNavEtatCourant());
    }
    
    
    /**
     * @param pos : la position de classe Position
     * @return le Navire situer sur la case pos
     */
    public Navire getNavOnPos(Position pos) {
    	
    	for(Navire nav : this.navires) {
    		if(nav.getPos().equals(pos)) {
    			return nav;
    		}
    	}
        return null;
    }
    
    
    public Navire getNavEtatCourant() {
    	for(Navire nav : navires){
    		if(nav.getEtatCourant() == Courant.getEtat()) {
    			return nav;
    		}
    	}
    	return null;
    }

	public Navire getCurrentN() {
		return currentN;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCurrentN(Navire nav) {
		this.currentN = nav;
	}

	public void initTour() {
		for(Navire nav : navires) {
			nav.initTour();
		}
	}

	public Navire[] getNavires() {
		Navire[] nav = new Navire[navires.size()];
		int i = 0;
		for(Navire n : navires) {
			nav[i] = n;
			i++;
		}
		return nav;
	}
}
