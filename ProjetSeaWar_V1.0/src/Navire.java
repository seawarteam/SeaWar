
import java.util.*;

/**
 * 
 */
public class Navire {

	public static void main(String [] args) {
		
		
		Navire unNavire = new Navire("Charles De Gaulle", 1000, 4, "France", Orientation.NE, new Position(0,0));
		
		List<Position> zone = new LinkedList<Position>();
		zone.add(new Position(0,1));
		zone.add(new Position(0,2));
		zone.add(new Position(0,3));
		zone.add(new Position(0,4));
		zone.add(new Position(0,5));
		zone.add(new Position(0,6));
		zone.add(new Position(0,7));
		Canons unCanon = new Canons("La Grosse Berta", 200, 2, zone, unNavire);
		
		/*	
			unNavire.addCanon(unCanon);
			System.out.println(unCanon.getZoneTire().toString());
			System.out.println(unCanon.posCanShoot().toString());
		*/	
		
		//System.out.println(caseVoisineXPaire.toString());
		unNavire.initTour();
		System.out.println(unNavire.getCaseAccessible(new LinkedHashSet<Position>()).toString());
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
	 private static Map<Orientation,List<Vector<Object>>> caseVoisineXPaire;
	 private static Map<Orientation,List<Vector<Object>>> caseVoisineXImpaire;
	
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
    	initCaseVoisineXImpaire();
    	initCaseVoisineXPaire();
    	
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
     * Mets à jour les variables d'instances lors d'un déplacement
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
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
    
    public Map<Position,Set<Orientation>> getCaseAccessible(Set<Position> obstacle){
    	List<Vector<Object>> fileDattente = new LinkedList<Vector<Object>>();
    			Vector<Object> v = new Vector<Object>(2);
    				v.add(0, this.pos);
    				v.add(1, this.dir);
    			fileDattente.add(v);
    	int deplace = this.dep;
		System.out.println(""+this.depMax);
    	Map<Position,Set<Orientation>> map = new HashMap<Position,Set<Orientation>>();
    	_getNextCaseAcc(map, deplace, fileDattente, obstacle);
    	return map;
    	
    }
    
    /**
     * Construction par récursion des cases Accessibles
     * @param map
     * @param deplace : nbre de deplacement encore possible
     * @param fileDattente : dernières cases atteintes
     */
    private void _getNextCaseAcc( Map<Position,Set<Orientation>> map, int deplace, List<Vector<Object>> fileDattente, Set<Position> obstacle){
    	if(deplace > 0){
    		List<Vector<Object>> prochaineFileDattente = new LinkedList<Vector<Object>>();
    		for(Vector<Object> vect : fileDattente){
    			
    			Position cell = (Position) vect.elementAt(0);
    			Orientation ori = (Orientation) vect.elementAt(1);
    			List<Vector<Object>> voisinsRela;
    			if(cell.getX()%2 == 0){
    				voisinsRela = Navire.caseVoisineXPaire.get(ori);
    			} else {
    				voisinsRela = Navire.caseVoisineXImpaire.get(ori);
    			}
    			for(Vector<Object> vectVoisinRela : voisinsRela){
    				Position cellVoisinRela = (Position) vectVoisinRela.elementAt(0);
    				Orientation DirVoisinRela = (Orientation)vectVoisinRela.elementAt(1);
    				Position cellVoisinReel = new Position(cell.getX()+cellVoisinRela.getX(),cell.getY()+cellVoisinRela.getY());//TODO:Check si la position existe !!! => le navire connait les tailles max de la carte 
    				if(!obstacle.contains(cellVoisinReel) || cellVoisinReel.equals(this.pos)){//TODO:vérifier que le contains ce fait bien avec la méthode equals et non avec @ => reponse: il le fait avec @ => faire un getPosition dans Position
    					Vector<Object> v = new Vector<Object>(2);
    						v.add(0,cellVoisinReel);
    						v.add(1, DirVoisinRela);
    					prochaineFileDattente.add(v);
    					// Ajouter les coordonnées dans la map
    					if(map.containsKey(cellVoisinReel)){
    						Set<Orientation> s = map.get(cellVoisinReel);
    						s.add(DirVoisinRela);
    					} else {
    						Set<Orientation> s = new LinkedHashSet<Orientation>();
    							s.add(DirVoisinRela);
    						map.put(cellVoisinReel, s);
    					}
    				}
    			}
    		}
    		_getNextCaseAcc(map, deplace-1, prochaineFileDattente, obstacle);
    	}
    }
    
    
    /**
     * Remplit la Map des voisins avec pour clé l'orientation du Navire pour les cases X impaires
     */
    private static void initCaseVoisineXImpaire(){//hypothèse : L'origine est en bas à gauche, l'axe des x va vers la droite et l'axe des y est vers le haut
    	Navire.caseVoisineXImpaire = new HashMap<Orientation,List<Vector<Object>>>();
    	
    	List<Vector<Object>> Nord = new LinkedList<Vector<Object>>();
    		Vector<Object> vect = new Vector<Object>(2);
    			vect.add(0, new Position(0,1));
    			vect.add(1, Orientation.N);
    		Nord.add(vect);
    		vect = new Vector<Object>(2);
    			vect.add(0, new Position(-1,1));
    			vect.add(1, Orientation.NO);
    		Nord.add(vect);
        	vect = new Vector<Object>(2);
        		vect.add(0, new Position(1,1));
        		vect.add(1, Orientation.NE);
        	Nord.add(vect);
    	Navire.caseVoisineXImpaire.put(Orientation.N,Nord);
    	
    	List<Vector<Object>> NordEst = new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,1));
				vect.add(1, Orientation.NE);
			NordEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,1));
				vect.add(1, Orientation.N);
			NordEst.add(vect);
			vect = new Vector<Object>(2);
    			vect.add(0, new Position(1,0));
    			vect.add(1, Orientation.SE);
    		NordEst.add(vect);
    	Navire.caseVoisineXImpaire.put(Orientation.NE,NordEst);
    	
    	List<Vector<Object>> SudEst = new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,0));
				vect.add(1, Orientation.SE);
			SudEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			SudEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,1));
				vect.add(1, Orientation.NE);
			SudEst.add(vect);
		Navire.caseVoisineXImpaire.put(Orientation.SE,SudEst);
		
		List<Vector<Object>> Sud= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			Sud.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,0));
				vect.add(1, Orientation.SE);
			Sud.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,0));
				vect.add(1, Orientation.SO);
			Sud.add(vect);
		Navire.caseVoisineXImpaire.put(Orientation.S,Sud);
		
		List<Vector<Object>> SudOuest= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,0));
				vect.add(1, Orientation.SO);
			SudOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			SudOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,1));
				vect.add(1, Orientation.NO);
			SudOuest.add(vect);
		Navire.caseVoisineXImpaire.put(Orientation.SO,SudOuest);
		
		List<Vector<Object>> NordOuest= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,1));
				vect.add(1, Orientation.NO);
			NordOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.SO);
			NordOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,1));
				vect.add(1, Orientation.N);
			NordOuest.add(vect);
		Navire.caseVoisineXImpaire.put(Orientation.NO,NordOuest);
    	
    }
    
    /**
     * Remplit la Map des voisins avec pour clé l'orientation du Navire pour les cases X paires
     */
    private static void initCaseVoisineXPaire(){//hypothèse : L'origine est en bas à gauche, l'axe des x va vers la droite et l'axe des y est vers le haut
    	Navire.caseVoisineXPaire = new HashMap<Orientation,List<Vector<Object>>>();
    	
    	List<Vector<Object>> Nord = new LinkedList<Vector<Object>>();
    		Vector<Object> vect = new Vector<Object>(2);
    			vect.add(0, new Position(0,1));
    			vect.add(1, Orientation.N);
    		Nord.add(vect);
    		vect = new Vector<Object>(2);
    			vect.add(0, new Position(-1,0));
    			vect.add(1, Orientation.NO);
    		Nord.add(vect);
        	vect = new Vector<Object>(2);
        		vect.add(0, new Position(1,0));
        		vect.add(1, Orientation.NE);
        	Nord.add(vect);
    	Navire.caseVoisineXPaire.put(Orientation.N,Nord);
    	
    	List<Vector<Object>> NordEst = new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,0));
				vect.add(1, Orientation.NE);
			NordEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,1));
				vect.add(1, Orientation.N);
			NordEst.add(vect);
			vect = new Vector<Object>(2);
    			vect.add(0, new Position(1,-1));
    			vect.add(1, Orientation.SE);
    		NordEst.add(vect);
    	Navire.caseVoisineXPaire.put(Orientation.NE,NordEst);
    	
    	List<Vector<Object>> SudEst = new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,-1));
				vect.add(1, Orientation.SE);
			SudEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			SudEst.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,0));
				vect.add(1, Orientation.NE);
			SudEst.add(vect);
		Navire.caseVoisineXPaire.put(Orientation.SE,SudEst);
		
		List<Vector<Object>> Sud= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			Sud.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(1,-1));
				vect.add(1, Orientation.SE);
			Sud.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,-1));
				vect.add(1, Orientation.SO);
			Sud.add(vect);
		Navire.caseVoisineXPaire.put(Orientation.S,Sud);
		
		List<Vector<Object>> SudOuest= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,-1));
				vect.add(1, Orientation.SO);
			SudOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.S);
			SudOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,0));
				vect.add(1, Orientation.NO);
			SudOuest.add(vect);
		Navire.caseVoisineXPaire.put(Orientation.SO,SudOuest);
		
		List<Vector<Object>> NordOuest= new LinkedList<Vector<Object>>();
			vect = new Vector<Object>(2);
				vect.add(0, new Position(-1,0));
				vect.add(1, Orientation.NO);
			NordOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,-1));
				vect.add(1, Orientation.SO);
			NordOuest.add(vect);
			vect = new Vector<Object>(2);
				vect.add(0, new Position(0,1));
				vect.add(1, Orientation.N);
			NordOuest.add(vect);
		Navire.caseVoisineXPaire.put(Orientation.NO,NordOuest);
    	
    }

	

}