package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.*;

public class Phare extends Eau implements Serializable {

	public static final Color couleurVide = Color.WHITE;
	public static final Color couleurOccupe = Color.RED;
	public static final Color couleurOri = new Color(0, 255, 0);
	public static final Color couleurDep = new Color(0, 150, 255);

	public Phare(Polygon p, int i, int j, Observer obs) {
		super(p, i, j, obs);
		poly = p;
		estOccupe = false;
		takePosition = null;
		col = couleurVide;
		position = Position.getPosition(i, j - ((int) i / 2));
	}

	public void getInfo() {
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	public void ResetCouleur() {
		if (!estOccupe) {
			/*
			 * if(col != couleurVide){ col = couleurVide; setChanged();
			 * notifyObservers(this); clearChanged(); } } else {
			 * if(!col.equals(takePosition.couleur)){ col =
			 * takePosition.couleur; setChanged(); notifyObservers(this);
			 * clearChanged(); }
			 */
			col = couleurVide;
			setChanged();
			notifyObservers(this);
			clearChanged();
		} else {
			col = takePosition.couleur;
			setChanged();
			notifyObservers(this);
			clearChanged();
		}
	}

	public void surbrillanceDeptemp() {
		col = Color.black;
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	public void surbrillanceD() {
		col = couleurDep;
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	public void surbrillanceO() {
		col = couleurOri;
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	public String toString() {
		return "Phare";
	}

	public void takeCase(Navire n) {
		estOccupe = true;
		takePosition = n;
		col = couleurOccupe;
	}

	public void freeCase() {
		estOccupe = false;
		// On conserve le dernier bateau
	}
}
