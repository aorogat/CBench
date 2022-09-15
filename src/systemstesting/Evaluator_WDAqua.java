package systemstesting;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import qa.dataStructures.Question;

public class Evaluator_WDAqua {

    static ArrayList<Query> qs;
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

    static String KB;

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException, Exception {
        KB = "dbpedia";
//        performance(Benchmark.LC_QUAD, "LC_QUAD", true);
//        performance(Benchmark.QALD_1, "QALD_1",false);
//        performance(Benchmark.QALD_2, "QALD_2");
//        performance(Benchmark.QALD_3, "QALD_3");
//        performance(Benchmark.QALD_4, "QALD_4");
//        performance(Benchmark.QALD_5, "QALD_5");
//        performance(Benchmark.QALD_6, "QALD_6");
//        performance(Benchmark.QALD_7, "QALD_7");
//        performance(Benchmark.QALD_8, "QALD_8");
//        performance(Benchmark.QALD_9, "QALD_9", true);
//        performance(Benchmark.SMART_6, "SMART_6", false);
//        performance(Benchmark.SMART_7, "SMART_7", false);
//        performance(Benchmark.SMART_8, "SMART_8", false);
        performance(Benchmark.SMART_9, "SMART_9", false);
//        performance(Benchmark.TempQuestions, "TempQuestions", false);
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
        int qsWithAnswers = 0;
        for (Question question : questions) {
            corectAnswersList = new ArrayList<>();
            counter++;
            //1- Determine CorectAnswerList
            if (curated) {
                corectAnswersList = CuratedAnswer.upToDateAnswer(question.getQuestionQuery(), KB);
            } else {
                corectAnswersList = question.getAnswers();

                for (int i = 0; i < corectAnswersList.size(); i++) {
                    if (corectAnswersList.get(i) != null) {
                        corectAnswersList.set(i, corectAnswersList.get(i).trim().replace('_', ' ')
                                .replaceAll("\n", "").replaceAll("\t", "")
                                .replace("http://dbpedia.org/resource/", "")
                                .replace("https://en.wikipedia.org/wiki/", "")
                                .replace("http://www.wikidata.org/entity/", ""));
                    }

                }
                try {
                    if (corectAnswersList != null) {
                        if (corectAnswersList.size() > 0) {
                            qsWithAnswers++;
                        } else {
                            continue;
                        }

                        if (corectAnswersList.size() == 1 && corectAnswersList.get(0).equals("null")) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    corectAnswersList = new ArrayList<>();
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

            systemAnswersList = new ArrayList<String>(new HashSet<String>(systemAnswersList));
            corectAnswersList = new ArrayList<String>(new HashSet<String>(corectAnswersList));

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

        JSONObject json = null;

        //////////////////
        String command
                = "curl -X POST http://qanswer-core1.univ-st-etienne.fr/api/gerbil?"
                + "query=" + question
                + "&kb=" + KB;
        
        
  
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        Process process = processBuilder.start();
        try {
            process.waitFor(5, TimeUnit.SECONDS);  // let the process run for 5 seconds
            process.destroy();                     // tell the process to stop
            process.waitFor(10, TimeUnit.SECONDS); // give it a chance to stop
            process.destroyForcibly();             // tell the OS to kill the process
            process.waitFor();                     // the process is now dead
        } catch (InterruptedException ex) {
            Logger.getLogger(Evaluator_WDAqua.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputStream inputStream = process.getInputStream();
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        /////////////////

        result = result.replace("\\n", " ").replace("\\{", "{").replace("\\}", "}").replace("\\", "")
                .replace("\"{", "{").replace("\"}", "}").replace("}\"", "}");
//        System.out.println(result);

        try {
            systemAnswersList = new ArrayList<>();
            json = new JSONObject(result);
            JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("results").getJSONArray("bindings");

            String varName = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("head").getJSONArray("vars").getString(0);

//            System.out.println(varName);
            for (int i = 0; i < bindings.length(); i++) {
                JSONObject binding = (JSONObject) bindings.getJSONObject(i);
                JSONObject o1 = (JSONObject) binding.getJSONObject(varName);
                String value = (String) o1.get("value");
                //For wikidata with Freebase benchmarks
//                if (value.startsWith("http")) {
//                    org.jsoup.nodes.Document doc = Jsoup.connect(value).get();
//                    Elements div = doc.select(".wikibase-title-label");
//                    Element e = div.get(0);
//                    value = e.text();
//                    systemAnswersList.add(value.replace('_', ' '));
//                }
                systemAnswersList.add(value.replace('_', ' ')
                        .replace("http://dbpedia.org/resource/", "")
                        .replace("https://en.wikipedia.org/wiki/", "")
                        .replace("http://www.wikidata.org/entity/", ""));
            }
        } catch (Exception e) {
            try {
                boolean b = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONObject("question").getJSONObject("answers")
                        .getBoolean("boolean");
                systemAnswersList = new ArrayList<>();
                if (b) {
                    systemAnswersList.add("Yes");
                } else {
                    systemAnswersList.add("No");
                }
            } catch (Exception ex) {
//                ex.printStackTrace();
            }
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
