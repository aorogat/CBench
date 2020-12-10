package systemstesting;

import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import static systemstesting.Evaluator.readJsonFromUrl;

public class Evaluator_Qanary extends Evaluator {

    public static void main(String[] args) throws JSONException, Exception {
        Evaluator_Qanary evaluator = new Evaluator_Qanary();
        evaluate(evaluator);
    }

    @Override
    public ArrayList<String> answer(String question) throws IOException, JSONException {
        url = "http://localhost:8080/startquestionansweringwithtextquestion";
        try {
            systemAnswersList = new ArrayList<>();

            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

            //1- Get Graph ID from Qanary
            //add reuqest header
            httpClient.setRequestMethod("POST");
            //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "question=What is the real name of Carling?";

            // Send post request
            httpClient.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            int responseCode = httpClient.getResponseCode();

            StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()))) {

                String line;
                response = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

            }
            JSONObject json = new JSONObject(response.toString());
            String outGraph = json.getString("outGraph");

            //2- Get SPARQL Query from Stardog DB
            String sparqlURL = "http://admin:admin@localhost:5820/qanary/query?reasoning=false&query=PREFIX%20oa%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fopenannotation%2Fcore%2F%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20qa%3A%20%3Chttp%3A%2F%2Fwww.wdaqua.eu%2Fqa%23%3E%0ASELECT%20*%0AFROM%20%3C"
                    + outGraph.replaceAll(":", "%3A") + "%3E%0AWHERE%20%7B%0A%20%20%20%20%3Fs%20rdf%3Atype%20qa%3AAnnotationOfAnswerSPARQL.%0A%20%20%20%20%3Fs%20oa%3AhasBody%20%3FresultAsSparqlQuery.%0A%7D%0A";
            //System.out.println(sparqlURL);

            HttpClient httpClient2 = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(sparqlURL);
            httpGet.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials("admin", "admin"),
                    "UTF-8", false));

            HttpResponse httpResponse = httpClient2.execute(httpGet);
            HttpEntity responseEntity = httpResponse.getEntity();
            BufferedReader inb = new BufferedReader(
                    new InputStreamReader(responseEntity.getContent()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = inb.readLine()) != null) {
                content.append(inputLine);
            }
            //System.out.println(content);
            //Use method to convert XML string content to XML Document object
            Document doc = convertStringToXMLDocument(content.toString());

            //Verify XML document is build correctly
            String sparqlQuery = doc.getElementsByTagName("results").item(0).getNodeValue(); 
            if(sparqlQuery==null)
                sparqlQuery="";
            //System.out.println(sparqlQuery);

            
            
            //3- Get Answer from DBpedia
            systemAnswersList = CuratedAnswer.upToDateAnswerDBpedia(sparqlQuery, "dbpedia");;

            
        } catch (Exception eex) {
            eex.printStackTrace();
        }
        return systemAnswersList;
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
