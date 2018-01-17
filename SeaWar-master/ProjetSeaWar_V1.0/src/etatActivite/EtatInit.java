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
		System.out.println("EtatInit "+pos);
		Navire n = c.getPartie().getNavOnPos(pos);
		if(n != null){
			System.out.println("ok navire");
			if(n.getNomJ().equals(c.getPartie().getCurrentJ().getNom())){
				c.getPartie().getCurrentJ().setCurrentN(n);
				System.out.println(c.getPartie().getCurrentJ().getCurrentN());
				n.getPrivateInfo();
			}
			n.getInfo();
			System.out.println("-------------");
		}else{
			System.out.println("ok case");
			//System.out.println(Position.toStringTab());
			Case cases = c.getPartie().getCaseOnPos(pos);
			System.out.println(cases.toString());
			
			cases.getInfo();
			System.out.println("-------------");
		}
		
	}
	
	public static EtatInit getEtat() {
		return e;
	}

}
