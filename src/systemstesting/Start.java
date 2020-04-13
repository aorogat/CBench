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
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import qa.dataStructures.Question;
import static systemstesting.GAnswer.answer;
import static systemstesting.GAnswer.readJsonFromUrl;

public class Start {

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException {
        String format = "%-40s%-70s%n";
        System.out.format(format, "Answer\t", "Question\t");
        System.out.format(format, "======\t", "========\t");

        String question = "Who is the president of United World College of Costa Rica ?";
        //answer(question);

        //1- Single Edge questions.xml
        ArrayList<Question> questionsList = new ArrayList<Question>();
        try {
            File fXmlFile = new File("1- Qald - 6-- Single Edge questions.xml");
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
        String url = "http://start.csail.mit.edu/justanswer.php?query=" + question;

        String as, qs;
        String format = "%-40s%-70s%n";
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            if(doc.text().contains("I don't know the answer.")
              ||doc.text().contains("don't know"))
            {
                System.out.format(format, "??????" + "\t", question + "\t");
            }
            else
            {
                Elements div = doc.select(".category_data.subfield.text");
                qs = question;
                as = div.get(0).ownText();
                System.out.format(format, as + "\t", qs + "\t");
            }

        } catch (Exception e) {
            System.out.format(format, "-------" + "\t", question + "\t");
        }
    }

}
