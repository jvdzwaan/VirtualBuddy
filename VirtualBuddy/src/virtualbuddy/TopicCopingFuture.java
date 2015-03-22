package virtualbuddy;

import java.util.LinkedList;

public class TopicCopingFuture extends Topic {
	
	public TopicCopingFuture(String startSeq) {
		super("coping_future", startSeq);
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		//System.out.println("CurrentS: "+currentSequence);

		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		
		// conversation objective = tell_story
		} else if(currentSequence.equals("qa(tell_story_to)")){
			if(incidentFacts.contains("incident(tell_story_to,dont_know)")){
				nextS = "talk_to_pestweb";
			} else if (incidentFacts.contains("incident(tell_story_to,robin)")){
				nextS = "cant_talk_to_robin";
			} else {
				nextS = "qa(want_tips)";
			}
		} else if(currentSequence.equals("cant_talk_to_robin")){
			nextS = "talk_to_pestweb";
		} else if(currentSequence.equals("talk_to_pestweb")){
			nextS = "qa(want_tips)";
		
		// conversation objective = tips
		} else if(currentSequence.equals("coping_options")){
			nextS = "qa(coping_future)";
		} else if(currentSequence.equals("qa(coping_future)")){
			if(incidentFacts.contains("incident(coping_future,talk_to)")){
				nextS = "qa(cf_conversation_partner)";
			} else if(incidentFacts.contains("incident(coping_future,stand_up)")){
				nextS = "confirm_cf(stand_up)";
			} else if(incidentFacts.contains("incident(coping_future,ignore)")){
				nextS = "confirm_cf(ignore)";
			}
		} else if(currentSequence.equals("qa(cf_conversation_partner)")){
			if(incidentFacts.contains("incident(cf_conversation_partner,sibling)")){
				nextS = "talk_to_adult(parent)";
			} else if(incidentFacts.contains("incident(cf_conversation_partner,peer)")){
				nextS = "talk_to_adult(teacher)";
			} else if(incidentFacts.contains("incident(cf_conversation_partner,dont_know)")){
				nextS = "ask_pestweb";
			}
		}
		
		currentSequence = nextS;
		
		//System.out.println("nextS: "+currentSequence);
		//System.out.println();
		
		return nextS;
	}

}
