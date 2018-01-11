package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditCarte implements EtatModif {
	private static EditCarte etat = new EditCarte();
	private EditCarte(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}
	@Override
	public void clique(Position pos, ControleurModif c) {
	}


	@Override
	public void modifCanonP(ControleurModif c) {
	}

	@Override
	public void modifCanonS(ControleurModif c) {
	}

	@Override
	public void modifRocher(ControleurModif c) {
		c.setEtat(EditRocher.getEtat());
	}

	@Override
	public void modifEau(ControleurModif c) {
		c.setEtat(EditEau.getEtat());
	}

	@Override
	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}



	@Override
	public void modifBase(ControleurModif c, String str) {
		c.setEtat(EditBase.getEtat());
		EditBase.getEtat().modifBase(c, str);
		
	}

}
