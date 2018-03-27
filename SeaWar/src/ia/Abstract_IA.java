package ia;

import id.ID_Joueur;

import java.io.Serializable;


public abstract class Abstract_IA implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final ID_Joueur monJoueur;
	protected Controleur_IA controleur;
	
	public Abstract_IA(ID_Joueur id) {
		this.monJoueur = id;
	}
	
	public void setControleur(Controleur_IA controleur) {
		this.controleur = controleur;
	}
	
	abstract public void executerIA();
}
