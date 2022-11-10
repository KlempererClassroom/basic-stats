package gui;

import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import model.BasicStatsModel;
import gui.view.*;


/**
 * Create a simple GUI that includes:
 * - a text field and a button that allows the user to enter numbers.
 * - a button that allows the user to clear all entered numbers.
 * - a panel with labels and text fields for count, median, and mean.
 * - a text area that shows all numbers.
 *
 * For the MVC architecture pattern, these are the views and controllers.
 */
public class BasicStatsGUI implements View
{
    public static final String APP_TITLE = "Simple stats";

    private static BasicStatsModel model = new BasicStatsModel();

    private CountView countView;
    private MedianView medianView;
    private MeanView meanView;
    private MaxView maxView;
    private NumbersView numbersView;
    private JTextField jtfNumber;
    private JButton jbReset;
    private JButton jbAdd;

    private JFrame jfMain = new JFrame(APP_TITLE);

    public BasicStatsGUI() {
	// Create the main frame of the application, and set size and position
	jfMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	jfMain.setSize(600,400);
	jfMain.setLocationRelativeTo(null);

	// Panel that shows stats about the numbers
	JPanel jpStats = new JPanel(new FlowLayout(FlowLayout.CENTER));

	countView = new CountView();
    medianView = new MedianView();
    meanView = new MeanView();
    maxView = new MaxView();
	jpStats.add(new JLabel("Numbers:"));
	jpStats.add(countView.getView());
	jpStats.add(new JLabel("Median:"));
	jpStats.add(medianView.getView());
	jpStats.add(new JLabel("Mean:"));
	jpStats.add(meanView.getView());
    jpStats.add(new JLabel("Max:"));
	jpStats.add(maxView.getView());

	jfMain.getContentPane().add(jpStats, BorderLayout.CENTER);

	// TextArea that shows all the numbers
	numbersView = new NumbersView();
	jfMain.getContentPane().add(numbersView.getView(), BorderLayout.SOUTH);


	// Panel with a text field/button to enter numbers and a button to reset the application
	jbReset = new JButton("Reset");
	jbReset.addActionListener(new ActionListener() {
		// The interface ActionListener defines a call-back method actionPerformed,
		// which is invoked if the user interacts with the GUI component -- in this
		// case, if the user clicks the button.
		public void actionPerformed(ActionEvent e) {
		    // Clear the ArrayList and all text fields
		    model.reset();

		    update(model);
		}
	    });

	jtfNumber = new JTextField(5);
    //jtfNumber.setEditable(true);
	jbAdd = new JButton("Add number");
	jbAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    // Parse input and add number to the ArrayList
            try
            {
                Double num = Double.parseDouble(jtfNumber.getText());
                model.addNumber(num);
    		    update(model);
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(jfMain, "Please enter a valid number!",
               "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch(NullPointerException npe)
            {
                JOptionPane.showMessageDialog(jfMain, "Please enter a number!",
               "Error", JOptionPane.ERROR_MESSAGE);
            }

		}
	    });
	JPanel jpInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
	jpInput.add(jtfNumber);
	jpInput.add(jbAdd);
	jpInput.add(jbReset);


	jfMain.getContentPane().add(jpInput, BorderLayout.NORTH);
    }

    public void update(BasicStatsModel model) {
	    // Update the displayed list of numbers
        jtfNumber.setText("");

	    // Compute and set the count
	    countView.update(model);

	    // Compute and set the mean
	    meanView.update(model);

	    // Compute and set the median
	    medianView.update(model);

        numbersView.update(model);

        maxView.update(model);
    }

    public void show() {
	// Show the frame
	jfMain.setVisible(true);
    }

    public JTextField getCountView() {
        return countView.getView();
    }

    public JTextField getMaxView() {
        return maxView.getView();
    }

    public JTextField getMeanView() {
        return meanView.getView();
    }

    public JTextField getMedianView() {
        return medianView.getView();
    }

    public JTextArea getNumbersView() {
        return numbersView.getView();
    }

    public JTextField getNumberField() {
        return jtfNumber;
    }

    public JButton getAddNumberButton() {
        return jbAdd;
    }

    public JButton getResetButton() {
        return jbReset;
    }

}
