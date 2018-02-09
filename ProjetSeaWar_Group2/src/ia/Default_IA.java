package ia;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import partie.Orientation;
import partie.Position;

import id.ID_Canon;
import id.ID_Joueur;
import id.ID_Navire;

public class Default_IA extends Abstract_IA {
	public Default_IA(ID_Joueur id) {
		super(id);
	}

	public void executerIA() {
		
System.out.println("\n\t\tDébut de tour\n");
		Set<ID_Navire> mesNavires = controleur.getNavires(monJoueur);
		Set<ID_Navire> navireEnnemis = getNaviresEnnemis(mesNavires);

		for (ID_Navire nav : mesNavires) {
			if (!controleur.isDetruit(nav)) {
				Set<ID_Canon> canons = controleur.getCanons(nav);
				boolean tir = false;
				for(ID_Canon canon : canons) {
					if (!tir && controleur.getTpsRest(canon)==0) {
						tir = tryToShootOnPos(canon, getPosNavires(navireEnnemis));
					}
				}
				if(controleur.getDep(nav)>0) {
					randomMove(nav);
				}
			}
		}
		controleur.demandeFinTour();
	}

	private Set<ID_Navire> getNaviresEnnemis(Set<ID_Navire> mesNavires) {
		Set<ID_Navire> allNav = controleur.getNavires();
		Set<ID_Navire> ennemies = new HashSet<ID_Navire>();
		for(ID_Navire n : allNav) {
			if(!mesNavires.contains(n) && !controleur.isDetruit(n)) {
				ennemies.add(n);
			}
		}
		return ennemies;
	}

	private Set<Position> getPosNavires(Set<ID_Navire> navs) {
		Set<Position> pos = new HashSet<Position>();
		for (ID_Navire nav : navs) {
			pos.add(controleur.getPos(nav));
		}
		return pos;
	}

	private boolean tryToShootOnPos(ID_Canon canon, Set<Position> posATirer) {
		ID_Navire nav = controleur.getNavire(canon);

		List<Position> cellCible = controleur.getPosCanShoot(canon,
				controleur.getOrientation(nav), controleur.getPos(nav));
		for (Position cell : cellCible) {
			if (posATirer.contains(cell)) {
				boolean a = controleur.tir(canon, cell);
				if (!a)
					System.err
							.println("IA : erreur dans l'algo : tryToShootOnPos ??? | canon("+canon+")");
				return a;
			}
		}

		Map<Position, Set<Vector<Object>>> mapCasesAcc = controleur.getRechercheCasesAccessible(controleur.getNavire(canon));

		Set<Position> posKey = mapCasesAcc.keySet();
		for (Position pos : posKey) {
			Set<Vector<Object>> SetVect = mapCasesAcc.get(pos);
			for (Vector<Object> vect : SetVect) {
				Orientation dir = (Orientation) vect.get(0);
				cellCible = controleur.getPosCanShoot(canon, dir, pos);
				for (Position cell : cellCible) {
					if (posATirer.contains(cell)) {
						boolean a = controleur.deplacement(nav, pos, dir);
						if (!a)
							System.err
									.println("IA : erreur dans l'algo : tryToShootOnPos ??? || ");
						a = controleur.tir(canon, cell);
						if (!a)
							System.err
									.println("IA : erreur dans l'algo : tryToShootOnPos ??? ||| canon("+canon+")");
						return a;
					}
				}
			}
		}
		return false;
	}

	
	private void randomMove(ID_Navire id) {
		Set<Position> accessible = controleur.getCasesAccessibles(id);
		int taille = accessible.size();
		int random = (int) (Math.random()*taille);
		int i = 0;
		Position finalPos = null;
		for(Position pos : accessible) {
			if(i==random) {
				finalPos = pos;
			}
			i++;
		}
		Set<Orientation> ori = controleur.findOrientationsPossibles(id, finalPos);
		taille = ori.size();
		random = (int) (Math.random()*taille);
		i = 0;
		for(Orientation dir : ori) {
			if(i==random) {
				boolean a = controleur.deplacement(id, finalPos, dir);
				if(!a) System.err.println("IA : erreur randomMove");
			}
			i++;
		}
	}
}


