import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Comparison_OCR.
 */
public class Comparison_OCR {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		setLookandFeel();
		GUI window = new GUI();
		window.toggleVisibility();

		// try {
		// CharExtract ce = new CharExtract(new File("rsz_a.png"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * Sets the look and feel.
	 */
	private static void setLookandFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}
	}
}
