package mvc;

import java.util.Vector;

public class Observable {
	private Vector<Observer> obs;
	private boolean hasChanged;
	public Observable(){
		super();
		obs = new Vector<Observer>();
		hasChanged = false;
	}
	
	public void addObserver(Observer carte){
		obs.add(carte);
	}
	
	public void deleteObserver(Observer o){
		obs.remove(o);
	}
	
	public void setChange(){
		hasChanged = true;
	}
	
	public void clearChange(){
		hasChanged = false;
	}
	
	public void notifyObserver(Observer o){
		if(hasChanged){
			o.update();
		}
		clearChange();
	}
	
	public void notifyObservers(){
		if(hasChanged){
			for(Observer o : obs){
				o.update();
			}
		}
		clearChange();
	}
	
	
}
