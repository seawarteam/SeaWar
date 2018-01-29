package etatModif;

import java.awt.Polygon;
import java.util.HashSet;
import java.util.Set;

import partie.ControleurModif;
import partie.Eau;
//import partie.EditCanonS;
import partie.Position;
import partie.Rocher;

public class EditEau implements EtatModif{
	private static EditEau etat = new EditEau();
	private EditEau(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}


	public void clique(Position p, ControleurModif c) {
		
		int nCasesX = c.getEditeur().nX;
		int nCasesY = c.getEditeur().nY;
		
		Set<Position> posCote = new HashSet<Position>();
		for (int i = 0; i < nCasesX; i++) {
			for (int j = 0; j < nCasesY; j++) {
				if (i == 0 || j == 0 || i == nCasesX - 1 || j == nCasesY - 1) {
					Position pos = Position.getPosition(i, j - ((int) i / 2));
					posCote.add(pos);
				}
			}
		}
		if(!posCote.contains(p)) {
			c.getEditeur().getMap().setCaseEau(p);
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
	}

	
	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}

	
	public void modifBase(ControleurModif c, String str) {
		c.setEtat(EditBase.getEtat());
		EditBase.getEtat().modifBase(c, str);
		
	}

	



	
}
