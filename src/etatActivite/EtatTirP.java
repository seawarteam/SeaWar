package etatActivite;

import java.util.Set;

import etat.Detruit;
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
		Set<Position> rochers = c.getPartie().getPlateau().getRochers();
		boolean succes = nav.tir(nav.getCanonP(), pos, navC, rochers);
		if(succes) {
			int degats = nav.getCanonP().getDegat();
			Navire cible = c.getPartie().getNavOnPos(pos);
			if(cible != null) {
				cible.toucher(degats);
				if(cible.getEtatCourant() == Detruit.getEtat()) {
					c.getPartie().getPlateau().freeCase(cible.getPos());
				}
				if(c.getPartie().nbJoueursRestant() == 1){
					c.getPartie().finPartie(c.getPartie().currentJ);
				}
			}
		} else {
			//System.out.println("tps de recharge ou case non atteignable");
		}
		c.getPartie().ResetCouleur();
		c.setEtat(EtatInit.getEtat());
	}

	public static EtatTirP getEtat() {
		return e;
	}

}
