package etatActivite;

import java.util.Set;

import partie.Controleur;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public class EtatOrientation implements EtatAction {

	private static EtatOrientation e = new EtatOrientation();
	private static Position position;

	private EtatOrientation() {
	}

	public void clique(Position pos, Controleur c) {
		Orientation dir = Orientation.getOrientation(position, pos);
		if (dir != null) {
			Navire nav = c.getPartie().currentJ.getCurrentN();
			Navire navCourant = c.getPartie().currentJ.getNavEtatCourant();
			Set<Position> obstacle = c.getPartie().getObstacle();
			if(nav.canGoOnPos(position, dir, obstacle)) {
				int nbCase = nav.getPathLengh(position, dir, obstacle);
				if (nbCase == -1)
					System.err.println(" EtatOrientation : nbCase = -1");
				Position posNavInit = nav.getPos();
				boolean ok = nav.deplacement(position, dir, nbCase, navCourant);
				if (ok) {
					// c.getPartie().notifier(nav);
					c.getPartie().plateau.freeCase(posNavInit);
					c.getPartie().plateau.takeCase(position, nav);
				}
			}
		}
		c.getPartie().ResetCouleur();
		c.setEtat(EtatInit.getEtat());
	}

	public static EtatOrientation getEtat() {
		return e;
	}

	public static void setPos(Position pos) {
		position = pos;

	}

}
