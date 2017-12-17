package partie;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Vector;

import fenetre.FenetrePrincipale;

import List.*;


/**
 * 
 */
public class Partie extends Observable{
	public static void main(String [] args){
		
		/*================== Partie ====================*/
		
		String []nomJ = new String[2];
		nomJ[0] = "Joueur 1";
		nomJ[1] = "Joueur 2";
		Partie partie = new Partie(nomJ, 20, 20, 3, 25, null);
		
		/*================== Controleur ==================*/
		
		Controleur controleur = new Controleur(partie);
		
		/*=============== Fenetre =================*/
		
		FenetrePrincipale carte = new FenetrePrincipale();
		partie.addObserver(carte);
		
		/*================ Joueurs ================*/
		
		Joueur[] j = partie.getJoueurs();
		Joueur j1 = j[0];
		Joueur j2 = j[1];
		
		/*=============== Navire ================*/
		
		Vector<Navire> navs1 = j1.ajoutDefaultNavire(carte);
		Vector<Navire> navs2 = j2.ajoutDefaultNavire(carte);
		Navire Fregate1 = navs1.get(0);
		Navire Amiral1 = navs1.get(1);
		Navire Fregate2 = navs2.get(0);
		Navire Amiral2 = navs2.get(1);
		
		partie.initBateau(Position.getPosition(0, 1), Orientation.SE, Amiral1);
		partie.initBateau(Position.getPosition(1, 0), Orientation.SE, Fregate1);
		partie.initBateau(Position.getPosition(19, 9), Orientation.NO, Amiral2);
		partie.initBateau(Position.getPosition(18, 10), Orientation.NO, Fregate2);
		
		/*============ Zone Canon ====================*/
		
		List<Position> ZCAP = new LinkedList<Position>();
			ZCAP.add(new Position(0,-1));ZCAP.add(new Position(0,-2));
			ZCAP.add(new Position(0,-3));ZCAP.add(new Position(0,-4));
		List<Position> ZCAS = new LinkedList<Position>();
			ZCAS.add(new Position(0,-1));ZCAS.add(new Position(0,-2));
			ZCAS.add(new Position(1,-1));ZCAS.add(new Position(1,-2));
			ZCAS.add(new Position(-1,-1));ZCAS.add(new Position(-1,0));
		List<Position> ZCFP = new LinkedList<Position>();
			ZCFP.add(new Position(0,-1));ZCFP.add(new Position(0,-2));
			ZCFP.add(new Position(1,-1));ZCFP.add(new Position(1,0));
			ZCFP.add(new Position(-1,0));ZCFP.add(new Position(-1,1));
		List<Position> ZCFS = new LinkedList<Position>();
			ZCFS.add(new Position(0,-1));ZCFS.add(new Position(0,1));
			ZCFS.add(new Position(1,0));ZCFS.add(new Position(-1,0));
			ZCFS.add(new Position(1,-1));ZCFS.add(new Position(-1,1));
			
		/*================= Canon ====================*/
		
		Canons CAP1 = new Canons("Primaire", 50, 3, ZCAP, Amiral1);
		Canons CAS1 = new Canons("Secondaire", 30, 1, ZCAS, Fregate1);
		Canons CFP1 = new Canons("Primaire", 30, 1, ZCFP, Amiral1);
		Canons CFS1 = new Canons("Secondaire", 10, 0, ZCFS, Fregate1);
		Canons CAP2 = new Canons("Primaire", 50, 3, ZCAP, Amiral2);
		Canons CAS2 = new Canons("Secondaire", 30, 1, ZCAS, Fregate2);
		Canons CFP2 = new Canons("Primaire", 30, 1, ZCFP, Amiral2);
		Canons CFS2 = new Canons("Secondaire", 10, 0, ZCFS, Fregate2);
		
		Amiral1.addCanon(CAP1, CAS1);
		Amiral2.addCanon(CAP2, CAS2);
		Fregate1.addCanon(CFP1, CFS1);
		Fregate2.addCanon(CFP2, CFS2);
		
		/*================ Start ===========================*/
		partie.initTour();
		
	}
	
	public WheelList<Joueur> joueurs;
	private int nbJoueurs;
	public int numTour;
	public Plateau plateau;
	private Iterator<Joueur> iteratorJ;
	public Joueur currentJ;
	public Joueur[] listeJ;
	
	
    /**
     * Default constructor
     */
    public Partie(String []nomJ, int nX, int nY, int nbPhares, int nbRochers, Observer observeur) {
    	Position.initTabPosition(nX, nY);
    	if(observeur != null) {addObserver(observeur);}
    	plateau = new Plateau(nX, nY, nbPhares, nbRochers,observeur);
    	numTour = 1;
    	joueurs = new WheelList<Joueur>();
    	nbJoueurs = nomJ.length;
    	ajoutJoueurs(nomJ, nbJoueurs);
    	iteratorJ = joueurs.getIterator();
    	currentJ = iteratorJ.present();
    	initDefNavires(getJoueurs(),observeur);
    	initNavires();
    	
    }
    
    public Joueur[] getJoueurs() {
    	return listeJ;
    }
    
    private void ajoutJoueurs(String []nomJ, int nbJoueurs){
    	Joueur newJ = null;
    	listeJ = new Joueur[nbJoueurs];
    	for(int i=0; i<nbJoueurs; i++){
    		newJ = new Joueur(nomJ[i]);
    		joueurs.add(newJ);
    		listeJ[i]=newJ;
    	}
    }
    public String toString(){
    	return "Partie de "+nbJoueurs+" joueurs et de caractéristiques :\n"+joueurs.toString();
    }
    /**
     * 
     */
    public void initTour() {    	
    	numTour++; 
        currentJ = iteratorJ.next();
        currentJ.initTour();
        //Check si un joueur a gagné
        //Check et MAJ des compteurs du joueur courant
    }
    
    public boolean hasWinner(){
    	return true;
    }
    
	public void initBateau(Position p, Orientation o, Navire n){
		n.setDir(o);
		n.setPos(p);
		int x = p.getX();
		int y = p.getY() + (int) (p.getX()/2);
		plateau.getCases()[x][y].setEstOccupe(true);
		plateau.getCases()[x][y].setTakePosition(n);	
		plateau.getCases()[x][y].col = n.couleur;
	}
	
	private void initNavires() { //Seulement pour 2 navires � 2 joueurs
		Joueur[] jou = getJoueurs();
		
		Navire[] navs = jou[0].getNavires();
		initBateau(Position.getPosition(0, 0), Orientation.SE, navs[0]);
		initBateau(Position.getPosition(0, 1), Orientation.SE, navs[1]);
		
		navs = jou[1].getNavires();
		initBateau(Position.getPosition(plateau.getCases().length-1,(int) plateau.getCases()[0].length/2), Orientation.NO, navs[0]);
		initBateau(Position.getPosition(plateau.getCases().length-2, (int) plateau.getCases()[0].length/2), Orientation.NO, navs[1]);
	}
	
    
    public Plateau getPlateau(){
    	return plateau;
    }

	public Navire getNavOnPos(Position pos) {
		int i = 0;
		Navire nav = null;
		while((i<nbJoueurs) && (nav == null)) {
			nav = listeJ[i].getNavOnPos(pos);
			i=i+1;
		}
		return nav;
	}

	public Set<Position> getObstacle() {
		return plateau.getObstacle();
	}

	public Case getCaseOnPos(Position pos) {
		return plateau.getCases()[pos.getX()][pos.getY() + (int) pos.getX()/2];
	}

	public Joueur getCurrentJ() {
		return currentJ;
	}

	public void setCurrentJ(Joueur currentJ) {
		this.currentJ = currentJ;
	}
	
	public void surbrillanceO(Set<Position> s){
		plateau.surbrillanceO(s);
	}
	
	public void surbrillanceD(Set<Position> s) {
		plateau.surbrillanceD(s);
	}
	
	public void surbrillanceT(Set<Position> s) {
		plateau.surbrillanceT(s);
	}

	public void notifier(Object nav) {
		notifyObservers(nav);
		
	}

	public void ResetCouleur() {
		plateau.ResetCouleur();	
	}
	
	public void addObserveur(Observer obs) {
		addObserver(obs);
	}

	private void initDefNavires(Joueur[] j, Observer obs) {
		for(Joueur jou : j) {
			jou.ajoutDefaultNavire(obs);
		}
	}

}
