package compiling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONArray;
import org.json.JSONObject;

import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.XMLParser;

public class QACompiler {
	public static ArrayList<ArrayList<Question>> totalQuestions = new ArrayList<ArrayList<Question>>();
	public static ArrayList<String> changeLogMessagesTimeout = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesException = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesOutdated= new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesDuplicate = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesNoAnswers = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesOther = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesRemoved = new ArrayList<String>();
	public static ArrayList<String> errorLogMessages = new ArrayList<String>();
	
	public static HashMap<String, Integer> TypesofExceptionsCounters = new HashMap<String, Integer> ();
	
	public static ArrayList<String> TypesofExceptions = new ArrayList<String>();
	public static ArrayList<String> UnresolvedPrefixedNameExceptions = new ArrayList<String>();
	public static ArrayList<String> LexicalErrorExceptions = new ArrayList<String>();
	public static ArrayList<String> UnexpectedEncounterExceptions = new ArrayList<String>();
	public static ArrayList<String> EncounteredPname  = new ArrayList<String>();
	public static ArrayList<String> OtherExceptions = new ArrayList<String>();
	public static String[] keywords = {"select", "ask", "describe", "construct", "distinct", "limit", "offset", "order by", "filter", "and","union", "opt", "graph", "not exists", "minus", "exists", "count", "max","min", "avg", "sum", "group by", "having"};
	public static Hashtable<String, Integer> keywordCount = new Hashtable<String, Integer>();
	public static int counter = 0;
	public static int outdatedcounter = 0;
	public static int subqueriescounter = 0;
	
	//Shallow Analysis
	public static Hashtable<Integer, Integer> tripleCounter = new Hashtable<Integer, Integer>();
	public static Hashtable<String, Integer> operatorDistribution = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> subqueryDistribution = new Hashtable<String, Integer>();
	
	public static Hashtable<Integer, Integer> tripleCounterFull = new Hashtable<Integer, Integer>();
	public static Hashtable<String, Integer> operatorDistributionFull = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> subqueryDistributionFull = new Hashtable<String, Integer>();
	
	
	public static void main(String[] args) {
		initHashmaps();
        totalQuestions = parseQuestions();
        checkForRedundancy();
		System.out.println("Check for valid answers");
		validateAnswers();
		System.out.print("Done. \n");
		writeToFile();
	return;
	}
	public static void initHashmaps() {
		operatorDistribution.put("None", 0);
		operatorDistribution.put("And", 0);
		operatorDistribution.put("Filter", 0);
		operatorDistribution.put("And, Filter", 0);
		operatorDistribution.put("Opt", 0);
		operatorDistribution.put("Opt, Filter", 0);
		operatorDistribution.put("And, Opt", 0);
		operatorDistribution.put("And, Opt, Filter", 0);
		operatorDistribution.put("Graph", 0);
		operatorDistribution.put("Union", 0);
		operatorDistribution.put("Union, Filter", 0);
		operatorDistribution.put("And, Union", 0);
		operatorDistribution.put("And, Union, Filter", 0);
		operatorDistribution.put("And, Opt, Union, Filter", 0);
		
		operatorDistributionFull.put("None", 0);
		operatorDistributionFull.put("And", 0);
		operatorDistributionFull.put("Filter", 0);
		operatorDistributionFull.put("And, Filter", 0);
		operatorDistributionFull.put("Opt", 0);
		operatorDistributionFull.put("Opt, Filter", 0);
		operatorDistributionFull.put("And, Opt", 0);
		operatorDistributionFull.put("And, Opt, Filter", 0);
		operatorDistributionFull.put("Graph", 0);
		operatorDistributionFull.put("Union", 0);
		operatorDistributionFull.put("Union, Filter", 0);
		operatorDistributionFull.put("And, Union", 0);
		operatorDistributionFull.put("And, Union, Filter", 0);
		operatorDistributionFull.put("And, Opt, Union, Filter", 0);
		
		for(int i = 0; i < keywords.length; i++) {
			subqueryDistribution.put(keywords[i].replace(" ", ""), 0);
		}
		
		for(int i = 0; i < keywords.length; i++) {
			subqueryDistributionFull.put(keywords[i].replace(" ", ""), 0);
		}


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
								changeLogMessagesDuplicate.add(curr.get(j).getFilepath() +"\t" + "'" + currQuestion + "'" + " is a duplicate; found in paths: \t "
										+ "        " + curr.get(j).getFilepath() + " and \t" 
										+ "        " + curr2.get(j2).getFilepath() + "\t" + ""
										+ "Instance found in newer file " + curr.get(j).getFilepath() + " will be kept.\t\t");

								curr2.remove(j2);
							}
						}
					}
				}
			}
		}
	}
	static int getNumberOfTuples(String query) {
		int ind = query.indexOf("{");
		int result = 0;
		//System.out.println(query.substring(ind)  + "\n");
		String[] temp = query.substring(ind).split("         ");
		
		for(int i = 0; i< temp.length; i++) {
			try {
				//System.out.println(temp[i]);
				if(temp[i].contains("{") && i==0) {
					result ++;
				}
				else if(temp[i].substring(0, 1).contains("?")) {
					result ++;
				}
			}
			catch(Exception e) {}
		}
		return result;
	}
	//Create json file of questions/answers and txt file of changes.
	static void writeToFile() {
		//Write
			    for(int i =0; i< TypesofExceptions.size(); i++) {
			    	//System.out.println(TypesofExceptions.get(i) + ": " + TypesofExceptionsCounters.get(TypesofExceptions.get(i)));
			    }
			    //System.out.println("\n");
				//System.out.println(TypesofExceptions.size());
				try (FileWriter file = new FileWriter("./finalQuestionAnswerList.json")) {
					file.write(QuestionArrayListToJSONArray(totalQuestions).toString(4));
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./KeywordList.txt")) {
					file.write("Number of keywords: " + keywordCount.size()  + "\n");
					int total = 0;
					for(int i = 0; i < keywords.length; i++){
						if(keywordCount.get(keywords[i]) != null) {
							total = total + keywordCount.get(keywords[i]);
						}
					}
					
					file.write("Number of hits: " + total  + "\n\n");
					
					for(int i = 0; i < keywords.length; i++) {
						if(keywordCount.get(keywords[i]) != null) {
							int val = keywordCount.get(keywords[i]);
							
							file.write(keywords[i] + ": " + keywordCount.get(keywords[i]) + "\n");
						}
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./UnresolvedPrefixedNameExceptionsList.txt")) {
					file.write("Total: "+ UnresolvedPrefixedNameExceptions.size()+  "\n\n");
					for(int i = 0; i < UnresolvedPrefixedNameExceptions.size(); i++){
						file.write(UnresolvedPrefixedNameExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./changeLogMessagesRemoved.txt")) {
					file.write("Total: "+ changeLogMessagesRemoved.size()+  "\n\n");
					for(int i = 0; i < changeLogMessagesRemoved.size(); i++){
						file.write(changeLogMessagesRemoved.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				try (FileWriter file = new FileWriter("./OtherExceptionsList.txt")) {
					file.write("Total: "+ OtherExceptions.size()+  "\n\n");
					for(int i = 0; i < OtherExceptions.size(); i++){
						file.write(OtherExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				try (FileWriter file = new FileWriter("./LexicalErrorExceptionsList.txt")) {
					file.write("Total: "+ LexicalErrorExceptions.size()+  "\n\n");
					for(int i = 0; i < LexicalErrorExceptions.size(); i++){
						file.write(LexicalErrorExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./UnexpectedEncounterExceptionsList.txt")) {
					file.write("Total: "+ UnexpectedEncounterExceptions.size()+  "\n\n");
					for(int i = 0; i < UnexpectedEncounterExceptions.size(); i++){
						file.write(UnexpectedEncounterExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				try (FileWriter file = new FileWriter("./log/changeLogMessagesNoAnswers.txt")) {
					for(int i = 0; i < changeLogMessagesNoAnswers.size(); i++){
						file.write(changeLogMessagesNoAnswers.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/changeLogMessagesTimeout.txt")) {
					for(int i = 0; i < changeLogMessagesTimeout.size(); i++){
						file.write(changeLogMessagesTimeout.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/changeLogMessagesException.txt")) {
					for(int i = 0; i < changeLogMessagesException.size(); i++){
						file.write(changeLogMessagesException.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				//changeLogMessagesOutdated
				try (FileWriter file = new FileWriter("./log/changeLogMessagesOutdated.txt")) {
					for(int i = 0; i < changeLogMessagesOutdated.size(); i++){
						file.write(changeLogMessagesOutdated.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				//changeLogMessagesDuplicate
				try (FileWriter file = new FileWriter("./log/changeLogMessagesDuplicate.txt")) {
					for(int i = 0; i < changeLogMessagesDuplicate.size(); i++){
						file.write(changeLogMessagesDuplicate.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				//changeLogMessagesOther
				try (FileWriter file = new FileWriter("./log/changeLogMessagesOther.txt")) {
					for(int i = 0; i < changeLogMessagesOther.size(); i++){
						file.write(changeLogMessagesOther.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/TripleCounters.txt")) {
			
					Set<Entry<Integer, Integer>> tempSet = tripleCounter.entrySet();
					Iterator<Entry<Integer, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							Integer curr = iterator.next().getKey();
							file.write(curr + " triples: "+ tripleCounter.get(curr) +  "\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/TripleCountersFull.txt")) {
					
					Set<Entry<Integer, Integer>> tempSet = tripleCounterFull.entrySet();
					Iterator<Entry<Integer, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							Integer curr = iterator.next().getKey();
							file.write(curr + " triples: "+ tripleCounterFull.get(curr) +  "\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				try (FileWriter file = new FileWriter("./log/OperatorDistribution.txt")) {
					
					Set<Entry<String, Integer>> tempSet = operatorDistribution.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(operatorDistribution.get(curr) > 0)
								file.write(curr + ": " + operatorDistribution.get(curr) +"\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/OperatorDistributionFull.txt")) {
					
					Set<Entry<String, Integer>> tempSet = operatorDistributionFull.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(operatorDistributionFull.get(curr) > 0)
								file.write(curr + ": " + operatorDistributionFull.get(curr) +"\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/SubqueryDistribution.txt")) {
					
					Set<Entry<String, Integer>> tempSet = subqueryDistribution.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(subqueryDistribution.get(curr) > 0)
								file.write(curr + ": " + subqueryDistribution.get(curr) +"\n");
						}
					
					
					file.flush();
					
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/SubqueryDistributionFull.txt")) {
					
					Set<Entry<String, Integer>> tempSet = subqueryDistributionFull.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(subqueryDistributionFull.get(curr) > 0)
								file.write(curr + ": " + subqueryDistributionFull.get(curr) +"\n");
						}
					
					
					file.flush();
					
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				
				System.out.println("changeLogMessagesException: " + changeLogMessagesException.size());
				System.out.println("changeLogMessagesTimeout: " + changeLogMessagesTimeout.size());
				System.out.println("changeLogMessagesOutdated: " + outdatedcounter);
				System.out.println("changeLogMessagesDuplicate: " + changeLogMessagesDuplicate.size());
				System.out.println("Total messages: " + (changeLogMessagesDuplicate.size()+changeLogMessagesOutdated.size()+changeLogMessagesTimeout.size()+changeLogMessagesException.size()));
				System.out.println("Total queries: " + counter);
		
	
	}
	
	//https://stackoverflow.com/questions/767759/occurrences-of-substring-in-a-string
	static int subQueries(Question q, boolean full) {
		String curr = q.getQuestionQuery();
		curr = curr.replace(" ", "").toLowerCase();
		
		for(int i = 0; i < keywords.length; i++) {
			int lastIndex = 0;
			int counter = 0;
			
			while(lastIndex != -1){

			    lastIndex = curr.indexOf("{" + keywords[i].replace(" ", "").toLowerCase(),lastIndex);

			    if(lastIndex != -1){
			    	counter = counter + 1;
			        lastIndex = lastIndex + curr.length();
			    }
			}
			if(full) {
				int temp = subqueryDistributionFull.get(keywords[i].replace(" ", ""));
				temp = temp + counter;
				subqueryDistributionFull.put(keywords[i], temp);
			}
			else {
				int temp = subqueryDistribution.get(keywords[i].replace(" ", "").toLowerCase());
				temp = temp + counter;
				subqueryDistribution.put(keywords[i].toLowerCase(), temp);
			}
		}
		
		return counter;
		
	}
	static void operatorDistribution(Question q, boolean full) {
		String query = q.getQuestionQuery();
		boolean filter, and, opt, graph, union;
		filter = query.toLowerCase().contains("filter");
		and = query.toLowerCase().contains("and");
		opt = query.toLowerCase().contains("opt");
		graph = query.toLowerCase().contains("graph");
		union = query.toLowerCase().contains("union");
		if(!filter && !and && !opt && !graph && !union) {
			int temp = operatorDistribution.put("None", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("None", temp);
			else
				operatorDistribution.put("None", temp);
		}
		else if(filter && !and && !opt && !graph && !union) {
			int temp = operatorDistribution.put("Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Filter", temp);
			else
				operatorDistribution.put("Filter", temp);
		}
		else if(!filter && and && !opt && !graph && !union) {
			int temp = operatorDistribution.put("And", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And", temp);
			else
				operatorDistribution.put("And", temp);
		}
		else if(filter && and && !opt && !graph && !union) {
			int temp = operatorDistribution.put("And, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Filter", temp);
			else
				operatorDistribution.put("And, Filter", temp);
		}
		else if(!filter && !and && opt && !graph && !union) {
			int temp = operatorDistribution.put("Opt", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Opt", temp);
			else
				operatorDistribution.put("Opt", temp);
		}
		else if(filter && !and && opt && !graph && !union) {
			int temp = operatorDistribution.put("Opt, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Opt, Filter", temp);
			else
				operatorDistribution.put("Opt, Filter", temp);
		}
		else if(!filter && and && opt && !graph && !union) {
			int temp = operatorDistribution.put("And, Opt", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Opt", temp);
			else
				operatorDistribution.put("And, Opt", temp);
		}
		else if(filter && and && opt && !graph && !union) {
			int temp = operatorDistribution.put("And, Opt, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Opt, Filter", temp);
			else
				operatorDistribution.put("And, Opt, Filter", temp);
		}
		else if(!filter && !and && !opt && graph && !union) {
			int temp = operatorDistribution.put("Graph", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Graph", temp);
			else
				operatorDistribution.put("Graph", temp);
		}
		else if(!filter && !and && !opt && !graph && union) {
			int temp = operatorDistribution.put("Union", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Union", temp);
			else
				operatorDistribution.put("Union", temp);
		}
		else if(filter && !and && !opt && !graph && union) {
			int temp = operatorDistribution.put("Union, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("Union, Filter", temp);
			else
				operatorDistribution.put("Union, Filter", temp);
		}
		else if(!filter && and && !opt && !graph && union) {
			int temp = operatorDistribution.put("And, Union", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Union", temp);
			else
				operatorDistribution.put("And, Union", temp);
		}
		else if(filter && and && !opt && !graph && union) {
			int temp = operatorDistribution.put("And, Union, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Union, Filter", temp);
			else
				operatorDistribution.put("And, Union, Filter", temp);
		}
		else if(filter && and && opt && !graph && union) {
			int temp = operatorDistribution.put("And, Opt, Union, Filter", 0);
			temp ++;
			if(full)
				operatorDistributionFull.put("And, Opt, Union, Filter", temp);
			else
				operatorDistribution.put("And, Opt, Union, Filter", temp);
		}
	}
	
	//Functiosn used to create JSONArray of questions that will be printed to file.
	static JSONObject QuestionToJSON(Question q) {
		JSONObject output = new JSONObject();
		output.put("question", q.getQuestionString());
		output.put("file", q.getFilepath());
		output.put("source", q.getQuestionSource());
		output.put("query", q.getQuestionQuery());
		output.put("answers", q.getAnswers());
		output.put("number of triples", getNumberOfTuples(q.getQuestionQuery()));
		
		int numberOfTriples = getNumberOfTuples(q.getQuestionQuery());
		int currTripleCounter = 0;
		if(tripleCounter.get(numberOfTriples) == null)
			currTripleCounter = 1;
		else
			currTripleCounter = tripleCounter.get(numberOfTriples) + 1;
		
		tripleCounter.put(numberOfTriples, currTripleCounter);
		for(int i = 0; i< keywords.length; i++) {
			if(q.getQuestionQuery().toLowerCase().contains(keywords[i].toLowerCase())) {
				if(keywordCount.containsKey(keywords[i])) {
					int counter = keywordCount.get(keywords[i]);
					counter  = counter + 1;
					keywordCount.put(keywords[i], counter );
				}
				else {
					keywordCount.put(keywords[i], 1);
				}
				
			}
		}
		
		operatorDistribution(q, false);
		subqueriescounter = subQueries(q, false);
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
		//quad    C:\Users\Gabe\eclipse-workspace\QuestionAnswerBenchmark\data\original\LC-QuAD-data
		results.add(JSONParser.parseQuADFile("./data/original/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
		results.add(JSONParser.parseQuADFile("./data/original/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));
		//qlad
		//1
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-train-CDATA.xml", "QALD-1", "dbpedia", false));
				
				//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "musicbrainz", false));
				
				//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "musicbrainz", false));
				//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train-CDATA.xml", "QALD-1", "musicbrainz", false));
				
		//2
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
		
		//Freez
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz", false));
		
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
		//3
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		
		
		//Freeze
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz", true));
		
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz", true));
		
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
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
				
		return results;
	}
	
	//Returns endpoint url when given a string. Not fully complete.
	static String getDatabaseDomain(String database) {
		if(database.compareTo("musicbrainz") == 0)
			return "http://dbtune.org/musicbrainz/snorql?force=true";	
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
			System.out.println("File " + i + " of " + totalQuestions.size());
			counter = counter + totalQuestions.get(i).size();
			ArrayList<Question> questionsToRemove = new ArrayList<Question>();
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {

				Question curr = totalQuestions.get(i).get(j);
				operatorDistribution(curr, true);
			
				subQueries(curr, true);
				int numberOfTriples = getNumberOfTuples(curr.getQuestionQuery());
				int currTripleCounter = 0;
				if(tripleCounterFull.get(numberOfTriples) == null)
					currTripleCounter = 1;
				else
					currTripleCounter = tripleCounterFull.get(numberOfTriples) + 1;
				
				tripleCounterFull.put(numberOfTriples, currTripleCounter);
				
				ArrayList<String> temp = getUpdatedAnswers(curr);
				if(temp == null) {
					//System.out.println("Does this get called");
					questionsToRemove.add(curr);
				}
				else {
					//System.out.println(temp.get(0));
				    curr.setAnswers(temp);
				}
			}
			
			for(int k = 0; k < questionsToRemove.size(); k++) {
			
				totalQuestions.get(i).remove(questionsToRemove.get(k));
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
	static void addException(String exec, Question question) {
		int temp = 0;
		if(exec == null) { return; }
		String exception = exec;
		if(exception.contains("Unresolved prefixed name:")){
			exception = "Unresolved prefixed name";
			UnresolvedPrefixedNameExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else if(exception.contains("<PNAME_NS>")){
			exception = "Encountered <PNAME_NS>";
			changeLogMessagesRemoved.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "\nExpected Answers: " + question.getAnswers()+"\n"
					+ "Exception: "+ exec +"\n\n");
		}
		else if(exception.contains("Was expecting one of:")){
			exception = "Was expecting one of";
			UnexpectedEncounterExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else if(exception.contains("Lexical error at")){
			exception = "Lexical error at";
			LexicalErrorExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else {
			OtherExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");
		}
		if(TypesofExceptionsCounters.get(exception) != null) {
			temp = TypesofExceptionsCounters.get(exception);
		}
		else {
			TypesofExceptions.add(exception);
		}
		TypesofExceptionsCounters.put(exception, temp+1);
		
	}

	static ArrayList<String> getUpdatedAnswers(Question question){
		ArrayList<String> returnAnswers = new ArrayList<String>();
		ArrayList<String> answers =  question.getAnswers();
		String query = question.getQuestionQuery().replace("\n", " ").replace("\t", " ");
		question.setQuestionQuery(query);
		ParameterizedSparqlString qs = new ParameterizedSparqlString(query);
		//System.out.println(query);
        List<QuerySolution> newAnswers = null;
        
        try {
        	//System.out.println("1");
        	QueryExecution exec = QueryExecutionFactory.sparqlService(getDatabaseDomain(question.getDatabase()), qs.asQuery());

        	//System.out.println("2");
        	final ResultSet[] resultsTemp = new ResultSet[1];
        	final int[] resultErrorCode = new int[1];

        	//System.out.println("3");
        	resultsTemp[0] = null;
        	resultErrorCode[0] = -1;
        	//System.out.println("4");
        	ResultSet results = null;
        	//System.out.println("5");
        	Object LOCK = new Object();
        	//System.out.println("6");
        	//System.out.println("\n");
        	
        	
            Thread test = new Thread("test") {
            		public void run() {
            			synchronized(LOCK){
            				try {
            					resultErrorCode[0] = -1;
            					resultsTemp[0] = exec.execSelect();
            					resultErrorCode[0] = 0;
            				}
            				catch(Exception e) {
            					resultErrorCode[0] = -2;
            					resultsTemp[0] = null;
            				}
            				
	            			LOCK.notifyAll();
            				
            			}
            		}
            };
            
            	
	            
	            synchronized(LOCK) {
	            	test.start();
	            	LOCK.wait(15000);
	            }
	            
				if(resultErrorCode[0] == -1) {
					test.getState();
					throw(new Exception("SPARQL query timed out."));
				}
				else if(resultErrorCode[0] == -2) {
					try {
					if(exec.execAsk())
						returnAnswers.add("true");
					else
						returnAnswers.add("false");
					return returnAnswers;
					}
					catch(Exception e) {
						//System.out.println(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
						changeLogMessagesRemoved.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
						throw(new Exception("returns no answers"));
					}
				}
				else {
					results = resultsTemp[0];
				}
			
	        newAnswers = ResultSetFormatter.toList(results);
	      
	        if(newAnswers.size() == 0) {
	        	//System.out.println(question.getQuestionString());
	        	changeLogMessagesRemoved.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
	        	throw(new Exception("returns no answers"));
	        }
		}
		catch(Exception e) {
			addException(e.getMessage(), question);
			if(e.getMessage() == null) {
				return answers;
			}
        	else
        	{
        		/*
        		String expectedAnswers = "";
        		for(int i = 0; i< question.getAnswers().size(); i++) {
        			expectedAnswers = expectedAnswers + question.getAnswers().get(i) +"\n";
        		}
        		changeLogMessagesException.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
    					+ "Expected Answers: "+ expectedAnswers + "\nReturned Answers: "+ e +"\n\n");
        		returnAnswers = answers;*/
        		return null;
        	}
        }
		
		 
	        //Check if Answers are still valid
	        //Case 1: If the size of the stored and fetched answer arrays are different, update with the fetech answer array
       // System.out.println("newAnswers.size(): " + newAnswers.size());    
        //System.out.println("answers.size(): " + answers.size()); 
        if(newAnswers.size() == answers.size()) {
        	boolean outdated = false;
	        	for(int i = 0; i < answers.size(); i++) {
	        		String varName = newAnswers.get(i).varNames().next();
        			String fetched = newAnswers.get(i).get(varName).toString().split("\\^")[0];

	        		if(answers.get(i).compareTo(fetched) != 0) {
	        			changeLogMessagesOutdated.add("File: " + question.getFilepath()+ "\n" + "'" + question.getQuestionString() + "' has an outdated answer. \n"
	        					+ "Stored: " + answers.get(i) +"\n"
	        					+ "Fetched: " + fetched +"\n\n");
	    				returnAnswers.add(fetched);
	    				outdated=true;
	    				
	    			}
	    			else {
	    				//System.out.println("	Answer matches. Updating.");
	    				returnAnswers.add(answers.get(i));
	    				
	    			}
	        		
	        	}
	        	if(!outdated) {
        			outdatedcounter++;
        		}
	    			
	        }
	        else 
	        {
	        	if(newAnswers.size()>0)
	        	{
		        	changeLogMessagesOutdated.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nNumber of stored answers do not match number of answers found online.\n"
		        			+ "Stored: "+ answers.size() + "\n"
		        			+ "Fetched: "+ newAnswers.size() +"\n\n ");
		        	for(int i = 0; i < newAnswers.size(); i++) {
		        		String varName = newAnswers.get(i).varNames().next();
		        		String fetched = newAnswers.get(i).get(varName).toString().split("\\^")[0];
		        		returnAnswers.add(fetched);
		        		return returnAnswers;
		        	}
	        	}
	        	else {
	        		changeLogMessagesNoAnswers.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
	        		returnAnswers = answers;
	        		return null;
	        	}
	        }
        return returnAnswers;
	}
}

