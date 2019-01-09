package qa.dataStructures;

import java.util.ArrayList;


/**
 * The data structure for the question
 * @author ahmed
 *
 */
public class Question {

	private String questionString;
	private String questionQuery;
	private String questionSource;
	private ArrayList<String> answers;
	private String database;
	
	public Question() {
		answers = new ArrayList<String>();
	}
	public Question(String _questionString, String _questionQuery, String _questionSource) {
		questionString = _questionString;
		questionQuery = _questionQuery;
		questionSource = _questionSource;
		answers = new ArrayList<String>();
	}
	
	public void addAnswer (String answer) {
		answers.add(answer);
	}
	public void setDatabase (String newdatabase) {
		database = newdatabase;
	}
	public String getDatabase() {
		return database;
	}
	public String getQuestionString() {
		return questionString;
	}
	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}
	public String getQuestionQuery() {
		return questionQuery;
	}
	public void setQuestionQuery(String questionQuery) {
		this.questionQuery = questionQuery;
	}
	public String getQuestionSource() {
		return questionSource;
	}
	public void setQuestionSource(String questionSource) {
		this.questionSource = questionSource;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	
	
	
}
