package compiling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONArray;
import org.json.JSONObject;

import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.XMLParser;

public class QACompiler {
	public static ArrayList<ArrayList<Question>> totalQuestions = new ArrayList<ArrayList<Question>>();
	public static ArrayList<String> changeLogMessages = new ArrayList<String>();
	public static ArrayList<String> errorLogMessages = new ArrayList<String>();
	public static int counter = 0;
	
	public static void main(String[] args) {
        
        totalQuestions = parseQuestions();
        checkForRedundancy();
		System.out.println("Check for valid answers");
		validateAnswers();
		System.out.print("Done. \n");
		writeToFile();
		
		
        
	return;
	}
	
	//Searchs and replaces redundant questions
	static void checkForRedundancy() {
		System.out.print("Checking for redundancy. \n");
		for(int i = 0; i < totalQuestions.size(); i++) {
			ArrayList<Question> curr= totalQuestions.get(i);
			for(int j = 0; j < curr.size(); j++) {
				String currQuestion = curr.get(j).getQuestionString();

				//Check for redundancy
				for(int i2 = 0; i2 < totalQuestions.size(); i2++) {
					ArrayList<Question> curr2 = totalQuestions.get(i2);
					for(int j2 = 0; j2 < curr2.size(); j2++) {
						String currQuestionCompare = curr2.get(j2).getQuestionString();
						if(i != i2 || j != j2)
						{
							if(currQuestion.compareTo(currQuestionCompare) == 0)
							{
								changeLogMessages.add(curr.get(j).getFilepath() +"\n" + "    '" + currQuestion + "'" + " is a duplicate, found in paths: \n "
										+ "        " + curr.get(j).getFilepath() + " and \n" 
										+ "        " + curr2.get(j2).getFilepath() + "\n" + ""
										+ "Instance found in newer file " + curr.get(j).getFilepath() + " will be kept.\n\n");

								curr2.remove(j2);
							}
						}
					}
				}
			}
		}
	}
	
	//Create json file of questions/answers and txt file of changes.
	static void writeToFile() {
		//Write
				try (FileWriter file = new FileWriter("./finalQuestionAnswerList.json")) {
					file.write(QuestionArrayListToJSONArray(totalQuestions).toString(4));
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./QuestionAnswerChangelog.txt")) {
					for(int i = 0; i < changeLogMessages.size(); i++){
						file.write(changeLogMessages.get(i));
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	}
	
	//Functiosn used to create JSONArray of questions that will be printed to file.
	static JSONObject QuestionToJSON(Question q) {
		JSONObject output = new JSONObject();
		output.put("question", q.getQuestionString());
		output.put("source", q.getQuestionSource());
		output.put("answers", q.getAnswers());
		return output;
	}
	
	static JSONArray QuestionArrayListToJSONArray(ArrayList<ArrayList<Question>> arr) {
		JSONArray output = new JSONArray();
		for(int i = 0; i < arr.size(); i++) {
			JSONArray subArr = new JSONArray();
			for(int j = 0; j< arr.get(i).size(); j++) {
				subArr.put(QuestionToJSON(arr.get(i).get(j)));
			}
			output.put(subArr);
		}
		return output;
		
	}
	
	//Goes through and parses all the questions.
	static ArrayList<ArrayList<Question>> parseQuestions()
	{
		ArrayList<ArrayList<Question>> results = new ArrayList<ArrayList<Question>>();

		//1
				results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
				results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
				results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-train-CDATA.xml", "QALD-1", "dbpedia", false));
				// Freezing results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "musicbrainz", false));
				results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "musicbrainz", false));
				results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train-CDATA.xml", "QALD-1", "musicbrainz", false));
				
		//2
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
		
		//Freez
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz", false));
		
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
		//3
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		
		
		//Freeze
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz", true));
		
		//4 

		//Down for maintenance
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "mannheimu"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "mannheimu"));
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));

		
		//5
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
		
		//NOT NEEDED?
		//results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5"));
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.json
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.xml
		
		//6
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
		//Causes problems due to format...repalce with datacube raw results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-test-datacube.json", "QALD-6", "linkedspending"));
		results.add(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
		//results.add(JSONParser.parseQald7File4("./data/original/QALD-master/6/data/qald-6-train-datacube-raw.json", "QALD-6", "linkedspending"));
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
		results.add(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
		//results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual-raw.json", "QALD-6", "dbpedia"));

		//7
		//Freezes at 6
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File1("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", false, "dbpedia"));
		
		//Freeze
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
		
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		
		//8
		
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
		
		//not
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
		
		//9 
		results.add(JSONParser.parseQald9File("./data/original/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));

		return results;
	}

	//Goes through and parses all the questions.
	static ArrayList<ArrayList<Question>> parseQuestionsBroken()
	{
		ArrayList<ArrayList<Question>> results = new ArrayList<ArrayList<Question>>();

		
		//2		
		//Freeze
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz", false));
		
		//3
		
		//Freeze
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz", true));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz", true));
		
		//4 

		//Down for maintenance
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "mannheimu"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "mannheimu"));
		
		//5
		//NOT NEEDED?
		//results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5"));
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.json
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.xml
		
		//6
		//7
		//Freezes at 6
		//results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
		
		//Freeze
		//results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
		
		//8
		
		//not
		//results.add(JSONParser.parseQald7File2("./data/original/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
		
		//9 
		
		return results;
	}
	
	//Returns endpoint url when given a string. Not fully complete.
	static String getDatabaseDomain(String database) {
		if(database.compareTo("musicbrainz") == 0)
			return "http://dbtune.org/musicbrainz/snorql/?force=true";	
		else if(database.compareTo("dbpedia") == 0)
			return "http://dbpedia.org/sparql/";
		else if(database.compareTo("wikidata") == 0)
			return "https://query.wikidata.org/sparql/";
		else if(database.compareTo("linkedspending") == 0)
			return "http://linkedspending.aksw.org/sparql/";
		else
			return "";
	}
	
	//Goes through each of the questions in each file and validates that they are up to date.
	static void validateAnswers() 
	{
		for(int i = 0; i < totalQuestions.size(); i++) {
			//System.out.println("File " + i + " of " + totalQuestions.size());
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {
				//System.out.println("    Question " + j + " of " + totalQuestions.get(i).size() + ". " + totalQuestions.get(i).get(j).getQuestionString());
				Question curr = totalQuestions.get(i).get(j);
				ArrayList<String> temp = getUpdatedAnswers(curr);
				//for(int k = 0; k < temp.size(); k++) {
				//	System.out.println("    	"+ temp.get(k));
				//}
				
				curr.setAnswers(temp);
			}
		}
	}
	
	static String getResultFromIterator(Iterator<String> input) {
		for(int i = 0; i <1; i++) {	
			if(input.hasNext())
				input.next();
		}
		return input.next();
	}
	

	static ArrayList<String> getUpdatedAnswers(Question question){
		ArrayList<String> returnAnswers = new ArrayList<String>();
		ArrayList<String> answers =  question.getAnswers();
		String query = question.getQuestionQuery();
		ParameterizedSparqlString qs = new ParameterizedSparqlString(query);
		//System.out.println(query);
        List<QuerySolution> newAnswers = null;
        
        try {
        	//System.out.println("1");
        	QueryExecution exec = QueryExecutionFactory.sparqlService(getDatabaseDomain(question.getDatabase()), qs.asQuery());

        	//System.out.println("2");
        	final ResultSet[] resultsTemp = new ResultSet[1];
        	//System.out.println("3");
        	resultsTemp[0] = null;
        	//System.out.println("4");
        	ResultSet results = null;
        	Object LOCK = new Object();
        	System.out.println("\n");
            Thread test = new Thread("test") {
            		public void run() {
            			synchronized(LOCK){
            				//System.out.println("	a" + exec.getContext());
            				System.out.println("other thread: " + Thread.currentThread().getName());
	            			resultsTemp[0] = exec.execSelect();
	            			//System.out.println("	b");
	            			LOCK.notifyAll();
            			}
            		}
            };
            
            	//System.out.println("5");
	            
	            synchronized(LOCK) {
	            	test.start();
	           // System.out.println("6");
	            	//test.run();
	            System.out.println("current thread: " + Thread.currentThread().getName());
	            	//test.wait(5000);
	            }
				//System.out.println("7");
				if(resultsTemp[0] == null) {
					test.getState();
					throw(new Exception("SPARQL query timed out."));
				}
				else {
					results = resultsTemp[0];
				}	
            

	        newAnswers = ResultSetFormatter.toList(results);
		}
		catch(Exception e) {
			//System.out.println(e.getMessage());
        	if(e.getMessage().compareTo("Not a ResultSet result")==0){
     
        		QueryExecution exec = QueryExecutionFactory.sparqlService(getDatabaseDomain(question.getDatabase()), qs.asQuery());
    	        if(exec.execAsk())
    	        	returnAnswers.add("true");
    	        else 
    	        	returnAnswers.add("false");
    	        
    	        return returnAnswers;
        	}
        	else if(e.getMessage().compareTo("SPARQL query timed out.")==0){
        		changeLogMessages.add(question.getFilepath()+ "\n" + "    '" + question.getQuestionString() + "' Query times out when run. Stored answer is kept. \n"
    					+ "\n\n"
    					+ question.getQuestionQuery() + "\n");
        		returnAnswers = answers;
        		return returnAnswers;
        	}
        	
        	else
        	{
        		changeLogMessages.add(question.getFilepath()+ "\n" + "    '" + question.getQuestionString() + "\n"
    					+ e);
        		returnAnswers = answers;
        		return returnAnswers;
        	}
        }
		
		 
	        //Check if Answers are still valid
	        //Case 1: If the size of the stored and fetched answer arrays are different, update with the fetech answer array
	        if(newAnswers.size() == answers.size()) {
	        	for(int i = 0; i < answers.size(); i++) {
	        		String varName = newAnswers.get(i).varNames().next();
        			String fetched = newAnswers.get(i).get(varName).toString().split("\\^")[0];

	        		if(answers.get(i).compareTo(fetched) != 0) {
	        			changeLogMessages.add(question.getFilepath()+ "\n" + "    '" + question.getQuestionString() + "' has an outdated answer. \n"
	        					+ "        Stored: " + answers.get(i) +"\n"
	        					+ "        Fetched: " + fetched +"\n"
	        					+ "    Updating.\n\n");
	    				returnAnswers.add(fetched);
	    			}
	    			else {
	    				//System.out.println("	Answer matches. Updating.");
	    				returnAnswers.add(answers.get(i));
	    			}
	    		}		
	        }
	        else 
	        {
	        	if(newAnswers.size()>0)
	        	{
		        	changeLogMessages.add(question.getFilepath() + "\n" +"    '" + question.getQuestionString() + "'" + " Number of stored answers do not match number of answers found online. \n"
		        			+ "        Stored: "+ answers.size() + "\n"
		        			+ "        Fetched: "+ newAnswers.size() +"\n "
		        			+ "    Updating with fetched answers. \n\n");
		        	for(int i = 0; i < newAnswers.size(); i++) {
		        		String varName = newAnswers.get(i).varNames().next();
		        		String fetched = newAnswers.get(i).get(varName).toString().split("\\^")[0];
		        		returnAnswers.add(fetched);
		        	}
	        	}
	        	else {
	        		changeLogMessages.add(question.getFilepath() + "\n" +"    '" + question.getQuestionString() + "'" + " Query returns no answers. Keeping stored answers. \n");
	        		returnAnswers = answers;
	        	}
	        }
        return returnAnswers;
	}
}

