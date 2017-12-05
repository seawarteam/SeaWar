
import java.util.*;

/**
 * 
 */
public class Navire {

	public static void main(String [] args) {
		
		Position.initTabPosition(10, 10);
		
		Navire unNavire = new Navire("Charles De Gaulle", 1000, 2, "France", Orientation.S, new Position(2,1));
		
		List<Position> zone = new LinkedList<Position>();
		zone.add(new Position(0,-1));
		zone.add(new Position(0,-2));
		zone.add(new Position(0,-3));
		zone.add(new Position(0,-4));
		zone.add(new Position(0,-5));
		zone.add(new Position(0,-6));
		zone.add(new Position(0,-7));
		zone.add(new Position(0,-8));
		Canons unCanon = new Canons("La Grosse Berta", 200, 2, zone, unNavire);
		unNavire.addCanon(unCanon);
		//System.out.println(unCanon.getZoneTire().toString());
		//System.out.println(unCanon.posCanShoot().toString());
			
		
		//System.out.println(caseVoisineXPaire.toString());
		unNavire.initTour();
		LinkedHashSet<Position> obstacles = new LinkedHashSet<Position>();
		obstacles.add(Position.getPosition(3, 1));
		System.out.println(unNavire.getCaseAccessible(obstacles).toString());
		
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
	 /*TODO: déplacer dans la Classe Plateau ???*/
	 private static Map<Orientation,List<Vector<Object>>> caseVoisine;
	
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
    	initCaseVoisine();
    	
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
    	
    	this.dep -= nbCase;
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
    
    
    /**
     * 
     * @param obstacle : Set des positions où le navire ne peux pas aller
     * @return Map avec pour clé une position accessible et valeur la liste des orientation possible pour cette position
     */
    public Map<Position,Set<Vector<Object>>> getCaseAccessible(Set<Position> obstacle){//TODO: Calculer la distance en même temps !
    	List<Vector<Object>> fileDattente = new LinkedList<Vector<Object>>();
    			Vector<Object> v = new Vector<Object>(2);
    				v.add(0, this.pos);
    				v.add(1, this.dir);
    			fileDattente.add(v);
    	int deplace = 0;
    	Map<Position,Set<Vector<Object>>> map = new HashMap<Position,Set<Vector<Object>>>();
    	_getNextCaseAcc(map, deplace, fileDattente, obstacle);
    	return map;
    }
    
    /**
     * Construction par récursion des cases Accessibles
     * @param map
     * @param deplace : nbre de deplacement encore possible
     * @param fileDattente : dernières cases atteintes
     */
    private void _getNextCaseAcc( Map<Position,Set<Vector<Object>>> map, int deplace, List<Vector<Object>> fileDattente, Set<Position> obstacle){
    	if(deplace < this.dep){
    		List<Vector<Object>> prochaineFileDattente = new LinkedList<Vector<Object>>();
    		for(Vector<Object> vect : fileDattente){
    			
    			Position cell = (Position) vect.elementAt(0);
    			Orientation ori = (Orientation) vect.elementAt(1);
    			List<Vector<Object>> voisinsRela;
    			voisinsRela = Navire.caseVoisine.get(ori);
    			for(Vector<Object> vectVoisinRela : voisinsRela){
    				Position cellVoisinRela = (Position) vectVoisinRela.elementAt(0);
    				Orientation DirVoisinRela = (Orientation)vectVoisinRela.elementAt(1);
    				Position cellVoisinReel = Position.getPosition(cell.getX()+cellVoisinRela.getX(),cell.getY()+cellVoisinRela.getY());
    				if(cellVoisinReel != null && (!obstacle.contains(cellVoisinReel) || cellVoisinReel.equals(this.pos))){
    					Vector<Object> v = new Vector<Object>(2);
    						v.add(0,cellVoisinReel);
    						v.add(1, DirVoisinRela);
    					prochaineFileDattente.add(v);
    					Vector <Object> vectInfo = new Vector<Object>(3);
							vectInfo.add(0, DirVoisinRela);
							vectInfo.add(1, deplace);
							vectInfo.add(2, vect);
    					// Ajouter les coordonnées dans la map
    					if(map.containsKey(cellVoisinReel)){
    						Set<Vector<Object>> s = map.get(cellVoisinReel);
    						s.add(vectInfo);
    					} else {
    						Set<Vector<Object>> s = new LinkedHashSet<Vector<Object>>();
    							s.add(vectInfo);
    						map.put(cellVoisinReel, s);
    					}
    				}
    			}
    		}
    		System.out.println(deplace);
    		_getNextCaseAcc(map, ++deplace, prochaineFileDattente, obstacle);
    	}
    }
    
        
    /**
     * Remplit la Map des voisins avec pour clé l'orientation du Navire pour les cases
     * Les case voisines sont initialisés pour une case de position (0,0). Pour trouver les case suivantes pour une position (x, y) il faut appliquer la translation.
     */
    private static void initCaseVoisine(){
		Navire.caseVoisine = new HashMap<Orientation,List<Vector<Object>>>();
		//A chaque orientation correspond un vecteur (Position, Orientation)
		Vector<Object> vectN = new Vector<Object>(2);
		vectN.add(0, new Position(0,-1));
		vectN.add(1, Orientation.N);
		
		Vector<Object> vectNO = new Vector<Object>(2);
		vectNO.add(0, new Position(-1,0));
		vectNO.add(1, Orientation.NO);
		
		Vector<Object> vectNE = new Vector<Object>(2);
		vectNE.add(0, new Position(1,-1));
		vectNE.add(1, Orientation.NE);
		
		Vector<Object> vectS = new Vector<Object>(2);
		vectS.add(0, new Position(0,1));
		vectS.add(1, Orientation.S);
		
		Vector<Object> vectSO = new Vector<Object>(2);
		vectSO.add(0, new Position(-1,1));
		vectSO.add(1, Orientation.SO);
		
		Vector<Object> vectSE = new Vector<Object>(2);
		vectSE.add(0, new Position(1,0));
		vectSE.add(1, Orientation.SE);
		
		
		List<Vector<Object>> Nord = new LinkedList<Vector<Object>>();
		Nord.add(vectN);
		Nord.add(vectNO);
		Nord.add(vectNE);
		
		List<Vector<Object>> NordOuest = new LinkedList<Vector<Object>>();
		NordOuest.add(vectN);
		NordOuest.add(vectNO);
		NordOuest.add(vectSO);
		
		List<Vector<Object>> NordEst = new LinkedList<Vector<Object>>();
		NordEst.add(vectN);
		NordEst.add(vectNE);
		NordEst.add(vectSE);
		
		List<Vector<Object>> Sud = new LinkedList<Vector<Object>>();
		Sud.add(vectS);
		Sud.add(vectSO);
		Sud.add(vectSE);
		
		List<Vector<Object>> SudOuest = new LinkedList<Vector<Object>>();
		SudOuest.add(vectS);
		SudOuest.add(vectNO);
		SudOuest.add(vectSO);
		
		List<Vector<Object>> SudEst = new LinkedList<Vector<Object>>();
		SudEst.add(vectS);
		SudEst.add(vectSE);
		SudEst.add(vectNE);
		
		Navire.caseVoisine.put(Orientation.N,Nord);
		Navire.caseVoisine.put(Orientation.NO,NordOuest);
		Navire.caseVoisine.put(Orientation.NE,NordEst);
		Navire.caseVoisine.put(Orientation.S,Sud);
		Navire.caseVoisine.put(Orientation.SO,SudOuest);
		Navire.caseVoisine.put(Orientation.SE,SudEst);
		
	}
	
	
    
    
   
    
}