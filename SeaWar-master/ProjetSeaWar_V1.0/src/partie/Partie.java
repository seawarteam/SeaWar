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

/**
 * 
 */
public class Partie extends Observable implements Serializable {



	private static final long serialVersionUID = -6654834839716177494L;

	public static void main(String[] args) {

	}

	// final String pathMap =
	// "C:/Users/delaunay/Documents/Sauvegardes/Reglages/Cartes/";
	final String pathPartie = "/Sauvegardes/Parties/";
	// final String pathCanon =
	// "C:/Users/delaunay/Documents/Sauvegardes/Reglages/Canons/";
	// final String pathBateau =
	// "C:/Users/delaunay/Documents/Sauvegardes/Reglages/Bateaux/";

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

	public void initPartie(int nX, int nY, int nbPhares, int nbRochers,
			Observer observeur) {
		addObserver(observeur);
		plateau = new Plateau(nX, nY, nbPhares, nbRochers, observeur);

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
		Joueur newJ = null;
		listeJ = new Joueur[nbJoueurs];
		int i = 0;
		for (Joueur j : lJoueurs) {
			joueurs.add(j);
			listeJ[i] = newJ;
			i++;
		}
	}

	public String toString() {
		return "Partie de " + nbJoueurs
				+ " joueurs et de caractÃ©ristiques :\n" + joueurs.toString();
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
		numTour++;
		currentJ = iteratorJ.next();
		boolean hasWinner = false;
		Joueur jNext;
		while (currentJ.isDead()) {
			jNext = iteratorJ.next();
			if (jNext == currentJ) {
				hasWinner = true;
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
		return false;
	}

	public void finPartie(Joueur joueurGagnant) {
		jGagnant = joueurGagnant;
		gagne = true;
		setChanged();
		notifyObservers(this);
		clearChanged();
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
		Navire n1;
		Position p1;
		Position p2;
		ArrayList<Position> positions = null;
		ArrayList<Joueur> keyUtilisee = new ArrayList<Joueur>();
		Joueur currentKey = null;
		java.util.Iterator<Joueur> i = keys.iterator();
		java.util.Iterator<Position> p;
		Navire n2;
		for (Joueur j : listeJ) {
			do {
				currentKey = i.next();
				positions = (ArrayList<Position>) bases.get(currentKey);
			} while ((positions == null || keyUtilisee.contains(currentKey))
					&& i.hasNext());// Ajouter une garde pour sortir
			keyUtilisee.add(currentKey);
			p = positions.iterator();
			p1 = p.next();
			p2 = p.next();
			n1 = j.getNavires()[0];
			n2 = j.getNavires()[1];
			n1.setColEnVie(j.getCol());
			n1.setColAsColEnVie();
			n1.setDir(Orientation.aleatoire());
			n1.setPos(p1);
			plateau.takeCase(p1, n1);
			n2.setColEnVie(j.getCol());
			n2.setColAsColEnVie();
			n2.setDir(Orientation.aleatoire());
			n2.setPos(p2);
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

	public void addObserveur(Observer obs) {
		addObserver(obs);
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

	public void sauvegarder(String nomFichier) { // ex : partie.sauvegarder (
													// "Test01" );
		String name = pathPartie + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(this);
				oos.flush();
			} catch (final java.io.IOException e) {
				File path = new File(getPath() + pathPartie);
				path.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(this);
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier deja  existant !");
		}
	}

	public Partie charger(String nomFichier) {
		String name = getPath() + pathPartie + nomFichier;
		Partie partie = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				partie = (Partie) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier non existant !");
		}
		return partie;
	}

	private String getPath() {
		return System.getProperty("user.dir");
	}
	/*
	 * public Plateau chargerMap(String nomFichier) { String name = pathMap +
	 * nomFichier; Plateau plateau = null; File pathF = new File(name); if
	 * (pathF.exists()) { ObjectInputStream ois = null; try { final
	 * FileInputStream fichier = new FileInputStream(name); ois = new
	 * ObjectInputStream(fichier); plateau = (Plateau) ois.readObject(); } catch
	 * (final java.io.IOException e) { e.printStackTrace(); } catch (final
	 * ClassNotFoundException e) { e.printStackTrace(); } finally { try { if
	 * (ois != null) { ois.close(); } } catch (final IOException ex) {
	 * ex.printStackTrace(); } } } else {
	 * System.out.println("Fichier non existant !"); } return plateau; }
	 * 
	 * public Canons chargerCanon(String nomFichier) { String name = pathCanon +
	 * nomFichier; Canons canon = null; File pathF = new File(name); if
	 * (pathF.exists()) { ObjectInputStream ois = null; try { final
	 * FileInputStream fichier = new FileInputStream(name); ois = new
	 * ObjectInputStream(fichier); canon = (Canons) ois.readObject(); } catch
	 * (final java.io.IOException e) { e.printStackTrace(); } catch (final
	 * ClassNotFoundException e) { e.printStackTrace(); } finally { try { if
	 * (ois != null) { ois.close(); } } catch (final IOException ex) {
	 * ex.printStackTrace(); } } } else {
	 * System.out.println("Fichier non existant !"); } return canon; }
	 * 
	 * public Navire chargerNavire(String nomFichier) { String name = pathBateau
	 * + nomFichier; Navire navire = null; File pathF = new File(name); if
	 * (pathF.exists()) { ObjectInputStream ois = null; try { final
	 * FileInputStream fichier = new FileInputStream(name); ois = new
	 * ObjectInputStream(fichier); navire = (Navire) ois.readObject(); } catch
	 * (final java.io.IOException e) { e.printStackTrace(); } catch (final
	 * ClassNotFoundException e) { e.printStackTrace(); } finally { try { if
	 * (ois != null) { ois.close(); } } catch (final IOException ex) {
	 * ex.printStackTrace(); } } } else {
	 * System.out.println("Fichier non existant !"); } return navire; }
	 */
}
