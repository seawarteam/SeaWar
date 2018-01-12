package List;

import java.io.Serializable;

class Elements<E> implements Serializable {
	protected E obj;
	protected Elements<E> next;
	protected Elements<E> previous;
	
	protected Elements(E e, Elements<E> isNext, Elements<E> isPrevious){
		obj = e;
		next = isNext;
		previous = isPrevious;
	}
	
	protected Elements(E e){
		obj = e;
		next = this;
		previous = this;
	}
	
	protected Elements<E> next(){
		return next;
	}
	
	protected void setPrevious(Elements<E> isPrevious){
		previous = isPrevious;
	}
	
	protected Elements<E> previous(){
		return previous;
	}
	
	protected void add(E o){
		Elements<E> newE = new Elements<E>(o, this.next(), this);
		this.next().setPrevious(newE);
		next = newE;
	}
	
	protected E getObj(){
		return obj;
	}
	
	public String toString(){
		return obj.toString();
	}
	
}
    