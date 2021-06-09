package systemstesting;

import UptodatAnswers.CuratedAnswer;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Evaluator_QAsparql extends Evaluator {
    
    public static final int ASK_QUERY = 1;
    public static final int COUNT_QUERY = 2;
    public static final int OTHER_QUERY = 0;
    
    public static void main(String[] args) throws JSONException, Exception {
        Evaluator_QAsparql evaluator = new Evaluator_QAsparql();
        evaluate(evaluator);
    }
    
    @Override
    public ArrayList<String> answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();

            //1- Get SPARQL Query data from File
            String queryGraph = "";
            String target_var = "";
            int queryType = 0;
            
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("qald_9_answer_output.json"));
                //Object obj = parser.parse(new FileReader("LCQuad_All_answer_output.json"));

                JSONArray jsonObject = new JSONArray(obj.toString());
                JSONObject questionResponse = null;
                        
                for (Object c : jsonObject) {
                    JSONObject current = (JSONObject) c;
                    String currentQuestion = current.getString("question");
                    
                    if(currentQuestion.trim().toLowerCase().equals(question.trim().toLowerCase()))
                    {
                        questionResponse = current;
                        System.out.println(current.toString());
                        break;
                    }
                }
                
                try{
                    JSONArray generated_queries = questionResponse.getJSONArray("generated_queries");
                    
                    for (Object o : generated_queries) {
                        JSONObject generated_query = (JSONObject) o;
                        if(generated_query.getBoolean("correct"))
                        {
                            queryGraph = generated_query.getString("query");
                            target_var = generated_query.getString("target_var");
                        }
                    }
                    
                    try {
                        JSONArray query_type = questionResponse.getJSONArray("question_type");
                        queryType = query_type.getInt(0);
                    } catch (Exception ex) {
                        try {
                            queryType = questionResponse.getInt("question_type");
                        } catch (Exception e) {
                            queryType = -1;
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            String sparqlQuery = "";
            if(!(queryGraph.equals("")))
                switch (queryType) {
                    case ASK_QUERY:
                        sparqlQuery = "ASK WHERE{ " + queryGraph + "}";
                    case COUNT_QUERY:
                        sparqlQuery = "SELECT (COUNT(DISTINCT " + target_var + ") as ?c) WHERE{ " + queryGraph + "}";
                    case OTHER_QUERY:                    
                        sparqlQuery = "SELECT DISTINCT " + target_var + " WHERE{ " + queryGraph + "}";
                }
            
            try {
                
            } catch (Exception e) {
                sparqlQuery = "";
            }
            if (sparqlQuery == null) {
                sparqlQuery = "";
            }
            
            sparqlQuery = sparqlQuery.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            
            System.out.println("               " + sparqlQuery);

            //2- Get Answer from DBpedia
            if (!sparqlQuery.equals("")) {
                systemAnswersList = CuratedAnswer.upToDateAnswerDBpedia(sparqlQuery, "dbpedia");
            } else {
                systemAnswersList = new ArrayList<>();
            }
            
        } catch (Exception eex) {
            eex.printStackTrace();
        }
        return systemAnswersList;
    }
    
}
