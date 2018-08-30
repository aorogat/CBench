package qa.dataStructures;

import java.util.ArrayList;

public class Question {

	private String questionString;
	private String questionQuery;
	private String questionSource;
	private ArrayList<String> answers;
	
	public Question() {
		answers = new ArrayList<String>();
	}
	
	public void addAnswer (String answer) {
		answers.add(answer);
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
