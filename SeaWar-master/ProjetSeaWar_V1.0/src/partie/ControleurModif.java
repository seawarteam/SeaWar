package partie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;

import etatModif.*;

public class ControleurModif {
	private EtatModif etat = EditCarte.getEtat();
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
	
	
	public Editeur getEditeur(){
		return editeur;
	}

	public void demandeAjoutEau() {
		etat.modifEau(this);
	}
	
	public void demandeAjoutRocher() {
		etat.modifRocher(this);		
	}
	
	public void demandeAjoutPhare() {
		etat.modifPhare(this);		
	}

	public void demandeModifCanonP() {
		getEditeur().resetPlateau();
		etat.modifCanonP(this);
		
	}
	
	public void demandeModifCanonS() {
		getEditeur().resetPlateau();
		etat.modifCanonS(this);
		
	}

	public void demandeAjoutBase(String str) {
		etat.modifBase(this, str);
		
	}

	public void demandeSauvegardeMap(String nom) {
		if(nom != null || nom!="" || nom!=" ") {
			editeur.sauvegarderMap(nom);
		}
	}

	public void demandeCharger(String nom) {
		if(nom != null || nom!="" || nom!=" ") {
			editeur.chargerMap(nom);
		}
	}

	
	

	
	

	

	

}
