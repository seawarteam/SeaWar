
import java.util.*;

public class Phare extends Eau {

    
 private Joueur proprio //J1,J2 ou Null
     
     
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
