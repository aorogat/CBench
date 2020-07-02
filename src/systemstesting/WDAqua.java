package systemstesting;

import DataSet.DataSetPreprocessing;
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
import org.apache.jena.query.Query;
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

    static ArrayList<Query> qs = DataSetPreprocessing.getQueriesWithoutDuplicates(9, false, false, false);
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

    static String KB_File = "QALD_9.xml";
    static String KB = "dbpedia";
    static String QUESTION_SOURCE = "QALD-9";

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

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException, Exception {

        for (Question question : questions) {
            //1- Determine CorectAnswerList
            corectAnswersList = question.getAnswers();
            for (String s : corectAnswersList)
                s.trim().replace('_', ' ').replaceAll("\n", "").replaceAll("\t", "").replace("http://dbpedia.org/resource/", "");
            //2- Determine systemAnswersList
            answer(question.getQuestionSource());

            //Loading indicator
            System.out.print(".");

            //3- List of Questions and their (R, P, F1)
            evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), corectAnswersList, systemAnswersList));

            //4- Global F-1 Score requied parameters
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

        //5- At the End, Print Results
        printResults();
        
        
//        WDAqua obj = new WDAqua();
//
//        String question = "";
//
//        ArrayList<Question> questionsList = new ArrayList<Question>();
//        try {
//            //Detemine XML File of Questions with tags
//            //"id","database","questionSource","filepath","questionString","keywords","questionQuery","answers"
//            File fXmlFile = new File(KB_File);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
//
//            doc.getDocumentElement().normalize();
//
//            NodeList allQuesions = doc.getElementsByTagName("question");
//
//            //for every question
//            for (int questionIndex = 0; questionIndex < allQuesions.getLength(); questionIndex++) {
//
//                Node currentQuestion = allQuesions.item(questionIndex);
//
//                if (currentQuestion.getNodeType() == Node.ELEMENT_NODE) {
//                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) currentQuestion;
//                    //Select questions with specific source e.g. QALD-9 or Freebase_web for Freebase file
//                    if (eElement.getElementsByTagName("questionSource").item(0).getTextContent().equals(QUESTION_SOURCE)) {
//                        question = (eElement.getElementsByTagName("questionString").item(0).getTextContent());
//
//                        //1- Determine CorectAnswerList
//                        NodeList ans = eElement.getElementsByTagName("answer");
//                        try {
//                            corectAnswersList = new ArrayList<>();
//                            for (int i = 0; i < ans.getLength(); i++) {
//                                corectAnswersList.add(ans.item(i).getTextContent().trim().replace('_', ' ').replaceAll("\n", "").replaceAll("\t", "").replace("http://dbpedia.org/resource/", ""));
//                            }
//                        } catch (Exception e) {
//                        }
//
//                        //2- Determine systemAnswersList
//                        answer(question);
//
//                        //Loading indicator
//                        System.out.print(".");
//
//                        //3- List of Questions and their (R, P, F1)
//                        evaluatedQuestions.add(new QuestionEval(question, corectAnswersList, systemAnswersList));
//
//                        //4- Global F-1 Score requied parameters
//                        if (corectAnswersList.size() > 0) {
//                            questionsWithCorrectAnswers++;
//                            if (systemAnswersList.size() > 0) {
//                                answered++;
//                                if (Performance.hasAnyCorrectAnswer(corectAnswersList, systemAnswersList)) {
//                                    patiallyAnswered++;
//                                }
//                                if (Performance.hasCompleteCorrectAnswer(corectAnswersList, systemAnswersList)) {
//                                    correctAnswered++;
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //5- At the End, Print Results
//        printResults();

    }

    static void printResults() {
        System.out.println("\n============== Questions ====================");
        for (QuestionEval q : evaluatedQuestions) {
            System.out.println(q.toString() + "\n");
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
    }

    static void answer(String question) throws IOException, JSONException {
        String ks, as, qs;
        String format = "%-50s%-70s%n";
        JSONObject json;

        String url = "https://qanswer-core1.univ-st-etienne.fr/api/gerbil";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "query=" + question;
        String urlParameters2 = "kb=" + KB;

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.writeBytes(urlParameters2);
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
            result = response.toString().replace("\\n", " ").replace("\\{", "{").replace("\\}", "}").replace("\\", "")
                    .replace("\"{", "{").replace("\"}", "}").replace("}\"", "}");
            json = new JSONObject(result);

            JSONArray questions = json.getJSONArray("questions");
            JSONObject q = (JSONObject) questions.getJSONObject(0);
            JSONObject qq = (JSONObject) q.getJSONObject("question");
            JSONObject answer = (JSONObject) qq.getJSONObject("answers");
            JSONObject results = (JSONObject) answer.getJSONObject("results");
            JSONArray bindings = results.getJSONArray("bindings");

            systemAnswersList = new ArrayList<>();
            //String ans = "[";
            for (int i = 0; i < bindings.length(); i++) {
                JSONObject binding = (JSONObject) bindings.getJSONObject(i);
                JSONObject o1 = (JSONObject) binding.getJSONObject("o1");
                String value = (String) o1.get("value");
                try {
                    if (value.startsWith("http")) {
                        org.jsoup.nodes.Document doc = Jsoup.connect(value).get();
                        Elements div = doc.select(".wikibase-title-label");
                        Element e = div.get(0);
                        value = e.text();
                        systemAnswersList.add(value.replace('_', ' '));
                        //ans += value + ", ";

                    }
                } catch (Exception e) {
                    value = "Cannot Access wikidata";
                }

            }
            //ans = ans.substring(0, ans.length() - 2) + "]";
            //System.out.println(question + "\nSystem: " + ans + "\n" + "Corect: " + correct);
        } catch (Exception e) {
            //System.out.println(question + "\n-------");
        }
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
