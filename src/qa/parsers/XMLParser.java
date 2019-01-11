package qa.parsers;

import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.CharacterData;
import qa.dataStructures.Question;

public class XMLParser {

	public static ArrayList<Question> parseQald4(String fileDirectory, String sourceString, String endpoint) {
		ArrayList<Question> questionsList = new ArrayList<Question>();
		try {
	         File inputFile = new File(fileDirectory);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         //System.out.println("Reading file " + fileDirectory + "...");
	         Document doc = dBuilder.parse(inputFile);
	        // System.out.println("Parsing file...");
	         doc.getDocumentElement().normalize();
	         //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
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
	            for(int j = 0; j < langList.getLength(); ++j) {
	            	Element langElement = (Element) langList.item(j);
	            	if(langElement.getAttribute("lang").compareTo("en") == 0) {
	            		question.setQuestionString(getCharacterDataFromElement(langElement));
	            		break;
	            	}
	            }
	            question.setQuestionQuery(getCharacterDataFromElement((Element)element.
	            		getElementsByTagName("query").item(0)));
	            Element answersNode = (Element) element.getElementsByTagName("answers").item(0);
	            try {
	            	NodeList answersList = answersNode.getElementsByTagName("answer");
	            	
	            	for(int j = 0; j < answersList.getLength(); ++j) {
		            	
		            	question.addAnswer(answersList.item(j).getTextContent().replace("\n", ""));
		            }
	            }
	            catch(Exception e)
	            {
	            }
	            
	            
	            questionsList.add(question);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return questionsList;
	}
	
	public static ArrayList<Question> parseQald5(String fileDirectory, String sourceString, String endpoint) {
		ArrayList<Question> questionsList = new ArrayList<Question>();
		try {
	         File inputFile = new File(fileDirectory);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        // System.out.println("Reading file " + fileDirectory + "...");
	         Document doc = dBuilder.parse(inputFile);
	       //  System.out.println("Parsing file...");
	         doc.getDocumentElement().normalize();
//	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
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
	            for(int j = 0; j < langList.getLength(); ++j) {
	            	Element langElement = (Element) langList.item(j);
	            	if(langElement.getAttribute("lang").compareTo("en") == 0) {
	            		question.setQuestionString(getCharacterDataFromElement(langElement));
	            		break;
	            	}
	            }
	            question.setQuestionQuery(getCharacterDataFromElement((Element)element.
	            		getElementsByTagName("query").item(0)));
	            Element answersNode = (Element) element.getElementsByTagName("answers").item(0);
	            NodeList answersList = answersNode.getElementsByTagName("answer");
	            for(int j = 0; j < answersList.getLength(); ++j) {
	            	question.addAnswer(getCharacterDataFromElement((Element)answersList.item(j)));
	            }
	            questionsList.add(question);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return questionsList;
	}
	
	/**
	 * This function extracts the string from ![CDATA[]]
	 * @param e The XML element
	 * @return The string of CDATA
	 */
	public static String getCharacterDataFromElement(Element e) {
		  Node child = e.getFirstChild();
		  if (child instanceof CharacterData) {
		    CharacterData cd = (CharacterData) child;
		    return cd.getData();
		  }
		  return "";
	}

}
