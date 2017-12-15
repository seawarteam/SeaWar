package etatActivite;

import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatDeplacement implements EtatAction {

	private static EtatDeplacement e = new EtatDeplacement();

	private EtatDeplacement() {
	}

	public void clique(Position pos, Controleur c) {
		
		Navire nav = c.getPartie().currentJ.getCurrentN();
		if(nav.getAffichageCaseAccessible().contains(pos)) {
			nav.findOrientationsPossibles(pos, c.getPartie().getObstacle());
			c.setEtat(EtatOrientation.getEtat());
			EtatOrientation.setPos(pos);
		} else {
			c.setEtat(EtatInit.getEtat());
		}
		
	}

	public static EtatDeplacement getEtat() {
		return e;
	}

}
