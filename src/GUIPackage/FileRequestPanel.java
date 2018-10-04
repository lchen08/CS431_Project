package GUIPackage;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is the panel requesting the user for the job file to be submitted to create the Gantt
 * charts for the CPU Schedulers. The job file comes in .txt format and has the following layout:
 *      One line for a job's name
 *      One line for the burst time of a job (from the previous line)
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
class FileRequestPanel extends JPanel
{
    //gets the directory of the application
    private final String START_FILE_DIR = System.getProperty("user.dir");
    private final String REQUEST_TEXT = "Please select the job file to create Gantt Charts.";
    private final String  JOB_REQ_TEXT = "Job File";
    private final int BUFFER_SPACE = 10;
    private final int REQ_PANEL_WIDTH = 500;
    private final int REQ_PANEL_HEIGHT = 15;
    private final String DEFAULT_PATH = "No file selected.";
    private final String CHOOSE_BUTTON_TEXT = "Select Job File";
    private final String SUBMIT_BUTTON_TEXT = "Calculate";
    private Main parentFrame;
    private String jobFilePath;
    private JFileChooser fc;
    private JPanel submitPanel;
    private JLabel requestText;
    private BorderLayout panelLayout;

    /**
     * Constructs the panel interface for the file request section.
     * @param parentFrame The parent frame of the interface.
     */
    public FileRequestPanel(Main parentFrame)
    {
        this.parentFrame = parentFrame;
        panelLayout = new BorderLayout();
        requestText = new JLabel(REQUEST_TEXT);
        fc = new JFileChooser();
        jobFilePath = DEFAULT_PATH;
        submitPanel = getSubmitButton();

        requestText.setHorizontalAlignment(SwingConstants.CENTER);
        //formatting and layout
        panelLayout.setVgap(BUFFER_SPACE);
        setLayout(panelLayout);
        setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE,
                BUFFER_SPACE));

        add(requestText, panelLayout.NORTH);
        add(initializeFileReqPanel(), panelLayout.CENTER);
    }

    /**
     * Initializes the overall file request panel that contains the choose file panel, the job
     * file path, and the submit button.
     * @return The overall file request panel
     */
    private JPanel initializeFileReqPanel()
    {
        JPanel overallPanel = new JPanel();
        JPanel jobReqPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(2,1);
        overallPanel.setLayout(gridLayout);

        jobReqPanel.add(getChooseFilePanel());
        overallPanel.add(jobReqPanel);
        overallPanel.add(submitPanel);

        return overallPanel;
    }

    /**
     * Initializes a choose job file panel.
     * @return The choose file panel
     */
    private JPanel getChooseFilePanel()
    {
        JPanel choosePanel = new JPanel();
        JButton chooseButton = new JButton(CHOOSE_BUTTON_TEXT);
        JLabel filenameLabel = new JLabel(DEFAULT_PATH);

        //formatting and adding listener
        filenameLabel.setPreferredSize(new Dimension(REQ_PANEL_WIDTH,REQ_PANEL_HEIGHT));
        chooseButton.addActionListener(new ChooseFileButtonListener(filenameLabel));

        //formats and sets up the panel
        choosePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        choosePanel.add(chooseButton);
        choosePanel.add(filenameLabel);

        return choosePanel;
    }

    /**
     * Retrieves the container for the submit button.
     * @return The panel container of the submit button
     */
    private JPanel getSubmitButton()
    {
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton(SUBMIT_BUTTON_TEXT);

        button.addActionListener(new SubmitButtonListener());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE,
                BUFFER_SPACE, BUFFER_SPACE));
        buttonPanel.add(button);

        return buttonPanel;
    }

    /**
     * This is the listener class for the choose file submit button. It updates the file path for
     * the job file for the user and for the parent frame per what the user chooses.
     */
    private class ChooseFileButtonListener implements ActionListener
    {
        private JLabel fileNameText;

        /**
         * Constructs the button listener and related attributes.
         * @param fileNameText The label for the filename path for user's reference
         */
        private ChooseFileButtonListener(JLabel fileNameText)
        {
            this.fileNameText = fileNameText;

            fc.setCurrentDirectory(new File(START_FILE_DIR));
            fc.setFileFilter(new TxtFileFilter());
            fc.setAcceptAllFileFilterUsed(false);
        }

        /**
         * Invoked when the choose file button is pressed. Updates to show the filename path to
         * the user.
         * @param buttonPressed The choose file button is pressed
         */
        public void actionPerformed(ActionEvent buttonPressed)
        {
            int returnVal = fc.showDialog(fc, "Select " + JOB_REQ_TEXT);
            if (returnVal == fc.APPROVE_OPTION)
            {
                //updates the file path for user's reference
                fileNameText.setText(fc.getSelectedFile().toString());
                jobFilePath = fileNameText.getText();
            }
        }
    }

    /**
     * This is a listener class for the submit button where the job filename path is submitted
     * to the parent frame.
     */
    private class SubmitButtonListener implements ActionListener
    {

        /**
         * When the submit button is pressed, it submits the job file path to create the Gantt
         * charts through the parent frame. It handles issues with the files (incorrect files,
         * wrong pathways, or no files selected) and prompts the user to enter again.
         * @param buttonPressed The submit button is pressed
         */
        public void actionPerformed(ActionEvent buttonPressed)
        {
            try
            {
                parentFrame.updateCharts(new File(jobFilePath));
            }
            //when a file isn't selected or file path is wrong for some reason
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "File does not exist or nothing were selected.",
                        "File Not Found", JOptionPane.WARNING_MESSAGE);
                //hides and recreates the frame since it is no longer interactive on pop-up dialog
                parentFrame.setVisible(false);
                parentFrame = new Main();
                parentFrame.setVisible(true);
            }
            //when the files chosen have the wrong text, it can cause numerous exceptions
            catch(Exception e2)
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "The file selected is not valid for this program.",
                        "Invalid File", JOptionPane.WARNING_MESSAGE);
                //hides and recreates the frame since it is no longer interactive on pop-up dialog
                parentFrame.setVisible(false);
                parentFrame = new Main();
                parentFrame.setVisible(true);
            }
        }
    }

    /** This is a file filter class that is filters for .txt files in the file chooser. */
    private class TxtFileFilter extends FileFilter
    {
        private String acceptableExt = "txt";

        /**
         * Tests whether the specified abstract pathname should be included in a pathname list.
         * @param f The pathname to be tested
         * @return True if the file path ends with .txt or is a directory; false otherwise
         */
        public boolean accept(File f)
        {
            //allows directory folders to show
            if (f.isDirectory())
                return true;

            String path = f.getPath();
            int dotLocation = path.lastIndexOf('.');
            if (dotLocation > 0)
            {
                String ext = path.substring(dotLocation + 1);
                if (ext.equals(acceptableExt))
                    return true;
            }
            return false;
        }

        /**
         * Gets the description of the file type that is accepted.
         * @return The description of the accepted file type
         */
        public String getDescription()
        {
            return "." + acceptableExt;
        }
    }
}
