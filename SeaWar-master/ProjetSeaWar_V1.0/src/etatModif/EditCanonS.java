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
	}

	public void modifCanonS(ControleurModif c) {
		c.setEtat(Init.getEtat());
		
	}

	
	public void modifCanonP(ControleurModif c) {
	}

	@Override
	public void modifRocher(ControleurModif c) {
	}

	@Override
	public void modifEau(ControleurModif c) {
	}

	@Override
	public void modifPhare(ControleurModif c) {
	}

	@Override
	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

}
