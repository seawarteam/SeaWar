package partie;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.*;

public class Phare extends Eau implements Serializable {

	public static final Color couleurVide = Color.WHITE;
	public Color couleurOccupeDef = Color.RED;
	public static final Color couleurOri = new Color(0, 255, 0);
	public static final Color couleurDep = new Color(20, 170, 255);
	public Navire occupeeDefinitivementPar = null;
	
	
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
			if(occupeeDefinitivementPar!=null) {
				col = couleurOccupeDef;
			}
			setChanged();
			notifyObservers(this);
			clearChanged();
		} else {
				col = takePosition.couleur;

			}
			setChanged();
			notifyObservers(this);
			clearChanged();
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
		couleurOccupeDef = new Color((int) (n.couleur.getRed() + 2*couleurVide.getRed())/3, (int) (n.couleur.getGreen() + 2*couleurVide.getGreen())/3, (int) (n.couleur.getBlue() + 2*couleurVide.getBlue())/3);
		col = couleurOccupeDef;
	}

	public void freeCase() {
		takePosition = null;
		col = couleurVide;
		// On conserve le dernier bateau
	}
}
