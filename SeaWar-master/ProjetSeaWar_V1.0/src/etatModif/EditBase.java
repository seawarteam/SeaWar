package etatModif;

import java.awt.Color;
import java.util.ArrayList;
import partie.Case;
import partie.ControleurModif;
//import partie.EditCanonS;
import partie.Joueur;
import partie.Phare;
import partie.Position;
import partie.Rocher;

public class EditBase implements EtatModif {
	private static EditBase etat = new EditBase();
	private Joueur currentJ;
	private ArrayList<Position> positions;
	private int nbPosition;
	private final int maxPositions = 2;
	private ArrayList<Joueur> joueurs;
	
	private EditBase(){
		super();
		nbPosition = 0;
		currentJ = new Joueur("Joueur1", couleur(0));
		positions = new ArrayList<Position>();
		initJoueurs();
		
		
	}
	
	
	private Color couleur(int i) {
		Color[] tab = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.GRAY, Color.DARK_GRAY};
		return tab[i];
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	private void initJoueurs() {
		joueurs = new ArrayList<Joueur>();
		Joueur j;
		for(int i=1; i<=6; i++) {
			j = new Joueur("Joueur"+i, couleur(i-1));
			//j.setColorBase(255, 100, 30*i);
			joueurs.add(j);
		}
	}
	
	public void clique(Position p, ControleurModif c) {
		Case ca;
		if(nbPosition<maxPositions) {
			ca = c.getEditeur().getMap().getCase(p);
			if(!(ca instanceof Rocher) && !(ca instanceof Phare)) {
				positions.add(p);
				nbPosition++;	
				c.getEditeur().getMap().setColorBase(p, currentJ);
			}
			
			
		}
		
	}
	
	
	public void finalizeBases(ControleurModif c) {
		c.getEditeur().getMap().addBases(currentJ, positions);
	}
		
	public void modifMap(ControleurModif c) {
		finalizeBases(c);
		c.setEtat(Init.getEtat());
	}

	public void setCurrentJ(String str) {
		nbPosition = 0;
		positions = new ArrayList<Position>();
		currentJ = (Joueur)(getJoueur(str));
		
	}
	
	private Joueur getJoueur(String str) {
		Joueur j = null;
		int i = 0;
		while(j == null) {
			if(((Joueur)(joueurs.get(i))).getNom().equals(str)){
				j = (Joueur)joueurs.get(i);
			}else {
				i++;
			}
		}
		return j;
	}


	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
		
	}


	
	public void modifCanonS(ControleurModif c) {
		c.setEtat(EditCanonS.getEtat());
		
	}


	

}
