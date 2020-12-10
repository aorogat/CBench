package systemstesting;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import static systemstesting.Evaluator_WDAqua.systemAnswersList;


public class CURLTest {

    public static void main(String[] args) throws IOException {
        String command = 
                "curl -X POST http://qanswer-core1.univ-st-etienne.fr/api/gerbil?"
                + "query=Who%20discovered%20Pluto%20&kb=wikidata";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8); 
        System.out.println(result);
        JSONObject json;
        result = result.replace("\\n", " ").replace("\\{", "{").replace("\\}", "}").replace("\\", "")
                    .replace("\"{", "{").replace("\"}", "}").replace("}\"", "}");
        json = new JSONObject(result);

            JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("results").getJSONArray("bindings");
            System.out.println(bindings.toString());

            
            systemAnswersList = new ArrayList<>();
            
            for (int i = 0; i < bindings.length(); i++) {
                JSONObject binding = (JSONObject) bindings.getJSONObject(i);
                JSONObject o1 = (JSONObject) binding.getJSONObject("o1");
                String value = (String) o1.get("value");
                systemAnswersList.add(value.replace('_', ' ')
                        .replace("http://dbpedia.org/resource/", "")
                        .replace("https://en.wikipedia.org/wiki/", ""));

            }
    }
}
