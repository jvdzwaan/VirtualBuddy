package virtualbuddy;

import java.util.LinkedList;

public class TopicAdvice extends Topic {
	
	LinkedList<String> advice;
	LinkedList<String> teachable;
	LinkedList<String> facts;
	int adviceIndex;
	
	boolean teaching;
	String teachAdvice;
	
	public TopicAdvice(LinkedList<String> facts){
		super("advice");
		
		//System.out.println("Topic Advice gemaakt");
		
		this.facts = facts;
		
		// vindt al het relevante advies
		findAdvice();
		
		adviceIndex=-1;
		teaching = false;
		teachAdvice = null;
		
		// set teachable
		teachable = new LinkedList<String>();
		teachable.add("advice(msn_block_contact)");
		teachable.add("advice(hyves_block_contact)");
		teachable.add("advice(facebook_block_contact)");
		teachable.add("advice(hyves_report)");
		teachable.add("advice(facebook_report)");
		
		//System.out.println(teachable);
		
		// is er relevant advice?
		if(!advice.isEmpty()){
			if(facts.contains("conversation(conversation_objective,tips)") ){
				setStartSequence("introduce_tips");
			} else {
				setStartSequence("conversation(want_tips,x)");
			}
		}		
	}

	@Override
	public String nextSequence(LinkedList<String> incidentFacts) {
		String nextS = null;
		
		//System.out.println();
		//System.out.println(advice);
		//System.out.println("Current sequence: "+currentSequence);
		//System.out.println("Start sequence: "+startSeq);
		//System.out.println("Advice index: "+adviceIndex);
		//System.out.println("Advice size: "+advice.size());
		
		if(teaching){
			nextS = teaching(incidentFacts);
			//System.out.println("Next sequence (teaching): "+nextS);
		}
		
		if( nextS == null ){
			//System.out.println("NextS: "+nextS);
			//System.out.println("Advice index: "+adviceIndex);
			//System.out.println("Advice size: "+advice.size());

			if( currentSequence == null && startSeq != null){
				nextS = startSeq;
				startSeq = null;
			} else if( ( currentSequence.equals("introduce_tips") || (currentSequence.equals("conversation(want_tips,x)") && incidentFacts.contains("conversation(want_tips,yes)") ))  && !advice.isEmpty()){
				//System.out.println("Ja, ik ga advice geven 1");
				adviceIndex = 0;
				nextS = advice.get(adviceIndex);
				adviceIndex++;
				if(teachable.contains(nextS)){
					teaching = true;
					teachAdvice = nextS;
					//System.out.println("Ja, ik ga teachen");
				}
			} else if( adviceIndex != -1 && adviceIndex < advice.size() ){
				//System.out.println("Ja, ik ga advice geven 2");
				nextS = advice.get(adviceIndex);
				adviceIndex++;
				if(teachable.contains(nextS)){
					teaching = true;
					teachAdvice = nextS;
					//System.out.println("Ja, ik ga teachen");
				}

			} else if( adviceIndex == advice.size()){
				nextS = "conclude_tips";
				adviceIndex = -1;
			}
		}
		
		currentSequence = nextS;
		//System.out.println("NextS: "+nextS);
		//System.out.println();
		
		return nextS;
	}
	
	private String teaching(LinkedList<String> facts){
		//System.out.println("In teaching ");
		
		String teachStep = null;
		if(teachAdvice.equals("advice(msn_block_contact)")){
			if(currentSequence.equals("advice(msn_block_contact)")){
				teachStep = "request_teaching(msn_block_contact)";
			} else if(facts.contains("request_teaching(msn_block_contact,no)")){
				teaching = false;
				teachAdvice = null;
			} else if(facts.contains("request_teaching(msn_block_contact,yes)") && !facts.contains("teach_step(msn_block_contact,log_in,confirm)")){
				teachStep = "teach(msn_block_contact1)";
			} else if(currentSequence.equals("teach(msn_block_contact1)")){
				teachStep = "teach(msn_block_contact2)";
			} else if(currentSequence.equals("teach(msn_block_contact2)")){
				teachStep = "teach(msn_block_contact3)";
			} else if(currentSequence.equals("teach(msn_block_contact3)")){
				teachStep = "teach_end(msn_block_contact)";
				teaching = false;
				teachAdvice = null;
			}
			
		} else if(teachAdvice.equals("advice(hyves_block_contact)")){
			if(currentSequence.equals("advice(hyves_block_contact)")){
				teachStep = "request_teaching(hyves_block_contact)";
			} else if(facts.contains("request_teaching(hyves_block_contact,no)")){
				teaching = false;
				teachAdvice = null;
			} else if(facts.contains("request_teaching(hyves_block_contact,yes)") && !facts.contains("teach_step(hyves_block_contact,log_in,confirm)")){
				teachStep = "teach(hyves_block_contact1)";
			} else if(currentSequence.equals("teach(hyves_block_contact1)")){
				teachStep = "teach(hyves_block_contact2)";
			} else if(currentSequence.equals("teach(hyves_block_contact2)")){
				teachStep = "teach(hyves_block_contact3)";
			} else if(currentSequence.equals("teach(hyves_block_contact3)")){
				teachStep = "teach(hyves_block_contact4)";
			} else if(currentSequence.equals("teach(hyves_block_contact4)")){
				teachStep = "teach_end(hyves_block_contact)";
				teaching = false;
				teachAdvice = null;
			}
			
		} else if(teachAdvice.equals("advice(facebook_block_contact)")){
			if(currentSequence.equals("advice(facebook_block_contact)")){
				teachStep = "request_teaching(facebook_block_contact)";
			} else if(facts.contains("request_teaching(facebook_block_contact,no)")){
				teaching = false;
				teachAdvice = null;
			} else if(facts.contains("request_teaching(facebook_block_contact,yes)") && !facts.contains("teach_step(facebook_block_contact,log_in,confirm)")){
				teachStep = "teach(facebook_block_contact1)";
			} else if(currentSequence.equals("teach(facebook_block_contact1)")){
				teachStep = "teach(facebook_block_contact2)";
			} else if(currentSequence.equals("teach(facebook_block_contact2)")){
				teachStep = "teach(facebook_block_contact3)";
			} else if(currentSequence.equals("teach(facebook_block_contact3)")){
				teachStep = "teach(facebook_block_contact4)";
			} else if(currentSequence.equals("teach(facebook_block_contact4)")){
				teachStep = "teach_end(facebook_block_contact)";
				teaching = false;
				teachAdvice = null;
			}
			
		} else if(teachAdvice.equals("advice(hyves_report)")){
			if(currentSequence.equals("advice(hyves_report)")){
				teachStep = "request_teaching(hyves_report)";
			} else if(facts.contains("request_teaching(hyves_report,no)")){
				teaching = false;
				teachAdvice = null;
			} else if(facts.contains("request_teaching(hyves_report,yes)") && !facts.contains("teach_step(hyves_report,log_in,confirm)")){
				teachStep = "teach(hyves_report1)";
			} else if(currentSequence.equals("teach(hyves_report1)")){
				teachStep = "teach(hyves_report2)";
			} else if(currentSequence.equals("teach(hyves_report2)")){
				teachStep = "teach(hyves_report3)";
			} else if(currentSequence.equals("teach(hyves_report3)")){
				teachStep = "teach(hyves_report4)";
			} else if(currentSequence.equals("teach(hyves_report4)")){
				teachStep = "teach_end(hyves_report)";
				teaching = false;
				teachAdvice = null;
			}
			
		} else if(teachAdvice.equals("advice(facebook_report)")){
			if(currentSequence.equals("advice(facebook_report)")){
				teachStep = "request_teaching(facebook_report)";
			} else if(facts.contains("request_teaching(facebook_report,no)")){
				teaching = false;
				teachAdvice = null;
			} else if(facts.contains("request_teaching(facebook_report,yes)") && !facts.contains("teach_step(facebook_report,log_in,confirm)")){
				teachStep = "teach(facebook_report1)";
			} else if(currentSequence.equals("teach(facebook_report1)")){
				teachStep = "teach(facebook_report2)";
			} else if(currentSequence.equals("teach(facebook_report2)")){
				teachStep = "teach(facebook_report3)";
			} else if(currentSequence.equals("teach(facebook_report3)")){
				teachStep = "teach(facebook_report4)";
			} else if(currentSequence.equals("teach(facebook_report4)")){
				teachStep = "teach_end(facebook_report)";
				teaching = false;
				teachAdvice = null;
			}
			
		}  
		
		return teachStep;
	}
	
	private void findAdvice(){
		advice = new LinkedList<String>();
		
		// voor testen
		//advice.add("advice(accept_friends)");
		//advice.add("advice(email_friends_only)");
		//advice.add("advice(print_evidence)");
		//advice.add("advice(msn_block_contact)");
		//advice.add("advice(create_new_account)");
		//advice.add("advice(dont_share_private_info)");
		//advice.add("advice(facebook_block_contact)");
		//advice.add("advice(hyves_block_contact)");
		//advice.add("advice(dont_respond)");	
		//advice.add("advice(hyves_report)");
		//advice.add("advice(facebook_report)");
		
		//advice.add("advice(dont_respond)");
		
		if( !facts.contains("incident(type_cb,hacked)") && 
				!facts.contains("incident(type_cb,pictures)")){
			if(facts.contains("incident(method_cb,msn)") || facts.contains("incident(method_cb,hyves)") || facts.contains("incident(method_cb,facebook)")){
				advice.add("advice(accept_friends)");
			}
		}
		
		if(!facts.contains("incident(type_cb,hacked)")){
			advice.add("advice(print_evidence)");
		}
		
		if(facts.contains("incident(method_cb,email)") && !facts.contains("incident(type_cb,hacked)")){
			advice.add("advice(email_friends_only)");
		}
				
		if(facts.contains("incident(method_cb,msn)") && 
				!facts.contains("incident(type_cb,hacked)")){
			advice.add("advice(msn_block_contact)");
		}
		
		if(facts.contains("incident(method_cb,facebook)") && 
				!facts.contains("incident(type_cb,hacked)")&& 
				!facts.contains("incident(type_cb,pictures)")){
			advice.add("advice(facebook_block_contact)");
		}
		
		if(facts.contains("incident(method_cb,hyves)") && 
				!facts.contains("incident(type_cb,hacked)")	&& 
				!facts.contains("incident(type_cb,pictures)")){
			advice.add("advice(hyves_block_contact)");
		}
		
		if(facts.contains("incident(method_cb,hyves)") &&  
				facts.contains("incident(type_cb,pictures)")){
			advice.add("advice(hyves_report)");
		}
		
		if(facts.contains("incident(method_cb,facebook)") &&  
				facts.contains("incident(type_cb,pictures)")){
			advice.add("advice(facebook_report)");
		}
			
		if(facts.contains("incident(type_cb,hacked)")){
			advice.add("advice(dont_share_private_info)");
		}
		
		if(facts.contains("incident(type_cb,hacked)")){
			advice.add("advice(create_new_account)");
		}		
	}
	
	public static void main(String args []){
		
		LinkedList<String> method = new LinkedList<String>();
		method.add("incident(method_cb,msn)");
		method.add("incident(method_cb,facebook)");
		method.add("incident(method_cb,hyves)");
		method.add("incident(method_cb,email)");
		
		LinkedList<String> type = new LinkedList<String>();
		type.add("incident(type_cb,called_names)");
		type.add("incident(type_cb,threatened)");
		type.add("incident(type_cb,pictures)");
		type.add("incident(type_cb,hacked)");
		
		for(String m : method){
			for( String t : type ){
				LinkedList<String> f = new LinkedList<String>();
				f.add(m);
				f.add(t);
				
				System.out.print(f+": ");
				
				TopicAdvice a = new TopicAdvice(f);
				
				System.out.println(a.advice);
			}
		}
		
		
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
		//System.out.println(a.nextSequence(new LinkedList<String>()));
	}
}
