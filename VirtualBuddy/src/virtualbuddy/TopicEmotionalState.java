package virtualbuddy;

import java.util.LinkedList;

public class TopicEmotionalState extends Topic {

	public TopicEmotionalState() {
		super("emotional_state", "qa(emotional_state)");
	}
	
	@Override
	public String nextSequence(LinkedList<String> incidentFacts){
		// alleen qa(emotional_state)
		if( currentSequence == null && startSeq != null){
			currentSequence = startSeq;
			startSeq = null;
		} else {
			currentSequence = null;
		}
		return currentSequence;
	}

	

}
