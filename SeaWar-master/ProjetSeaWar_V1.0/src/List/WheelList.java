package List;

import java.io.Serializable;

public class WheelList<E> implements Serializable{

	private static final long serialVersionUID = 827396534119862988L;
	public static void main(String args []){
		WheelList<String> w = new WheelList<String>();
		
		w.add("I'm glad to meet you ");
		w.add("Can you give me your name ? ");
		
		System.out.println(w.toString());
	}
	
	private Elements<E> first;
	public Iterator<E> i;
	public WheelList(){
		super();
		first = null;
		i = null;
	}
	
	public Iterator<E> getIterator(){
		if(first != null){
			i = new Iterator<E>(first);
		}
		return i;
	}
	
	public void add(E o){
		if(first == null){
			first = new Elements<E>(o);
		}else{
			first.add(o);
		}
		
	}
	
	public String toString(){
		Elements<E> current = first;
		String str = "";
		do{
			//System.out.println(str);
			str+=current.getObj().toString();
			
			str+="\n";
			current = current.next();
		}while(current != first);
		return str;
	}
}