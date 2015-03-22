package virtualbuddy;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Embodiment {
	
	JLabel expression;
	String expressionLabel;
	String expressionIntensity;
	
	public Embodiment(Font f){
		
		// set initial expression
		expressionLabel = "content";
		expressionIntensity = "med";
		
		expression = new JLabel("Robin", FacialExpression.CONTENT.getExpressionMed(), JLabel.CENTER);
		expression.setFont(f);
		expression.setVerticalTextPosition(JLabel.BOTTOM);
		expression.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	public JLabel getExpression(){
		return expression;
	}
	
	public String getExpressionLabel(){
		return expressionLabel;
	}
	
	public String getExpressionIntensity(){
		return expressionIntensity;
	}
	
	/*
	public void setExpression(String expr){
		expressionLabel = expr;
		expressionIntensity = "high";
		
		expression.setIcon(FacialExpression.toKey(expr).getExpressionHigh());	
	}*/
	
	public void setExpression(String expr, String intensity){
		expressionLabel = expr;
		expressionIntensity = intensity;
		
		if(intensity.equals("high")) expression.setIcon(FacialExpression.toKey(expr).getExpressionHigh());
		else expression.setIcon(FacialExpression.toKey(expr).getExpressionMed());
		
		//System.out.println("Emotion: "+expr+", "+intensity);
	}
	
	public void addEmotion(String expr, int steps){
		if(expr.equals(expressionLabel)){
			if(expressionIntensity.equals("med") && steps > 0 ){
				expressionIntensity = "high";	
			}
		} else {
			if(expressionIntensity.equals("med") ){
				expressionLabel = expr;
				if( steps == 2 ){
					expressionIntensity = "high";
				} 					
			} else if(expressionIntensity.equals("high")){
				expressionIntensity = "med";
				if(steps==2){
					expressionLabel = expr;
				}
			}
		}
		
		setExpression(expressionLabel,expressionIntensity);
		
	}
	
	public String[] mirror(double p, double a, double d){
		ImageIcon result = null;
		double dist = 100.0;
		double distHigh, distMed;
		String expr = "neutral";
		String intensity = "med";
		
		for( FacialExpression f : FacialExpression.values() ){
			distHigh = Math.sqrt( Math.pow(f.highP-p, 2) +Math.pow(f.highA-a, 2) + Math.pow(f.highD-d, 2) );
			distMed = Math.sqrt( Math.pow(f.medP-p, 2) +Math.pow(f.medA-a, 2) + Math.pow(f.medD-d, 2) );
			
			//System.out.println("FacialExpression: "+f.toString());
			//System.out.println("distHigh "+distHigh);
			//System.out.println("distMed "+distMed);
			
			if(dist > distHigh ){
				dist = distHigh;
				result = f.getExpressionHigh();
				expr = f.toString();
				intensity = "high";
			}
			
			if(dist > distMed ){
				dist = distMed;
				result = f.getExpressionMed();
				expr = f.toString();
				intensity = "med";
			}

		}
		
		String[] res = {expr.toLowerCase(), intensity};
		
		return res;
	}

	protected enum FacialExpression {
		ANGRY (1, -1, 1, 1, 4, -0.5, 0.5, 0.5), 
		CONTENT (2, 1, -1, 1, 5, 0.5, -0.5, 0.5), 
		HAPPY (3, 1, 1, 1, 6, 0.5, 0.5, 0.5), 
		RELAXED (16, 1, -1, -1, 15, 0.5, -0.5, -0.5), 
		SURPRISED (25, 1, 1, -1, 22, 0.5, 0.5, -0.5), 
		SAD (24, -1, -1, -1, 21, -0.5, -0.5, -0.5), 
		AFRAID (23, -1, 1, -1, 20, -0.5, 0.5, -0.5), 
		FRUSTRATED (10, -1, -1, 1, 11, -0.5, -0.5, 0.5), 
		NEUTRAL(13, 0, 0, 0, 13, 0, 0, 0);
		
		private final ImageIcon high;
		private final double highP;
		private final double highA;
		private final double highD;
		private final ImageIcon med;
		private final double medP;
		private final double medA;
		private final double medD;
		
		FacialExpression(int high, double highP, double highA, double highD, int med,  double medP, double medA, double medD){
		
			this.high = createImageIcon("/images/Robin_"+getImageNr(high)+".jpg", true);
			this.highP = highP;
			this.highA = highA;
			this.highD = highD;
			this.med = createImageIcon("/images/Robin_"+getImageNr(med)+".jpg", true);
			this.medP = medP;
			this.medA = medA;
			this.medD = medD;
		}
		
		public ImageIcon getExpressionMed() { return med; }

		public ImageIcon getExpressionHigh() { return high; }

		//private ImageIcon getExpression(){ return high; }
				
		public static FacialExpression toKey(String key) {
			try {
				return valueOf(key.toUpperCase());
			} catch (Exception ex) {
				return NEUTRAL;
			}
		}
		
		private String getImageNr(int i){
			String imageNr;
			
			if(i<=9) imageNr = "0"+i;
			else imageNr = ""+i;
			
			return imageNr;
			
		}
		
		/** Returns an ImageIcon, or null if the path was invalid. 
		 * Source: http://download.oracle.com/javase/tutorial/uiswing/components/icon.html 
		 * Toegevoegd: scale
		 * */
		private ImageIcon createImageIcon(String path, boolean scale) {
			ImageIcon result;
			Image image;

			java.net.URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				result = new ImageIcon(imgURL);
				if(scale){
					image = result.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					result.setImage(image);
				}
			} else {
				System.err.println("Couldn't find file: " + path);
				result = null;
			}

			return result;
		}

	}
	
	public static void main(String[] args){
		Font normalFont = new Font("Serif", Font.PLAIN, 18);
		
		Embodiment e = new Embodiment(normalFont);
		
		//String expr = e.mirror(-1, 1, -1);
		
		//System.out.println(expr);
		e.addEmotion("happy", 2);
		System.out.println("Expr: "+e.expressionLabel+" - Int: "+e.expressionIntensity);
		e.addEmotion("sad", 2);
		System.out.println("Expr: "+e.expressionLabel+" - Int: "+e.expressionIntensity);
	}
	
}
