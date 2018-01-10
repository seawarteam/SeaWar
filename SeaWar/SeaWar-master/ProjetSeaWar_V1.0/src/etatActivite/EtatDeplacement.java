package etatActivite;

import java.awt.Color;
import java.util.Set;

import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatDeplacement implements EtatAction {

	private static EtatDeplacement e = new EtatDeplacement();

	private EtatDeplacement() {
	}

	public void clique(Position pos, Controleur c) {
		c.getPartie().ResetCouleur();
		Navire nav = c.getPartie().currentJ.getCurrentN();
		Set<Position> obstacle = c.getPartie().getObstacle();
		if(nav.afficherCasesAccessibles(obstacle).contains(pos)) {
			c.getPartie().plateau.getCases()[pos.getX()][pos.getY() + ((int) pos.getX()/2)].surbrillanceDeptemp();
			c.getPartie().surbrillanceO(nav.findOrientationsPossibles(pos, obstacle));//Notify + surbillance
			EtatOrientation.setPos(pos);
			c.setEtat(EtatOrientation.getEtat());
		} else {
			c.setEtat(EtatInit.getEtat());
		}
		
	}

	public static EtatDeplacement getEtat() {
		return e;
	}

}
