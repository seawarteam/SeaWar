package partie;


public enum Orientation {
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
    
}