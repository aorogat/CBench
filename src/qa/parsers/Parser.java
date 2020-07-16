package qa.parsers;

import fileManagement.FileManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import qa.dataStructures.Question;
import static qa.parsers.XMLParser.getCharacterDataFromElement;

public class Parser {

    public static ArrayList<Question> parseQald1(String fileDirectory, String sourceString, String endpoint, boolean multilingual) {
		ArrayList<Question> questionsList = new ArrayList<Question>();
		try {
	         File inputFile = new File(fileDirectory);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("question");
	         
	         for (int i = 0; i < nList.getLength(); i++) {
	        	// Skip hybrid questions
	        	Element element = (Element) nList.item(i);
	            if(element.getAttribute("hybrid").compareTo("true") == 0) {
	            	continue;
	            }
	        	Question question = new Question();
	        	question.setQuestionSource(sourceString);
	        	question.setDatabase(endpoint);
	        	question.setFilepath(fileDirectory);
	            NodeList langList = element.getElementsByTagName("string");

		        question.setQuestionString(getCharacterDataFromElement((Element)langList.item(0)).replace("\n", "")
                        .replaceAll("\\!\\[CDATA\\[", "").replaceAll("]]", ""));
                     try{   
                        question.setKeywords(getCharacterDataFromElement((Element)element.
	            		getElementsByTagName("keywords").item(0))
                            .replaceAll("\\!\\[CDATA\\[", "").replaceAll("]]", ""));
                     
                    }catch(Exception e){}
	            question.setQuestionQuery(getCharacterDataFromElement((Element)element.
	            		getElementsByTagName("query").item(0))
                            .replaceAll("\\!\\[CDATA\\[", "").replaceAll("]]", ""));
	            if(question.getQuestionQuery().replaceAll("\n", "").replaceAll(" ", "").compareTo("OUTOFSCOPE") == 0) {
	            	continue;
	            }
	            
	            Element answersNode = (Element) element.getElementsByTagName("answers").item(0);
	            try {
	            	NodeList answersList = answersNode.getElementsByTagName("answer");
	            	
	            	for(int j = 1; j < langList.getLength(); ++j) {
	            		question.addAnswer(getCharacterDataFromElement((Element)langList.item(j)).replace("\n", "")
                                .replaceAll("\\!\\[CDATA\\[", "").replaceAll("]]", ""));
		            }
	            }
	            catch(Exception e)
	            {
	            	//System.out.println(e);
	            }
	            
	            if(question.getQuestionQuery() != null)
	            questionsList.add(question);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return questionsList;
	}
    
      
    public static ArrayList<Question> parseJSON(String fileDirectory,
            String sourceString,
            String endpoint,
            boolean multilingual,
            String questionString_attributeName,
            String questionSparqlQuery_attributeName,
            String answers_attributeName) throws JSONException {
        ArrayList<Question> questionsList = new ArrayList<>();
        String fileContents = FileManager.readWholeFile(fileDirectory);
        JSONObject obj = new JSONObject(fileContents);
        JSONArray arr = obj.getJSONArray("questions");

        for (int i = 0; i < arr.length(); i++) {
            Question question = new Question();
            JSONObject currentQuestionObject = arr.getJSONObject(i);

            question.setDatabase(endpoint);
            question.setFilepath(fileDirectory);
            question.setQuestionSource(sourceString);
            question.setQuestionString(currentQuestionObject.getString(questionString_attributeName).replace("\n", ""));
            question.setQuestionQuery(currentQuestionObject.getString(questionSparqlQuery_attributeName));

            JSONArray answersArray = currentQuestionObject.getJSONArray(answers_attributeName);
            for (int j = 0; j < answersArray.length(); ++j) {
                JSONArray resultsArray = answersArray.getJSONObject(j).getJSONObject("results").getJSONArray("bindings");
                for (int k = 0; k < resultsArray.length(); ++k) {
                    Set<String> keysSet = resultsArray.getJSONObject(k).keySet();
                    Iterator<String> it = keysSet.iterator();
                    question.addAnswer(resultsArray.getJSONObject(k).getJSONObject(it.next()).getString("value"));
                }

            }

            questionsList.add(question);

        }
        return questionsList;
    }
}
