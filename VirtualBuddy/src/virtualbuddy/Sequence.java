package virtualbuddy;

public class Sequence {
	private String name;
	private Sequence nextSeq;
	
	public Sequence(String n){
		name = n;
		nextSeq = null;
	}
	
	public Sequence(String n, Sequence s){
		name = n;
		nextSeq = s;
	}
	
	public void setNextSeq(Sequence s){
		nextSeq = s;
	}
	
	public String getName(){
		return name;
	}
	
	public Sequence getNextSeq(){
		return nextSeq;
	}

}
