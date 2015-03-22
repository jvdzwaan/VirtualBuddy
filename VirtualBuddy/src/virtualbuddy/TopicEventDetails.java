package virtualbuddy;

import java.util.LinkedList;

public class TopicEventDetails extends Topic {

	public TopicEventDetails() {
		super("event_details", "qa(bully)");
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		// bully -> bullying_duration
		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		} else if(currentSequence.equals("qa(bully)")){
			nextS = "qa(bullying_duration)";
		} 
		
		currentSequence = nextS;
		
		return nextS;
	}

}
