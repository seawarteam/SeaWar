package partie;

import ia.Abstract_IA;
import ia.Controleur_IA;
import ia.Default_IA;
import id.ID_Joueur;
import id.ID_Navire;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;

import etat.Bloque;
import etat.Courant;
import etat.Detruit;

public class Joueur implements Serializable {
	private static final long serialVersionUID = 5754584580014620245L;

	private Color couleur;
	private String nom;
	private List<Navire> navires;
	private Navire currentN;// Navire selectionne dans l'interface
	
	private ID_Joueur id;
	private boolean isBot;
	private Abstract_IA ia;

	/**
	 * Default constructor
	 */
	public Joueur(String s, Color couleurPerso) {
		this.nom = s;
		navires = new ArrayList<Navire>();
		couleur = couleurPerso;
	}
	
	public Joueur(String s, Color couleurPerso, Navire n, Canons canonP, Canons canonS) {
		this.nom = s;
		navires = new ArrayList<Navire>();
		n.addCanon(canonP, canonS);
		navires.add(n);
		couleur = couleurPerso;
		//id = new ID_Joueur(ID_Joueur.getNewID());
		isBot = false;
		
	}
	public Joueur(String s, Color couleurPerso, Navire n, Canons canonP, Canons canonS, Navire n2, Canons canonP2, Canons canonS2, boolean isBot) {
		this.nom = s;
		navires = new ArrayList<Navire>();
		n.addCanon(canonP, canonS);
		n2.addCanon(canonP2,  canonS2);
		navires.add(n);
		navires.add(n2);
		couleur = couleurPerso;
		id = new ID_Joueur(ID_Joueur.getNewID());
		System.out.println(id);
		this.isBot = isBot;
		if(isBot) {
			ia = new Default_IA(id);
		}
	}

	public Vector<Navire> ajoutDefaultNavire(Observer obs) {
		Vector<Navire> navs = new Vector<Navire>(2);
		navs.add(new Navire("Fregate", 50, 7, this.nom, Orientation.N, Position
				.getPosition(0, 0), obs));
		navs.add(new Navire("Amiral", 100, 3, this.nom, Orientation.N, Position
				.getPosition(0, 1), obs));
		List<Position> liste = new LinkedList<Position>();
		liste.add(new Position(0, -1));
		liste.add(new Position(0, -2));
		liste.add(new Position(0, -3));
		liste.add(new Position(0, -4));
		Canons unCanon = new Canons("La Grosse Berta", 200, 2, liste,
				navs.get(0));
		navs.get(0).addCanon(unCanon, unCanon);
		unCanon = new Canons("La Grosse Berta", 200, 2, liste, navs.get(1));
		navs.get(1).addCanon(unCanon, unCanon);
		navires.add(navs.get(0));
		navires.add(navs.get(1));
		return navs;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Joueur)) {
			return false;
		}
		Joueur j = (Joueur) obj;
		return this.nom.equals(j.nom);
	}

	public String toString() {
		String navs = "\n\t";
		for (Navire nav : navires) {
			navs = navs + nav.toString();
			navs = navs + "\n\t ";
		}
		return "Joueur\tid_Joueur = "+id+"\tnom = " + this.nom + "\tNavires :" + navs + "\n";
	}

	public List<Navire> getListNavires() {
		return navires;
	}

	public String getNom() {
		return this.nom;
	}
	
	public ID_Joueur getID() {
		return id;
	}

	public void addNavire(Navire nav) {
		this.navires.add(nav);
	}

	public boolean deplacement(Position p, Orientation dir, int nbCase) {
		return currentN.deplacement(p, dir, nbCase, getNavEtatCourant());
	}

	public boolean isDead() {
		boolean stop = false;
		Navire n;
		Iterator<Navire> i = navires.iterator();
		while (!stop && i.hasNext()) {
			n = i.next();
			if (!n.getEtatCourant().equals(Detruit.getEtat())) {
				stop = true;
			}
		}
		return !stop;
	}

	/**
	 * @param pos
	 *            : la position de classe Position
	 * @return le Navire situer sur la case pos qui n'est pas detruit
	 */
	public Navire getNavOnPos(Position pos) {

		for (Navire nav : this.navires) {
			if (nav.getPos().equals(pos) && !(nav.getEtatCourant() instanceof Detruit)) {
				return nav;
			}
		}
		return null;
	}

	public Navire getNavEtatCourant() {
		for (Navire nav : navires) {
			if (nav.getEtatCourant() == Courant.getEtat()) {
				return nav;
			}
		}
		return null;
	}

	public Navire getCurrentN() {
		return currentN;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCurrentN(Navire nav) {
		this.currentN = nav;
	}

	public void initTour() {
		for (Navire nav : navires) {
			nav.initTour();
		}
		if(isBot) {
			ia.executerIA();
		}
	}

	public Navire[] getNavires() {
		Navire[] nav = new Navire[navires.size()];
		int i = 0;
		for (Navire n : navires) {
			nav[i] = n;
			i++;
		}
		return nav;
	}
	
	public Color getCol() {
		return couleur;
	}
	public void RechercheNaviresBloque(Set<Position> rochers) {
		for(Navire nav : navires) {
			if(nav.getEtatCourant() != Detruit.getEtat()) {
				List<Vector<Object>> cases = Navire.caseVoisine.get(nav.getDir());
				boolean libre = false;
				for(Vector<Object> vect : cases) {
					Position pos = (Position) vect.get(0);
					int x = nav.getPos().getX() + pos.getX();
					int y = nav.getPos().getY() + pos.getY();
					if(!rochers.contains(Position.getPosition(x, y))){
						libre = true;
					}
				}
				if(!libre) {
					nav.setEtat(Bloque.getEtat());
					nav.setADejaTire(true);
				}
			}
		}
	}

	
	public boolean haveNavire(ID_Navire id) {
		for(Navire nav : getNavires()) {
			if(nav.getID() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean isBot() {
		return isBot;
	}

	public Abstract_IA getIA() {
		return ia;
	}

	public void setID(ID_Joueur newID) {
		id = newID;		
	}
}
