package virtualbuddy;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SequenceParser extends DefaultHandler{
	SAXParser sp;
	String name;
	LinkedList<SpeechAct2> speechActs;
	boolean thisOne;
	
	public SequenceParser(){		
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
	
	public LinkedList<SpeechAct2> getSpeechActs(String name){
		speechActs = new LinkedList<SpeechAct2>();
		this.name = name;
		
		String fileName;
				
		// file in jar
		fileName = "/xml/sequence2speechact.xml";
		
		// file buiten jar
		//fileName = "sequence2speechact.xml";
				
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
		return speechActs;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.println("Test "+name);
		//System.out.println(qName + " aantal attr "+ attributes.getLength());
		//if( attributes.getLength() >= 1) System.out.println(attributes.getValue(0));
		
		if(qName.equalsIgnoreCase("sequence") && attributes.getValue("name").equalsIgnoreCase(name)) {
			thisOne = true;
			//System.out.println("Sequence "+name+" gevonden ");
		} else if( thisOne && qName.equalsIgnoreCase("sa") ){
			String contents = null;
			String from = attributes.getValue("from");
			
			//System.out.println("Attr "+ attributes.getLength());
			
			if(from.equals("robin")) contents = attributes.getValue("contents");
			speechActs.add(new SpeechAct2(from, contents) );
		}
	}

	//public void characters(char[] ch, int start, int length) throws SAXException { 
	//	tempVal = new String(ch,start,length);
	//}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("sequence")){
			thisOne = false;
		}
	}
	
	public String capitalize(String input){
		// source: http://www.coderanch.com/t/485368/java/java/Capitalize-first-letter-string
	    StringBuilder sb = new StringBuilder(input); // one StringBuilder object  
	    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
	    return sb.toString(); // one String object  
	}
	
	public static void main(String[] args){
		SequenceParser up = new SequenceParser();
		LinkedList<SpeechAct2> sas = up.getSpeechActs("qa(event_type)");
		
		for( SpeechAct2 s : sas){
			System.out.println(s.getFrom()+" "+s.getContents());
		}

	}
		
		

}
