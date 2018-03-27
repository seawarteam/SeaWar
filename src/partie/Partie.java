package partie;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import etat.Apte;
import etat.Bloque;
import List.*;
import ia.Controleur_IA;
import id.*;

public class Partie extends Observable implements Serializable {
	private static final long serialVersionUID = 5L;
	


	public WheelList<Joueur> joueurs;
	private int nbJoueurs;
	public int numTour;
	public Plateau plateau;
	private Iterator<Joueur> iteratorJ;
	public Joueur currentJ;
	public Joueur[] listeJ;
	public boolean gagne;
	public Joueur jGagnant = null;

	/**
	 * Default constructor
	 */
	public Partie(String[] nomJ, Color[] colJ, int nX, int nY) {
		// Position.initTabPosition(nX, nY);
		// if(observeur != null) {addObserver(observeur);}
		// plateau = new Plateau(nX, nY, nbPhares, nbRochers,observeur);
		numTour = 1;
		joueurs = new WheelList<Joueur>();
		nbJoueurs = nomJ.length;
		ajoutJoueurs(nomJ, nbJoueurs, colJ);
		iteratorJ = joueurs.getIterator();
		currentJ = iteratorJ.present();
		gagne = false;
	}
	
	public Partie(ArrayList<Joueur> lJoueurs, int nX, int nY) {
		// Position.initTabPosition(nX, nY);
		// if(observeur != null) {addObserver(observeur);}
		// plateau = new Plateau(nX, nY, nbPhares, nbRochers,observeur);
		numTour = 1;
		joueurs = new WheelList<Joueur>();
		nbJoueurs = lJoueurs.size();
		ajoutJoueurs(lJoueurs, nbJoueurs);
		iteratorJ = joueurs.getIterator();
		currentJ = iteratorJ.present();
		gagne = false;
	}
	
	public void initPartie(int nX, int nY, int nbPhares, int nbRochers, Observer observeur) {
		this.addObserver(observeur);
		plateau = new Plateau(nX, nY, nbPhares, nbRochers, observeur);

	}
	
	public void initPartie(Plateau map, Observer observeur) {
		plateau = map;
		plateau.ResetCouleur();
	}
	
	public void initPartie() {
		Navire.initCaseVoisine();
		plateau.ResetCouleur();
	}
	
	public void initObserver(Observer observeur){
		this.addObserver(observeur);
		plateau.initObserverCases(observeur);
	}

	public Joueur[] getJoueurs() {
		return listeJ;
	}

	private void ajoutJoueurs(String[] nomJ, int nbJoueurs, Color[] couleursJ) {
		Joueur newJ = null;
		listeJ = new Joueur[nbJoueurs];
		for (int i = 0; i < nbJoueurs; i++) {
			newJ = new Joueur(nomJ[i], couleursJ[i]);
			joueurs.add(newJ);
			listeJ[i] = newJ;
		}
	}
	
	private void ajoutJoueurs(ArrayList<Joueur> lJoueurs, int nbJoueurs) {
		listeJ = new Joueur[nbJoueurs];
		int i=0;
		for (Joueur j : lJoueurs) {
			joueurs.add(j);
			listeJ[i] = j;
			i++;
		}
	}
	public String toString() {
		return "Partie de " + nbJoueurs
				+ " joueurs et de caracteristiques :\n" + joueurs.toString();
	}

	public int nbJoueursRestant() {
		Joueur[] listeJ = getJoueurs();
		int nbJAlive = 0;
		for (Joueur j : listeJ) {
			if (!j.isDead()) {
				nbJAlive++;
			}
		}
		return nbJAlive;
	}

	public boolean initTour() {
		if(!gagne) {
			numTour++;
			currentJ = iteratorJ.next();
			boolean hasWinner = false;
			Joueur jNext;
			while (currentJ.isDead()) {
				jNext = iteratorJ.next();
				if (jNext == currentJ) {
					hasWinner = true;
					//gagne=true;
				} else {
					currentJ = jNext;
				}
			}
			for (Phare p : plateau.getPhares()) {
				for (Navire n : currentJ.getNavires()) {
					if (p.takePosition != null && p.takePosition.equals(n)) {
						p.occupeeDefinitivementPar = n;
					}
				}
			}
			if (hasWinner || plateau.hasWinner(currentJ)) {
				finPartie(currentJ);
				return true;
			}
	
			currentJ.initTour();
			currentJ.RechercheNaviresBloque(plateau.getRochers());
	
			setChanged();
			notifyObservers(this);
			clearChanged();
		}
		return false;
	}

	public void finPartie(Joueur joueurGagnant) {
		if(!gagne) {
			jGagnant = joueurGagnant;
			gagne = true;
			setChanged();
			notifyObservers(this);
			clearChanged();
		}
	}

	public boolean hasWinner(Joueur currentJ) {
		return plateau.hasWinner(currentJ);
	}

	public void initBateau(Position p, Orientation o, Navire n, Color c) {
		n.setColEnVie(c);
		n.setColAsColEnVie();
		n.setDir(o);
		n.setPos(p);
		/*
		 * int x = p.getX(); int y = p.getY() + (int) (p.getX() / 2);
		 * plateau.getCases()[x][y].setEstOccupe(true);
		 * plateau.getCases()[x][y].setTakePosition(n);
		 * plateau.getCases()[x][y].col = n.couleur;
		 */
		plateau.takeCase(p, n);
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	public void initBateau() {
		Map<Joueur, List<Position>> bases = plateau.getBases();
		Set<Joueur> keys = bases.keySet();
		//int nbJoueursPlaces = 0;
		Navire n1;
		Position p1;
		Position p2;
		ArrayList<Position> positions = null;
		ArrayList<Joueur> keyUtilisee = new ArrayList<Joueur>();
		Joueur currentKey = null;
		java.util.Iterator<Joueur> i = keys.iterator();
		java.util.Iterator<Position> p;
		Navire n2;
		for(Joueur j : listeJ) {
			n1 = null;
			n2 = null;
			p1 = null;
			p2 = null;
			do {
				currentKey = i.next();
				positions = (ArrayList<Position>) bases.get(currentKey);
			}while((positions == null || keyUtilisee.contains(currentKey))&& i.hasNext());//Ajouter une garde pour sortir
			//Mettre condition garde
			//nbJoueursPlaces++;
			keyUtilisee.add(currentKey);
			p = positions.iterator();
			p1 = p.next();
			p2 = p.next();
			n1 = j.getNavires()[0];
			n1.setNomJ(j.getNom());
			n2 = j.getNavires()[1];
			n2.setNomJ(j.getNom());
			n1.setColEnVie(j.getCol());
			n1.setColAsColEnVie();
			n1.setDir(Orientation.aleatoire());
			n1.setPos(Position.getPosition(p1.getX(), p1.getY()));
			plateau.takeCase(p1, n1);
			n2.setColEnVie(j.getCol());
			n2.setColAsColEnVie();
			n2.setDir(Orientation.aleatoire());
			n2.setPos(Position.getPosition(p2.getX(), p2.getY()));
			plateau.takeCase(p2, n2);
			setChanged();
			notifyObservers(this);
			clearChanged();
		}
		
		
	}
	
	
	
	public Plateau getPlateau() {
		return plateau;
	}

	public Navire getNavOnPos(Position pos) {
		int i = 0;
		Navire nav = null;
		
		while ((i < nbJoueurs) && (nav == null)) {
			nav = listeJ[i].getNavOnPos(pos);
			i = i + 1;
		}
		return nav;
	}

	public Set<Position> getObstacle() {
		return plateau.getObstacle();
	}

	public Case getCaseOnPos(Position pos) {
		return plateau.getCases()[pos.getX()][pos.getY() + (int) pos.getX() / 2];
	}

	public Joueur getCurrentJ() {
		return currentJ;
	}

	public void setCurrentJ(Joueur currentJ) {
		this.currentJ = currentJ;
	}

	public void surbrillanceO(Set<Position> s) {
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



	public void initDefNavires(Joueur[] j, Observer obs) {
		for (Joueur jou : j) {
			jou.ajoutDefaultNavire(obs);
		}
	}

	public Joueur getProprio(Navire nav) {
		int i = 0;
		while ((i < nbJoueurs) && (nav == null)) {
			Navire[] navs = listeJ[i].getNavires();
			for (Navire n : navs) {
				if (n == nav) {
					return listeJ[i];
				}
			}

			i = i + 1;
		}
		return null;
	}

	public boolean existeApteNav() {
		Navire[] navs = currentJ.getNavires();
		for (Navire nav : navs) {
			if (nav.getEtatCourant() == Apte.getEtat()
					|| nav.getEtatCourant() == Bloque.getEtat()) {
				return true;
			}
		}
		return false;
	}

	
	public Navire getNavire(ID_Navire id) {
		int i = 0;
		Navire n = null;
		while (i < nbJoueurs) {
			for(Navire nav : listeJ[i].getNavires()) {
				if(nav.getID() == id) {
					return nav;
				}
			}
			i = i + 1;
		}
		return null;
	}
	
	public Joueur getJoueur(ID_Joueur id) {
		int i = 0;
		Joueur j = null;
		while (i < nbJoueurs && j == null) {
			if(id == listeJ[i].getID()) {
				j = listeJ[i];
			}
			i = i + 1;
		}
		return j;
	}
	
	public Canons getCanon(ID_Canon id) {
		int i = 0;
		Navire n = null;
		while (i < nbJoueurs) {
			for(Navire nav : listeJ[i].getNavires()) {
				if(nav.getCanonP().getID() == id) {
					return nav.getCanonP();
				}
				if(nav.getCanonS().getID() == id) {
					return nav.getCanonP();
				}
			}
			i = i + 1;
		}
		return null;
	}

	public void setControleurIA(Controleur_IA c) {
		Joueur[] joueur = getJoueurs();
		for(Joueur j : joueur) {
			if(j.isBot()) {
				j.getIA().setControleur(c);
			}
		}
		
	}
	
	public void setID() {
		Joueur[] joueur = getJoueurs();
		for(Joueur j : joueur) {
			for(Navire n : j.getNavires()) {
				n.setID(new ID_Navire(ID_Navire.getNewID()));
				n.getCanonP().setID(new ID_Canon(ID_Canon.getNewID()));
				n.getCanonS().setID(new ID_Canon(ID_Canon.getNewID()));
			}
		}
	}
	
	
}
