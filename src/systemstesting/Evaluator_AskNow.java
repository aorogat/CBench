package systemstesting;

import UptodatAnswers.CuratedAnswer;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import static systemstesting.Evaluator.evaluate;
import static systemstesting.Evaluator.systemAnswersList;

/**
 *
 * @author aorogat
 */
public class Evaluator_AskNow extends Evaluator
{
    public static void main(String[] args) throws JSONException, Exception {
        Evaluator_AskNow evaluator = new Evaluator_AskNow();
        evaluate(evaluator);
    }
    
    @Override
    public ArrayList<String> answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();

            //1- Get SPARQL Query data from File
            String sparqlQuery = "";
            
            JSONParser parser = new JSONParser();
            try {
                //Object obj = parser.parse(new FileReader("qald_"+benchmark+"_answers.json"));
                Object obj = parser.parse(new FileReader("lcquad_answers.json"));
                
                JSONArray jsonObject = new JSONArray(obj.toString());
                JSONObject questionResponse = null;
                        
                for (Object c : jsonObject) {
                    JSONObject current = (JSONObject) c;
                    String currentQuestion = current.getString("questionString")
                            .replaceAll("\\u0027", "'");
                    String currentQuery = current.getString("generatedSparql");
                    
                    if(currentQuestion.trim().toLowerCase().equals(question.trim().toLowerCase()))
                    {
                        questionResponse = current;
                        sparqlQuery = currentQuery;
                        //System.out.println(currentQuestion);
                        //System.out.println(currentQuery);
                        break;
                    }
                }
                
                
            } catch (Exception e) {
                System.out.println("Json Error");
                e.printStackTrace();
            }
            
            if (sparqlQuery == null) {
                sparqlQuery = "";
            }
            
            sparqlQuery = sparqlQuery.replaceAll("\\u003c", "<").replaceAll("\\u003e", ">");
            
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
