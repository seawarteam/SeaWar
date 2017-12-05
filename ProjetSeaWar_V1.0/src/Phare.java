
import java.awt.Color;
import java.util.*;

public class Phare extends Eau {

    public static final Color couleurVide = Color.PINK;
    public static final Color couleurOccupe = Color.RED;
    
	private Joueur proprio ; //J1 ou J2 ou null
	
	
    public Phare() {
        super();
        proprio=null;
    }
    
    public Joueur getProprio(){
        return proprio;
    }
    
    public void setProprio(Joueur proprio){
        this.proprio=proprio;
    }
}
