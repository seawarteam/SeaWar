package partie;

import java.util.Set;

import etatActivite.*;

public class Controleur {
	private EtatAction etat = EtatInit.getEtat();
	private Partie partie;
	
	public Controleur(Partie p){
		super();
		partie = p;
	}
	
	public void setEtat(EtatAction e) {
		partie.ResetCouleur();
		etat = e;
	}
	public Partie getPartie() {
		return partie;
	}
	
	public void demandeFinTour() {
		// TODO s'occuper de la demande de fin de tour par un joueur (appui sur le bouton fin de tour)
	}
	
	public void hexClique(Position pos) {
		etat.clique(pos, this);
		
	}
	
	public void demandeDeplacement() {
		Navire nav = partie.getCurrentJ().getCurrentN(); if(nav == null) System.err.println("nav == null");
		Set<Position> obstacle = partie.getObstacle();
		partie.surbrillanceD(nav.afficherCasesAccessibles(obstacle));
		etat = EtatDeplacement.getEtat();
	}
	
	public void demandeTirP() {
		Navire nav = partie.getCurrentJ().getCurrentN();
		Set<Position> obstacle = partie.getObstacle();
		partie.surbrillanceT(nav.afficherCasesVisableCanonP(obstacle));
		etat = EtatDeplacement.getEtat();
	}
	
	public void demandeTirS() {
		Navire nav = partie.getCurrentJ().getCurrentN();
		Set<Position> obstacle = partie.getObstacle();
		partie.surbrillanceT(nav.afficherCasesVisableCanonS(obstacle));
		etat = EtatDeplacement.getEtat();
	}

	
	
}
