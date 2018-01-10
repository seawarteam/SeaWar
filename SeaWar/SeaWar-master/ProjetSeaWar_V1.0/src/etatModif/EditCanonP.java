package etatModif;

import partie.ControleurModif;
//import partie.EditCanonS;
import partie.Position;

public class EditCanonP implements EtatModif {
	private static EditCanonP etat = new EditCanonP();
	private EditCanonP(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position pos, ControleurModif c) {
		c.getEditeur().getCanonP().addZoneTire(pos);
		System.out.println("okkkk");
		c.getEditeur().getMap().setColorTire(pos); 
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
