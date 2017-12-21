package partie;

import java.util.HashSet;
import java.util.Set;

import etatModif.*;

public class ControleurModif {
	private EtatModif etat = Init.getEtat();
	private Editeur editeur;
	
	public ControleurModif(Editeur e) {
		super();
		editeur = e;
	}

	public void setEtat(EtatModif e) {
		etat = e;
	}

	public void hexClique(Position pos) {
		etat.clique(pos, this);

	}

	

}
