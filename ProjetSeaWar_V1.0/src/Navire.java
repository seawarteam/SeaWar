
import java.util.*;

/**
 * 
 */
public class Navire {

	public static void main(String [] args) {
		
		
		Navire unNavire = new Navire("Charles De Gaulle", 1000, 7, "France", Orientation.SE, new Position(0,0));
		
		List<Position> zone = new LinkedList<Position>();
		zone.add(new Position(0,1));
		zone.add(new Position(0,2));
		zone.add(new Position(0,3));
		zone.add(new Position(0,4));
		Canons unCanon = new Canons("La Grosse Berta", 200, 2, zone, unNavire);
		
			
			unNavire.addCanon(unCanon);
			System.out.println(unCanon.getZoneTire().toString());
			System.out.println(unCanon.posCanShoot().toString());
			
		
	}
	
	
	 private String nom;
	 private int pv;
	 private int depMax;
	 private String nomJ;
	 private Orientation dir;
	 private Position pos;
	 private Set<Canons> canons;
	 
	 private int dep;
	 private int nb_coup_reçu;
	
    /**
     * Default constructor
     */
    public Navire(String n, int vie, int d, String j, Orientation ori, Position p) {
    	this.nom = n;
    	this.pv = vie;
    	this.depMax = d;
    	this.nomJ = j;
    	this.dir = ori;
    	this.pos = p;
    	this.canons = new HashSet<Canons>();
    }
    public void addCanon(Canons c){
    	this.canons.add(c);
    }
    
    public Position getPos() {
    	return this.pos;
    }
    
    public String getNom() {
		return this.nom;
	}
    public int getDepMax(){
    	return depMax;
    }
    public Orientation getOrientation(){
    	return dir;
    }
    
    
    public void initTour() {
    	this.dep = this.depMax;
    	this.nb_coup_reçu = 0;
    	for(Canons canon : canons){
    		canon.initTour();
    	}
    }

    public boolean equals(Object obj) {
    	if(this == obj) {
    		return true;
    	}
    	if(!(obj instanceof Navire)) {
    		return false;
    	}
    	Navire nav = (Navire) obj;
    	return (this.nom.equals(nav.nom) && this.nomJ.equals(nav.nomJ));//TODO: Définir si ok  	
    }
    
    public String toString() {
    	return "Navire\tnom = "+this.nom+"\tpv = "+this.pv+"\tdepMax = "+this.depMax+"\tnomJ = "+this.nomJ+"\tdir = "+this.dir+"\tpos = "+this.pos.toString()+"\n";
    }
   
    /**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @return succès/echec
     */
    public boolean tir(Canons canon, Position pos) {
    	return canon.tire(pos);
    }
    
    

    /**
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
     * @return echec ou succès du déplacement
     */
    public void deplacement(Position pos, Orientation dir, int nbCase) {
    	
    	this.dep =- nbCase;
    	this.dir = dir;
    	this.pos = pos;
    }

    /**
     * @param degats à retirer aux pv du navire
     */
    public void toucher(int degats) {
    	this.nb_coup_reçu++;        
        if(this.nb_coup_reçu == 2) {// ou >1 ? => Hyp: plus que 2 navires
        	this.pv -= 3*degats; 
        } else {
        	this.pv -= degats;
        }
    }

    /**
     * @return
     */
    public List getInfo() {
        // TODO implement here
        return null;
    }

	

}