/*
 *
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class GUI.
 */
public class GUI {

	/** The main frame. */
	private JFrame mainFrame;

	/** The visibility. */
	private boolean visibility = false;

	/** The Char extraction panel. */
	private JPanel CharExtractionPanel;

	/** The image loc. */
	private JTextField imageLoc;

	private File defaultImageLoc;

	/** The output dir. */
	private JTextField outputDir;

	private File defaultOutDir;

	/** The pixel size. */
	private JTextField pixelSize;

	/**
	 * Instantiates a new gui.
	 */
	public GUI() {
		this.mainFrame = new JFrame("Optical Character Recognition");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.CharExtractionPanel = new JPanel();
		// this.CharExtractionPanel.setLayout(new BoxLayout(CharExtractionPanel,
		// BoxLayout.Y_AXIS));
		this.CharExtractionPanel.add(getFileAndFolderSelectionPanel());
		this.mainFrame.getContentPane().add(this.CharExtractionPanel);
		this.mainFrame.pack();

	}

	/**
	 * Gets the file and folder selection panel.
	 *
	 * @return the file and folder selection panel
	 */
	private JPanel getFileAndFolderSelectionPanel() {
		JPanel panel = new JPanel();
		// panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Create Image Selector
		panel.add(getImageSelectPanel());

		// Create Output Dir Selector
		panel.add(getOutputDirSelector());

		// Create pixel selection panel
		panel.add(getPixelSizeSelectionPanel());

		return panel;
	}

	/**
	 * Gets the image select panel.
	 *
	 * @return the image select panel
	 */
	private JPanel getImageSelectPanel() {
		JPanel imageSel = new JPanel();
		imageSel.setLayout(new BoxLayout(imageSel, BoxLayout.X_AXIS));
		JLabel imgLable = new JLabel("Select Image: ");
		imageSel.add(imgLable);

		imageLoc = new JTextField(40);
		imageSel.add(imageLoc);

		defaultImageLoc = new File(imageLoc.getText());

		JButton button = new JButton("Select");
		button.addActionListener(getImageSelectAction());
		imageSel.add(button);
		imageSel.setAlignmentY(Component.RIGHT_ALIGNMENT);

		imageSel.setMinimumSize(new Dimension(0, 100));

		return imageSel;
	}

	/**
	 * Gets the output dir selector.
	 *
	 * @return the output dir selector
	 */
	private JPanel getOutputDirSelector() {
		JPanel dirSelectPanel = new JPanel();
		dirSelectPanel.setLayout(new BoxLayout(dirSelectPanel, BoxLayout.X_AXIS));
		JLabel dirLabel = new JLabel("Select Output Dir: ");
		dirSelectPanel.add(dirLabel);

		outputDir = new JTextField(40);
		dirSelectPanel.add(outputDir);

		defaultOutDir = new File(outputDir.getText());

		JButton button = new JButton("Select");
		button.addActionListener(getOutDirSelectAction());
		dirSelectPanel.add(button);
		dirSelectPanel.setAlignmentY(Component.RIGHT_ALIGNMENT);

		return dirSelectPanel;
	}

	/**
	 * Gets the pixel size selection panel.
	 *
	 * @return the pixel size selection panel
	 */
	private JPanel getPixelSizeSelectionPanel() {
		JPanel panel = new JPanel();
		// panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(new JLabel("Select Output Character Size In Pixels (0 is default): "));

		pixelSize = new JTextField(5);
		pixelSize.setText("0");
		pixelSize.addActionListener(getPixelSizeAction());
		panel.add(pixelSize);
		panel.setAlignmentY(Component.RIGHT_ALIGNMENT);

		panel.add(getExtractButton());

		return panel;
	}

	private JPanel getExtractButton() {
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btn = new JButton("Extract Characters");
		btn.addActionListener(getExtractAction());

		btnPanel.add(btn);

		return btnPanel;
	}

	/**
	 * Gets the image select action.
	 *
	 * @return the image select action
	 */
	private ActionListener getImageSelectAction() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imageLoc.setText(chooser.getSelectedFile().getAbsolutePath());
				}

				// if (!imageLoc.getText().equals(null)) {
				// try {
				// CharExtract ce = new CharExtract(new
				// File(imageLoc.getText()),
				// Integer.parseInt(pixelSize.getText()));
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// }
			}
		};
	}

	/**
	 * Gets the out dir select action.
	 *
	 * @return the out dir select action
	 */
	private ActionListener getOutDirSelectAction() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					outputDir.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		};
	}

	private ActionListener getExtractAction() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isOk = true;
				int std_size = -1;
				File imageFile = new File(imageLoc.getText());
				File outDir = new File(outputDir.getText());

				if (!imageFile.equals(defaultImageLoc) && !imageFile.exists()) {
					JOptionPane.showMessageDialog(null, "Check image directory!");
					isOk = false;
				}

				if (!outDir.equals(defaultOutDir) && !outDir.exists()) {
					JOptionPane.showMessageDialog(null, "Check output directory!");
					isOk = false;
				}

				try {
					std_size = Integer.parseInt(pixelSize.getText());
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "Input valid size in pixels!");
					isOk = false;
				}

				if (isOk) {
					try {
						if (imageFile.equals(defaultImageLoc)) imageLoc.setText("/Users/V/Documents/workspace/Comparison OCR/Comfortaa.png");
						Thread.sleep(10);
						CharExtract ce = new CharExtract(new File(imageLoc.getText()), Integer.parseInt(pixelSize.getText()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Complete.");
				}
			}
		};
	}

	private ActionListener getPixelSizeAction() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pixelSize.getText().equals("0")) {
					pixelSize.setText(null);
				}
			}
		};

	}

	/**
	 * Toggle visibility.
	 */
	public void toggleVisibility() {
		this.visibility = !this.visibility;
		this.mainFrame.setVisible(true);
	}
}
