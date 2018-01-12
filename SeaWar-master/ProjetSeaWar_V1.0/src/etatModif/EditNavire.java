package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditNavire implements EtatModif{
	
	private static EtatModif etat = new EditNavire();
	
	private EditNavire() {
		super();
	}

	public static EtatModif getEtat(){
		return etat ;
	}

	public void clique(Position pos, ControleurModif c) {
		
	}

	public void modifCanonP(ControleurModif c) {
		
	}

	public void modifRocher(ControleurModif c) {
		
	}

	public void modifEau(ControleurModif c) {
		
	}

	public void modifPhare(ControleurModif c) {
		
	}

	public void modifBase(ControleurModif c, String str) {
		
	}






}
