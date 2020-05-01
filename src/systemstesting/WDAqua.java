package systemstesting;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import qa.dataStructures.Question;

public class WDAqua {

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

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException, Exception {
        WDAqua obj = new WDAqua();
        //obj.sendPost();
        
        String format = "%-50s%-70s%n";
        System.out.format(format,"Answer\t", "Question\t");
        System.out.format(format, "======\t", "========\t");
        
        String question = "Who is the president of United World College of Costa Rica ?";
        //answer(question);

        //1- Single Edge questions.xml
        ArrayList<Question> questionsList = new ArrayList<Question>();
        try {
            File fXmlFile = new File("8- Qald - 9-- Flower questions.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("question");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                    //"id","database","questionSource","filepath","questionString","keywords","questionQuery","answers"
                    //if (eElement.getElementsByTagName("database").item(0).getTextContent().equals("dbpedia")) {
                        question = (eElement.getElementsByTagName("questionString").item(0).getTextContent());
                        answer(question);
                   // }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    static void answer(String question) throws IOException, JSONException {
        String ks,as,qs;
        String format = "%-50s%-70s%n";
        JSONObject json;
        
        String url = "https://qanswer-core1.univ-st-etienne.fr/api/gerbil";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "query=" + question;

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        String result = "";
        int responseCode = httpClient.getResponseCode();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            result = response.toString().replace("\\n", " ").replace("\\{","{").replace("\\}","}").replace("\\","")
                    .replace("\"{","{").replace("\"}","}").replace("}\"","}");
            json = new JSONObject(result);
            //print result
            //System.out.println(json.toString());

        
        JSONArray questions = json.getJSONArray("questions");
        JSONObject q = (JSONObject) questions.getJSONObject(0);
        JSONObject qq =  (JSONObject) q.getJSONObject("question");
        JSONObject answer =  (JSONObject) qq.getJSONObject("answers");
        JSONObject results =  (JSONObject) answer.getJSONObject("results");
        JSONArray bindings = results.getJSONArray("bindings");
        JSONObject binding = (JSONObject) bindings.getJSONObject(0);
        JSONObject o1 =  (JSONObject) binding.getJSONObject("o1");
        String value = (String) o1.get("value");
        try{
        if(value.startsWith("http"))
        {
            org.jsoup.nodes.Document doc = Jsoup.connect(value).get();
            Elements div = doc.select(".wikibase-title-label");
            Element e = div.get(0);
            value = e.text();
        
        }
        }catch(Exception e)
        {
            value = "Cannot Access wikidata";
        }
        
        System.out.format(format, value+"\t", question+"\t");

        }
        catch(Exception e)
        {
            System.out.format(format, "-------"+"\t",  question+"\t");
        }
    }
    
    

/*
    private void sendPost() throws Exception {
        String url = "https://qanswer-core1.univ-st-etienne.fr/api/gerbil";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "query=Who is the wife of Barack Obama";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        String result = "";
        int responseCode = httpClient.getResponseCode();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            result = response.toString().replace("\\n", " ").replace("\\{","{").replace("\\}","}").replace("\\","")
                    .replace("\"{","{").replace("\"}","}").replace("}\"","}");
            JSONObject json = new JSONObject(result);
            //print result
            System.out.println(json.toString());

        }

    }
*/
}
