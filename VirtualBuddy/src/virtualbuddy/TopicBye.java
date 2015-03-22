package virtualbuddy;

import java.util.LinkedList;

public class TopicBye extends Topic {

	public TopicBye(){
		super("bye", "qa(feedback_conversation)");
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		//System.out.println();
		//System.out.println("In nextSequence()");
		//System.out.println("Current sequence: "+currentSequence);

		if( currentSequence == null && startSeq != null){
			nextS = startSeq;
			startSeq = null;
		} else if(currentSequence.equals("qa(feedback_conversation)")){
			if(incidentFacts.contains("conversation(feedback_conversation,no)")){
				if(incidentFacts.contains("incident(cf_conversation_partner,parent)") ){
					nextS = "advice(talk_to,parent_neg)";
				} else if (incidentFacts.contains("incident(cf_conversation_partner,teacher)")){
					nextS = "advice(talk_to,teacher_neg)";
				} else if (incidentFacts.contains("incident(tell_story_to,parent)")){
					nextS = "advice(talk_to,parent_neg)";
				} else if (incidentFacts.contains("incident(tell_story_to,teacher)")){
					nextS = "advice(talk_to,teacher_neg)";
				} else {
					nextS = "advice(talk_to,pestweb_neg)";
				}
			} else if(incidentFacts.contains("conversation(feedback_conversation,yes)")){
				if(incidentFacts.contains("incident(cf_conversation_partner,parent)") ){
					nextS = "advice(talk_to,parent_pos)";
				} else if (incidentFacts.contains("incident(cf_conversation_partner,teacher)")){
					nextS = "advice(talk_to,teacher_pos)";
				} else if (incidentFacts.contains("incident(tell_story_to,parent)")){
					nextS = "advice(talk_to,parent_pos)";
				} else if (incidentFacts.contains("incident(tell_story_to,teacher)")){
					nextS = "advice(talk_to,teacher_pos)";
				} else {
					nextS = "advice(talk_to,pestweb_pos)";
				}
			}
		} else if(currentSequence.equals("advice(talk_to,parent_neg)")){
			nextS = "bye1";
		} else if(currentSequence.equals("advice(talk_to,teacher_neg)")){
			nextS = "bye1";
		} else if(currentSequence.equals("advice(talk_to,parent_pos)")){
			nextS = "bye1";
		} else if(currentSequence.equals("advice(talk_to,teacher_pos)")){
			nextS = "bye1";
		} else if(currentSequence.equals("advice(talk_to,pestweb_neg)")){
			nextS = "bye1";
		} else if(currentSequence.equals("advice(talk_to,pestweb_pos)")){
			nextS = "bye1";
		}
					
		//System.out.println("Gevonden nextS"+nextS);
		
		currentSequence = nextS;

		return nextS;
	}
}
