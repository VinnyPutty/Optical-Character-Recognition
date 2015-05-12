/*
 * 
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	/** The output dir. */
	private JTextField outputDir;
	
	/** The pixel size. */
	private JTextField pixelSize;
	
	/**
	 * Instantiates a new gui.
	 */
	public GUI(){
		this.mainFrame = new JFrame("Optical Character Recognition");
		this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		this.CharExtractionPanel = new JPanel();
//		this.CharExtractionPanel.setLayout(new BoxLayout(CharExtractionPanel, BoxLayout.Y_AXIS));
		this.CharExtractionPanel.add(getFileAndFolderSelectionPanel());
		this.mainFrame.getContentPane().add(this.CharExtractionPanel);
		this.mainFrame.pack();
		
	}
	
	
	/**
	 * Gets the file and folder selection panel.
	 *
	 * @return the file and folder selection panel
	 */
	private JPanel getFileAndFolderSelectionPanel(){
        JPanel panel = new JPanel();
//      panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //Create Image Selector
        panel.add(getImageSelectPanel());

        //Create Output Dir Selector
        panel.add(getOutputDirSelector());

        //Create pixel selection panel
        panel.add(getPixelSizeSelectionPanel());

        return panel;
    }
	
	
	
	/**
	 * Gets the image select panel.
	 *
	 * @return the image select panel
	 */
	private JPanel getImageSelectPanel()
    {
        JPanel imageSel = new JPanel();
        imageSel.setLayout(new BoxLayout(imageSel, BoxLayout.X_AXIS));
        JLabel imgLable = new JLabel("Select Image: ");
        imageSel.add(imgLable);

        imageLoc = new JTextField(40);
        imageSel.add(imageLoc);

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
    private JPanel getOutputDirSelector()
    {
        JPanel dirSelectPanel = new JPanel();
        dirSelectPanel.setLayout(new BoxLayout(dirSelectPanel, BoxLayout.X_AXIS));
        JLabel dirLabel = new JLabel("Select Output Dir: ");
        dirSelectPanel.add(dirLabel);

        outputDir = new JTextField(40);
        dirSelectPanel.add(outputDir);

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
    private JPanel getPixelSizeSelectionPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Select Output Character Size In Pixels: "));

        pixelSize = new JTextField(5);
        pixelSize.setText("75");
        panel.add(pixelSize);
        panel.setAlignmentY(Component.RIGHT_ALIGNMENT);

        return panel;
    }

    /**
	 * Gets the image select action.
	 *
	 * @return the image select action
	 */
    private ActionListener getImageSelectAction()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    imageLoc.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }

    /**
	 * Gets the out dir select action.
	 *
	 * @return the out dir select action
	 */
    private ActionListener getOutDirSelectAction()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    outputDir.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }
    
    
	
	/**
	 * Toggle visibility.
	 */
	public void toggleVisibility(){
		this.visibility = !this.visibility;
		this.mainFrame.setVisible(true);
	}
}
