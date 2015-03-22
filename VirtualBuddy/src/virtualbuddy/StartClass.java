package virtualbuddy;

public class StartClass {
	
	 
	public static void main(String [] args)
	{
		// create an object of type CaesarCode which is the main applet class
		DemoRobin theApplet = new DemoRobin();
		theApplet.init();   // invoke the applet's init() method
		theApplet.start();  // starts the applet

		// Create a window (JFrame) and make applet the content pane.
		javax.swing.JFrame window = new javax.swing.JFrame("Chat with Robin");
		//javax.swing.JFrame window = new javax.swing.JFrame("Chat met Robin");
		window.setContentPane(theApplet);
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.pack();              // Arrange the components.
		window.setVisible(true);    // Make the window visible.
	}

}
