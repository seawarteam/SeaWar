package partie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Editeur extends Observable {
	final String pathMap = "./Sauvegardes/Reglages/Cartes/";
	final String pathCanon = "./Sauvegardes/Reglages/Canons/";
	final String pathBateau = "./Sauvegardes/Reglages/Bateaux/";
	Plateau map;
	Canons canonP;
	
	Navire navire;
	private int nX, nY;
	private Observer obs;

	public Editeur(int nX, int nY, Observer obs) {
		navire = new Navire("", 0, 0, "", Orientation.N, new Position(0,0), obs);
		canonP = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		// canonS = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		addObserver(obs);
		map = new Plateau(nX, nY, 0, 0, obs);
		this.nX = nX;
		this.nY = nY;
		this.obs = obs;
	}

	public Plateau getMap() {
		return map;
	}

	public Canons getCanonP() {
		return canonP;
	}
	public void setNavire(String nom, int pv, int depMax) {
		navire.setNom(nom);
		navire.setPv(pv);
		navire.setDepMax(depMax);
	}
	
	public void setCanon(String nom, int degat, int tpsRech) {
		canonP.setDegat(degat);
		canonP.setNom(nom);
		canonP.setTpsRech(tpsRech);
	}
	/*
	 * public Canons getCanonS(){ return canonS; }
	 */

	public void resetPlateau() {
		map = new Plateau(nX, nY, 0, 0, obs);
		map.ResetCouleur();
	}

	public void sauvegarderMap(String nomFichier) { // ex : partie.sauvegarder ( "Test01" );
		String name = pathMap + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(map);
				oos.flush();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier dejaÂ  existant !");
		}
	}
	
	public void sauvegarderCanon(String nomFichier) { // ex : partie.sauvegarder ( "Test01" );
		String name = pathCanon + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(canonP);
				oos.flush();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier dejaÂ  existant !");
		}
	}
	
	public void sauvegarderNavire(String nomFichier) { // ex : partie.sauvegarder ( "Test01" );
		String name = pathBateau + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(navire);
				oos.flush();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier dejaÂ  existant !");
		}
	}
	


	

}