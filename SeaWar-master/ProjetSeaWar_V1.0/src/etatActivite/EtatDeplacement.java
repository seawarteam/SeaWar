package etatActivite;

import java.util.Set;

import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatDeplacement implements EtatAction {

	private static EtatDeplacement e = new EtatDeplacement();

	private EtatDeplacement() {
	}

	public void clique(Position pos, Controleur c) {
		
		Navire nav = c.getPartie().currentJ.getCurrentN();
		Set<Position> obstacle = c.getPartie().getObstacle();
		if(nav.afficherCasesAccessibles(obstacle).contains(pos)) {
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
