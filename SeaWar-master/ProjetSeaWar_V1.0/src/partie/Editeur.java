package partie;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class Editeur extends Observable{
	Plateau map;
	Canons canonP;
	Canons canonS;
	Navire navire;
	private int nX, nY;
	private Observer obs;
	public Editeur(int nX, int nY, Observer obs){
		navire = new Navire("", 0, 0, "", Orientation.N, new Position(0, 0), obs);
		canonP = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		canonS = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		addObserver(obs);
		map = new Plateau(nX, nY, 0, 0, obs);
		this.nX = nX;
		this.nY = nY;
		this.obs = obs;
	}

	public Plateau getMap() {
		return map;
	}
	
	public Canons getCanonP(){
		return canonP;
	}
	
	public Canons getCanonS(){
		return canonS;
	}
	
	public void resetPlateau(){
		map = new Plateau(nX, nY, 0, 0, obs);
		map.ResetCouleur();
	}
	
	
}