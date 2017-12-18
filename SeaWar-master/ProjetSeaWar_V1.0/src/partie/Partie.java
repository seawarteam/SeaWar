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
    public Partie(String []nomJ, int nX, int nY) {
    	//Position.initTabPosition(nX, nY);
    	//if(observeur != null) {addObserver(observeur);}
    	//plateau = new Plateau(nX, nY, nbPhares, nbRochers,observeur);
    	numTour = 1;
    	joueurs = new WheelList<Joueur>();
    	nbJoueurs = nomJ.length;
    	ajoutJoueurs(nomJ, nbJoueurs);
    	iteratorJ = joueurs.getIterator();
    	currentJ = iteratorJ.present();/*
    	initDefNavires(getJoueurs(),observeur);
    	initNavires();*/
    	
    }
    
    public void initPartie(int nX, int nY, int nbPhares, int nbRochers, Observer observeur) {
    	addObserver(observeur);
    	plateau = new Plateau(nX, nY, nbPhares, nbRochers,observeur);
    	
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
        setChanged();
        notifyObservers(this);
        clearChanged();
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
		initBateau(Position.getPosition(1, 1), Orientation.SE, navs[0]);
		initBateau(Position.getPosition(1, 2), Orientation.SE, navs[1]);
		
		navs = jou[1].getNavires();
		initBateau(Position.tabPosition[(plateau.getCases().length-2)*(plateau.getCases()[0].length)+ plateau.getCases()[0].length-2],Orientation.NO, navs[0]);
		initBateau(Position.tabPosition[(plateau.getCases().length-3)*(plateau.getCases()[0].length)+ plateau.getCases()[0].length-2], Orientation.NO, navs[1]);
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

	public Joueur getProprio(Navire nav) {
		int i = 0;
		while((i<nbJoueurs) && (nav == null)) {
			Navire[] navs = listeJ[i].getNavires();
			for(Navire n : navs) {
				if(n == nav) {
					return listeJ[i];
				}
			}
			
			i=i+1;
		}
		return null;
	}

}
