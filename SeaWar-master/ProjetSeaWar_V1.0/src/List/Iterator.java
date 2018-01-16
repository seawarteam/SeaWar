package List;

import java.io.Serializable;

/**
 * 
 */
public class Iterator<E> implements Serializable{
	
	private static final long serialVersionUID = -1187299749324654383L;
	Elements<E> currentElements;
	protected Iterator(Elements<E> e){
		super();
		currentElements = e;
	}
	
	public boolean hasNext(){
		return true;
	}
	
	public E present(){
		return currentElements.getObj();
	}
	
	public E next(){
		currentElements = currentElements.next();
		return currentElements.getObj();
	}
	
	public E previous(){
		currentElements = currentElements.previous();
		return currentElements.getObj();
	}
    

}