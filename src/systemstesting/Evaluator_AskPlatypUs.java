package systemstesting;

import UptodatAnswers.CuratedAnswer;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class Evaluator_AskPlatypUs extends Evaluator {

    @Override
    public ArrayList<String> answer(String question) throws IOException, JSONException {
        ArrayList<String> systemAnswers = new ArrayList<>();

        //This sytem targets Wikidata
        
        
        
        String systemURL = "https://qa.askplatyp.us/v0/ask?q=" + question + "&lang=en";
        JSONObject json = readJsonFromUrl(systemURL);
        //System.out.println(json.toString());
        
        //Get Sparql Query
        String queryString = "";
        try {
            queryString = json.getJSONObject("member").getString("platypus:sparql").replaceAll("\\n", " ")
                .replaceAll("\\t", " ");
        } catch (Exception e) {
            queryString = ""; return systemAnswers;
        }
        
        if(queryString.equals("") || queryString==null)
            return  systemAnswers;
        
        System.out.println(queryString);
        
        
        
        return CuratedAnswer.upToDateAnswerWikidata(queryString, "wikidata");
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
        catch(Exception e)
        {
            System.out.println("               No JSON returned");
            return new JSONObject("{}");
        }
    }

    public static void main(String[] args) throws IOException, JSONException, Exception {
        Evaluator_AskPlatypUs evaluator = new Evaluator_AskPlatypUs();
        evaluate(evaluator);
    }
}
