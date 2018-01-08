package partie;

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
	
	public void demandeModifMap(){
		//Sauvegarder la carte
		getEditeur().resetPlateau();
		//Débloquer les boutons (eau, phare, rocher)
		etat.modifMap(this);
	}
	
	public Editeur getEditeur(){
		return editeur;
	}

	public void demandeAjoutEau() {
		etat.modifMap(this);
	}
	
	public void demandeAjoutRocher() {
		etat.modifMap(this);		
	}
	
	public void demandeAjoutPhare() {
		etat.modifMap(this);		
	}

	public void demandeModifCanonP() {
		getEditeur().resetPlateau();
		System.out.println("1111");
		etat = EditCanonP.getEtat();
		
	}
	
	public void demandeModifCanonS() {
		getEditeur().resetPlateau();
		etat = EditCanonS.getEtat();
		
	}

	public void demandeAjoutBase(String str) {
		setEtat(EditBase.getEtat());
		etat.setCurrentJ(str);
	}

	
	

	
	

	

	

}
