/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jdk.internal.org.xml.sax.SAXException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author aorogat
 */
public class TestGet {

    public static void main(String[] args) throws MalformedURLException, IOException, SAXException {
        String outGraph = "urn:graph:931a5ea4-7e35-443a-b768-7fdd5a0c1e06";
        String sparqlURL = "http://admin:admin@localhost:5820/qanary/query?reasoning=false&query=PREFIX%20oa%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fopenannotation%2Fcore%2F%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20qa%3A%20%3Chttp%3A%2F%2Fwww.wdaqua.eu%2Fqa%23%3E%0ASELECT%20*%0AFROM%20%3C"
                + outGraph.replaceAll(":", "%3A") + "%3E%0AWHERE%20%7B%0A%20%20%20%20%3Fs%20rdf%3Atype%20qa%3AAnnotationOfAnswerSPARQL.%0A%20%20%20%20%3Fs%20oa%3AhasBody%20%3FresultAsSparqlQuery.%0A%7D%0A";
        System.out.println(sparqlURL);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(sparqlURL);
        httpGet.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials("admin", "admin"),
                "UTF-8", false));

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity responseEntity = httpResponse.getEntity();
        BufferedReader inb = new BufferedReader(
                    new InputStreamReader(responseEntity.getContent()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = inb.readLine()) != null) {
                content.append(inputLine);
            }
        System.out.println(content);
        //Use method to convert XML string content to XML Document object
        Document doc = convertStringToXMLDocument( content.toString() );
         
        //Verify XML document is build correctly
        System.out.println(doc.getElementsByTagName("results").item(0).getNodeValue());
    }
    
    private static Document convertStringToXMLDocument(String xmlString) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

}
