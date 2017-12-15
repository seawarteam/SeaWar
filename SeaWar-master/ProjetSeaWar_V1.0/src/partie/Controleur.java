package partie;

import etatActivite.*;

public class Controleur {
	private EtatAction etat = EtatInit.getEtat();
	private Partie partie;
	
	public Controleur(Partie p){
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
		// TODO s'occuper de la demande de fin de tour par un joueur (appui sur le bouton fin de tour)
	}
	
	public void hexClique(Position pos) {
		etat.clique(pos, this);
		
	}
	
	public void demandeDeplacement() {
		// TODO s'occuper de la demande de se deplacer (appui sur le bouton deplacer)
	}
	
	public void demandeTirP() {
		// TODO s'occuper de la demande de tirer avec le canon primaire (appui sur le bouton tir primaire)
	}
	
	public void demandeTirS() {
		// TODO s'occuper de la demande de tirer avec le canon secondaire (appui sur le bouton tir secondaire)
	}

	
	
}
