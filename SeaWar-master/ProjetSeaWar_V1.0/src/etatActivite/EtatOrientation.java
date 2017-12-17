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
		if(dir != null) {
			Navire nav = c.getPartie().currentJ.getCurrentN();
			Navire navCourant = c.getPartie().currentJ.getNavEtatCourant();
			Set<Position> obstacle = c.getPartie().getObstacle();
			int nbCase = nav.getPathLengh(pos, dir, obstacle);
			if (nbCase == -1) System.err.println("nbCase = -1");
			Position posNavInit = nav.getPos();
			boolean ok = nav.deplacement(position, dir, nbCase, navCourant);
			if (ok) {
				c.getPartie().notifier(nav);
				System.out.println("EtatOri : Deplacement reussi");
				c.getPartie().plateau.getCases()[position.getX()][position.getY()+ ((int) position.getX()/2)].estOccupe=true;
				c.getPartie().plateau.getCases()[position.getX()][position.getY()+ ((int) position.getX()/2)].takePosition=nav;
				c.getPartie().plateau.getCases()[posNavInit.getX()][posNavInit.getY()+ ((int) posNavInit.getX()/2)].estOccupe=false;
				c.getPartie().plateau.getCases()[posNavInit.getX()][posNavInit.getY()+ ((int) posNavInit.getX()/2)].takePosition = null;
			}
		} else {
			
		}
		c.getPartie().ResetCouleur();
		c.setEtat(EtatInit.getEtat());
		//getInfo;
	}

	public static EtatOrientation getEtat() {
		return e;
	}

	public static void setPos(Position pos) {
		position = pos;
		
	}

}
