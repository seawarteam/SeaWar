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
		//getInfo();
		
		Navire n = c.getPartie().getNavOnPos(pos);
		if(n != null){
			n.getInfo();
			if(n.getNomJ().equals(c.getPartie().getCurrentJ().getNom())){
				c.getPartie().getCurrentJ().setCurrentN(n);
				n.getPrivateInfo();
			}
		}else{
			Case cases = c.getPartie().getCaseOnPos(pos);
			cases.getInfo();
		}
		
	}
	
	public static EtatInit getEtat() {
		return e;
	}

}
