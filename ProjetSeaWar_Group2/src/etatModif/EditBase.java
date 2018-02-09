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

	private EditBase() {
		super();
		nbPosition = 0;
		currentJ = null;
		positions = new ArrayList<Position>();

	}

	public static EtatModif getEtat() {
		return etat;
	}

	public void clique(Position p, ControleurModif c) {
		Case ca;
		if (nbPosition < maxPositions) {
			ca = c.getEditeur().getMap().getCase(p);
			if(!positions.contains(p)) {
				if (!(ca instanceof Rocher) && !(ca instanceof Phare)) {
					positions.add(p);
					nbPosition++;
					c.getEditeur().getMap().setColorBase(p, currentJ);
					if (nbPosition == 2) {
						finalizeBases(c);
					}
				}
			}
		}

	}

	public void finalizeBases(ControleurModif c) {
		c.getEditeur().getMap().addBases(currentJ, positions);
		
	}

	

	public void resetInvalidBase(ControleurModif c) {
		if (nbPosition != 2) {
			for (Position p : positions) {
				c.getEditeur().getMap().ResetCouleurCase(p);
			}
		}
	}

	public void modifCanonP(ControleurModif c) {
	}

	public void modifCanonS(ControleurModif c) {
	}

	public void modifRocher(ControleurModif c) {
		c.setEtat(EditRocher.getEtat());
	}

	public void modifEau(ControleurModif c) {
		c.setEtat(EditEau.getEtat());
	}

	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}

	public void modifBase(ControleurModif c, String str) {
		resetInvalidBase(c);
		nbPosition = 0;
		positions = new ArrayList<Position>();
		//Si le joueur est deja present dans la map alors reinitialiser ses positions
		currentJ = (Joueur)(c.getEditeur().getMap().getJoueurFromBases(str));
		c.getEditeur().getMap().ResetCouleurBaseJoueur(currentJ);
	}

}
