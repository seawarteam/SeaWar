package etatActivite;

import partie.Case;
import partie.Controleur;
import partie.Navire;
import partie.Position;

public class EtatInit implements EtatAction{

	private static EtatInit e = new EtatInit();
	
	private EtatInit() {	
	}
	
	public void clique(Position pos, Controleur c) {
		
		Navire n = c.getPartie().getNavOnPos(pos);
		if(n != null){

			if(n.getNomJ().equals(c.getPartie().getCurrentJ().getNom())){
				c.getPartie().getCurrentJ().setCurrentN(n);
				n.getPrivateInfo();
			}
			n.getInfo();
		}else{
			Case cases = c.getPartie().getCaseOnPos(pos);
			cases.getInfo();
		}
		
	}
	
	public static EtatInit getEtat() {
		return e;
	}

}
