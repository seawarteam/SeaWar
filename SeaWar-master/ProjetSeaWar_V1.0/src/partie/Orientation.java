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
    	if(x==-1 && y==0) {
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
    
}