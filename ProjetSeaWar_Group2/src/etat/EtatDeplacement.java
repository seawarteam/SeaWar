package etat;

import java.util.Set;

import partie.Canons;
import partie.Navire;
import partie.Orientation;
import partie.Position;

public interface EtatDeplacement {
	public void setEtatCourant(Navire n);
	public void setEtatInapte(Navire n);
    public boolean tir(Canons canon, Position pos, Navire previous, Navire current, Set<Position> rochers);
    public boolean deplacement(Position pos, Orientation dir, int nbCase, Navire previous, Navire current);
}
