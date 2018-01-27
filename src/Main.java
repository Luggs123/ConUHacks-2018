import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class Main {
	private static JTextArea inputField;
	private static JTextField filePath;
	private static void showWindow() throws BadLocationException {
		JFrame frame = new JFrame("Mood Interpreter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 450);
		frame.getContentPane().setLayout(null);		
		frame.setLocation(500, 300);
		
		inputField = new JTextArea();		
		inputField.setColumns(10);
		inputField.setEnabled(false);
		inputField.setDisabledTextColor(Color.BLACK);
		inputField.setWrapStyleWord(true);
		inputField.setLineWrap(true);
		inputField.setCaretPosition(inputField.getDocument().getLength());
		
		Highlighter highlighter = inputField.getHighlighter();
        HighlightPainter painter = 
                 new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		
		
		JScrollPane scroll = new JScrollPane(inputField);
		scroll.setBounds(57, 56, 395, 286);
		frame.getContentPane().add(scroll);
				
		JButton fileImport = new JButton("Import...");
		fileImport.setBounds(57, 18, 97, 25);
		frame.getContentPane().add(fileImport);
		fileImport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser fChooser = new JFileChooser();
				int returnValue = fChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					try {
					File selFile = fChooser.getSelectedFile();
					filePath.setText(selFile.toString());
					Scanner sc = new Scanner(new FileInputStream(selFile.getPath()));
					sc.useDelimiter("\\A");
					inputField.setText(sc.hasNext() ? sc.next() : "");
					sc.close();
					
			        // Highlighting every instance of 'Barry'.
			        for (String sentence : inputField.getText().split("\\."))
			        {
			            int i = sentence.indexOf("Barry");
			            int j = i + "Barry".length();
			            highlighter.addHighlight(i, j, painter);
			        }
			        
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		filePath = new JTextField();
		filePath.setBounds(182, 19, 270, 22);
		frame.getContentPane().add(filePath);
		filePath.setColumns(10);
		
		JButton sentenceSelect = new JButton("Sentence Mode");
		sentenceSelect.setBounds(57, 355, 165, 25);
		frame.getContentPane().add(sentenceSelect);
		sentenceSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputField.setEnabled(false);				
			}
		});
		
		JButton editMode = new JButton("Edit Mode");
		editMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputField.setEnabled(true);				
			}
		});
		editMode.setBounds(287, 355, 165, 25);
		frame.getContentPane().add(editMode);
		
		JButton textAnalyze = new JButton("Analyze");
		textAnalyze.setBounds(607, 18, 97, 25);
		frame.getContentPane().add(textAnalyze);
		
		JPanel toneContainer = new JPanel();
		toneContainer.setBounds(534, 56, 245, 286);
		frame.getContentPane().add(toneContainer);
		toneContainer.setLayout(null);
		
		JLabel[] tones = new JLabel[7];
		
		for (int i = 0; i < 7; i++) {
			tones[i] = new JLabel("");
			tones[i].setBounds(22, 57 + 19*i, 211, 16);
			toneContainer.add(tones[i]);
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
                    showWindow();
                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
		});
	}
}
