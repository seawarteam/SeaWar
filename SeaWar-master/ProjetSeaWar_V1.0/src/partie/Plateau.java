package partie;

import java.awt.Polygon;
import java.io.Serializable;
import java.util.*;

public class Plateau implements Serializable {

	private Case cases[][];
	private Set<Phare> phares;
	private Set<Position> caseR;
	private Set<Position> caseN;
	private int nCasesX;
	private int nCasesY;
	private static int longueurCote;
	private static int apotheme;
	private static int resteX;

	public Plateau(int l, int L, int nbPhares, int nbRochers, Observer obs) {
		cases = new Case[l][L];
		nCasesX = l;
		nCasesY = L;
		caseR = new HashSet<Position>();
		caseN = new HashSet<Position>();
		phares = new HashSet<Phare>();
		initTabHex(nbPhares, nbRochers, obs);

		// TO DO : pouvoir retourner un set d'obstacles (tout les obstacles
		// présents)
		// --> faire une liste "fixe" avec les rochers et une autre "changeante"
		// avec les bateaux.

	}

	// crée un hexagone au coordonnées pixel x0,y0 (!!! pour l'insant, x0 et y0
	// sont les coordonnées en pixels)
	public static Polygon hexagone(int x0, int y0) {
		int x = x0;
		int y = y0;

		int[] cx, cy; // tableau de coordonnées x et y de tous les points d'un
						// hexagone en commencant par le point en haut à gauche

		cx = new int[] { x + resteX, x + longueurCote + resteX,
				x + longueurCote + resteX + resteX, x + longueurCote + resteX,
				x + resteX, x, x + resteX };
		cy = new int[] { y, y, y + apotheme, y + apotheme + apotheme,
				y + apotheme + apotheme, y + apotheme, y };

		return new Polygon(cx, cy, 6);
	}

	private void initTabHex(int nbPhares, int nbRochers, Observer obs) {
		int x, y;

		int nbx, nby;
		ArrayList<Position> tabPosUtil = new ArrayList<Position>();

		// Positions obstacles ou il y a les bateaux au debut
		// TODO: à Vérifier !
		tabPosUtil.add(Position.getPosition(1, 1));
		caseN.add(Position.getPosition(1, 1));
		tabPosUtil.add(Position.getPosition(1, 2));
		caseN.add(Position.getPosition(1, 2));
		tabPosUtil.add(Position.tabPosition[(nCasesX - 2) * (nCasesY) + nCasesY
				- 2]);
		caseN.add(Position.tabPosition[(nCasesX - 2) * (nCasesY) + nCasesY - 2]);
		tabPosUtil.add(Position.tabPosition[(nCasesX - 3) * (nCasesY) + nCasesY
				- 2]);
		caseN.add(Position.tabPosition[(nCasesX - 3) * (nCasesY) + nCasesY - 2]);

		boolean ok = false;

		for (int i = 0; i < nCasesX; i++) {
			for (int j = 0; j < nCasesY; j++) {
				x = i * (longueurCote + resteX);
				y = j * apotheme * 2 + (i % 2) * apotheme;
				Polygon poly = hexagone(x, y);
				if (i == 0 || j == 0 || i == nCasesX - 1 || j == nCasesY - 1) {
					cases[i][j] = new Rocher(poly, i, j, obs);
					Position p = Position.getPosition(i, j - ((int) i / 2));
					tabPosUtil.add(p);
					caseR.add(p);
				} else {
					cases[i][j] = new Eau(poly, i, j, obs);
				}
			}
		}
		Phare ph;
		// On vient positionner les entiés (Phares, Rochers)
		for (int i = 1; i <= nbPhares; i++) {
			Position p;
			ok = false;
			while (!ok) {
				nbx = 0 + (int) (Math.random() * ((nCasesX - 1) + 1));
				nby = 0 + (int) (Math.random() * ((nCasesY - 1) + 1));
				p = Position.getPosition(nbx, nby - (int) nbx / 2);
				if (!tabPosUtil.contains(p)) {
					tabPosUtil.add(p);
					x = nbx * (longueurCote + resteX);
					y = nby * apotheme * 2 + (i % 2) * apotheme;
					Polygon poly = hexagone(x, y);
					ph = new Phare(poly, nbx, nby, obs);
					cases[nbx][nby] = ph;
					phares.add(ph);
					ok = true;
				}
			}
		}
		for (int i = 1; i <= nbRochers; i++) {
			Position p;
			ok = false;
			while (!ok) {
				nbx = 0 + (int) (Math.random() * ((nCasesX - 1) + 1));
				nby = 0 + (int) (Math.random() * ((nCasesY - 1) + 1));
				p = Position.getPosition(nbx, nby - (int) nbx / 2);
				if (!tabPosUtil.contains(p)) {
					tabPosUtil.add(p);
					tabPosUtil.add(p);
					x = nbx * (longueurCote + resteX);
					y = nby * apotheme * 2 + (i % 2) * apotheme;
					Polygon poly = hexagone(x, y);
					cases[nbx][nby] = new Rocher(poly, nbx, nby, obs);
					caseR.add(p);
					ok = true;
				}
			}
		}

	}

	public void freeCase(Position p) {
		int i, j;
		i = p.getX();
		j = p.getY() + i / 2;
		cases[i][j].freeCase();
		cases[i][j].estOccupe = false;
		caseN.remove(p);
	}

	public void takeCase(Position p, Navire N) {
		int i, j;
		i = p.getX();
		j = p.getY() + i / 2;
		cases[i][j].takeCase(N);
		cases[i][j].estOccupe = true;
		caseN.add(p);
	}

	public Set<Position> getObstacle() {
		Set<Position> caseO = new HashSet<Position>();
		caseO.addAll(caseN);
		caseO.addAll(caseR);
		return caseO;
	}

	public Case[][] getCases() {
		return cases;
	}

	public boolean hasWinner(Joueur currentJ) {
		Iterator<Phare> i = phares.iterator();
		boolean stop = false;
		Phare ph;
		while (i.hasNext() && !stop) {
			ph = i.next();
			if (ph.getTakePosition() != null) {
				if (!currentJ.getListNavires().contains(ph.getTakePosition())) {
					stop = true;
				}
			} else {
				stop = true;
			}
		}
		return (!stop);
	}

	public void surbrillanceO(Set<Position> s) {
		for (Position p : s) {
			cases[p.getX()][p.getY() + (int) p.getX() / 2].surbrillanceO();
		}
	}

	public void surbrillanceD(Set<Position> s) {
		for (Position p : s) {
			cases[p.getX()][p.getY() + (int) p.getX() / 2].surbrillanceD();
		}
	}

	public void surbrillanceT(Set<Position> s) {
		for (Position p : s) {
			cases[p.getX()][p.getY() + (int) p.getX() / 2].surbrillanceT();
		}
	}

	public void ResetCouleur() {
		for (int i = 0; i < nCasesX; i++) {
			for (int j = 0; j < nCasesY; j++) {
				cases[i][j].ResetCouleur();
			}
		}

	}

}
