package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditRocher implements EtatModif {
	private static EditRocher etat = new EditRocher();
	private EditRocher(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position p, ControleurModif c) {
		c.getEditeur().getMap().setCaseRocher(p);
	}
	
	public void modifMap(ControleurModif c) {
		c.setEtat(Init.getEtat());
	}

	@Override
	public void setCurrentJ(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
		
	}

	@Override
	public void modifCanonS(ControleurModif c) {
		c.setEtat(EditCanonS.getEtat());
		
	}

	

}
