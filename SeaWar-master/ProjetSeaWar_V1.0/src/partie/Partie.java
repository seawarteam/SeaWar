package partie;
import java.awt.Point;
import java.util.Set;

import List.*;
import mvc.Observable;
import mvc.Observer;

/**
 * 
 */
public class Partie extends Observable{
	public static void main(String [] args){
		
	}
	
	public WheelList<Joueur> joueurs;
	private int nbJoueurs;
	public int numTour;
	public Plateau plateau;
	private Iterator<Joueur> iteratorJ;
	public Joueur currentJ;
	
	
    /**
     * Default constructor
     */
    public Partie(String []nomJ, int nbJ, int nX, int nY, int nbPhares, int nbRochers, mvc.Observer carte) {
    	Position.initTabPosition(nX, nY);
    	addObserver(carte);
    	plateau = new Plateau(nX, nY, nbPhares, nbRochers);
    	numTour = 1;
    	nbJoueurs = nbJ;
    	joueurs = new WheelList<Joueur>();
    	ajoutJoueurs(nomJ, nbJ);
    	iteratorJ = joueurs.getIterator();
    	currentJ = iteratorJ.present();
    	//Init Plateau
    	initBateau(new Position(0,0), Orientation.SE, new Navire("", 0, 0, "", Orientation.NE, new Position(0, 0)));
    	
    }
    
    private void ajoutJoueurs(String []nomJ, int nbJoueurs){
    	Joueur newJ = null;
    	for(int i=0; i<nbJoueurs; i++){
    		newJ = new Joueur(nomJ[i]);
    		joueurs.add(newJ);
 
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
	
    
    public Plateau getPlateau(){
    	return plateau;
    }

	public Navire getNavOnPos(Position pos) {
		//TODO: à Tester !
		int i = 0;
		Navire nav = null;
		while(i<= nbJoueurs && nav == null) {
			nav = iteratorJ.next().getNavOnPos(pos);
		}
		return nav;
	}

	public Set<Position> getObstacle() {
		// TODO Auto-generated method stub
		return null;
	}

	public Case getCaseOnPos(Position pos) {
		Case [][] c = plateau.getCases();
		return c[pos.getX()][pos.getY()+(int)pos.getX()/2];
		
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

	

}
