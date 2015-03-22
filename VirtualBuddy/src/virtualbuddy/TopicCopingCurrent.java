package virtualbuddy;

import java.util.LinkedList;

public class TopicCopingCurrent extends Topic {

	public TopicCopingCurrent() {
		super("coping_current", "qa(response_to_bullying)");
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		// response_to_bullying -> effectiveness_response_to_bullying -> talked_to_someone 
		// response_to_bullying -> effectiveness_response_to_bullying -> talked_to_someone -> talked_to -> effectiveness_talked_to
		// response_to_bullying -> talked_to -> effectiveness_talked_to
		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		} else if(currentSequence.equals("qa(response_to_bullying)")){
			// als feit incident(response_to_bullying,talk_to_someone) niet in de incident-facts zit
			// dan is nextS effectiveness_response_to_bullying
			if(incidentFacts.contains("incident(response_to_bullying,talk_to_someone)")){
				nextS = "qa(talked_to)";
			} else {
				nextS = "qa(effectiveness_response_to_bullying)";
			}
		} else if(currentSequence.equals("qa(effectiveness_response_to_bullying)")){
			nextS = "qa(talked_to_someone)";
		} else if(currentSequence.equals("qa(talked_to_someone)")){
			if(incidentFacts.contains("incident(talked_to_someone,yes)")){
				nextS = "qa(talked_to)";
			} else if(incidentFacts.contains("incident(response_to_bullying,talk_to_someone)")){
				nextS = "qa(talked_to)";
			}
		} else if(currentSequence.equals("qa(talked_to)")){
			nextS = "qa(effectiveness_talked_to)";
		} 
		
		currentSequence = nextS;
		
		return nextS;
	}

}
