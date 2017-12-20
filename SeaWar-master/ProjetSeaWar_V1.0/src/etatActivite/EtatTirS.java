package etatActivite;

import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatTirS implements EtatAction {

	private static EtatTirS e = new EtatTirS();

	private EtatTirS() {
	}

	public void clique(Position pos, Controleur c) {
		
		Navire nav = c.getPartie().currentJ.getCurrentN();
		Navire navC = c.getPartie().currentJ.getNavEtatCourant();
		boolean succes = nav.tir(nav.getCanonS(), pos, navC);
		if(succes) {
			int degats = nav.getCanonS().getDegat();
			Navire cible = c.getPartie().getNavOnPos(pos);
			
			if(cible != null) {
				cible.toucher(degats);
			}
		} else {
			System.out.println("tps de recharge ou case non atteignable");
		}
		
		c.setEtat(EtatInit.getEtat());
		c.getPartie().ResetCouleur();

	}

	public static EtatTirS getEtat() {
		return e;
	}

}
