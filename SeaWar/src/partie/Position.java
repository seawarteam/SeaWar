package partie;

import java.io.Serializable;

public class Position implements Serializable {
	private static final long serialVersionUID = 536614019925950560L;
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
    
    public static String toStringTab(){
    	String str = "";
    	for(Position p : tabPosition){
    		str+=p.toString();
    	}
    	return str;
    }
    
    public boolean equals(Object obj){
    	if(this == obj) return true;
    	if(!(obj instanceof Position)) return false;
    	Position pos = (Position) obj;
    	return (this.x == pos.x && this.y == pos.y);
    }

    /**
     * Methode statique qui permet d'initialiser toutes les positions du plateau de dimension tailleX*tailleY
     * A utiliser une seule fois au debut de la partie !!!
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
     * Methode statique qui permet de reccuperer la Position a  l'emplacement (x,y)
     * => Permet eviter de creer a  chaque de nouveau objet Position
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
	
	public static Position[] getPosOnLineOfSight(Position ori, Position dest) {
		int ax, ay, az, bx, by, bz;
		int N;
		ax = ori.getX();
		ay = ori.getY() ;//+ (int) ax / 2;
		az = -ax - ay;
		bx = dest.getX();
		by = dest.getY();// + (int) bx / 2;
		bz = -by - bx;
		int[] a = { ax, ay, az };
		int[] b = { bx, by, bz };
		N = (Math.abs(ax - bx) + Math.abs(ay - by) + Math.abs(az - bz)) / 2;
		Position[] resultat = new Position[N + 1];
		for (int i = 0 ; i <= N ; i++) {
			float[] result = cube_round(cube_lerp(a, b, (float) (1.0 / N * i)));
			int x = (int) result[0];
			int y = (int) (result[1]/* - x / 2*/);
			resultat[i] = getPosition(x, y);
		}
		return resultat;
	}

	public static float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}

	public static float[] cube_lerp(int[] a, int[] b, float t) {
		float[] tab = new float[3];
		tab[0] = lerp(a[0], b[0], t);
		tab[1] = lerp(a[1], b[1], t);
		tab[2] = lerp(a[2], b[2], t);
		return tab;
	}

	private static float[] cube_round(float[] cube) {
		float rx = (float) cube[0];
		float ry = (float) cube[1];
		float rz = (float) cube[2];

		float x_diff = Math.abs(rx - cube[0]);
		float y_diff = Math.abs(ry - cube[1]);
		float z_diff = Math.abs(rz - cube[2]);

		if (x_diff > y_diff && x_diff > z_diff) {
			rx = -ry - rz;
		} else if (y_diff > z_diff) {
			ry = -rx - rz;
		} else {
			rz = -rx - ry;
		}

		return new float[] { rx, ry, rz };
	}
    
}
