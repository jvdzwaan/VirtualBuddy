package virtualbuddy;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UtteranceParser extends DefaultHandler{
	SAXParser sp;
	String tempVal;
	Utterance2 tempUtt;
	boolean thisOne;
	
	public UtteranceParser(){		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			sp = spf.newSAXParser();
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}
	
	public Utterance2 findUtterance(String source, String code){
		String fileName;
		
		tempUtt = new Utterance2(code);
		
		// file in jar
		if(source.equals("user")) fileName = "/xml/userUtterances-en.xml";
		else fileName = "/xml/agentUtterances-en.xml";
		//if(source.equals("user")) fileName = "/xml/userUtterances.xml";
		//else fileName = "/xml/agentUtterances.xml";
		
		// file buiten jar
		//if(source.equals("user")) fileName = "userUtterances.xml";
		//else fileName = "agentUtterances.xml";
		
		//System.out.println("FileName: "+fileName);
		
		// file in jar
		java.net.URL xmlUrl = getClass().getResource(fileName);
				
		//parse the file and also register this class for call backs
		try{
			// file in jar
			sp.parse(xmlUrl.openStream(), this);
			
			// file buiten jar
			//sp.parse(fileName, this);
		}catch (IOException ie) {
			ie.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		//System.out.println(tempUtt.toString());
		return tempUtt;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		
		if(qName.equalsIgnoreCase("nlUtterance") && attributes.getValue("code").equalsIgnoreCase(tempUtt.getCode())) {
			thisOne = true;
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException { 
		tempVal = new String(ch,start,length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("nlUtterance")){
			thisOne = false;
		} else if(qName.equalsIgnoreCase("text") && thisOne){
			tempUtt.setUtterance(tempVal);
		} else if(qName.equalsIgnoreCase("responseOption") && thisOne ){
			tempUtt.addResponseOption(tempVal);
		}
	}
	
	public String capitalize(String input){
		// source: http://www.coderanch.com/t/485368/java/java/Capitalize-first-letter-string
	    StringBuilder sb = new StringBuilder(input); // one StringBuilder object  
	    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
	    return sb.toString(); // one String object  
	}
	
	public static void main(String[] args){
		UtteranceParser up = new UtteranceParser();
		System.out.println(up.findUtterance("user", "incident(q1,a1)"));
		//up.findUtterance("/utterances/userUtterances.xml", "greet(code1thyh)");
	}
		
		

}
