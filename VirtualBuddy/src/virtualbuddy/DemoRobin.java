package virtualbuddy;

import java.net.*;
import java.util.LinkedList;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import org.tudelft.affectbutton.AffectButton;
import org.tudelft.affectbutton.PADFaceMapped;

import jcmdline.StringFormatHelper;


public class DemoRobin extends JApplet {  
	private static final long serialVersionUID = 1L;

	// gui components
	JTextArea conversation, input;
	JScrollPane conversationScroll;
	JPanel userInput;
	JPanel abPanel;
	Embodiment faceRobin;
	TextInputPanel tip;
	JButton send;
	AffectButton ab;
	JButton activateAB, croButton;
	Font normalFont;

	String userName;
	//UserSpeechActs uSA;
	LinkedList<SpeechAct> currentResponseOptions;
	String abActionCommand;
	
	Topic currentTopic;
	LinkedList<String> incidentFacts;

	UtteranceParser uParser;
	SequenceParser sParser;

	private volatile boolean waitForUser;
	private volatile boolean emotionalResponseInProgress;
	private volatile boolean conversationInProgress; 
	int facialExpressionDelay;
	EmotionalResponse feltEmotion;
	private boolean affectButtonUsed;
	
	Logger log;
	
	// condition
	boolean verbal;
	boolean nonverbal;

	public void init(){
		log = new Logger();
		
		userName = "Tom";
		
		//setCondition( getParameter("foo") ); 
		setCondition("4");

		//uSA = new UserSpeechActs();
		currentResponseOptions = new LinkedList<SpeechAct>();
		uParser = new UtteranceParser();
		sParser = new SequenceParser();
		waitForUser = false;
		emotionalResponseInProgress = false;
		affectButtonUsed = false;
		conversationInProgress = true;
		facialExpressionDelay = 2500;
		feltEmotion = new EmotionalResponse();
		
		incidentFacts = new LinkedList<String>();
		//incidentFacts.add("conversation(conversation_objective,tips)");
		//incidentFacts.add("incident(cf_conversation_partner,dont_know)");
		//incidentFacts.add("incident(tell_story_to,teacher)");
		currentTopic = new TopicHello();
		//currentTopic = new TopicConversationObjective();
		//currentTopic = new TopicAdvice(incidentFacts);
		//currentTopic = new TopicBye();
		//currentTopic = new TopicCopingFuture("qa(tell_story_to)");
		//currentTopic = new TopicCopingFuture("coping_options");
		
		//Execute a job on the event-dispatching thread:
	    //creating this applet's GUI.
	    try {
	        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
	            public void run() {
	                createGUI();
	            }
	        });
	    } catch (Exception e) {
	        log.log("error, createGUI didn't successfully complete");
	    }
	    
	    //addMessageToChat("------------------------------------------------------");
	    //addMessageToChat("Empathic virtual buddy demo");
	    //addMessageToChat("For demonstration purposes only!");
	    //addMessageToChat("Please refer to http://ict1.tbm.tudelft.nl/empathicbuddy/ for instructions");
	    //addMessageToChat("------------------------------------------------------");
	    //addMessageToChat("------------------------------------------------------");
	    //addMessageToChat("Empathische virtuele buddy demo");
	    //addMessageToChat("Alleen ter demonstratie!");
	    //addMessageToChat("Kijk op http://ict1.tbm.tudelft.nl/empathicbuddy/ voor gebruiksinstructies");
	    //addMessageToChat("------------------------------------------------------");
	    
	    Thread thread = new Thread(){
	    	public void run(){
	    		while(conversationInProgress){

	    			if(!waitForUser && !emotionalResponseInProgress){
	    				String ns = null;    					

	    				synchronized(incidentFacts){
	    					currentTopic = currentTopic.getCurrentTopic(incidentFacts);
	   
	    					if(currentTopic != null ){
	    						//addMessageToChat("topic: "+currentTopic.getName());

	    						ns = currentTopic.nextSequence(incidentFacts);
	    					} else {
	    						conversationInProgress = false;
	    					}
	    				}

	    				//addMessageToChat("about to do sequence: "+ns);
	    				if(ns != null) doSequence( ns, null );
	    			}
	    		}
	    		
	    		// gesprek is afgelopen
	    		//addMessageToChat("[End of conversation]");
	    		addMessageToChat("[Het gesprek is afgelopen]");
	    		
	    		//waitTime(1500);
	    		
	    		//addMessageToChat("Je mag nu verder gaan met het invullen van de vragenlijst.");
	    		
	    		// TODO log naar db schrijven
	    		//System.out.println(log.getLog());
	    	}
	    };
	    thread.start();
	    
	}
	
	private void setCondition(String parameter) {
		nonverbal = false;
		verbal = false; 
		
		if( parameter.equals("2") ){
			nonverbal = true;			
		} else if( parameter.equals("3") ){
			verbal = true;
		} else if( parameter.equals("4") ){
			verbal = true;
			nonverbal = true;
		} 
		
	}

	public void doSequence(String sequenceName, Fact2Emotion em){
		
		int utteranceLength = 0;
	
		if(em!=null){
			// delay for changing expression
			waitTime(1000);
			
			if(em.getMirror()){
				// mirror emotion
				faceRobin.setExpression(em.getEmotionLabel(), em.getEmotionIntensity());
			} else {
				//addMessageToChat("Emotional response: "+em.getEmotionLabel());
				waitTime(1500);
				faceRobin.addEmotion(em.getEmotionLabel(), em.getSteps());
			}

		} else {
			// decay emotion - one step to content - med
			if( ! (faceRobin.getExpressionLabel().equals("content") && faceRobin.getExpressionIntensity().equals("med")) ){
				faceRobin.addEmotion("content", 1);
				//addMessageToChat("Decay emotion");
			}
		}

		log.log("robin,"+faceRobin.getExpressionLabel()+","+faceRobin.getExpressionIntensity());

		if(sequenceName != null){
			// LinkedList van speech acts maken op basis van naam
			LinkedList<SpeechAct2> sequenceSpeechActs = sParser.getSpeechActs(sequenceName);
			//waitForUser = false;

			// Loop over linked list speech acts
			for( SpeechAct2 s : sequenceSpeechActs ){
				// find nlUtterance
				String from = s.getFrom();

				if(from.equals("robin")){
					Utterance2 t = uParser.findUtterance(from, s.getContents());
					utteranceLength = t.getUtterance().length();
					//addMessageToChat("Lengte bericht: "+(t.getUtterance().length()));
					// delay for typing
					waitTime((int) ((utteranceLength/5.0) * 200));
					// stuur bericht naar chat interface
					//addMessageToChat(uParser.capitalize(from)+" zegt: "+t.getUtterance());
					addMessageToChat(uParser.capitalize(from)+" says: "+t.getUtterance());

					log.log("robin,"+t.getCode());

					// Display response options
					LinkedList<SpeechAct> options = new LinkedList<SpeechAct>();

					//addMessageToChat("sequenceName");

					// wachten met volgende bericht (leestijd) als het een lang bericht is
					if(utteranceLength > 200){
						waitTime(7000);
					} else if(utteranceLength > 100 ){
						waitTime(3500);
					} else {
						waitTime(2000);
					}

					// AffectButton nodig?
					if(sequenceName.equals("qa(emotional_state)") && !affectButtonUsed){
						abActionCommand = t.getCode();
						ab.setActionCommand(abActionCommand);
						showAffectButton();

					} else if(!t.getResponseOptions().isEmpty()) {
						for (String ro : t.getResponseOptions()) {
							SpeechAct sa = new SpeechAct("inform", "user", ro, uParser);
							options.add(sa);
						}

						changeResponseOptions(options);
					}

				} else {
					waitForUser = true;
					//addMessageToChat("Wait for user!");
				}
			}
			
		}

		// aanhouden facial expression na verbale uiting (leestijd)
		if(em!=null){
			waitTime(facialExpressionDelay);
		}


	}
	
	public String getLog(){
		return log.getLog().toString();
	}
	
	private void println(String msg) {
		addMessageToChat(msg);
	}

	public void addMessageToChat(String message){
		message = message.replace("***", "\n");
		message = message.replace("NAAM", userName);
		message = message.replace("HYVESURL", "http://www.hyves.nl/help/feedback/?feedbackMode= email&answerId=96&categoryId=142&category= Privacy%2C+pesten+en+spam");
		message = StringFormatHelper.getHelper().formatHangingIndent(message, 5, 60);

		conversation.append(message+ "\n\n");
		conversation.setCaretPosition(conversation.getText().length() - 1);

	}

	public String getUserName(){
		return userName;
	}

	//public LinkedList<SpeechAct> getUserSpeechActs(){
	//	return uSA.getUserSpeechActs();
	//}

	// Bij klikken op de knop wordt er een Speech Act toegevoegd aan de UserSpeechActs
	public class UserInputButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String ac = e.getActionCommand();
			Fact2Emotion em;

			if(ac.equals(abActionCommand)){
				// AffectButton in response to question
				affectButtonUsed = true;
				
				double p = ab.getP();
				double a = ab.getA();
				double d = ab.getD();

				//String message = getUserName()+" uses AffectButton (P:"+p+" A:"+a+" D:"+d+")";
				//String message = getUserName()+" klikt op de AffectButton";
				String message = getUserName()+" clicks the AffectButton";
				
				addMessageToChat(message);

				SpeechAct sa = new SpeechAct(p, a, d, ac);
				log.log("user,"+sa.getCode());
				synchronized(incidentFacts){
					incidentFacts.add(sa.getCode());
				}
				
				//addMessageToChat("about to hide affectbutton");

				hideAffectButton();
				
				//addMessageToChat("affectbutton hidden");
				
				em = new Fact2Emotion(p,a,d,faceRobin);

			} else {
				// Response option
				SpeechAct sa = null;

				for(SpeechAct option:currentResponseOptions){
					if(ac.equals(option.getCode())){
						sa = option;
						//addMessageToChat(sa.toString());
					}
				}

				if(sa != null ){
					synchronized(incidentFacts){
						incidentFacts.add(sa.getCode());
					}

					//String message = getUserName()+" zegt: "+sa.getNLUtterance();
					String message = getUserName()+" says: "+sa.getNLUtterance();
					addMessageToChat(message);
					log.log("user,"+sa.getCode());
				}
				
				em = feltEmotion.getEmotionalResponse(ac);

				// Remove response options
				changeResponseOptions(new LinkedList<SpeechAct>());						
			}
			
			if(em != null){
				emotionalResponseInProgress = true;
				EmotionalResponseThread t = new EmotionalResponseThread(em);
				t.start();
			}
			
			waitForUser = false;
			//addMessageToChat("Wait for user is klaar");
		
		}

	} // einde class UserInputButtonListener
	
	public class EmotionalResponseThread extends Thread {
		
		Fact2Emotion emotion;
		
		public EmotionalResponseThread(Fact2Emotion em){
			emotion = em;
		}
		
		public void run(){
			//addMessageToChat("Emotionele reactie voor: "+userMessage);
			//addMessageToChat("about to do sequence (+emotion): "+em.getSequenceName());
			while(waitForUser){
				waitTime(10);
			}
			
			if(verbal && nonverbal){
				doSequence(emotion.getSequenceName(), emotion);
			} else if(verbal){
				doSequence(emotion.getSequenceName(), null);
			} else if(nonverbal){
				doSequence(null, emotion);
			}
			
			emotionalResponseInProgress = false;
		}		

	} // einde class EmotionalResponseThread

	/**
	 * GUI
	 */
	public void changeResponseOptions(LinkedList<SpeechAct> options) {
		remove(userInput);

		GridBagConstraints c;
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		//c.gridheight = 2;
		//c.insets = new Insets(10, 5, 0, 5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		userInput = generateResponseOptions(options);

		add(userInput, c);

		validate();
		repaint();
	}
	
	public JPanel generateResponseOptions(LinkedList<SpeechAct> options){
		JPanel responsePanel = new JPanel();
		JButton b;

		responsePanel.setBackground(Color.white);
		responsePanel.setLayout(new GridLayout(0,1));

		currentResponseOptions.clear();

		for(SpeechAct option : options){
			currentResponseOptions.add(option);
			b = newButton(option.getNLUtterance(), option.getCode());
			responsePanel.add(b);
		}

		return responsePanel;
	}

	public JButton newButton(String name, String actionCommand){
		name = name.replace("NAAM", userName);
		JButton button = new JButton(name);
		button.setFont(normalFont);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setActionCommand(actionCommand);
		button.addActionListener(new UserInputButtonListener());

		return button;
	}
	
	private void createGUI() {

		//Border blackline = BorderFactory.createLineBorder(Color.gray);
		Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		//Border compound = BorderFactory.createCompoundBorder(blackline, innerBorder);
		Border blueline = BorderFactory.createLineBorder(new Color(68, 131, 208), 2);
		Border greenline = BorderFactory.createLineBorder(new Color(228, 210, 103), 2);

		//backgroundColor = new Color(250,255,191);
		Color backgroundColor = Color.white;

		//setSize(800, 700);
		setSize(800,600);
		Container content = getContentPane();
		content.setBackground(backgroundColor);
		//((JComponent) content).setBorder(greenline);

		// set default font
		normalFont = new Font("Serif", Font.PLAIN, 18);

		faceRobin = new Embodiment(normalFont);

		// message history
		conversation = new JTextArea(12, 33);
		conversation.setFont(normalFont);
		conversation.setEditable(false);
		conversation.setOpaque(false);
		conversation.setWrapStyleWord(true);
		conversation.setLineWrap(true);
		conversation.setBorder(innerBorder);

		conversationScroll = new JScrollPane(conversation, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		ab = new AffectButton(new PADFaceMapped(0,0,1));
		ab.setActionCommand("ab");
		ab.addActionListener(new UserInputButtonListener());
		ab.setBounds(0, 0, 150, 150);
		//ab.setVisible(false);

		// For some reason the AffectButton doesn't have a size, unless it is added to its own panel
		abPanel = new JPanel();
		abPanel.setLayout(null);
		abPanel.setPreferredSize(new Dimension(200, 200));
		abPanel.setBackground(Color.white);
		abPanel.add(ab);

		JPanel spacer = new JPanel();
		spacer.setLayout(null);
		spacer.setPreferredSize(new Dimension(200, 200));
		spacer.setBackground(Color.white);

		// Response options
		userInput = generateResponseOptions(new LinkedList<SpeechAct>());
		userInput.setPreferredSize(new Dimension(150, 200));

		content.setLayout(new GridBagLayout());
		GridBagConstraints c;

		// Plaatje buddy
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		content.add(faceRobin.getExpression(), c);

		// message history
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(10, 5, 10, 5);
		//c.gridwidth = 2;
		//c.gridheight = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		content.add(conversationScroll, c); 

		// spacer
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 5);
		c.anchor = GridBagConstraints.CENTER;

		content.add(spacer, c);

		// response options
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		//c.gridheight = 2;
		//c.insets = new Insets(10, 5, 0, 5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		content.add(userInput, c);
	}
	
	public void showAffectButton(){
		remove(userInput);

		GridBagConstraints c;
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		//c.gridheight = 2;
		//c.insets = new Insets(10, 5, 0, 5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		add(abPanel, c);

		validate();
		repaint();
	}

	public void hideAffectButton(){
		remove(abPanel);

		changeResponseOptions(new LinkedList<SpeechAct>());
	}

	/**
	 * Other methods
	 */
	public void waitTime(int delay){
		// delay for changing expression
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	public void setEmotionalResponseInProgress(boolean e) {
		emotionalResponseInProgress = e;
	}
}