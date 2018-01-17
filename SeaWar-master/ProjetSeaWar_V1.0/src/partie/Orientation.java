package partie;

import java.io.Serializable;


public enum Orientation implements Serializable {
    N(0),
    NE(1),
    SE(2),
    S(3),
    SO(4),
    NO(5);
    
    public int value;
    private Orientation(int value){
    	this.value = value;
    }
    
    public static Orientation getOrientation (Position fromP, Position toP) {
    	int x = toP.getX() - fromP.getX();
    	int y = toP.getY() - fromP.getY();
    	
    	if(x==0 && y==-1) {
    		return Orientation.N;
    	}
    	if(x==1 && y==-1) {
    		return Orientation.NE;
    	}
    	if(x==1 && y==0) {
    		return Orientation.SE;
    	}
    	if(x==0 && y==1) {
    		return Orientation.S;
    	}
    	if(x==-1 && y==1) {
    		return Orientation.SO;
    	}
    	if(x==-1 && y==0) {
    		return Orientation.NO;
    	}
		return null;
    }
    
    public static Position getPosition (Position pos, Orientation or) {
		switch(or) {
		case N:
			return Position.getPosition(pos.getX(), pos.getY()-1);
		case NE:
			return Position.getPosition(pos.getX()+1, pos.getY()-1);
		case SE:
			return Position.getPosition(pos.getX()+1, pos.getY());
		case S:
			return Position.getPosition(pos.getX(), pos.getY()+1);
		case SO:
			return Position.getPosition(pos.getX()-1, pos.getY()+1);
		case NO:
			return Position.getPosition(pos.getX()-1, pos.getY());
		
		}
    	return pos;
    	
    }
    
    public static Orientation getDirOppose(Orientation dir)  {
    	switch(dir) {
			case S: return N;
			case N: return S;
			case SO: return NE;
			case SE: return NO;
			case NO: return SE;
			case NE: return SO;
    	}
    	return null;
    }
    
    public static Orientation aleatoire() {
		int n = 0 + (int)(Math.random() * ((5 - 0) + 1));
		switch(n) {
		case 0:
			return N;
		case 1:
			return NE;
		case 2:
			return SE;
		case 3:
			return S;
		case 4:
			return SO;
		case 5:
			return NO;
		default:
			return null;
		}
	}
    
}