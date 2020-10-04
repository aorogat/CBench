package UptodatAnswers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CuratedAnswer {
    public static String endpoint = "http://134.117.101.79:8890/sparql/?";

    public static void main(String[] args) throws UnsupportedEncodingException {
        upToDateAnswerDBpedia("ASK WHERE { <http://dbpedia.org/resource/Ganymede_(moon)> <http://dbpedia.org/property/discoverer> <http://dbpedia.org/resource/Galileo_Galilei> }", "dbpedia");
    }

    public static ArrayList upToDateAnswer(String query, String kg) throws UnsupportedEncodingException {
        if (kg.toLowerCase().equals("dbpedia")) {
            return upToDateAnswerDBpedia(query, kg);
        } else if(kg.toLowerCase().equals("freebase")) {
            return upToDateAnswerFreebase(query, kg); 
        } else if(kg.toLowerCase().equals("wikidata")) {
            return upToDateAnswerWikidata(query, kg); 
        }
        else
            return null;
    }
    
    public static ArrayList upToDateAnswerFreebase(String query, String kg) throws UnsupportedEncodingException
    {
        return upToDateAnswerDBpedia(query, kg);
    }
    
    public static ArrayList upToDateAnswerWikidata(String query, String kg) throws UnsupportedEncodingException
    {
        
        return upToDateAnswerDBpedia(query, kg);
    }

    public static ArrayList upToDateAnswerDBpedia(String query, String kg) throws UnsupportedEncodingException {
        ArrayList<String> answersList = new ArrayList<>();
        String url = endpoint
                + "default-graph-uri=http%3A%2F%2Fdbpedia.org&"
                + "query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) + "&"
                + "format=application%2Fsparql-results%2Bjson&"
                + "timeout=0&"
                + "debug=on";
        try {
            JSONObject json = readJsonFromUrl(url);
            try {

                JSONArray vars = json.getJSONObject("head").getJSONArray("vars");
                JSONArray bindings = json.getJSONObject("results").getJSONArray("bindings");

                for (Object var : vars) {
                    for (Object binding : bindings) {
                        try {
                            String v = (String) var;
                            JSONObject b = (JSONObject) binding;
                            answersList.add(b.getJSONObject(v).getString("value")
                                    .replace("http://dbpedia.org/resource/", "")
                                    .trim().replace('_', ' '));

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }
                    }
                }

            } catch (Exception e) {
                try {
                    //Boolean
                    if (json.getBoolean("boolean")) {
                        answersList.add("Yes");
                    } else {
                        answersList.add("No");
                    }

                } catch (Exception et) {
                    et.printStackTrace();
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        try {
        } catch (Exception e) {
        }
        return answersList;
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
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
