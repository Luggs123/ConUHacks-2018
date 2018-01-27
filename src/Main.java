import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class Main {
	private static JTextField inputField;
	private static JTextField filePath;
	private static void showWindow() {
		JFrame frame = new JFrame("Mood Interpreter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 450);
		frame.getContentPane().setLayout(null);		
		frame.setLocation(500, 300);
		
		inputField = new JTextField();
		inputField.setBounds(57, 56, 395, 286);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
				
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
					inputField.setText(sc.next());
					sc.close();
					}catch(Exception ex) {
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
		
		JButton editMode = new JButton("Edit Mode");
		editMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
				showWindow();
			}
		});
	}
}
