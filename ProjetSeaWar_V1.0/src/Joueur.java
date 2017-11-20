
import java.util.*;

/**
 * 
 */
public class Joueur {

	
	private String nom;
	private Set<Navire> navires;
	
    /**
     * Default constructor
     */
    public Joueur(String s) {
    	this.nom=s;
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
    	String navs="";
    	for(Navire nav : navires) {
    		navs = navs+nav.getNom()+" ";
    	}
    	return "Objet Joueur\tnom = "+this.nom+"\tNavires :"+navs+"\n";
    }


    
    //getter
    public String getNom() {
    	return this.nom;
    }
    
    
    public void addNavire(Navire nav) {
    	this.navires.add(nav);
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

}