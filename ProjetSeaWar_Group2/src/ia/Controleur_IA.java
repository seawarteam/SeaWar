package ia;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import etat.Bloque;
import etat.Detruit;
import etat.EtatDeplacement;
import id.*;
import partie.Canons;
import partie.Joueur;
import partie.Navire;
import partie.Partie;
import partie.Phare;
import partie.Position;
import partie.Orientation;

public class Controleur_IA extends Thread implements Serializable{

	private static final long serialVersionUID = 1L;

	private Partie partie;

	// Stockage des id/instance
	private Map<ID_Joueur, Joueur> conversionIdToJoueur;
	private Map<ID_Navire, Navire> conversionIdToNavire;
	private Map<ID_Canon, Canons> conversionIdToCanon;
	private Map<Joueur, ID_Joueur> conversionJoueurToId;
	/*private Map<Navire, ID_Navire> conversionNavireToId;
	private Map<Canons, ID_Canon> conversionCanonToId;
	private Map<Phare, ID_Phare> conversionPhareToId;*/
	private Map<ID_Phare, Phare> conversionIDToPhare;

	public Controleur_IA(Partie p) {
		partie = p;
		conversionIdToJoueur = new HashMap<ID_Joueur, Joueur>();
		conversionIdToNavire = new HashMap<ID_Navire, Navire>();
		conversionIdToCanon = new HashMap<ID_Canon, Canons>();
		conversionIDToPhare = new HashMap<ID_Phare, Phare>();
		//conversionPhareToId = new HashMap<Phare, ID_Phare>();
		conversionJoueurToId = new HashMap<Joueur, ID_Joueur>();
		//conversionNavireToId = new HashMap<Navire, ID_Navire>();
		//conversionCanonToId = new HashMap<Canons, ID_Canon>();
		initConversion();
		//System.out.println(conversionIdToJoueur);
		//System.out.println(conversionIdToNavire);
	}
	
	/*======================================================================*
	 |								   										|
	 |			Fonctions misent a disposition pour realiser l'IA			|
	 |																		|
	 *======================================================================*/
	
	/*======================= Fonctionnalitee ==========================*/
	
	public void demandeFinTour() {
		boolean changerTour;
		boolean navApte = partie.existeApteNav();
		if(navApte) {
			changerTour = false;
		} else {
			Navire nav = partie.currentJ.getNavEtatCourant();
			if(nav != null) {
				if (nav.getaEteDeplace()) {
					changerTour = true;
				} else {
					changerTour = false;
				}
			} else {
				changerTour = true;
			}
		}
		if(changerTour) {
			if(!partie.gagne) {
				partie.initTour();
			}
		}
	}
	
	
	public boolean deplacement(ID_Navire id, Position position, Orientation dir){
		Navire nav = getNavire(id);
		if(!partie.currentJ.haveNavire(id)) {// Pas notre navire
			return false;
		}
		Navire navCourant = partie.currentJ.getNavEtatCourant();
		Set<Position> obstacle = partie.getObstacle();
		if(nav.canGoOnPos(position, dir, obstacle)) {
			int nbCase = nav.getPathLengh(position, dir, obstacle);
			Position posNavInit = nav.getPos();
			boolean ok = nav.deplacement(position, dir, nbCase, navCourant);
			if (ok) {
				partie.plateau.freeCase(posNavInit);
				partie.plateau.takeCase(position, nav);
				System.out.println("[IA] : Deplacement du navire : " + nav.getNom()+"("+id+")");
				partie.ResetCouleur();
				return true;
			} else System.err.println("[IA] : Erreur de deplacement |");
		} else System.err.println("[IA] : Erreur de deplacement ||");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean tir(ID_Canon id, Position pos) {
		Canons can = getCanon(id);
		if(!partie.currentJ.haveNavire(can.getNav().getID())) {// pas notre canon
			return false;
		}
		
		Navire nav = getNavire(getNavire(id));
		Navire navC = partie.currentJ.getNavEtatCourant();
		Set<Position> rochers = partie.getPlateau().getRochers();
		boolean succes = nav.tir(can, pos, navC, rochers);
		if(succes) {
			int degats = can.getDegat();
			Navire cible = partie.getNavOnPos(pos);
			if(cible != null) {
				cible.toucher(degats);
				if(cible.getEtatCourant() == Detruit.getEtat()) {
					partie.getPlateau().freeCase(cible.getPos());
				}
				if(partie.nbJoueursRestant() == 1){
					partie.finPartie(partie.currentJ);
				}
				System.out.println("[IA] : tir du navire : " + nav.getNom()+" ("+nav.getID()+") avec le canon "+id);
				partie.ResetCouleur();
				try {
					Thread.sleep(500);//marche pas comme voulu... => TODO: le controleur dans un nouveau thread
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return succes;
	}
	
	/* ======================= Getter Phare ===========================*/
	
	public Set<ID_Phare> getPhares() {
		return conversionIDToPhare.keySet();
	}
	
	public Position getPos(ID_Phare id) {
		return getPhare(id).getPosition();
	}
	
	public ID_Joueur getProprio(ID_Phare id) {
		return getJoueur(getPhare(id).occupeeDefinitivementPar.getID());
	}
	
	public boolean isOccupe(ID_Phare id) {
		return getPhare(id).occupeeDefinitivementPar!=null;
	}
	
	
	/* ======================= Position ============================= */
	
	public Position getPosition(int x, int y) {
		return Position.getPosition(x, y);
	}
	
	/* ======================= Cases ================================= */
	
	public boolean isFree(Position pos) {
		return !partie.getPlateau().getCase(pos).estOccupe;
	}
	
	public Set<Position> getRochers() {
		return partie.getPlateau().getRochers();
	}
	
	public Set<Position> getObstacle() {
		return partie.getPlateau().getObstacle();
	}
	
	/*========================= Getter Navire =============================*/
	public Set<ID_Navire> getNavires() {
		return conversionIdToNavire.keySet();
	}

	public Position getPos(ID_Navire id) {
		return getNavire(id).getPos();
	}

	public int getDepMax(ID_Navire id) {
		return getNavire(id).getDepMax();
	}

	public int getDep(ID_Navire id) {
		return getNavire(id).getDep();
	}

	public Orientation getOrientation(ID_Navire id) {
		return getNavire(id).getOrientation();
	}

	public EtatDeplacement getEtatCourant(ID_Navire id) {
		return getNavire(id).getEtatCourant();
	}

	public boolean getADejaTire(ID_Navire id) {
		return getNavire(id).getADejaTire();
	}

	public int getPV(ID_Navire id) {
		return getNavire(id).getPV();
	}
	
	public ID_Joueur getJoueur(ID_Navire id) {
		for(Joueur j : conversionJoueurToId.keySet()) {
			if(j.haveNavire(id)) {
				return j.getID();
			}
		}
		return null;
	}
	
	public boolean isDetruit(ID_Navire id) {
		return getNavire(id).getEtatCourant()==Detruit.getEtat();
	}
	
	public Set<Position> getAllCaseVisable(ID_Canon idC) {
		ID_Navire idN = getNavire(idC); 
		if(getEtatCourant(idN)==Detruit.getEtat() || getEtatCourant(idN)==Bloque.getEtat()) {
			return new HashSet<Position>();
		}
		Set<Position> obstacle = partie.getObstacle();
		Set<Position> rochers = partie.plateau.getRochers();
		if(getNavire(idN).getCanonP()==getCanon(idC)) {
			return getNavire(idN).getCasesVisableCanonP(obstacle, rochers);
		}
		if(getNavire(idN).getCanonS()==getCanon(idC)) {
			return getNavire(idN).getCasesVisableCanonS(obstacle, rochers);
		}
		return null;
	}
	
	public Map<Position, Set<Vector<Object>>> getRechercheCasesAccessible(ID_Navire id) {
		return getNavire(id).rechercheCasesAccessible(getObstacle());
	}
	
	public Set<Position> getCasesAccessibles(ID_Navire id) {
		return getNavire(id).getCasesAccessibles(getObstacle());
	}
	
	public Set<Orientation> findOrientationsPossibles(ID_Navire id, Position pos) {
		return getNavire(id).findOrientationsPossiblesForIA(pos, getObstacle());
	}
	/*======================= Getter Canons ============================*/
	public Set<ID_Canon> getCanons(ID_Navire id) {
		Navire nav = getNavire(id);
		Set<ID_Canon> idCan = new HashSet<ID_Canon>();
		idCan.add(nav.getCanonP().getID());
		idCan.add(nav.getCanonS().getID());
		return idCan;
	}

	public ID_Navire getNavire(ID_Canon id) {
		return getCanon(id).getNav().getID();
	}

	public int getTpsRech(ID_Canon id) {
		return getCanon(id).getTpsRech();
	}

	public int getTpsRest(ID_Canon id) {
		return getCanon(id).getTpsRest();//TODO: erreur ???
	}

	public List<Position> getZoneTire(ID_Canon id) {
		return getCanon(id).getZoneTire();
	}

	public Map<Orientation, List<Position>> getMatZoneTire(ID_Canon id) {
		return getCanon(id).getMatZoneTire();
	}

	public int getDegat(ID_Canon id) {
		return getCanon(id).getDegat();
	}
	
	
	public List<Position> getPosCanShoot(ID_Canon id,Orientation dir,Position posi) {
		return getCanon(id).posCanShoot(dir, posi, partie.plateau.getRochers());
	}
	/*====================== Getter Joueur ======================*/
	
	public Set<ID_Navire> getNavires(ID_Joueur id) {
		Navire[] navs = getJoueur(id).getNavires();
		Set<ID_Navire> idNavs = new HashSet<ID_Navire>();
		for (Navire nav : navs) {
			idNavs.add(nav.getID());
		}
		return idNavs;
	}
	
	public boolean isDead(ID_Joueur id) {
		return getJoueur(id).isDead();
	}
	
	
	

	
	
	/*============ Fonctions Privee =====================*/

	private void initConversion() {
		for (Joueur j : partie.getJoueurs()) {
			conversionIdToJoueur.put(j.getID(), j);
			conversionJoueurToId.put(j, j.getID());
			for (Navire nav : j.getNavires()) {
				conversionIdToNavire.put(nav.getID(), nav);
				conversionIdToCanon.put(nav.getCanonP().getID(),nav.getCanonP());
				conversionIdToCanon.put(nav.getCanonS().getID(),nav.getCanonS());
/*
				conversionNavireToId.put(nav, nav.getID());
				conversionCanonToId.put(nav.getCanonP(), nav.getCanonP().getID());
				conversionCanonToId.put(nav.getCanonS(), nav.getCanonS().getID());*/
			}
		}
		for (Phare p : partie.getPlateau().getPhares()) {
			conversionIDToPhare.put(p.getID(), p);
			//conversionPhareToId.put(p, p.getID());
		}
	}

	private Joueur getJoueur(ID_Joueur id) {
		Map<ID_Joueur, Joueur> copy = new HashMap<ID_Joueur, Joueur>();
		for(ID_Joueur id1 : conversionIdToJoueur.keySet()) {
			copy.put(id1, conversionIdToJoueur.get(id1));
		}
		return copy.get(id);
	}

	private Navire getNavire(ID_Navire id) {/*
		Navire nav = conversionIdToNavire.get(id);
		System.out.println("Nav = "+nav);
		System.out.println(conversionIdToJoueur);
		System.out.println(conversionIdToNavire);
		if(conversionIdToNavire.get(id) == null) System.err.println(id + " => Null !!!!");
		return nav;*/
		
		Map<ID_Navire, Navire> copy = new HashMap<ID_Navire, Navire>();
		for(ID_Navire id1 : conversionIdToNavire.keySet()) {
			copy.put(id1, conversionIdToNavire.get(id1));
		}
		return copy.get(id);
	}

	private Canons getCanon(ID_Canon id) {
		Map<ID_Canon, Canons> copy = new HashMap<ID_Canon, Canons>();
		for(ID_Canon id1 : conversionIdToCanon.keySet()) {
			copy.put(id1, conversionIdToCanon.get(id1));
		}
		return copy.get(id);
	}
	
	private Phare getPhare(ID_Phare id) {
		//return conversionIDToPhare.get(id);
		Map<ID_Phare, Phare> copy = new HashMap<ID_Phare, Phare>();
		for(ID_Phare id1 : conversionIDToPhare.keySet()) {
			copy.put(id1, conversionIDToPhare.get(id1));
		}
		return copy.get(id);
	}

	
	
	public void show() {
		System.out.println("<show>");
		System.out.println(conversionIdToJoueur);
		System.out.println(conversionIdToNavire);
		System.out.println(conversionIdToCanon);
		System.out.println(conversionIDToPhare);
		System.out.println("</show>\n");
	}

	/*public void run() {
		while(true);
		
	}*/

}
