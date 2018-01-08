package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditCanonS implements EtatModif {
	private static EditCanonS etat = new EditCanonS();
	private EditCanonS(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	
	public void clique(Position pos, ControleurModif c) {
		c.getEditeur().getCanonS().addZoneTire(pos);
		System.out.println("okkkk");
		c.getEditeur().getMap().setColorTire(pos); 
	}

	
	public void modifMap(ControleurModif c) {
		//c.setEtat(EditRocher.getEtat());
	}

	
	public void setCurrentJ(String str) {
	}

	
	public void modifCanonS(ControleurModif c) {
		c.setEtat(Init.getEtat());
		
	}

	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
		
	}

}
