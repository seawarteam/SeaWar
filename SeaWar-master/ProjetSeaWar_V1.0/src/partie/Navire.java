package partie;

import java.awt.Color;
import java.util.*;

import etat.EtatDeplacement;

/**
 * 
 */
public class Navire extends Observable{	
	
	public static void main (String [] args) {
		Position.initTabPosition(10, 10);
		Navire nav = new Navire("bateau", 20, 1, "Louis", Orientation.S, Position.getPosition(1, 1));
		
		List<Position> zoneP = new LinkedList<Position>();
		zoneP.add(new Position(0,-1));zoneP.add(new Position(0,-2));
		/*zoneP.add(new Position(0,-3));zoneP.add(new Position(0,-4));
		zoneP.add(new Position(0,-5));*/
		Canons canonP = new Canons("canonP", 50, 2, zoneP, nav);
		
		List<Position> zoneS = new LinkedList<Position>();
		zoneS.add(new Position(0,-1));zoneS.add(new Position(0,1));
		zoneS.add(new Position(1,0));zoneS.add(new Position(-1,0));
		zoneS.add(new Position(1,-1));zoneS.add(new Position(-1,1));
		Canons canonS = new Canons("canonS", 20, 2, zoneS, nav);
	
		
		nav.addCanon(canonP, canonS);
		nav.initTour();
		
		
		nav.afficherCasesAccessibles(new HashSet<Position>());
		nav.findOrientationsPossibles(Position.getPosition(2, 1), new HashSet<Position>());
		System.out.println(nav.affichageCaseAccessible.toString());
		System.out.println(nav.orientationsPossibles.toString());
	}
	
	
	 public Color couleur = new Color(254, 45, 87);
	
	 private String nom;
	 private int pv;
	 private int depMax;
	 private String nomJ;
	 private Orientation dir;
	 private Position pos;
	 //private Set<Canons> canons;
	 private Canons canonP;
	 private Canons canonS;
	 private EtatDeplacement etatCourant;
	 private boolean aDejaTire;
	 private boolean aEteDeplace;
	 private int dep;
	 private int nb_coup_recu;
	 private Set<Position> affichageCaseAccessible;
	 private Set<Position> casesVisableCanonP;
	 private Set<Position> casesVisableCanonS;
	 private Set<Orientation> orientationsPossibles;
	 /*TODO: déplacer dans la Classe Plateau ???*/
	 private static Map<Orientation,List<Vector<Object>>> caseVoisine;
	
    /**
     * Default constructor
     */
    public Navire(String n, int vie, int depM, String nameJ, Orientation ori, Position posi) {
    	this.nom = n;
    	this.pv = vie;
    	this.depMax = depM;
    	this.nomJ = nameJ;
    	this.dir = ori;
    	this.pos = posi;
    	
    	/* affichageCaseAccessible = new HashSet<Position>();
    	 casesVisableCanonP = new HashSet<Position>();
    	 casesVisableCanonS = new HashSet<Position>();*/
    	 
    	 
    	//this.canons = new HashSet<Canons>();
    	initCaseVoisine();//TODO: initialiser en dehors de navire => pour le faire qu'une seule fois 
    	
    }
    public void addCanon(Canons P, Canons S){
    	this.canonS = S;
    	this.canonP = P;
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
    public EtatDeplacement getEtatCourant() {
    	return etatCourant;
    }
    public boolean getADejaTire() {
    	return aDejaTire;
    }
    
    public Set<Position> getAffichageCaseAccessible() {
    	return this.affichageCaseAccessible;
    }
    
    public Set<Position> getCasesVisableCanonP() {
    	return this.casesVisableCanonP;
    }
    
    public Set<Position> getCasesVisableCanonS() {
    	return this.casesVisableCanonS;
    }
    public Set<Orientation> getOrientationsPossible() {
		return orientationsPossibles;
	}
    
    public void initTour() {
    	//TODO: changer les états
    	this.dep = this.depMax;
    	this.nb_coup_recu = 0;
    	/*for(Canons canon : CanonS){
    		canon.initTour();
    	}*/
    	if(canonP != null) canonP.initTour();
    	if(canonS != null) canonS.initTour();
    }
    
    public void setEtat(EtatDeplacement e){
    	etatCourant = e;
    }
    
    public void setADejaTire(boolean e){
    	aDejaTire = e;
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
    	if(this==null) return "";
    	return "Navire\tnom = "+this.nom+"\tpv = "+this.pv+"\tdepMax = "+this.depMax+"\tnomJ = "+this.nomJ+"\tdir = "+this.dir+"\tpos = "+this.pos.toString()+"\n";
    }
   
    /**
     * 
     * @param canon avec lequel on veut tirer
     * @param pos : position de la case visée
     * @return succès/echec
     */
    public boolean tir(Canons canon, Position pos, Navire current) {
    	
    	return etatCourant.tir(canon, pos, current, this);
    	
    }
    
    
    /**
     * Mets à jour les variables d'instances lors d'un déplacement
     * @param pos : la case où on veut aller 
     * @param dir : l'orientation que l'on veut avoir 
     */
    public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire current) {
    	return etatCourant.deplacement(pos, dir, nbCase, current, this);
    }

    /**
     * @param degats à retirer aux pv du navire
     */
    public void toucher(int degats) {
    	this.nb_coup_recu++;        
        if(this.nb_coup_recu == 2) {// ou >1 ? => Hyp: plus que 2 navires
        	this.pv -= 3*degats; 
        } else {
        	this.pv -= degats;
        }
    }
    
     
    

    public void getInfo() {
    	notifyObservers(this); //TODO:
    }
    
    
    
    /*TODO: Stocker dans une variable d'instance le résultat de la fonction : "getCaseAccessible(obstacle)"
    *	+ créer un booléen pour savoir si le résultat est à jour
    *	=> but : limité l'appel de la fonction dans les 4 fonctions ci-dessous
    **/
    public void afficherCasesAccessibles(Set<Position> obstacle) {
    	Map<Position,Set<Vector<Object>>> MapCases = getCaseAccessible(obstacle);
    	this.affichageCaseAccessible = MapCases.keySet();
    	notifyObservers(this.affichageCaseAccessible);// TODO:
    }
    
    public void afficherCasesVisableCanonP(Set<Position> obstacle){
    	Map<Position,Set<Vector<Object>>> mapCasesAcc = getCaseAccessible(obstacle);
    	Set<Position> resultat = new HashSet<Position>();
    	
    	Set<Position> posKey = mapCasesAcc.keySet();
    	for(Position pos : posKey) {
    		Set<Vector<Object>> SetVect = mapCasesAcc.get(pos);
    		for(Vector<Object> vect : SetVect) {
    			Orientation dir = (Orientation) vect.get(0);
    			List<Position> cellCible = this.canonP.posCanShoot(dir, pos);
    			for(Position cell : cellCible){
    				resultat.add(cell);
    			}
    		}
    	}
    	//	Ajout des cases que l'on peux toucher sans se déplacer
    	List<Position> cellCible = this.canonP.posCanShoot(this.getDir(), this.getPos());
		for(Position cell : cellCible){
			resultat.add(cell);
		}
    	this.casesVisableCanonP = resultat;
    	notifyObservers(this.casesVisableCanonP);// TODO:
    }
    
    public void afficherCasesVisableCanonS(Set<Position> obstacle){
    	Map<Position,Set<Vector<Object>>> mapCasesAcc = getCaseAccessible(obstacle);
    	Set<Position> resultat = new HashSet<Position>();
    	
    	Set<Position> posKey = mapCasesAcc.keySet();
    	for(Position pos : posKey) {
    		Set<Vector<Object>> SetVect = mapCasesAcc.get(pos);
    		for(Vector<Object> vect : SetVect) {
    			Orientation dir = (Orientation) vect.get(0);
    			List<Position> cellCible = this.canonS.posCanShoot(dir, pos);
    			for(Position cell : cellCible){
    				resultat.add(cell);
    			}
    		}
    	}
//    	Ajout des cases que l'on peux toucher sans se déplacer
    	List<Position> cellCible = this.canonS.posCanShoot(getDir(), getPos());
		for(Position cell : cellCible){
			resultat.add(cell);
		}
    	this.casesVisableCanonS = resultat;
    	notifyObservers(this.casesVisableCanonP);// TODO:
    }
    
    public void findOrientationsPossibles(Position pos, Set<Position> obstacle) {
    	Set<Vector<Object>> setVectPos = getCaseAccessible(obstacle).get(pos);
    	Set<Orientation> resultat = new HashSet<Orientation>();
    	
	    if(setVectPos != null) {
	    	for(Vector<Object> vect : setVectPos) {
	    		Orientation dir = (Orientation) vect.get(0);
	    		resultat.add(dir);
	    	}
	    	this.orientationsPossibles = resultat;
	    } else {
	    	this.orientationsPossibles = null;
	    }
	    notifyObservers(this.orientationsPossibles);// TODO:
    }
    
    
    /**
     * 
     * @param obstacle : Set des positions où le navire ne peux pas aller
     * @return Map avec pour clé une position accessible et valeur la liste des orientation possible pour cette position
     */
    public Map<Position,Set<Vector<Object>>> getCaseAccessible(Set<Position> obstacle){
    	List<Vector<Object>> fileDattente = new LinkedList<Vector<Object>>();
    			Vector<Object> v = new Vector<Object>(2);
    				v.add(0, this.pos);
    				v.add(1, this.dir);
    			fileDattente.add(v);
    	int deplace = 0;
    	Map<Position,Set<Vector<Object>>> map = new HashMap<Position,Set<Vector<Object>>>();
    	_getNextCaseAcc(map, ++deplace, fileDattente, obstacle);
    	return map;
    }
    
    /**
     * Construction par récursion des cases Accessibles
     * @param map
     * @param deplace : nbre de deplacement encore possible
     * @param fileDattente : dernières cases atteintes
     */
    private void _getNextCaseAcc( Map<Position,Set<Vector<Object>>> map, int deplace, List<Vector<Object>> fileDattente, Set<Position> obstacle){
    	if(deplace <= this.dep){
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
	public Orientation getDir() {
		return dir;
	}
	public void setDir(Orientation dir) {
		this.dir = dir;
	}
	public int getDep() {
		return dep;
	}
	public void setDep(int dep) {
		this.dep = dep;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public boolean getaEteDeplace() {
		return aEteDeplace;
	}
	public void setaEteDeplace(boolean aEteDeplace) {
		this.aEteDeplace = aEteDeplace;
	}
	public Canons getCanonS() {
		return canonS;
	}
	public Canons getCanonP() {
		return canonP;
	}
	
	
	
	
    
}