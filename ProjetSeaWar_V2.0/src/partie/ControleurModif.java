package partie;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;

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

	public Editeur getEditeur() {
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

		//getEditeur().resetPlateau();
		etat = EditCanonP.getEtat();
		getEditeur().map.setColor(
				((EditCanonP) EditCanonP.getEtat()).getRefPos(), Color.blue);

	}
	
	public void demandeModifCarte() {
		getEditeur().resetPlateau();//techniquement pas utile mais évite des pb d'affichage
		etat = EditRocher.getEtat();
	}
	
	public void demandeRetour() {
		getEditeur().resetPlateau();
		getEditeur().resetCanon();
		etat = Init.getEtat();
	}
	
	public void demandeModifNavire() {
		getEditeur().resetPlateau();//techniquement pas utile mais évite des pb d'affichage
		etat = EditNavire.getEtat();
	}

	/*
	 * public void demandeModifCanonS() { getEditeur().resetPlateau();
	 * etat.modifCanonS(this);
	 * 
	 * }
	 */

	public void demandeAjoutBase(String str) {
		etat.modifBase(this, str);

	}

	public void demandeSauvegardeMap(String nom) {
		if (nom != null || nom != "" || nom != " ") {
			editeur.sauvegarderMap(nom);
		}
	}

	public void demandeSauvegardeNavire(String nom) {
		if (nom != null || nom != "" || nom != " ") {
			editeur.getNavire().setNom(nom);
			editeur.sauvegarderNavire(nom);
		}
	}
	
	public void demandeSauvegardeCanon(String nom) {
		if (nom != null || nom != "" || nom != " ") {
			editeur.getCanonP().setNom(nom);
			editeur.sauvegarderCanon(nom);
		}
	}
	

}
  