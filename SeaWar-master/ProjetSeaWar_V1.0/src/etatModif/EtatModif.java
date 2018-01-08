package etatModif;

import javax.swing.ListModel;

import partie.Controleur;
import partie.ControleurModif;
import partie.Joueur;
import partie.Position;

public interface EtatModif {
	public void clique(Position pos, ControleurModif c);
	public void modifMap(ControleurModif c);
	public void modifCanonP(ControleurModif c);
	public void modifCanonS(ControleurModif c);
	public void setCurrentJ(String str);

	
}
