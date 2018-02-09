package partie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import erreur.FichierExistant;

public class Editeur extends Observable {
	
	
	Plateau map;
	Canons canonP;
	Navire navire;
	
	private int nbJoueurs;
	public int nX, nY;
	private Observer obs;

	public Editeur(int nX, int nY, Observer obs) {
		navire = new Navire("", 0, 0, "", Orientation.N, new Position(0, 0),
				obs);
		canonP = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		// canonS = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		addObserver(obs);
		map = new Plateau(nX, nY, 0, 0, obs);
		this.nX = nX;
		this.nY = nY;
		this.obs = obs;
		nbJoueurs = 0;
		
	}
	
	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public Plateau getMap() {
		return map;
	}

	public Canons getCanonP() {
		return canonP;
	}
	
	public Navire getNavire() {
		return navire;
	}

	/*
	 * public Canons getCanonS(){ return canonS; }
	 */

	public void resetPlateau() {
		map = new Plateau(nX, nY, 0, 0, obs);
		map.ResetCouleur();
	}
	
	public void resetCanon() {
		List<Position> zone = canonP.getZoneTire();
		zone.clear();
	}
	/*
	public void resetNavire() {
		String nom = navire.getNom();
		nom = "";
		int depMax = navire.getDepMax();
		depMax = 5;
	}*/

	
	
	

	
	

}  
