package virtualbuddy;

import java.util.LinkedList;

public class EmotionalResponse {
	LinkedList<Fact2Emotion> facts2emotion;
	
	public EmotionalResponse(){
		facts2emotion = new LinkedList<Fact2Emotion>();
		
		// type_cb
		facts2emotion.add(new Fact2Emotion("incident(type_cb,called_names)","pity","sad",2,"sympathy(type_cb,called_names)"));
		facts2emotion.add(new Fact2Emotion("incident(type_cb,threatened)","pity","sad",2,"sympathy(type_cb,threatened)"));
		facts2emotion.add(new Fact2Emotion("incident(type_cb,hacked)","pity","sad",2,"sympathy(type_cb,hacked)"));
		facts2emotion.add(new Fact2Emotion("incident(type_cb,pictures)","pity","sad",2,"sympathy(type_cb,pictures)"));
		facts2emotion.add(new Fact2Emotion("incident(type_cb,social_exclusion)","pity","sad",2,"sympathy(type_cb,social_exclusion)"));
		
		// response_to_bullying
		facts2emotion.add(new Fact2Emotion("incident(response_to_bullying,retaliate)","reproach","frustrated",1,"disapprovement(response_to_bullying,retaliate)"));
		//facts2emotion.add(new Fact2Emotion("incident(response_to_bullying,talk_to_someone)","admiration","happy",2,"compliment(response_to_bullying,talk_to_someone)"));
		facts2emotion.add(new Fact2Emotion("incident(response_to_bullying,stand_up)","admiration","happy",2,"compliment(response_to_bullying,stand_up)"));
		facts2emotion.add(new Fact2Emotion("incident(response_to_bullying,block_the_bully)","admiration","happy",2,"compliment(response_to_bullying,block_the_bully)"));
		facts2emotion.add(new Fact2Emotion("incident(response_to_bullying,report_bully)","admiration","happy",2,"compliment(response_to_bullying,report_bully)"));
		
		// effectiveness_response_to_bullying
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_response_to_bullying,stopped)","happy_for","happy",2,"happy_for(effectiveness_response_to_bullying,stopped)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_response_to_bullying,felt_better)","happy_for","happy",2,"happy_for(effectiveness_response_to_bullying,felt_better)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_response_to_bullying,no_change)","pity","sad",2,"sympathy(effectiveness_response_to_bullying,no_change)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_response_to_bullying,became_worse)","pity","sad",2,"sympathy(effectiveness_response_to_bullying,became_worse)"));
		
		// talked_to
		facts2emotion.add(new Fact2Emotion("incident(talked_to,sibling)","admiration","happy",2,"compliment(talked_to,sibling)"));
		facts2emotion.add(new Fact2Emotion("incident(talked_to,peer)","admiration","happy",2,"compliment(talked_to,peer)"));
		facts2emotion.add(new Fact2Emotion("incident(talked_to,parent)","admiration","happy",2,"compliment(talked_to,parent)"));
		facts2emotion.add(new Fact2Emotion("incident(talked_to,teacher)","admiration","happy",2,"compliment(talked_to,teacher)"));
		facts2emotion.add(new Fact2Emotion("incident(talked_to,online_contact)","admiration","happy",2,"compliment(talked_to,online_contact)"));
		facts2emotion.add(new Fact2Emotion("incident(talked_to,someone_else)","admiration","happy",2,"compliment(talked_to,someone_else)"));
		
		// effectiveness_talked_to
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_talked_to,stopped)","happy_for","happy",2,"happy_for(effectiveness_talked_to,stopped)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_talked_to,felt_better)","happy_for","happy",2,"happy_for(effectiveness_talked_to,felt_better)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_talked_to,no_change)","pity","sad",2,"sympathy(effectiveness_talked_to,no_change)"));
		facts2emotion.add(new Fact2Emotion("incident(effectiveness_talked_to,became_worse)","pity","sad",2,"sympathy(effectiveness_talked_to,became_worse)"));
		
		// tell_story_to
		facts2emotion.add(new Fact2Emotion("incident(tell_story_to,parent)","admiration","happy",2,"compliment(tell_story_to,parent)"));
		facts2emotion.add(new Fact2Emotion("incident(tell_story_to,teacher)","admiration","happy",2,"compliment(tell_story_to,teacher)"));
		facts2emotion.add(new Fact2Emotion("incident(tell_story_to,sibling)","admiration","happy",2,"compliment(tell_story_to,sibling)"));
		facts2emotion.add(new Fact2Emotion("incident(tell_story_to,peer)","admiration","happy",2,"compliment(tell_story_to,peer)"));
		
		// cf_conversation_partner
		facts2emotion.add(new Fact2Emotion("incident(cf_conversation_partner,parent)","admiration","happy",2,"compliment(cf_conversation_partner,parent)"));
		facts2emotion.add(new Fact2Emotion("incident(cf_conversation_partner,teacher)","admiration","happy",2,"compliment(cf_conversation_partner,teacher)"));
		facts2emotion.add(new Fact2Emotion("incident(cf_conversation_partner,sibling)","admiration","happy",2,"compliment(cf_conversation_partner,sibling)"));
		facts2emotion.add(new Fact2Emotion("incident(cf_conversation_partner,peer)","admiration","happy",2,"compliment(cf_conversation_partner,peer)"));
		
		// feedback_conversation
		facts2emotion.add(new Fact2Emotion("conversation(feedback_conversation,yes)", "joy", "happy", 2, "express_joy(feedback_conversation,yes)"));
		facts2emotion.add(new Fact2Emotion("conversation(feedback_conversation,no)", "distress", "sad", 2, "express_distress(feedback_conversation,no)"));

	}
	
	public Fact2Emotion getEmotionalResponse(String fact){
		Fact2Emotion result = null;
		for(Fact2Emotion em : facts2emotion){
			if(em.getIncidentFact().equals(fact)) result = em;
		}
		
		return result;
	}

}
