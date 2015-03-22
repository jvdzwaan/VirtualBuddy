package virtualbuddy;

import java.util.LinkedList;

public class TopicHello extends Topic {
	
	public TopicHello(){
		super("hello", "hello1");
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		// alleen sequence hallo
		if( currentSequence == null && startSeq != null){
			currentSequence = startSeq;
			startSeq = null;
		} else {
			currentSequence = null;
		}
		return currentSequence;
	}

}
