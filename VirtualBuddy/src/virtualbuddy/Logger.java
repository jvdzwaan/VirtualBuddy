package virtualbuddy;

public class Logger {
	private StringBuffer log;
	
	public Logger(){
		log = new StringBuffer();
	}
	
	public void log(String msg){
		log.append(msg);
		log.append("\n");
	}
	
	public StringBuffer getLog(){
		return log;
	}

}
