package List;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Iterator<E> implements Serializable {
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