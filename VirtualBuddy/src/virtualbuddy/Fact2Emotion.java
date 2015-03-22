package virtualbuddy;

public class Fact2Emotion {
	
	private String incidentFact;
	private String emotionType;
	private String emotionLabel;
	private int steps;
	private String sequenceName;
	private String intensity;
	private boolean mirror;
	
	public Fact2Emotion(String fact, String occType, String padLabel, int st, String seqName){
		incidentFact = fact;
		emotionType = occType;
		emotionLabel = padLabel;
		steps = st;
		sequenceName = seqName;
		intensity = null;
		mirror = false;
	}
	
	public Fact2Emotion(double p, double a, double d, Embodiment face){
		String[] expr = face.mirror(p, a, d);
		
		emotionLabel = expr[0];
		intensity = expr[1];
		emotionType = "";
		
		incidentFact = "incident(emotional_state,"+emotionLabel+")";
		
		steps = 0;
		if(p>0.3){
			sequenceName = "happy_for(emotional_state,"+emotionLabel+")";
		} else {
			sequenceName = "sympathy(emotional_state,"+emotionLabel+")";
		}
		
		mirror = true;
	}

	public String getIncidentFact() {
		return incidentFact;
	}

	public String getEmotionType() {
		return emotionType;
	}

	public String getEmotionLabel() {
		return emotionLabel;
	}

	public int getSteps() {
		return steps;
	}
	
	public String getSequenceName() {
		return sequenceName;
	}
	
	public String getEmotionIntensity() {
		return intensity;
	}
	
	public boolean getMirror() {
		return mirror;
	}

}
