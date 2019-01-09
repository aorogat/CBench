package compiling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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


import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.XMLParser;

public class QACompiler {
	public static ArrayList<ArrayList<Question>> totalQuestions = new ArrayList<ArrayList<Question>>();
	public static void main(String[] args) {
        
        totalQuestions = parseQuestions();
        
		//Check for redundancy
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
								System.out.print(currQuestion + " is a duplicate. Act accordingly. \n");
								curr2.remove(j2);
							}
						}
					}
				}
			}
		}
		
		System.out.println("Check for valid answers");
		validateAnswers();
		System.out.print("Done. \n");
		
		//Write
		try (FileWriter file = new FileWriter("./finalQuestionAnswerList.json")) {
            for(int i = 0; i < totalQuestions.size(); i++) {
            	ArrayList<Question> curr= totalQuestions.get(i);
            	for(int j = 0; j < curr.size(); j++) {
            		file.write("-----------------------------------------\n");
            		file.write(curr.get(j).getQuestionString());
            		file.write("\n");
            		for(int k = 0; k < curr.get(j).getAnswers().size(); k++) {
            			file.write("	");
            			file.write(curr.get(j).getAnswers().get(k));
            			file.write("\n");
            		}
            		/*
            		 QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

            	        ResultSet results = exec.execSelect();*/
            		file.write("\n");
            		file.write("\n");
            	}
            }
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	return;
	}
	
	static ArrayList<ArrayList<Question>> parseQuestions()
	{
		ArrayList<ArrayList<Question>> results = new ArrayList<ArrayList<Question>>();
		//1
		//2
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia"));
		//3
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "esdbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "esdbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz"));
		
		//4 
		//No answers but has queries
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4"));
		
	
		//Not working, fix later
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "mannheimu"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "mannheimu"));
		
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia"));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia"));

		
		//5
	
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
		
		//NOT NEEDED?
		//results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5"));
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.json
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.xml
		
		//6
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
		
		//7
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File1("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", false, "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		
		//8
		
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
		
		//9
		 
		 
		results.add(JSONParser.parseQald9File("./data/original/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));

		return results;
	}
	static String getDatabaseDomain(String database) {
		System.out.println(database);
		if(database.compareTo("musicbrainz") == 0)
			return "http://dbtune.org/musicbrainz/snorql/";	
		else if(database.compareTo("dbpedia") == 0)
			return "http://dbpedia.org/sparql/";
		
		else if(database.compareTo("wikidata") == 0)
			return "https://query.wikidata.org/sparql?force=true";
		else
			return "";
	}

	static void validateAnswers() 
	{
		for(int i = 0; i < totalQuestions.size();i++) {
			for(int j = 0; j <totalQuestions.get(i).size(); j++) {
				Question curr = totalQuestions.get(i).get(j);
				System.out.println("Testing answers for question ("+j+"/" + totalQuestions.get(i).size() +"): "+ curr.getQuestionString());
				System.out.println("Query: "+ curr.getQuestionQuery());
				System.out.println("Source: "+ curr.getQuestionSource());
				
				ArrayList<String> temp = getUpdatedAnswers(curr);
				System.out.println(j + "is done");
			}
		}
		System.out.println("ALL DONE");
	}
	static ArrayList<String> getUpdatedAnswers(Question question){
		ArrayList<String> returnAnswers = new ArrayList<String>();
		ArrayList<String> answers =  question.getAnswers();
		String query = question.getQuestionQuery();
		System.out.println("Endpoint used: " + getDatabaseDomain(question.getDatabase()));
		try {
			ParameterizedSparqlString qs = new ParameterizedSparqlString(query);
			
	        QueryExecution exec = QueryExecutionFactory.sparqlService(getDatabaseDomain(question.getDatabase()), qs.asQuery());
	        System.out.println(exec);
	        ResultSet results = exec.execSelect();
	        
	        List<QuerySolution> newAnswers = ResultSetFormatter.toList((org.apache.jena.query.ResultSet) results);
	        
	        //Check if Answers are still valid
	        
	        //Case 1: If the size of the stored and fetched answer arrays are different, update with the fetech answer array
	        if(newAnswers.size() == answers.size()) {
	        	for(int i = 0; i < answers.size(); i++) {
	        		System.out.println("	Stored: " + answers.get(i));
	        		System.out.println("	Fetched: " + newAnswers.get(i).get("uri").toString());
	    			if(answers.get(i).compareTo(newAnswers.get(i).get("uri").toString()) != 0) {
	    				System.out.println("	Answer does not match. Updating.");
	    				returnAnswers.add(newAnswers.get(i).get("uri").toString());
	    			}
	    			else {
	    				System.out.println("	Answer matches. Updating.");
	    				returnAnswers.add(answers.get(i));
	    			}
	    		}		
	        }
	        else 
	        {
	        	System.out.println("	Number of answers do not match. Fetching new ones");
	        	for(int i = 0; i < newAnswers.size(); i++) {
	        		returnAnswers.add(newAnswers.get(i).get("uri").toString());
	        		System.out.println("	Adding: " + newAnswers.get(i).get("uri").toString());
	        	}
	        }
	        System.out.println("Success");
		}
        catch(Exception e) {
        	System.out.println("	Exception: " + e);
        	return answers;
        }
      
        return returnAnswers;
	}
}

