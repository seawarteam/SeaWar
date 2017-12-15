package etatActivite;

import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatTirP implements EtatAction {

	private static EtatTirP e = new EtatTirP();

	private EtatTirP() {
	}

	public void clique(Position pos, Controleur c) {
		
		Navire nav = c.getPartie().currentJ.getCurrentN();
		Navire navC = c.getPartie().currentJ.getNavEtatCourant();
		boolean succes = nav.tir(nav.getCanonP(), pos, navC);
		if(succes) {
			int degats = nav.getCanonP().getDegat();
			Navire cible = c.getPartie().getNavOnPos(pos);
			if(cible != null) {
				cible.toucher(degats);
			}
		} else {
			//tps de recharge ou case non atteignable
		}
		
		c.setEtat(EtatInit.getEtat());
	}

	public static EtatTirP getEtat() {
		return e;
	}

}
