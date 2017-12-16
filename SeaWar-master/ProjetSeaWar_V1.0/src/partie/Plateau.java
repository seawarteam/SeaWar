package partie;

import java.awt.Polygon;
import java.util.*;

public class Plateau {
	
	private Case cases[][];
    
    private int nCasesX;
    private int nCasesY;
    private static int longueurCote;
    private static int apotheme;
    private static int resteX;
    
    public Plateau(int l, int L, int nbPhares, int nbRochers) {
    	cases = new Case[l][L];
        nCasesX = l;
        nCasesY = L;
        initTabHex(nbPhares, nbRochers);
        
        //TO DO : pouvoir retourner un set d'obstacles (tout les obstacles présents) 
        //--> faire une liste "fixe" avec les rochers et une autre "changeante" avec les bateaux.
            
    }
    
    
  //crée un hexagone au coordonnées pixel x0,y0 (!!! pour l'insant, x0 et y0 sont les coordonnées en pixels)
  	public static Polygon hexagone(int x0, int y0) {
  		int x = x0;
  		int y = y0;

  		int[] cx, cy; // tableau de coordonnées x et y de tous les points d'un hexagone en commencant par le point en haut à gauche

  		cx = new int[] {x+resteX,x+longueurCote+resteX,x+longueurCote+resteX+resteX,x+longueurCote+resteX,x+resteX,x,x+resteX};
  		cy = new int[] {y,y,y+apotheme,y+apotheme+apotheme,y+apotheme+apotheme,y+apotheme,y};

  		return new Polygon(cx,cy,6);
  	}
  	
    private void initTabHex(int nbPhares, int nbRochers) {
		int x,y;
		
		int nbx, nby;
		ArrayList<Position> tabPosUtil = new ArrayList<Position>();
		boolean ok = false;
		
		for (int i=0;i<nCasesX;i++) {
			for (int j=0;j<nCasesY;j++) {
				x = i * (longueurCote+resteX);
				y = j * apotheme*2 + (i%2) * apotheme;
				Polygon poly = hexagone(x,y);
				cases[i][j]= new Eau(poly, x, y);
			}
		}
		//On vient positionner les entiés (Phares, Rochers)
		for(int i=1; i<=nbPhares; i++){
			Position p;
			ok = false;
			while(!ok){
				nbx = 0 + (int)(Math.random() * ((nCasesX - 1) + 1));
				nby = 0 + (int)(Math.random() * ((nCasesY - 1) + 1));
				p = Position.getPosition(nbx, nby - (int)nbx/2);
				if(!tabPosUtil.contains(p)){
					tabPosUtil.add(p);
					x = nbx * (longueurCote+resteX);
					y = nby * apotheme*2 + (i%2) * apotheme;
					Polygon poly = hexagone(x,y);
					cases[nbx][nby]= new Phare(poly, x, y);
					ok  = true;
				}
			}
		}
		for(int i=1; i<=nbRochers; i++){
			Position p;
			ok = false;
			while(!ok){
				nbx = 0 + (int)(Math.random() * ((nCasesX - 1) + 1));
				nby = 0 + (int)(Math.random() * ((nCasesY - 1) + 1));
				p = Position.getPosition(nbx, nby - (int)nbx/2);
				if(!tabPosUtil.contains(p)){
					tabPosUtil.add(p);
					tabPosUtil.add(p);
					x = nbx * (longueurCote+resteX);
					y = nby * apotheme*2 + (i%2) * apotheme;
					Polygon poly = hexagone(x,y);
					cases[nbx][nby]= new Rocher(poly, x, y);
					ok  = true;
				}
			}
		}
		
	}
    
    public Case[][] getCases(){
    	return cases;
    }
    
    public void surbrillanceO(Set<Position> s){
    	for(Position p : s){
    		cases[p.getX()][p.getY()+(int)p.getX()/2].surbrillanceO();
    	}
    }
    
    public void surbrillanceD(Set<Position> s){
    	for(Position p : s){
    		cases[p.getX()][p.getY()+(int)p.getX()/2].surbrillanceD();
    	}
    }
    
    public void surbrillanceT(Set<Position> s){
    	for(Position p : s){
    		cases[p.getX()][p.getY()+(int)p.getX()/2].surbrillanceT();
    	}
    }
    


	public void ResetCouleur() {
		for(int i=0; i < nCasesX; i++) {
			for (int j=0; j < nCasesY; j++) {
				cases[i][j].ResetCouleur();
			}
		}
		
	}

}
