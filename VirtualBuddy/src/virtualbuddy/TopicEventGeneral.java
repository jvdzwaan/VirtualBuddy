package virtualbuddy;

import java.util.LinkedList;

public class TopicEventGeneral extends Topic {

	public TopicEventGeneral() {
		super("event_general", "qa(event_type)");
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		// type -> type_cb -> method_cb
		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		} else if(currentSequence.equals("qa(event_type)")){
			nextS = "qa(type_cb)";
		} else if(currentSequence.equals("qa(type_cb)")){
			nextS = "qa(method_cb)";
		}
		
		currentSequence = nextS;
		
		return nextS;
	}
	
}
