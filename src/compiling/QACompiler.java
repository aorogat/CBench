package compiling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.XMLParser;

public class QACompiler {
	public static ArrayList<ArrayList<Question>> totalQuestions = new ArrayList<ArrayList<Question>>();
		
	public static HashMap<String, Integer> TypesofExceptionsCounters = new HashMap<String, Integer> ();
	public static int operatordistributioncounter = 0;
	public static int operatordistributioncounterfull = 0;
	public static int counter = 0;
	public static int outdatedcounter = 0;
	public static int subqueriescounter = 0;
	static int originalquestionnumber =0;
	static int originalquestionnumberafterredundancy =0;
	
	public static void main(String[] args) {
		initHashmaps();
        totalQuestions = parseQuestions();
        checkForRedundancy();
		System.out.println("Check for valid answers");
		cleanEmptyFiles();
		validateAnswers();
		cleanEmptyFiles();
		System.out.print("Done. \n");
		writeToFile();
	return;
	}
	
	//This initalizes the hashmaps that will be used in logging. Also creates all the possible operator combonations.
	public static void initHashmaps() {
		
		for(int i = 0; i < SparqlInfo.keywords.length; i++) {
			for(int j = i+1; j < SparqlInfo.keywords.length; j++) {
				for(int k = j+1; k < SparqlInfo.keywords.length; k++) {	
					for(int l = k+1; l < SparqlInfo.keywords.length; l++) {
						for(int m = l+1; m < SparqlInfo.keywords.length; m++) {
							for(int n = m+1; n < SparqlInfo.keywords.length; n++) {
								SparqlInfo.commonkeywordscombo.add(SparqlInfo.keywords[i]+" "
												+SparqlInfo.keywords[j].toLowerCase()+" "
												+SparqlInfo.keywords[m].toLowerCase()+" "
												+SparqlInfo.keywords[k].toLowerCase()+" "
												+SparqlInfo.keywords[l].toLowerCase()+" "
												+SparqlInfo.keywords[n].toLowerCase());									
							}
						}
					}
				}
			}
		}
		
		CompilerLogs.operatorDistribution.put("none", 0);
		for(int i = 0; i < SparqlInfo.commonkeywordscombo.size(); i++) {	
			CompilerLogs.operatorDistribution.put(SparqlInfo.commonkeywordscombo.get(i), 0);
		}
		
		CompilerLogs.operatorDistributionFull.put("none", 0);
		for(int i = 0; i < SparqlInfo.commonkeywordscombo.size(); i++) {	
			CompilerLogs.operatorDistributionFull.put(SparqlInfo.commonkeywordscombo.get(i), 0);
		}

		for(int i = 0; i < SparqlInfo.keywords.length; i++) {
			CompilerLogs.subqueryDistribution.put(SparqlInfo.keywords[i].replace(" ", ""), 0);
		}
		
		for(int i = 0; i < SparqlInfo.keywords.length; i++) {
			CompilerLogs.subqueryDistributionFull.put(SparqlInfo.keywords[i].replace(" ", ""), 0);
		}


	}
	//Searchs and replaces redundant questions
	static void checkForRedundancy() {
		System.out.print("Checking for redundancy. \n");
		//Iterate through each file parsed.		
		for(int i = 0; i < totalQuestions.size(); i++) {
			//Iterate through each question in qa file
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {
				originalquestionnumber++;
			}
		}
		
		for(int i = 0; i < totalQuestions.size(); i++) {
			
			ArrayList<Question> curr= totalQuestions.get(i); // current qa file
			
			//Iterate through each question in qa file
			for(int j = 0; j < curr.size(); j++) {
				if(curr.get(j).getQuestionQuery() == null) {
					System.out.println("Hit");
					totalQuestions.get(i).remove(j);
					j--;
					continue; 
				};
				String currQuestion = curr.get(j).getQuestionString(); //Current question
				
				//Go through each other question in each other file
				for(int i2 = 0; i2 <= i; i2++) {
					ArrayList<Question> curr2 = totalQuestions.get(i2);
					if(i2 >= i) {
						//break;
					}
					for(int j2 = 0; j2 < curr2.size(); j2++) {
					
						String currQuestionCompare = curr2.get(j2).getQuestionString(); 

						if(i != i2 || j != j2)
						{
							//If a seperate question is found to be identical
							if(currQuestion.compareTo(currQuestionCompare) == 0) //????
							{
								//Log the duplicate
								CompilerLogs.changeLogMessagesDuplicate.add(curr.get(j).getFilepath() +"\t" + "'" + currQuestion + "'" + " is a duplicate; found in paths: \t "
										+ "        " + curr.get(j).getFilepath() + " and \t" 
										+ "        " + curr2.get(j2).getFilepath() + "\t" + ""
										+ "Instance found in newer file " + curr.get(j).getFilepath() + " will be kept.\t\t");
								//Remove the old question
								curr2.remove(j2);
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < totalQuestions.size(); i++) {
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {
				originalquestionnumberafterredundancy++;
			}
		}
	}
	
	
	//Create json file of questions/answers and txt file of changes.
	static void cleanEmptyFiles() {
		for(int i = 0; i < totalQuestions.size(); i++) {
			if(totalQuestions.get(i).size() == 0) {
				totalQuestions.remove(i);
				i--;
			}
		}
	}
	static void writeToFile() {
		//Write
		int totalQueries = 0;
		int totalQueriestest = 0;
		for(int i = 0; i < totalQuestions.size(); i++) {
			
			String currFile = totalQuestions.get(i).get(0).getFilepath();
			CompilerLogs.fileQuestionCount.put(currFile, totalQuestions.get(i).size());
			totalQueriestest = totalQueriestest + totalQuestions.get(i).size();
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {
				totalQueries++;
			}
		}

		try (FileWriter file = new FileWriter("./finalQuestionAnswerList.json")) {
			file.write(QuestionArrayListToJSONArray(totalQuestions).toString(4));
		    file.flush();
		} catch (IOException e) {
		    	e.printStackTrace();
		    }
				
				
				
		try (FileWriter file = new FileWriter("./log/QuestionFileCounter.txt")) {							
			Set<Entry<String, Integer>> tempSet = CompilerLogs.fileQuestionCount.entrySet();
			Iterator<Entry<String, Integer>> iterator = tempSet.iterator();
	
			while(iterator.hasNext()){
				String curr = iterator.next().getKey();
				file.write(curr + ": "+ CompilerLogs.fileQuestionCount.get(curr) +  "\n");
			}
				
		    file.flush();
		} catch (IOException e) { e.printStackTrace();}
		try (FileWriter file = new FileWriter("./log/KeywordList.txt")) {
			file.write("Number of keywords: " + CompilerLogs.keywordCount.size()  + "\n");
			int total = 0;
			for(int i = 0; i < SparqlInfo.keywords.length; i++){
				if(CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]) != null) {
					total = total + CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]);
				}
			}
					
			file.write("Number of hits: " + total  + "\nNumber of queries: " + totalQueries+"\n");
					
			for(int i = 0; i < SparqlInfo.keywords.length; i++) {
				if(CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]) != null) {
					int val = CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]);				
					file.write(SparqlInfo.keywords[i] + ": " + CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]) + "\n");
				}
			}
	        file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		try (FileWriter file = new FileWriter("./log/UnresolvedPrefixedNameExceptionsList.txt")) {
			file.write("Total: "+ CompilerLogs.UnresolvedPrefixedNameExceptions.size()+  "\n\n");
			for(int i = 0; i < CompilerLogs.UnresolvedPrefixedNameExceptions.size(); i++){
				file.write(CompilerLogs.UnresolvedPrefixedNameExceptions.get(i) +  "\n\n");
			}
		    file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		try (FileWriter file = new FileWriter("./log/changeLogMessagesRemoved.txt")) {
			file.write("Questions removed: "+ CompilerLogs.changeLogMessagesRemoved.size()+  "\n" 
					+ "Total questions after reduncany: "+originalquestionnumberafterredundancy+ "\n"
					+ "Oringal questions: " + originalquestionnumber+"\n");
			for(int i = 0; i < CompilerLogs.changeLogMessagesRemoved.size(); i++){
				file.write(CompilerLogs.changeLogMessagesRemoved.get(i) +  "\n\n");
			}
							
		    file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileWriter file = new FileWriter("./log/OtherExceptionsList.txt")) {
			file.write("Total: "+ CompilerLogs.OtherExceptions.size()+  "\n\n");
			for(int i = 0; i < CompilerLogs.OtherExceptions.size(); i++){
				file.write(CompilerLogs.OtherExceptions.get(i) +  "\n\n");
			}
		    file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
				try (FileWriter file = new FileWriter("./log/LexicalErrorExceptionsList.txt")) {
					file.write("Total: "+ CompilerLogs.LexicalErrorExceptions.size()+  "\n\n");
					for(int i = 0; i < CompilerLogs.LexicalErrorExceptions.size(); i++){
						file.write(CompilerLogs.LexicalErrorExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/UnexpectedEncounterExceptionsList.txt")) {
					file.write("Total: "+ CompilerLogs.UnexpectedEncounterExceptions.size()+  "\n\n");
					for(int i = 0; i < CompilerLogs.UnexpectedEncounterExceptions.size(); i++){
						file.write(CompilerLogs.UnexpectedEncounterExceptions.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				try (FileWriter file = new FileWriter("./log/changeLogMessagesNoAnswers.txt")) {
					for(int i = 0; i < CompilerLogs.changeLogMessagesNoAnswers.size(); i++){
						file.write(CompilerLogs.changeLogMessagesNoAnswers.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				//changeLogMessagesOutdated
				try (FileWriter file = new FileWriter("./log/changeLogMessagesOutdated.txt")) {
					int diffsize = 0;
					int outdated = 0;
					
					for(int i = 0; i < CompilerLogs.changeLogMessagesOutdated.size(); i++){
						if(CompilerLogs.changeLogMessagesOutdated.get(i).contains("do not match")) {diffsize++;}
						else { outdated ++; }
						
						file.write(CompilerLogs.changeLogMessagesOutdated.get(i) +  "\n\n");
					}
					file.write("Questions with different fetched sizes: " + diffsize+  "\n");
					file.write("Questions with outdated answers: " + outdated+  "\n");
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				//changeLogMessagesDuplicate
				try (FileWriter file = new FileWriter("./log/changeLogMessagesDuplicate.txt")) {
					for(int i = 0; i < CompilerLogs.changeLogMessagesDuplicate.size(); i++){
						file.write(CompilerLogs.changeLogMessagesDuplicate.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				//changeLogMessagesOther
				try (FileWriter file = new FileWriter("./log/changeLogMessagesOther.txt")) {
					for(int i = 0; i < CompilerLogs.changeLogMessagesOther.size(); i++){
						file.write(CompilerLogs.changeLogMessagesOther.get(i) +  "\n\n");
					}
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/TripleCounters.txt")) {
			
					Set<Entry<Integer, Integer>> tempSet = CompilerLogs.tripleCounter.entrySet();
					Iterator<Entry<Integer, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							Integer curr = iterator.next().getKey();
							file.write(curr + " triples: "+ CompilerLogs.tripleCounter.get(curr) +  "\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/TripleCountersFull.txt")) {
					
					Set<Entry<Integer, Integer>> tempSet = CompilerLogs.tripleCounterFull.entrySet();
					Iterator<Entry<Integer, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							Integer curr = iterator.next().getKey();
							file.write(curr + " triples: "+ CompilerLogs.tripleCounterFull.get(curr) +  "\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				try (FileWriter file = new FileWriter("./log/OperatorDistribution.txt")) {
					file.write("TOTAL: " + operatordistributioncounter+"\n\n");
					Set<Entry<String, Integer>> tempSet = CompilerLogs.operatorDistribution.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(CompilerLogs.operatorDistribution.get(curr) > 0)
								file.write(curr + ": " + CompilerLogs.operatorDistribution.get(curr) +"\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/OperatorDistributionFull.txt")) {
					file.write("TOTAL: " + operatordistributioncounter);
					Set<Entry<String, Integer>> tempSet = CompilerLogs.operatorDistributionFull.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(CompilerLogs.operatorDistributionFull.get(curr) > 0)
								file.write(curr + ": " + CompilerLogs.operatorDistributionFull.get(curr) +"\n");
						}
					
		            file.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/SubqueryDistribution.txt")) {
					
					Set<Entry<String, Integer>> tempSet = CompilerLogs.subqueryDistribution.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(CompilerLogs.subqueryDistribution.get(curr) > 0)
								file.write(curr + ": " + CompilerLogs.subqueryDistribution.get(curr) +"\n");
						}
					
					
					file.flush();
					
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				try (FileWriter file = new FileWriter("./log/full/SubqueryDistributionFull.txt")) {
					
					Set<Entry<String, Integer>> tempSet = CompilerLogs.subqueryDistributionFull.entrySet();
					Iterator<Entry<String, Integer>> iterator = tempSet.iterator();

						while(iterator.hasNext()){
							String curr = iterator.next().getKey();
							if(CompilerLogs.subqueryDistributionFull.get(curr) > 0)
								file.write(curr + ": " + CompilerLogs.subqueryDistributionFull.get(curr) +"\n");
						}
					
					
					file.flush();
					
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

				System.out.println("changeLogMessagesOutdated: " + outdatedcounter);
				System.out.println("changeLogMessagesDuplicate: " + CompilerLogs.changeLogMessagesDuplicate.size());
				System.out.println("Total messages: " + (CompilerLogs.changeLogMessagesDuplicate.size()+CompilerLogs.changeLogMessagesOutdated.size()));
				System.out.println("Total queries: " + counter);
	}
		
	//Functiosn used to create JSONArray of questions that will be printed to file.
	static JSONObject QuestionToJSON(Question q) {
		JSONObject output = new JSONObject();
		output.put("question", q.getQuestionString());
		output.put("file", q.getFilepath());
		output.put("source", q.getQuestionSource());
		output.put("query", q.getQuestionQuery());
		output.put("answers", q.getAnswers());
		output.put("number of triples", QueryParsingTools.getNumberOfTuples(q.getQuestionQuery()));
		
		int numberOfTriples = QueryParsingTools.getNumberOfTuples(q.getQuestionQuery());
		int currTripleCounter = 0;
		if(CompilerLogs.tripleCounter.get(numberOfTriples) == null)
			currTripleCounter = 1;
		else
			currTripleCounter = CompilerLogs.tripleCounter.get(numberOfTriples) + 1;
		
		CompilerLogs.tripleCounter.put(numberOfTriples, currTripleCounter);
		for(int i = 0; i< SparqlInfo.keywords.length; i++) {
			if(q.getQuestionQuery().contains(SparqlInfo.keywords[i])) {
				if(CompilerLogs.keywordCount.containsKey(SparqlInfo.keywords[i])) {
					int counter = CompilerLogs.keywordCount.get(SparqlInfo.keywords[i]);
					counter  = counter + 1;
					CompilerLogs.keywordCount.put(SparqlInfo.keywords[i], counter );
				}
				else {
					CompilerLogs.keywordCount.put(SparqlInfo.keywords[i], 1);
				}
				
			}
		}
		
		QueryParsingTools.operatorDistribution(q, false);
		return output;
	}
	//Create final array
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
	
	//Goes through and parses all the questions. Commented out questions are files that are unusable.
	static ArrayList<ArrayList<Question>> parseQuestions()
	{
		ArrayList<ArrayList<Question>> results = new ArrayList<ArrayList<Question>>();
		
		int totalNumberOfQuestions = 0;
		int resultsIndex = 0;
		
		//LC- QuAD
//		results.add(JSONParser.parseQuADFile("./data/original/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
//		System.out.println("File: " + "./data/original/QALD-master/1/data/dbpedia-test.xml");
//		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
//		totalNumberOfQuestions += results.get(resultsIndex).size();
//		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
//		++resultsIndex;
		
//		results.add(JSONParser.parseQuADFile("./data/original/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));
//		System.out.println("File: " + "./data/original/QALD-master/1/data/dbpedia-test.xml");
//		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
//		totalNumberOfQuestions += results.get(resultsIndex).size();
//		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
//		++resultsIndex;
		
		//QALD
		//1
		results.add(XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
		System.out.println("File: " + "./data/original/QALD-master/1/data/dbpedia-test.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
		System.out.println("File: " + "./data/original/QALD-master/1/data/dbpedia-train.xmloriginal/LC-QuAD-data/train-data.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-train-CDATA.xml", "QALD-1", "dbpedia", false));
		System.out.println("File: " + "./data/original/QALD-master/1/data/dbpedia-train-CDATA.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
				
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "musicbrainz", false));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "musicbrainz", false));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/1/data/musicbrainz-train-CDATA.xml", "QALD-1", "musicbrainz", false));
				
		//2
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
		System.out.println("File: " + "./data/original/QALD-master/2/data/dbpedia-train-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);		
		++resultsIndex;
		
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz", false));
		
		results.add(XMLParser.parseQald1("./data/original/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
		System.out.println("File: " + "./data/original/QALD-master/2/data/participants-challenge-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//3
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/3/data/dbpedia-test-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/3/data/dbpedia-train-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/3/data/esdbpedia-test-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/3/data/esdbpedia-train-answers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz", true));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz", true));
		
		//4 

		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "mannheimu"));
		//results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "mannheimu"));
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/4/data/qald-4_multilingual_test.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/4/data/qald-4_multilingual_train.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
		System.out.println("File: " + "./data/original/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		
		//5
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/5/data/qald-5_train.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/5/data/qald-5_test.xml");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//results.add(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5"));
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.json
		///QuestionAnswerBenchmark/data/original/QALD-master/5/data/qald-5_train.xml
		
		//6
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/6/data/qald-6-train-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/6/data/qald-6-test-hybrid.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/6/data/qald-6-test-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//results.add(JSONParser.parseQald7File4("./data/original/QALD-master/6/data/qald-6-train-datacube-raw.json", "QALD-6", "linkedspending"));
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
		System.out.println("File: " + "./data/original/QALD-master/6/data/qald-6-train-datacube.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/6/data/qald-6-train-hybrid.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//results.add(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual-raw.json", "QALD-6", "dbpedia"));

		//7
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-largescale.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File1("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-hybrid-extended-json.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File4("./data/original/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-hybrid.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-test-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/7/data/qald-7-train-largescale.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		
		//8
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/8/data/qald-8-test-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/8/data/qald-8-train-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		results.add(JSONParser.parseQald7File2("./data/original/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
		System.out.println("File: " + "./data/original/QALD-master/8/data/wikidata-train-7.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);
		++resultsIndex;
		
		//9 
		results.add(JSONParser.parseQald9File("./data/original/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
		System.out.println("File: " + "./data/original/QALD-master/9/data/qald-9-train-multilingual.json");
		System.out.println("Number of Questions: " + results.get(resultsIndex).size() );
		totalNumberOfQuestions += results.get(resultsIndex).size();
		System.out.println("Total Number of Questions: " + totalNumberOfQuestions);

		return results;
	}

	
	//Returns endpoint url when given a string.
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
			
			ArrayList<Question> questionsToRemove = new ArrayList<Question>();
			for(int j = 0; j < totalQuestions.get(i).size(); j++) {

				Question curr = totalQuestions.get(i).get(j);
				ArrayList<String> fetchedAnswers = getUpdatedAnswers(curr);
				
				if(fetchedAnswers == null) { questionsToRemove.add(curr); }
				else { curr.setAnswers(fetchedAnswers); }
				
				
				QueryParsingTools.operatorDistribution(curr, true);

				int numberOfTriples = QueryParsingTools.getNumberOfTuples(curr.getQuestionQuery());
				int currTripleCounter = 0;
				if(CompilerLogs.tripleCounterFull.get(numberOfTriples) == null)
					currTripleCounter = 1;
				else
					currTripleCounter = CompilerLogs.tripleCounterFull.get(numberOfTriples) + 1;
				
				CompilerLogs.tripleCounterFull.put(numberOfTriples, currTripleCounter);
				
				
			}
			
			for(int k = 0; k < questionsToRemove.size(); k++) {
			
				totalQuestions.get(i).remove(questionsToRemove.get(k));
			}
			counter = counter + totalQuestions.get(i).size();
		}
	}
	
	static String getResultFromIterator(Iterator<String> input) {
		for(int i = 0; i <1; i++) {	
			if(input.hasNext())
				input.next();
		}
		return input.next();
	}
	//Used for logging exceptions
	static void addException(String exec, Question question) {
		int temp = 0;
		if(exec == null) { return; }
		String exception = exec;
		if(exception.contains("Unresolved prefixed name:")){
			exception = "Unresolved prefixed name";
			CompilerLogs.UnresolvedPrefixedNameExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else if(exception.contains("<PNAME_NS>")){
			exception = "Encountered <PNAME_NS>";
			CompilerLogs.changeLogMessagesRemoved.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "\nExpected Answers: " + question.getAnswers()+"\n"
					+ "Exception: "+ exec +"\n\n");
		}
		else if(exception.contains("Was expecting one of:")){
			exception = "Was expecting one of";
			CompilerLogs.UnexpectedEncounterExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else if(exception.contains("Lexical error at")){
			exception = "Lexical error at";
			CompilerLogs.LexicalErrorExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");;
		}
		else {
			CompilerLogs.OtherExceptions.add("File: "+ question.getFilepath()+ "\n" + "Question: '" + question.getQuestionString() + "\nQuery: " + question.getQuestionQuery()+"\n"
					+ "Exception: "+ exec +"\n\n");
		}
		if(TypesofExceptionsCounters.get(exception) != null) {
			temp = TypesofExceptionsCounters.get(exception);
		}
		else {
			CompilerLogs.TypesofExceptions.add(exception);
		}
		TypesofExceptionsCounters.put(exception, temp+1);
		
	}
	
	//Sends SPARQL Query to endpoint and compares those answers with the stored answers
	static ArrayList<String> getUpdatedAnswers(Question question){
		ArrayList<String> returnAnswers = new ArrayList<String>();
		ArrayList<String> answers =  question.getAnswers();
		//System.out.println(question.getQuestionQuery());
		String query = question.getQuestionQuery().replace("\n", " ").replace("\t", " ");
		question.setQuestionQuery(query);
		ParameterizedSparqlString qs = new ParameterizedSparqlString(query);
        List<QuerySolution> newAnswers = null;
        
        try {

        	QueryExecution exec = QueryExecutionFactory.sparqlService(getDatabaseDomain(question.getDatabase()), qs.asQuery());
        	ResultSet resultsTemp = null;
        //	int resultErrorCode = -1; // -1 means fo
        	ResultSet results = null;

        
        	try {
				//resultErrorCode = -1;
				resultsTemp = exec.execSelect();
				//resultErrorCode = 0;
			}
			catch(Exception e) {
				try {
					if(exec.execAsk()) {
						returnAnswers.add("true");
					}
					else
						returnAnswers.add("false");
					return returnAnswers;
					}
					catch(Exception e2) {
						
						CompilerLogs.changeLogMessagesRemoved.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
						throw(new Exception("returns no answers"));
					}
			}

			results = resultsTemp;
				
			
	        newAnswers = ResultSetFormatter.toList(results);
	      
	        if(newAnswers.size() == 0) {
	        	
	        	CompilerLogs.changeLogMessagesRemoved.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
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

        		return null;
        	}
        }
		
		 
	    //Check if Answers are still valid
	    //Case: If the size of the stored and fetched answer arrays are different, update with the fetech answer array
        if(newAnswers.size() == answers.size()) {
        	boolean outdated = false;
	        	for(int i = 0; i < answers.size(); i++) {
	        		String varName = newAnswers.get(i).varNames().next();
        			String fetched = newAnswers.get(i).get(varName).toString().split("\\^")[0];

	        		if(answers.get(i).compareTo(fetched) != 0) {
	        			CompilerLogs.changeLogMessagesOutdated.add("File: " + question.getFilepath()+ "\n" + "'" + question.getQuestionString() + "' has an outdated answer. \n"
	        					+ "Stored: " + answers.get(i) +"\n"
	        					+ "Fetched: " + fetched +"\n\n");
	    				returnAnswers.add(fetched);
	    				outdated=true;
	    				
	    			}
	    			else {

	    				returnAnswers.add(answers.get(i));
	    				
	    			}
	        		
	        	}
	        	if(!outdated) {
        			outdatedcounter++;
        		}
	    			
	        }
        //Case: If the stored answers are different from the fetched answers.
	        else 
	        {
	        	if(newAnswers.size()>0)
	        	{
	        		CompilerLogs.changeLogMessagesOutdated.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nNumber of stored answers do not match number of answers found online.\n"
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
	        		CompilerLogs.changeLogMessagesNoAnswers.add(question.getFilepath() + "\n" +"'" + question.getQuestionString() + "'" + "\nQuery returns no answers. Deleting question.\n" + question.getQuestionQuery() + "\n\n");
	        		returnAnswers = answers;
	        		return null;
	        	}
	        }
        return returnAnswers;
	}
}

