package partie;

import java.awt.Color;
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

	private static Observer obs;
	private Map<Joueur, List<Position>> bases;

	public Plateau(int l, int L, int nbPhares, int nbRochers, Observer obs) {
		cases = new Case[l][L];
		nCasesX = l;
		nCasesY = L;
		caseR = new HashSet<Position>();
		caseN = new HashSet<Position>();
		phares = new HashSet<Phare>();

		initTabHex(nbPhares, nbRochers, obs);
		this.obs = obs;
		bases = new HashMap<Joueur, List<Position>>();
		initJoueursBases();

	}

	public void setObserver(Observer o) {
		obs = o;
	}

	public Observer getObserver() {
		return obs;
	}

	private Color couleur(int i) {
		Color[] tab = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE,
				Color.GRAY, Color.DARK_GRAY };
		return tab[i];
	}

	private void initJoueursBases() {
		Joueur j;
		for (int i = 1; i <= 6; i++) {
			j = new Joueur("Joueur" + i, couleur(i - 1));
			bases.put(j, null);
		}
	}

	public Joueur getJoueurFromBases(String str) {
		for (Joueur j : bases.keySet()) {
			if (j.getNom().equals(str)) {
				return j;
			}
		}
		return null;
	}

	// cree un hexagone au coordonnÃ©es pixel x0,y0 (!!! pour l'insant, x0 et y0
	// sont les coordonnees en pixels)
	public static Polygon hexagone(int x0, int y0) {
		int x = x0;
		int y = y0;

		int[] cx, cy; // tableau de coordonnees x et y de tous les points d'un
						// hexagone en commencant par le point en haut a gauche

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
		// TODO: a  Verifier !
		tabPosUtil.add(Position.getPosition(1, 1));
		caseN.add(Position.getPosition(1, 1));
		tabPosUtil.add(Position.getPosition(2, 0));
		caseN.add(Position.getPosition(2, 0));
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
		// On vient positionner les enties (Phares, Rochers)
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

	public Case getCase(Position p) {
		return cases[p.getX()][p.getY() + (int) p.getX() / 2];
	}

	public Map getBases() {
		return bases;
	}

	public void addBases(Joueur j, ArrayList<Position> l) {
		bases.put(j, l);
	}

	public void setColorBase(Position p, Joueur j) {
		cases[p.getX()][p.getY() + (int) p.getX() / 2].setColor(j.getCol());
	}

	public void setColorTire(Position p) {
		cases[p.getX()][p.getY() + (int) p.getX() / 2].setColor(Color.YELLOW);
	}

	public void setColor(Position p, Color c) {
		cases[p.getX()][p.getY() + (int) p.getX() / 2].setColor(c);

	}

	public void setCaseEau(Position p) {
		Case c = cases[p.getX()][p.getY() + (int) p.getX() / 2];
		Polygon poly = c.getPoly();
		cases[p.getX()][p.getY() + (int) p.getX() / 2] = new Eau(poly,
				p.getX(), p.getY() + p.getX() / 2, obs);
		cases[p.getX()][p.getY() + (int) p.getX() / 2].ResetCouleur();
	}

	public void setCaseRocher(Position p) {
		Case c = cases[p.getX()][p.getY() + (int) p.getX() / 2];
		Polygon poly = c.getPoly();
		cases[p.getX()][p.getY() + (int) p.getX() / 2] = new Rocher(poly,
				p.getX(), p.getY() + p.getX() / 2, obs);
		cases[p.getX()][p.getY() + (int) p.getX() / 2].ResetCouleur();
	}

	public void setCasePhare(Position p) {
		Case c = cases[p.getX()][p.getY() + (int) p.getX() / 2];
		Polygon poly = c.getPoly();
		cases[p.getX()][p.getY() + (int) p.getX() / 2] = new Phare(poly,
				p.getX(), p.getY() + p.getX() / 2, obs);
		cases[p.getX()][p.getY() + (int) p.getX() / 2].ResetCouleur();
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

	public Set<Position> getRochers() {
		return caseR;
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

	public void surbrillanceBases() {
		for (Joueur j : bases.keySet()) {
			for (Position p : bases.get(j)) {
				cases[p.getX()][p.getY() + (int) p.getX() / 2].setColor(j
						.getCol());
			}
		}
	}

	public void ResetCouleur() {
		for (int i = 0; i < nCasesX; i++) {
			for (int j = 0; j < nCasesY; j++) {
				cases[i][j].ResetCouleur();
			}
		}
	}

	public void ResetCouleurBaseJoueur(Joueur j) {
		System.out.println(bases.containsKey(j));
		if (bases.containsKey(j)) {
			ArrayList<Position> a = (ArrayList<Position>) bases.get(j);
			if (a != null) {
				Iterator<Position> i = a.iterator();
				Position p;
				while (i.hasNext()) {
					p = i.next();
					cases[p.getX()][p.getY() + (int) p.getX() / 2]
							.ResetCouleur();
				}

			}
		}
	}

	public void ResetCouleurCase(Position p) {
		cases[p.getX()][p.getY() + (int) p.getX() / 2].ResetCouleur();
	}

	public Phare[] getPhares() {
		Phare[] ph = new Phare[phares.size()];
		int i = 0;
		for (Phare n : phares) {
			ph[i] = n;
			i++;
		}
		return ph;
	}

}
