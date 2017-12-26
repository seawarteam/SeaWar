package partie;

import java.util.*;

import fenetre.FenetreMenuDepart;
import fenetre.FenetrePrincipale;

/**
 * 
 */
public class SeaWar {
	
	public static void main (String [] args) {
		FenetreMenuDepart f = new FenetreMenuDepart();
	}

    /**
     * Default constructor
     */
    public SeaWar() {
    }

    /**
     * 
     */
    public Partie partie;// utile ???
    
    
    public static void partieRapide(String jou1, String jou2) {
    	
    	/*================== Partie ====================*/
		
		String []nomJ = new String[2];
		nomJ[0] = jou1;
		nomJ[1] = jou2;
		int nX = 20;
		int nY = 14;
		int nbPhares = 3;
		int nbRochers = 20;
		Position.initTabPosition(nX, nY);
		Partie partie = new Partie(nomJ, nX, nY);
		
		/*================== Controleur ==================*/
		
		Controleur controleur = new Controleur(partie);
		
		/*=============== Fenetre =================*/
		
		FenetrePrincipale carte = new FenetrePrincipale(partie, controleur);
		//partie.addObserver(carte);
		
		/*================ Joueurs ================*/
		
		Joueur[] j = partie.getJoueurs();
		Joueur j1 = j[0];
		Joueur j2 = j[1];
		
		/*=============== Navire ================*/
		
		Vector<Navire> navs1 = j1.ajoutDefaultNavire(carte);
		Vector<Navire> navs2 = j2.ajoutDefaultNavire(carte);
		Navire Fregate1 = navs1.get(0);
		Navire Amiral1 = navs1.get(1);
		Navire Fregate2 = navs2.get(0);
		Navire Amiral2 = navs2.get(1);
		

		
		/*============ Zone Canon ====================*/
		
		List<Position> ZCAP = new LinkedList<Position>();
			ZCAP.add(new Position(0,-1));ZCAP.add(new Position(0,-2));
			ZCAP.add(new Position(0,-3));ZCAP.add(new Position(0,-4));
		List<Position> ZCAS = new LinkedList<Position>();
			ZCAS.add(new Position(0,-1));ZCAS.add(new Position(0,-2));
			ZCAS.add(new Position(1,-1));ZCAS.add(new Position(1,-2));
			ZCAS.add(new Position(-1,-1));ZCAS.add(new Position(-1,0));
		List<Position> ZCFP = new LinkedList<Position>();
			ZCFP.add(new Position(0,-1));ZCFP.add(new Position(0,-2));
			ZCFP.add(new Position(1,-1));ZCFP.add(new Position(1,0));
			ZCFP.add(new Position(-1,0));ZCFP.add(new Position(-1,1));
		List<Position> ZCFS = new LinkedList<Position>();
			ZCFS.add(new Position(0,-1));ZCFS.add(new Position(0,1));
			ZCFS.add(new Position(1,0));ZCFS.add(new Position(-1,0));
			ZCFS.add(new Position(1,-1));ZCFS.add(new Position(-1,1));
			
		/*================= Canon ====================*/
		
		Canons CAP1 = new Canons("Primaire", 50, 4, ZCAP, Amiral1);
		Canons CAS1 = new Canons("Secondaire", 30, 2, ZCAS, Amiral1);
		Canons CFP1 = new Canons("Primaire", 30, 2, ZCFP, Fregate1);
		Canons CFS1 = new Canons("Secondaire", 10, 1, ZCFS, Fregate1);
		Canons CAP2 = new Canons("Primaire", 50, 4, ZCAP, Amiral2);
		Canons CAS2 = new Canons("Secondaire", 30, 2, ZCAS, Amiral2);
		Canons CFP2 = new Canons("Primaire", 30, 2, ZCFP, Fregate2);
		Canons CFS2 = new Canons("Secondaire", 10, 1, ZCFS, Fregate2);
		
		Amiral1.addCanon(CAP1, CAS1);
		Amiral2.addCanon(CAP2, CAS2);
		Fregate1.addCanon(CFP1, CFS1);
		Fregate2.addCanon(CFP2, CFS2);
		
		/*================ Start ===========================*/
		partie.initPartie(nX, nY, nbPhares, nbRochers, carte);
		carte.initFenetrePrincipale();
		
		partie.initBateau(Position.getPosition(1, 1), Orientation.SE, Amiral1);
		partie.initBateau(Position.getPosition(2, 0), Orientation.SE, Fregate1);
		partie.initBateau(Position.tabPosition[(nX - 2) * (nY) + nY - 2], Orientation.NO, Amiral2);
		partie.initBateau(Position.tabPosition[(nX - 3) * (nY) + nY - 2], Orientation.NO, Fregate2);
		
		partie.currentJ.initTour();
		
    }

}
