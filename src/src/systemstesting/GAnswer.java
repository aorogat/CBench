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

    static int correctAnswered;
    static int patiallyAnswered;
    static int falseAnswered;
    static int answered;
    static int questionsWithCorrectAnswers;
    static double precision;
    static double recall;
    static double f1;

    static ArrayList<String> systemAnswersList = new ArrayList<>();
    static ArrayList<String> corectAnswersList = new ArrayList<>();

    static String correct;
    
    static ArrayList<QuestionEval> evaluatedQuestions = new ArrayList<>();

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

//    public static void main2(String[] args) throws IOException {
//        answer("Show me all books in Asimov's Foundation series.");
//    }

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException {
        String format = "%-60s%-170s%n";
        System.out.format(format, "Question\t", "Answer\t");
        System.out.format(format, "======\t", "========\t");

        String question = "Who is the president of United World College of Costa Rica ?";
        //answer(question);

        //questions.xml
        ArrayList<Question> questionsList = new ArrayList<Question>();
        try {
            File fXmlFile = new File("QALD_9.xml");
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
                    question = eElement.getElementsByTagName("questionString").item(0).getTextContent();
                    
                    //Corect Answer List
                    NodeList ans = eElement.getElementsByTagName("answer");
                    correct = "[";
                    try {
                        corectAnswersList.clear();
                        for (int i = 0; i < ans.getLength(); i++) {
                            corectAnswersList.add(ans.item(i).getTextContent().trim().replaceAll("\n", "").replaceAll("\t", "").replace("http://dbpedia.org/resource/", ""));
                            correct += ans.item(i).getTextContent().trim().replaceAll("\n", "").replaceAll("\t", "").replace("http://dbpedia.org/resource/", "") + ", ";
                        }
                        correct = correct.substring(0, correct.length() - 2) + "]";
                    } catch (Exception e) {
                    }

                    answer(question);
                    
                    evaluatedQuestions.add(new QuestionEval(question, corectAnswersList, systemAnswersList));
                    
                    //F-1 Score
                    if (corectAnswersList.size() > 0) {
                        questionsWithCorrectAnswers++;
                        if (systemAnswersList.size() > 0) {
                            answered++;

                            if (Performance.hasAnyCorrectAnswer(corectAnswersList, systemAnswersList)) {
                                patiallyAnswered++;
                            }
                            if (Performance.hasCompleteCorrectAnswer(corectAnswersList, systemAnswersList)) {
                                correctAnswered++;
                            }
                        }
                    }
                }
            }

            //Partial Correct
            //F1 score depends on answering any correct answer
            System.out.println("Partially Correct");
            recall = patiallyAnswered / (double) questionsWithCorrectAnswers;
            precision = patiallyAnswered / (double) answered;

            
            System.out.println("Recall = " + recall);
            System.out.println("precision = " + precision);
            System.out.println("F1 = " + (2 * precision * recall) / ((double) precision + recall));
            
            //Completely Correct
            //F1 score depends on answering any correct answer
            System.out.println("Completely Correct");
            recall = correctAnswered / (double) questionsWithCorrectAnswers;
            precision = correctAnswered / (double) answered;

            
            System.out.println("Recall = " + recall);
            System.out.println("precision = " + precision);
            System.out.println("F1 = " + (2 * precision * recall) / ((double) precision + recall));


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    static void answer(String question) throws IOException, JSONException {
        String as = "", qs = question;
        String format = "%-60s%-170s%-60s%n";
        try {
            JSONObject json = readJsonFromUrl("http://ganswer.gstore-pku.com/api/qald.jsp?query=" + question.replace(" ", "%20"));
            //System.out.println(json.toString());
            JSONArray questions = json.getJSONArray("questions");
            for (Object q : questions) {
                systemAnswersList.clear();
                JSONObject qq = (JSONObject) q;
                JSONArray answers = qq.getJSONArray("answers");
                for (Object a : answers) {
                    JSONObject aa = (JSONObject) a;
                    JSONArray vars = aa.getJSONObject("head").getJSONArray("vars");
                    JSONArray bindings = aa.getJSONObject("results").getJSONArray("bindings");
                    for (int i = 0; i < bindings.length(); i++) {
                        try {
                            JSONObject bb = (JSONObject) bindings.get(i);
                            JSONObject vv = (JSONObject) bb.getJSONObject(vars.getString(0));

                            systemAnswersList.add(vv.getString("value").replace("http://dbpedia.org/resource/", ""));
                            as += vv.getString("value").replace("http://dbpedia.org/resource/", "") + ", ";

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.format(format, qs + "\t", "[" + as.substring(0, as.length() - 2) + "]\t", correct);

                }
            }
        } catch (Exception e) {
            System.out.format(format, question + "\t", "-------\t", correct);
        }
    }

}
