package List;

public class WheelList<E>{
	public static void main(String args []){
		WheelList<String> w = new WheelList<String>();
		
		w.add("I'm glad to meet you ");
		w.add("Can you give me your name ? ");
		w.add("Hahahahah");
		
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
			str+=current.getObj().toString();
			str+="\n";
			current = current.next();
		}while(current != first);
		return str;
	}
}