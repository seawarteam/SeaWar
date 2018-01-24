package partie;

import erreur.ChampsInvalides;
import fenetre.FenetreMenuDepart;
import fenetre.FenetrePrincipale;

public class ControleurChargerPartie{
	private Lanceur lanceur;

	public ControleurChargerPartie(Lanceur l) {
		super();
		lanceur = l;
	}
	
	public void demandeChargerJoueur(String nom, Navire navire, Canons canonP, Canons canonS, Navire navire2, Canons canonP2, Canons canonS2) throws ChampsInvalides {
		if(navire != null && canonP != null && canonS != null && lanceur.getMap() != null && navire2 != null && canonP2 != null && canonS2 != null) {
			int nbJoueurs = lanceur.getNbJoueurs();
			int nbMaxJoueur = lanceur.getMap().getNbMaxJoueurs();
			if(nbJoueurs<nbMaxJoueur) {
				lanceur.addJoueur(nom, navire, canonP, canonS, navire2, canonP2, canonS2);
			}
			else {
				throw new ChampsInvalides("Nombre de joueurs max atteint");
			}
		}else {
			throw new ChampsInvalides("Veuillez re-selectionner les composants");
		}
	}
	
	public void demandeChargerMap(Plateau p) {
		lanceur.addMap(p);
	}

	public void demandeLancerPartie() throws ChampsInvalides {
		if(lanceur.getNbJoueurs()>=2) {
			Plateau map = lanceur.getMap();
			int nX = map.getNCasesX();
			int nY = map.getNCasesY();
			Position.initTabPosition(nX, nY);
			Partie partie = new Partie(lanceur.getJoueurs(), nX, nY);
			Controleur controleur = new Controleur(partie);
			FenetrePrincipale carte = new FenetrePrincipale(partie, controleur);
			
			partie.initPartie(map, carte);
			carte.initFenetrePrincipale();
			partie.initBateau();
			partie.initObserver(carte);
			partie.currentJ.initTour();
			lanceur.disposeFenetre();
		}else {
			throw new ChampsInvalides("Deux joueurs sont nécessaires");
		}
	}

	public void demandeRetour() {
		FenetreMenuDepart f = new FenetreMenuDepart();
		lanceur.disposeFenetre();
	}

	

}
