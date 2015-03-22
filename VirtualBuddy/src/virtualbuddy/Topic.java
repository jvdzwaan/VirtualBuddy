package virtualbuddy;

import java.util.LinkedList;

public abstract class Topic {

	public String name;
	protected String startSeq;
	public String currentSequence;
	
	public Topic(String n){
		name = n;
		startSeq = null;
		currentSequence = null;
	}
	
	public Topic(String n, String seq){
		name = n;
		startSeq = seq;
		currentSequence = null;
	}
	
	public String getName(){
		return name;
	}
	
	public Topic getCurrentTopic(LinkedList<String> facts){
		if(currentSequence != null || startSeq != null) return this;
		else return nextTopic(facts);
	}
	
	public void setStartSequence(String s){
		startSeq = s;
	}
	
	public Topic nextTopic(LinkedList<String> facts){
		Topic nextTopic = null;
		
		if(name.equals("hello")){
			nextTopic = new TopicEventGeneral();			
		} else if(name.equals("event_general")){
			nextTopic = new TopicEmotionalState();
		} else if(name.equals("emotional_state")){
			nextTopic = new TopicEventDetails();
		} else if(name.equals("event_details")){
			nextTopic = new TopicCopingCurrent();
		} else if(name.equals("coping_current")){
			nextTopic = new TopicConversationObjective();
		} else if(name.equals("conversation_objective")){
			if(facts.contains("conversation(conversation_objective,tell_story)")){
				nextTopic = new TopicCopingFuture("qa(tell_story_to)");
			} else {
				nextTopic = new TopicCopingFuture("coping_options");
			}
		} else if(name.equals("coping_future")){
			nextTopic = new TopicAdvice(facts);
		} else if(name.equals("advice")){
			nextTopic = new TopicBye();
		} 
		
		return nextTopic;
	}
	
	abstract public String nextSequence(LinkedList<String> incidentFacts);
	
	public String toString(){
		return name;
	}
}
