package virtualbuddy;

import java.util.LinkedList;

public class TopicConversationObjective extends Topic {

	public TopicConversationObjective() {
		super("conversation_objective", "qa(conversation_objective)");
	}
	
	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		// conversation_objective
		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		}
		
		return nextS;
	}

}
