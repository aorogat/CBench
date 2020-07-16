/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
public class GAnswerNew {

    static ArrayList<Query> qs;
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

    static String KB;

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException, Exception {
        KB = "dbpedia";

//        performance(Benchmark.QALD_1, "QALD_1", false);
//        performance(Benchmark.QALD_2, "QALD_2", false);
//        performance(Benchmark.QALD_3, "QALD_3", false);
//        performance(Benchmark.QALD_4, "QALD_4", false);
//        performance(Benchmark.QALD_5, "QALD_5", false);
//        performance(Benchmark.QALD_6, "QALD_6", false);
//        performance(Benchmark.QALD_7, "QALD_7", false);
        performance(Benchmark.QALD_8, "QALD_8", false);
//        performance(Benchmark.QALD_9, "QALD_9", false);

//        performance(Benchmark.LC_QUAD, "LC_QUAD", true);
    }

    public static void performance(int benchmark, String benchmarkName, boolean curated) throws IOException {
        systemAnswersList = new ArrayList<>();
        corectAnswersList = new ArrayList<>();
        evaluatedQuestions = new ArrayList<>();

        //KB = "freebase";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
        BenchmarkEval evaluatedBenchmark = new BenchmarkEval(benchmarkName);
        evaluatedBenchmark.allQuestions = questions.size();

        int counter = 0;
        for (Question question : questions) {
            counter++;
            //1- Determine CorectAnswerList
            if (curated) {
                corectAnswersList = CuratedAnswer.upToDateAnswer(question.getQuestionQuery(), KB);
            } else {
                corectAnswersList = question.getAnswers();

                for (int i = 0; i < corectAnswersList.size(); i++) {
                    if (corectAnswersList.get(i) != null) {
                        corectAnswersList.set(i, corectAnswersList.get(i).trim().replace('_', ' ').replaceAll("\n", "").replaceAll("\t", "")
                                .replace("http://dbpedia.org/resource/", "")
                                .replace("https://en.wikipedia.org/wiki/", "")
                                .replace("http://www.wikidata.org/entity/", "")
                                .replaceAll("True", "Yes")
                                .replaceAll("False", "No"));
                    }

                }
            }
            //2- Determine systemAnswersList
            //for (int i = 0; i < 3; i++) {
            String q = question.getQuestionString().replace('?', ' ').replace(" ", "%20");
            System.out.println();
            System.out.println(question.getDatabase() + "\t" + counter);
            System.out.println(q);
            System.out.println("Correct Answer = " + corectAnswersList.toString());

            //////////////////////////////////////////////////////////////////////////////////////////////////////////
            KB = question.getDatabase(); /////Use for muultiple endpoints////////////////////dbpedia, freebase, wikidata
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
            answer(q);
            //  if(systemAnswersList.size() == 0)
            //    continue;
            //else
            //  break;
            //  }
            //Loading indicator
            System.out.println("System Answer = " + systemAnswersList.toString());

            //3- List of Questions and their (R, P, F1)
            evaluatedBenchmark.evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), question, corectAnswersList, systemAnswersList));

        }

        //4- Calculate parameters
        evaluatedBenchmark.calculateParameters();

        //5- At the End, Print Results
        evaluatedBenchmark.printScores();

        System.out.println("\n\n\n\n\n\n\n");
    }

    static void answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();
            JSONObject json = readJsonFromUrl("http://ganswer.gstore-pku.com/api/qald.jsp?query=" + question.replace(" ", "%20"));
            System.out.println(json.toString());
            try {

                JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONArray("answers").getJSONObject(0)
                        .getJSONObject("results").getJSONArray("bindings");

                String varName = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONArray("answers").getJSONObject(0)
                        .getJSONObject("head").getJSONArray("vars").getString(0);

                System.out.println(varName);

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
