package virtualbuddy;

import java.util.LinkedList;
import java.util.StringTokenizer;

//import eis.iilang.*;

public class SpeechAct {
	
	private String mode;
	private String source;
	//private Parameter param;
	private String nlUtterance;
	private String code;
	private LinkedList<String> responseOptions;
	
	public SpeechAct(String m, String s, String c, UtteranceParser uParser){
		mode = m;
		source = s;
		code = c; 
		Utterance2 u = uParser.findUtterance(source, code);
		nlUtterance = u.getUtterance();
		responseOptions = u.getResponseOptions();
		//param = code2param(code);	
	}
	
	/*
	public SpeechAct(Action action, UtteranceParser uParser){
		LinkedList<Parameter> params = action.getParameters();
		// say(FromAgent, ToAgent, Type, Code)

		mode = params.get(2).toProlog();
		source = params.get(0).toProlog();
		code = params.get(3).toProlog();
		param = params.get(3);
		
		//String result = ""+params.get(0).toProlog()+" zegt: "+params.get(1).toProlog();
		
		Utterance2 u = uParser.findUtterance(source, code);
		nlUtterance = u.getUtterance();
		responseOptions = u.getResponseOptions();
	}*/
	
	
	public SpeechAct(double p, double a, double d, String c){
		mode = "inform";
		source = "user";
		responseOptions = new LinkedList<String>();
		code = "incident("+code2fact(c)+","+"pad("+p+","+a+","+d+"))";		
		nlUtterance = code;
	}
		
	public String getTarget(){
		if(source.equals("user")) return "robin";
		else return "user";
	}
	
	/*
	public Percept toPercept(){	
		return new Percept("said", new Identifier(source), new Identifier(getTarget()), new Identifier(mode), param);
	}*/
	
	public String code2fact(String c){
		String fact = null;
		String part;
		LinkedList<String> list = new LinkedList<String>();
		
		StringTokenizer st=new StringTokenizer(c, "(,)");
		while(st.hasMoreTokens()){
			part = st.nextToken().trim();
			//System.out.println(part);
			list.add(part);
		}
		
		if(list.size()>1){
			fact = list.get(1);
		}
		
		return fact;
	}
	
	public String getNLUtterance(){
		return nlUtterance;
	}
	
	public static void main(String[] args){
		UtteranceParser up = new UtteranceParser();
		//SpeechAct sa = new SpeechAct("inform", "user", "incident(q1,a1)", up);
		SpeechAct sa = new SpeechAct(0.1, 0.2, 0.3, "incident(feel,x)");
		//sa.code2param("incident(q1,a1,oo)");
		//sa.code2param("greet");
		//System.out.println(sa);
	}
	
	public String getCode(){
		return code;
	}
	
	public LinkedList<String> getResponseOptions(){
		return responseOptions;
	}
	
	public String getType(){
		return mode;
	}
	
	@Override
	public String toString(){
		return "Speech Act\n----------\nMode: "+mode+"\nSource: "+source+"\nNL utterance: "+nlUtterance+"\nCode: "+code+"\n";
	}

}
