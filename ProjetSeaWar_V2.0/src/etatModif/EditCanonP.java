package etatModif;

import partie.ControleurModif;
//import partie.EditCanonS;
import partie.Position;
import java.util.List;

public class EditCanonP implements EtatModif {
	private static EditCanonP etat = new EditCanonP();
	private Position posRef = new Position(10,5);
	private EditCanonP(){
		super();
	}
	
	public Position getRefPos() {
		return posRef;
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position pos, ControleurModif c) {
		if(!pos.equals(posRef)) { // on peut pas tirer sur soi
			Position posRelative = new Position(pos.getX()- posRef.getX(),pos.getY() - posRef.getY());
			List<Position> zoneTire = c.getEditeur().getCanonP().getZoneTire();
			boolean present = false;
			for (Position p : zoneTire) {
				if(p.equals(posRelative)) {
					present = true;
					posRelative = p;
					break;
				}
			}
			
			
			if (!present) {
				c.getEditeur().getCanonP().addZoneTire(posRelative);
				c.getEditeur().getMap().setColorTire(pos);
			} else {
				zoneTire.remove(posRelative);
				c.getEditeur().getMap().getCase(pos).ResetCouleur();
			}
		}
	}

	
	public void modifMap(ControleurModif c) {
	}

	
	public void setCurrentJ(String str) {
	}

	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(Init.getEtat());
		
	}

	
	public void modifCanonS(ControleurModif c) {
		
	}

	
	public void modifRocher(ControleurModif c) {
	}

	
	public void modifEau(ControleurModif c) {
	}

	
	public void modifPhare(ControleurModif c) {
	}

	
	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

}
