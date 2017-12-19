package partie;

import java.util.*;

/**
 * 
 */
public class Position {
	
	private int x;
    private int y;
    
    public static Position [] tabPosition;
    private static int tailleX;
    private static int tailleY;
    
    public static int getTailleX() {
    	return tailleX;
    }
    
    public static int getTailleY() {
    	return tailleY;
    }

    public Position(int x, int y) {
    	this.x = x;
    	this.y = y;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public String toString() {
        return "("+this.x+";"+this.y+")";
    }
    
    public boolean equals(Object obj){
    	if(this == obj) return true;
    	if(!(obj instanceof Position)) return false;
    	Position pos = (Position) obj;
    	return (this.x == pos.x && this.y == pos.y);
    }

    /**
     * Méthode statique qui permet d'initialiser toutes les positions du plateau de dimension tailleX*tailleY
     * A utiliser une seule fois au début de la partie !!!
     * @param tailleX
     * @param tailleY
     */
    public static void initTabPosition(int tailleX, int tailleY){
    	Position.tabPosition = new Position [tailleX * tailleY];
    	for(int i=0; i<tailleX; i++) {
    		for(int j=0; j<tailleY; j++) {
    			Position.tabPosition[i * tailleY + j] = new Position(i,j-(int)i/2);
    		}
    	}
    	Position.tailleX = tailleX;
    	Position.tailleY = tailleY;    	
    }
    
    /**
     * Méthode statique qui permet de réccupérer la Position à l'emplacement (x,y)
     * => Permet éviter de créer à chaque de nouveau objet Position
     * @param x
     * @param y
     * @return
     */
    public static Position getPosition(int x, int y) {
    	if(x < 0) return null;
    	if(y+(int) x/2 < 0) return null;
    	if(x >= Position.tailleX) return null;
    	if(y+(int) x/2 >= Position.tailleY) return null;
    	return Position.tabPosition[x * Position.tailleY + (y + (int) x/2)];
    }
    
    public static void main(String [] args) {
    	initTabPosition(20, 20);
    	System.out.println(getPosition(0, 4).toString());
    	for(int i=0;i<tailleX*tailleY;i++){
    		if(i%tailleY==0)System.out.println();
    		System.out.print(tabPosition[i]+" ");
    		
    	}
    }
    
}