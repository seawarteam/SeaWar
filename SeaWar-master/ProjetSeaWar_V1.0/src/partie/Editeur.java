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
	
	public Editeur(int nX, int nY, Observer obs){
		navire = new Navire("", 0, 0, "", Orientation.N, new Position(0, 0), obs);
		canonP = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		canonS = new Canons("", 0, 0, new LinkedList<Position>(), navire);
		addObserver(obs);
		map = new Plateau(nX, nY, 0, 0, obs);
	}

	public Plateau getMap() {
		return map;
	}
	
}
