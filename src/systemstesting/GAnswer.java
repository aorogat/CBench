/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import java.io.BufferedReader;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import qa.dataStructures.Question;
import static qa.parsers.XMLParser.getCharacterDataFromElement;


//Just working on DBpedia
public class GAnswer {

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
    public static void main(String[] args) throws IOException, JSONException {
        String format = "%-20s%-20s%-70s%n";
        System.out.format(format, "Keywords\t", "Answer\t", "Question\t");
        System.out.format(format, "========\t", "======\t", "========\t");
        
        String question = "Who is the president of United World College of Costa Rica ?";
        //answer(question);

        //1- Single Edge questions.xml
        ArrayList<Question> questionsList = new ArrayList<Question>();
        try {
            File fXmlFile = new File("8- Web-- Flower questions.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("question");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
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
        String format = "%-20s%-20s%-70s%n";
        try{
        JSONObject json = readJsonFromUrl("http://ganswer.gstore-pku.com/api/qald.jsp?query=" + question.replace(" ", "%20"));
        //System.out.println(json.toString());
        JSONArray questions = json.getJSONArray("questions");
        for (Object q : questions) {
            JSONObject qq = (JSONObject) q;
            JSONArray answers = qq.getJSONArray("answers");
            for (Object a : answers) {
                JSONObject aa = (JSONObject) a;
                JSONArray vars = aa.getJSONObject("head").getJSONArray("vars");
                JSONArray bindings = aa.getJSONObject("results").getJSONArray("bindings");
                for (int i = 0; i < vars.length(); i++) {
                    JSONObject bb = (JSONObject) bindings.get(i);
                    ks=vars.get(i)+"";
                    JSONObject vv = (JSONObject) bb.getJSONObject(vars.getString(i));
                    as = vv.getString("value").replace("http://dbpedia.org/resource/", "");
                    qs = question;
                    System.out.format(format, ks+"\t", as+"\t", qs+"\t");
                }

            }
        }
        }
        catch(Exception e)
        {
            System.out.format(format, "-------"+"\t", "-------"+"\t", question+"\t");
        }
    }

}
