package systemstesting;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Evaluator_WebServiceBased extends Evaluator{


    public static void main(String[] args) throws JSONException, Exception {
           
        System.out.println("        |");
        System.out.println("        ++++> What is your system URL? (e.g. http://ganswer.gstore-pku.com/api/qald.jsp)]");
        System.out.print("               URL is: ");
        url = in.nextLine().trim().toLowerCase();
        Evaluator_WebServiceBased evaluator = new Evaluator_WebServiceBased();
        evaluate(evaluator);
    }

    
    @Override
     public ArrayList<String> answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();
            JSONObject json = readJsonFromUrl(url + "?query=" + question.replace(" ", "%20")
                    + "&kb=" + KB);

            //System.out.println("               System JSON Answer: " + json.toString());
            try {

                JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONArray("answers").getJSONObject(0)
                        .getJSONObject("results").getJSONArray("bindings");

                String varName = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONArray("answers").getJSONObject(0)
                        .getJSONObject("head").getJSONArray("vars").getString(0);

                for (int i = 0; i < bindings.length(); i++) {
                    JSONObject binding = (JSONObject) bindings.getJSONObject(i);
                    JSONObject o1 = (JSONObject) binding.getJSONObject(varName);
                    String value = (String) o1.get("value");

                    systemAnswersList.add(value.replace('_', ' ')
                            .replace("http://dbpedia.org/resource/", "")
                            .replace("https://en.wikipedia.org/wiki/", "")
                            .replace("http://www.wikidata.org/entity/", ""));
                }
            } catch (Exception e) {
                try {
                    boolean b = json.getJSONArray("questions").getJSONObject(0)
                            .getJSONArray("answers").getJSONObject(0)
                            .getBoolean("boolean");
                    systemAnswersList = new ArrayList<>();
                    if (b) {
                        systemAnswersList.add("Yes");
                    } else {
                        systemAnswersList.add("No");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception eex) {
            eex.printStackTrace();
        }
        return systemAnswersList;
    }

   
    

}
