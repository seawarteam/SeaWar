package partie;

import java.awt.Color;

import javax.swing.ListModel;

import erreur.*;
import etatModif.*;
import util.Save;

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
		getEditeur().resetPlateau();//techniquement pas utile mais Ã©vite des pb d'affichage
		etat = EditRocher.getEtat();
	}
	
	public void demandeRetour() {
		getEditeur().resetPlateau();
		getEditeur().resetCanon();
		etat = Init.getEtat();
	}
	
	public void demandeModifNavire() {
		getEditeur().resetPlateau();//techniquement pas utile mais Ã©vite des pb d'affichage
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

	public void demandeSauvegardeMap(String nom) throws FichierExistant, ChampsInvalides{
		if (nom != null && nom != "" && nom != " ") {
			if(editeur.getMap().getNbBasesValides()>=2) {
				Save.sauvegarderMap(nom, editeur.getMap());
			}else {
				throw new ChampsInvalides("Deux base obligatoires");
			}
			
		}
	}

	public void demandeSauvegardeNavire(String nom) throws FichierExistant {
		if (nom != null && nom != "" && nom != " ") {
			editeur.getNavire().setNom(nom);
			Save.sauvegarderNavire(nom, editeur.getNavire());
		}
	}
	
	public void demandeSauvegardeCanon(String nom) throws FichierExistant, ChampsInvalides {
		
		if(editeur.getCanonP().getZoneTire().size() != 0) {
			if (nom != null && nom != "" && nom != " ") {
				editeur.getCanonP().setNom(nom);
				Save.sauvegarderCanon(nom, editeur.getCanonP());
			}
		}else {
			throw new ChampsInvalides("La zone de tire doit être créée");
		}
		
	}
	

}
  
