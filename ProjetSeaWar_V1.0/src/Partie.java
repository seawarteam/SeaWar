import List.*;
import List.Iterator;

import java.util.*;

/**
 * 
 */
public class Partie {
	public static void main(String [] args){
		String[]nomJ = {"Peter", "Alain", "Eric"};
		Partie p = new Partie(nomJ, 3);
		System.out.println(p.toString());
		for(int i=1; i<=6; i++){
			System.out.println("Joueur "+i);
			p.initTour();
		}
		
		
	}
	
	public WheelList<Joueur> joueurs;
	private int nbJoueurs;
	public int numTour;
	public Plateau plateau;
	private Iterator<Joueur> iteratorJ;
	private Joueur currentJ;
	
	
    /**
     * Default constructor
     */
    public Partie(String []nomJ, int nbJ) {
    	numTour = 1;
    	nbJoueurs = nbJ;
    	joueurs = new WheelList<Joueur>();
    	ajoutJoueurs(nomJ, nbJ);
    	iteratorJ = joueurs.getIterator();
    	currentJ = iteratorJ.present();
    	//Init Plateau
    	
    }
    
    private void ajoutJoueurs(String []nomJ, int nbJoueurs){
    	Joueur newJ = null;
    	for(int i=0; i<nbJoueurs; i++){
    		newJ = new Joueur(nomJ[i]);
    		
    		joueurs.add(newJ);
    		//joueurs.printAll();
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
        System.out.println(currentJ.toString());
        currentJ = iteratorJ.next();
        
        //Check si un joueur a gagné
        //Check et MAJ des compteurs du joueur courant
    }
	
	
    public void hexClique(int x, int y){
    	// TODO l'hexagone (x,y) a été cliqué, il faut faire qqch...
    	
    }
	
    public Joueur getJoueur() {
    	return currentJ;
    }


}
