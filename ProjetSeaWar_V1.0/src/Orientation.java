

public enum Orientation {
    N(0),
    S(1),
    NO(2),
    SO(3),
    NE(4),
    SE(5);
    
    public int value;
    private Orientation(int value){
    	this.value = value;
    }
}