package etatActivite;

import java.util.Set;

import etat.Detruit;
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
		Set<Position> rochers = c.getPartie().getPlateau().getRochers();
		boolean succes = nav.tir(nav.getCanonS(), pos, navC, rochers);
		if(succes) {
			int degats = nav.getCanonS().getDegat();
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
			//tps de recharge ou case non atteignable
		}
		
		c.setEtat(EtatInit.getEtat());
		c.getPartie().ResetCouleur();

	}

	public static EtatTirS getEtat() {
		return e;
	}

}
