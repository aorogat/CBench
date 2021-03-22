/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import UptodatAnswers.CuratedAnswer;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import static systemstesting.Evaluator.systemAnswersList;

/**
 *
 * @author aorogat
 */
public class Evaluator_File_Based extends Evaluator{

    @Override
    public ArrayList<String> answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();

            //1- Get SPARQL Query data from File
            String sparqlQuery = "";
            
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("QA.json"));
                
                JSONObject o = new JSONObject(obj.toString());
                JSONArray jsonObject = o.getJSONArray("questions");
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
