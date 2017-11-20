
import java.util.*;

/**
 * 
 */
public class Joueur {
	public static void main(String [] args){
		Joueur j = new Joueur("Albert");
		System.out.println(j.toString());
	}
	
	private String nom;
	private List<Navire> navires;
	
    /**
     * Default constructor
     */
    public Joueur(String s) {
    	this.nom=s;
    	navires = new ArrayList<Navire>();
    	ajoutNavire();
    }
    
    private void ajoutNavire(){
    	navires.add(new Navire("Fregate", 50, 7, this.nom, Orientation.N, new Position(0, 0)));
    	navires.add(new Navire("Amiral", 100, 3, this.nom, Orientation.N, new Position(0, 1)));
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
    		navs = navs+nav.toString();
    		navs = navs+ "\t ";
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