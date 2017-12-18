package partie;

import java.util.HashSet;
import java.util.Set;

import etatActivite.*;

public class Controleur {
	private EtatAction etat = EtatInit.getEtat();
	private Partie partie;

	public Controleur(Partie p) {
		super();
		partie = p;
	}

	public void setEtat(EtatAction e) {
		etat = e;
	}

	public Partie getPartie() {
		return partie;
	}

	public void demandeFinTour() {
		boolean changerTour;
		boolean navApte = partie.existeApteNav();
		if(navApte) {
			changerTour = false;
		} else {
			Navire nav = partie.currentJ.getNavEtatCourant();
			if (nav.getaEteDeplace()) {
				changerTour = true;
			} else {
				changerTour = false;
			}
		}
		if(changerTour) {
			partie.initTour();
			etat = EtatInit.getEtat();
		}
	}

	public void hexClique(Position pos) {
		etat.clique(pos, this);

	}

	public void demandeDeplacement() {
		Navire nav = partie.getCurrentJ().getCurrentN();
		if (nav == null)
			System.err.println("nav == null");
		Set<Position> obstacle = partie.getObstacle();
		partie.surbrillanceD(nav.afficherCasesAccessibles(obstacle));
		etat = EtatDeplacement.getEtat();
	}

	public void demandeTirP() {
		Navire nav = partie.getCurrentJ().getCurrentN();
		if (nav.getCanonP().getTpsRest() == 0 && nav.getADejaTire() == false) {
			Set<Position> positions = new HashSet<Position>();

			for (Position pos : (nav.getCanonP().posCanShoot(nav.getDir(), nav.getPos()))) {
				positions.add(pos);
			}
			partie.surbrillanceT(positions);
			etat = EtatTirP.getEtat();
		}
	}

	public void demandeTirS() {
		
		Navire nav = partie.getCurrentJ().getCurrentN();

		if (nav.getCanonS().getTpsRest() == 0 && nav.getADejaTire() == false) {
			Set<Position> positions = new HashSet<Position>();
			for (Position pos : (nav.getCanonS().posCanShoot(nav.getDir(), nav.getPos()))) {
				positions.add(pos);
			}
			partie.surbrillanceT(positions);
			etat = EtatTirS.getEtat();
		}
	}

}
