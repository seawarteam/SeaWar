package partie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Lanceur extends Observable {
	private Plateau map;
	private ArrayList<Joueur> joueurs;
	private int nbJoueurs = 0;
	private Observer obs;
	private Color[] tab = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE,Color.GRAY, Color.DARK_GRAY };
	private int indexCouleur = 0;
	
	public Lanceur(Observer obs) {
		map = null;
		joueurs = new ArrayList<Joueur>();
		addObserver(obs);
	}
	
	private Color couleurJoueur() {
		indexCouleur++;
		if(indexCouleur>=6) {
			indexCouleur=0;
		}
		return tab[indexCouleur];
	}
	
	public Plateau getMap() {
		return map;
	}
	
	public ArrayList<Joueur> getJoueurs(){
		return joueurs;
	}
	public void addJoueur(String s, Navire n, Canons canonP, Canons canonS, Navire n2, Canons canonP2, Canons canonS2) {
		Joueur j = new Joueur(s, couleurJoueur(), n, canonP, canonS, n2, canonP2, canonS2);
		joueurs.add(j);
		nbJoueurs++;
		//Vérifier que le nom de joueur est non présent
		setChanged();
		notifyObservers(j);
		clearChanged();
		
	}
	
	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public void addMap(Plateau p) {
		nbJoueurs = 0;
		joueurs = new ArrayList<Joueur>();
		map = p;
		setChanged();
		notifyObservers(map);
		clearChanged();
		
		
	}
}