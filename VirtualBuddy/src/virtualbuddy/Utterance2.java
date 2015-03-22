package virtualbuddy;

import java.util.LinkedList;

/**
 * Hulpmiddel bij parsen xml
 * @author jvanderzwaan
 *
 */

public class Utterance2 {
	private String code;
	private String utterance;
	private LinkedList<String> responseOptions;
	
	
	public Utterance2(String c){
		code = c;
		responseOptions = new LinkedList<String>();
		utterance = "";
	}
	
	public void setUtterance(String u){
		utterance = u;
	}
	
	public String getCode(){
		return code;
	}
	
	public LinkedList<String> getResponseOptions(){
		return responseOptions;
	}
	
	public String getUtterance(){
		String result;
		
		if(utterance.equals("")) result = code;
		else result = utterance;
		
		return result;
	}

	public void addResponseOption(String ro) {
		responseOptions.add(ro);
	}
	
	public String toString(){
		String result = "Utterance\n---------\ncode: "+code+"\nUtterance: "+getUtterance();
		if(responseOptions.size()>0){
			result = result + "\nResponse options: \n";
			for(String ro : responseOptions){
				result = result + "- "+ro+"\n";
			}
		}
		
		return result;
	}

}
