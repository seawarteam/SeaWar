
import java.util.*;

/**
 * 
 */
public class Canons {
	
	private Navire monNavire;
	private String nom;
	private int degat;
    private int tpsRech;
    private int tpsRest;
    private List<Position> zoneTire;
    private List<List<Position>> matZoneTire;
    
    /**
     * Default constructor
     */
    public Canons(String n, int d, int tps, List<Position> p, Navire nav) {
    	this.nom = n;
    	this.degat = d;
    	this.tpsRech = tps;
    	this.zoneTire = p;
    	this.tpsRest = 0;
    	this.matZoneTire = getMatrice(this.zoneTire);
    	this.monNavire = nav;
    	
    }

    
    public boolean equals(Object obj) {
    	if(this == obj) return true;
    	if(!(obj instanceof Canons)) return false;
    	Canons canon = (Canons) obj;
		return (this.nom.equals(canon.getNom()) && this.degat == canon.getDegat() && this.tpsRech == canon.getTpsRech() && this.zoneTire.equals(canon.getZoneTire()));
    }

    
    public String toString() {
    	return "Objet Canons\tnom = "+this.nom+"\tdegat = "+this.degat+"\tpsRech = "+this.tpsRech+"\tpsRest = "+this.tpsRest+"\n";
    }
    
    public String getNom() {
    	return this.nom;
    }
    
    public int getTpsRech() {
    	return this.tpsRech;
    }
    
    public List<Position> getZoneTire() {
    	return this.zoneTire;
    }

    public void initTour() {
		if(this.tpsRest > 0) {
    		this.tpsRest -= 1;
		}
	}
    
    public int getDegat() {
        return this.degat;
    }
    


    /**
     * tire() renvoie true si le tire est possible et false sinon
     * @param pos 
     * @return boolean
     */
    public boolean tire(Position pos) {
        if(this.tpsRest == 0) {
        	List<Position> posVisable = posCanShoot();
        	if(posVisable.contains(pos)) {
	        	//TODO: this.doSon();
	        	this.tpsRest=this.tpsRech;
	        	return true;
        	}else{
        		return false;
        	}
        }else{
        	return false;
        }
        
    }


    /**
     * 
     * @return La Liste des positions sur lequelles le canon peut tirer
     */
	private List<Position> posCanShoot() {
		
		int dir = monNavire.getOrientation().value;
		Position myPosition = monNavire.getPos();
		List<Position> posRela = this.matZoneTire.get((int)dir);
		List<Position> posReel = new LinkedList<Position>();
		int x = myPosition.getX();
		int y = myPosition.getY();
		for(Position pos : posRela) {
			Position reel = new Position(x+pos.getX(),y+pos.getY());
			posReel.add(reel);
		}
		return posReel;
	}

	/**
	 * Créée une matrice contenant les positions relative par rapport au navire d'indicer par l'orientation du navire
	 * ex: Mat[dir] => positions que l'on peut tirer avec l'orientation dir
	 * @param zone == this.zoneTire : les positions que l'on peut tirer avec l'orientation du navire vers le Nord
	 * @return la matrice
	 */
    private List<List<Position>> getMatrice(List<Position> zone){
    	List<List<Position>> matZone = new LinkedList<List<Position>>();    	
    	for(int i=0; i<6;i++) {
    		double O= i*Math.PI/6;
    		List<Position> sousListe = new LinkedList<Position>();
    		for(Position pos : zone) {
    			int x = pos.getX();
        		int y = pos.getY();
        		int x2 = (int) (x*Math.cos(O) - y*Math.sin(O));
    			int y2 = (int) (x*Math.sin(O) + x*Math.cos(O));
    			Position pos2 = new Position(x2, y2);
    			sousListe.add(pos);
    		}
    		matZone.set(i,sousListe);
    	}
    	return matZone;
    }

	

}