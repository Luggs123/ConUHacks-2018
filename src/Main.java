import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mood Interpreter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton importButton = new JButton("Import...");
		JButton analyzeButton = new JButton("Analyze");
		JButton sentenceSelect = new JButton("Sentence Mode");
		JButton editMode = new JButton("Edit Mode");
		frame.add(importButton);
		frame.add(analyzeButton);
		frame.add(sentenceSelect);
		frame.add(editMode);
		
		JTextField input = new JTextField();
		JTextField filePath = new JTextField();
		frame.add(input);
		frame.add(filePath);
		
		JPanel toneContainer = new JPanel();
		frame.add(toneContainer);
		JLabel[] tones = new JLabel[7];
		
		for (int i = 0; i < 7; i++) {
			tones[i] = new JLabel();
			frame.add(tones[i]);
		}
	}
}
