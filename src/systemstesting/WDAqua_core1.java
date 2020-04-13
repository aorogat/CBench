package systemstesting;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.json.JSONObject;

public class WDAqua_core1 
{
    
    public static void main(String[] args) throws IOException 
    {
        //String command = "curl --data \"query=Who is the wife of Barack Obama\" http://qanswer-core1.univ-st-etienne.fr/api/gerbil";
        String q = "In which country is Mecca located?";
        String command = "curl http://qanswer-core1.univ-st-etienne.fr/api/gerbil -d query='"+q+"'&leng=en&kb=dbpedia";
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String jsonText = "";
        
        
        
        while(scanner.hasNext())
        {
            jsonText += scanner.next();
        }
        jsonText = jsonText.replaceAll("\\\\n", " ").replaceAll("\\\"", "'");
        //System.out.println(jsonText);
        JSONObject json = new JSONObject(jsonText);
        System.out.println(json.toString());
        
        process.destroy();

    }
    
}
