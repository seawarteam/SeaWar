package partie;

import java.io.Serializable;
import java.util.*;


/**
 * 
 */
public class Canons implements Serializable {
	
	public static void main(String [] args) {
		
		List<Position> liste = new LinkedList<Position>();
		liste.add(new Position(1,0));
		liste.add(new Position(2,0));
		liste.add(new Position(3,0));
		liste.add(new Position(4,0));
		Canons unCanon = new Canons("La Grosse Berta", 200, 2, liste, null);
		System.out.println(unCanon.toString());
	}
	
	
	
	
	private Navire monNavire;
	private String nom;
	private int degat;
    private int tpsRech;
    private int tpsRest;
    private List<Position> zoneTire;
    private Map<Orientation,List<Position>> matZoneTire;
    
    /**
     * Default constructor
     */
    public Canons(String n, int d, int tps, List<Position> p, Navire nav) {
    	this.nom = n;
    	this.degat = d;
    	this.tpsRech = tps;
    	this.zoneTire = p;
    	this.tpsRest = 0;
    	this.matZoneTire = getMap(this.zoneTire);
    	this.monNavire = nav;
    	
    }

    
    public boolean equals(Object obj) {
    	if(this == obj) return true;
    	if(!(obj instanceof Canons)) return false;
    	Canons canon = (Canons) obj;
		return (this.nom.equals(canon.getNom()) && this.degat == canon.getDegat() && this.tpsRech == canon.getTpsRech() && this.zoneTire.equals(canon.getZoneTire()));
    }

    
    public String toString() {
    	return "Objet Canons\tnom = "+this.nom+"\ttdegat = "+this.degat+"\ttpsRech = "+this.tpsRech+"\tpsRest = "+this.tpsRest+"\n";
    }
    
    public String getNom() {
    	return this.nom;
    }
    
    public int getTpsRech() {
    	return this.tpsRech;
    }
    
    public int getTpsRest() {
    	return tpsRest;
    }
    
    public List<Position> getZoneTire() {
    	return this.zoneTire;
    }
    
    public Map<Orientation, List<Position>> getMatZoneTire(){
    	return this.matZoneTire;
    }

    public void initTour() {
		if(this.tpsRest > 0) {
    		this.tpsRest -= 1;
		}
	}
    
    public int getDegat() {
        return this.degat;
    }
	
    public void addZoneTire(Position p){
    	zoneTire.add(p);
    }

    /**
     * tire() renvoie true si le tire est possible et false sinon
     * @param pos 
     * @return boolean
     */
    public boolean tire(Position pos, Set<Position> rochers) {
        if(this.tpsRest == 0) {
        	List<Position> posVisable = posCanShoot(monNavire.getDir(), monNavire.getPos(), rochers);
        	if(posVisable.contains(pos)) {
        		this.tpsRest=this.tpsRech;
	        	monNavire.setADejaTire(true);
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
	public List<Position> posCanShoot(Orientation dir, Position posi, Set<Position> rochers) {
		
		/*Orientation dir = monNavire.getOrientation();
		Position myPosition = monNavire.getPos();*/
		List<Position> posRela = this.matZoneTire.get(dir);
		List<Position> posReel = new LinkedList<Position>();
		int x = posi.getX();
		int y = posi.getY();
		for(Position pos : posRela) {
			Position reel = Position.getPosition(x+pos.getX(),y+pos.getY());
			if(reel != null) {
				Position[] tab = Position.peutTirer(posi, reel);
				boolean can = true;
				for(Position p : tab) {
					if (rochers.contains(p)) {
						can = false;
						//break;
					}
				}
				if (can) posReel.add(reel);
			}
		}
		return posReel;
	}

	/**
	 * Cr��e une matrice contenant les positions relative par rapport au navire d'indicer par l'orientation du navire
	 * ex: Mat[dir] => positions que l'on peut tirer avec l'orientation dir
	 * @param zone == this.zoneTire : les positions que l'on peut tirer avec l'orientation du navire vers le Nord
	 * @return la matrice
	 */    
    private Map<Orientation,List<Position>> getMap(List<Position> zone){
    	Map<Orientation,List<Position>>  mapZone = new HashMap<Orientation,List<Position>> (); 
    	
    	for(Position pos : zone) {
    		for(int i=0; i<6;i++) {
    			Orientation dir = Orientation.values()[i];
    			List<Position> sousListe = mapZone.get(dir);
    			if(sousListe == null){
    				sousListe = new LinkedList<Position>();
    				mapZone.put(dir, sousListe);
    			}
    			if(i != 0) pos = rotation(pos);
    			sousListe.add(pos);
    		}
    	}
    	return mapZone;
    }
    
    private Position rotation(Position pos){
    	int x = pos.getX();
		int y = pos.getY();
		return new Position(-y, x+y);
    }
}
