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

import etatModif.EditBase;


public class Editeur extends Observable{
	final String path = "C:/Users/delaunay/Documents/Sauvegardes/Réglages/Cartes/";
	Plateau map;
	Canons canonP;
	Canons canonS;
	Navire navire;
	private int nX, nY;
	private Observer obs;
	public Editeur(int nX, int nY, Observer obs){
		navire = new Navire("", 0, 0, "", Orientation.N, new Position(0, 0), obs);
		canonP = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		canonS = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		addObserver(obs);
		map = new Plateau(nX, nY, 0, 0, obs);
		this.nX = nX;
		this.nY = nY;
		this.obs = obs;
	}

	
	public Plateau getMap() {
		return map;
	}
	
	public Canons getCanonP(){
		return canonP;
	}
	
	public Canons getCanonS(){
		return canonS;
	}
	
	public void resetPlateau(){
		map = new Plateau(nX, nY, 0, 0, obs);
		map.ResetCouleur();
	}
	
	public void sauvegarderMap ( String nomFichier ) { // ex : partie.sauvegarder ( "Test01" );
		String name = path + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if ( !pathF.exists() ) {
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
		} else { System.out.println("Fichier dejaÂ  existant !"); }
	}
	public void chargerMap ( String nomFichier ) { // ex : partie.charger ( "Test01" );
		String name = path + nomFichier;
		File pathF = new File(name);
		if ( pathF.exists() ) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				map = (Plateau)ois.readObject();
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
		} else { System.out.println("Fichier non existant !"); }
		map.ResetCouleur();
		map.surbrillanceBases();
	}

	
	
}