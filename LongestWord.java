package Project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LongestWord extends JFrame implements ActionListener{
	
	private final static String[] COMMENTS = { 	"Why do you feel %s is important?",//
												"OK tell me more about %s",
												"How does %s affect you?",
												"We seem to be making great progress with %s",
												"Is there something else you would like to discuss?"};
	
	
	private JPanel container;
	
	// container
	
	//main window components initializing the gui
	private JLabel question;
	private JTextField answer;
	private JButton submit;
	private JLabel longestWords;
	private JButton requestLongestWords;
	private JButton getSummary;
	
	//components of closing window
	private JLabel fileName;
	private JTextArea sessionSummary;
//	private JButton confirmClose;
	
	//helper fields
	private ArrayList<String> longestWordsAdded;// use it for add more items which i longgegst word
	private FileWriter summaryFile = null;// create the file to recognixz efrom my computer 
	
	//constructor
	LongestWord() {
		container = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));// jpanel thats is called container
		container.setSize(new Dimension(500, 400));
		
		question = new JLabel("Tell me what is on your mind today in 1 sentence.");
		question.setSize(new Dimension(450, 40));
		container.add(question);// adding the label to the container
		
		answer = new JTextField(40);
		answer.setSize(new Dimension(450, 40));
		container.add(answer);
		
		submit = new JButton("Submit Answer");
		submit.setMinimumSize(new Dimension(150, 40));
		submit.addActionListener(this);
		container.add(submit);
		
		requestLongestWords = new JButton("Request Longest Words");// jbutton which calles request longest word
		requestLongestWords.setSize(new Dimension(150, 40));
		requestLongestWords.addActionListener(this);
		container.add(requestLongestWords);
		
		getSummary = new JButton("Get Summary");
		getSummary.setSize(new Dimension(150, 40));
		getSummary.addActionListener(this);
		container.add(getSummary);
		
		longestWords = new JLabel("Longest Words Entered So Far: \n");
		longestWords.setSize(new Dimension(450, 80));
		container.add(longestWords);
		
		
		try {//exception handler fo creation of the file EXTRA CREDIT
			summaryFile = new FileWriter(new File(System.getProperty("user.home"), "SessionSummary.txt"));
			summaryFile.flush();
			summaryFile.write("Session Summary: \n");
			summaryFile.flush();
		} catch (IOException e) {}
		longestWordsAdded = new ArrayList<String>();
		
		add(container);
		container.setBackground(Color.BLUE);
		add(container);
		
		setSize(new Dimension(500, 400));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Longest Word");
		setVisible(true);
		
	}
	
	
	//extra credit
	public static void appendToFile(String fileName, String text) {// cpoy stuff  from gui to file
		PrintWriter outStream = null;
		try{
			outStream = new PrintWriter(new FileOutputStream(fileName, true));//true tells computer we want to keep previous content
			outStream.println(text);//append to the file
		}catch(FileNotFoundException e){
			System.err.println("Could not append to the file "+fileName+ " MESSAGE: "+e.getMessage());
		}
		finally{//do clean up
			if(outStream != null){
				outStream.close();
			}
		}
		
	}
		public String readFile(String fileName) {// copy stuff from file to gui
			Scanner inStream = null;
			String fileContents = "";
			try {
				
				inStream = new Scanner( new File ( fileName ));
				while(inStream.hasNextLine()){//get all lines and append to string
					fileContents += inStream.nextLine() +"\n";//read 1 line from file and append it to fileContents
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finally{
				if(inStream != null){
					inStream.close();
				}
			}
			
			return fileContents;
		}
	
	
	
	////
	
	public static void main(String[] args) {
		LongestWord instance = new LongestWord();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {// has to do more with jButtons
		if(arg0.getSource().equals(submit)) {
			String answerEntered = answer.getText();
			try {
				summaryFile.write(answerEntered + "\n");
				appendToFile("SessionSummary.txt",answerEntered);
				
			} catch (IOException e) {}
			
			String words[] = answerEntered.split(" "); //entered sentencced transform into array
			int longestWordIndex = 0;
			for(int i=1; i<words.length; i++){
				if(words[i].length() > words[longestWordIndex].length())
					longestWordIndex = i;
			}
			
			longestWordsAdded.add(words[longestWordIndex]);
			
			answer.setText("");
			int commentIndex = 4;
			if(words[longestWordIndex].length() == 3)
				commentIndex = 0;
			else if(words[longestWordIndex].length() == 4)
				commentIndex = 1;
			else if(words[longestWordIndex].length() == 5)
				commentIndex = 2;
			else if(words[longestWordIndex].length() > 5)
				commentIndex = 3;
			
			question.setText(String.format(COMMENTS[commentIndex], words[longestWordIndex]));
			//appendToFile("SessionSummary.txt",);

			
		}
		else if(arg0.getSource().equals(requestLongestWords)) {// select the longest word
			StringBuffer listOfLongestWords = new StringBuffer();
			for(String word: longestWordsAdded)
				listOfLongestWords.append(word + ", ");
			
			int last = listOfLongestWords.length();
			listOfLongestWords.deleteCharAt(last - 1);
			listOfLongestWords.deleteCharAt(last - 2);
			
			longestWords.setText("Longest Words Entered So Far: \n" + listOfLongestWords.toString());
		}
		else if(arg0.getSource().equals(getSummary)) {
			remove(container);
			container = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
			container.setSize(new Dimension(500, 400));
			
			fileName = new JLabel("Session Summary File: " + System.getProperty("user.home") + "SessionSummary.txt");
			fileName.setSize(new Dimension(450, 40));
			container.add(fileName);
			
			sessionSummary = new JTextArea(5, 40);
			sessionSummary.setSize(new Dimension(450, 40));
			/////
			
			sessionSummary.setText(readFile("SessionSummary.txt"));
			/////
			container.add(sessionSummary);
			try {
				BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\SessionSummary.txt"));
				String line=null;
				//appendToFile("SessionSummary.txt","helo123");
				//while((line = br.readLine()) != null)
					//sessionSummary.append(line + "\n");
			} catch (Exception e) {}
			
			longestWords = new JLabel();
			longestWords.setSize(new Dimension(450, 80));
			container.add(longestWords);
			StringBuffer listOfLongestWords = new StringBuffer();
			for(String word: longestWordsAdded)
				listOfLongestWords.append(word + ", ");
			int last = listOfLongestWords.length();
			if(last >= 2){
			listOfLongestWords.deleteCharAt(last - 1);
			listOfLongestWords.deleteCharAt(last - 2);
			}
			longestWords.setText("Longest Words Entered So Far: \n" + listOfLongestWords.toString());
			
	
			add(container);
			
			setSize(new Dimension(500, 400));
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setTitle("Longest Word");
			setVisible(true);
	
		}
//		else if(arg0.getSource().equals(confirmClose)) {
//			dispose();
//		}
	}
	
	
}
