package DataSet;

import ShapeAnalysis.QuestionByQuestionAnalysis;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.Parser;
import qa.parsers.XMLParser;

public class DataSetPreprocessing {

    public static ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<Question> questionsWithoutDuplicates = new ArrayList<>();
    public static ArrayList<Query> queries = new ArrayList<>();
    public static ArrayList<String> queriesWithProblems;
    public static String currentDirectory;

    public static ArrayList getQueriesWithoutDuplicates(int benchmark) {

        Scanner in = new Scanner(System.in);
        //System.out.println("        |");
        //System.out.println("        ++++> Enter the main project directory path; the path of the folder that contains the CBench.jar.");
        //System.out.print("               Your answer is: ");
        //currentDirectory = in.next();
        currentDirectory = System.getProperty("user.dir");
        questions.clear();
        if (benchmark == Benchmark.QALD_1 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "musicbrainz", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "musicbrainz", false));
        }
        if (benchmark == Benchmark.QALD_2 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-test.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-test.xml", "QALD-2", "musicbrainz", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train.xml", "QALD-2", "musicbrainz", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "musicbrainz", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
        }
        if (benchmark == Benchmark.QALD_3 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test.xml", "QALD-3", "musicbrainz", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "musicbrainz", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train.xml", "QALD-3", "musicbrainz", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "musicbrainz", true));
        }
        if (benchmark == Benchmark.QALD_4 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_test_withanswers.xml", "QALD-4", "dbpedia", true));
        }
        if (benchmark == Benchmark.QALD_5 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(XMLParser.parseQald5(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
            questions.addAll(XMLParser.parseQald5(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
        }
        if (benchmark == Benchmark.QALD_6 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
//            questions.addAll(JSONParser.parseQald7File4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
            questions.addAll(JSONParser.parseQald7File6(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File6(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
        }
        if (benchmark == Benchmark.QALD_7 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File1(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File3(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File3(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
        }
        if (benchmark == Benchmark.QALD_8 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQald8File(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald8File(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
        }
        if (benchmark == Benchmark.QALD_9 || benchmark == Benchmark.QALD_ALL || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQald9File(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
//            questions.addAll(JSONParser.parseQald9File(currentDirectory + "/data/DBpedia/SPARQL/QALD-master/9/data/qald-9-test-multilingual.json", "QALD-9", "dbpedia"));
        }
        if (benchmark == Benchmark.LC_QUAD || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQuADFile(currentDirectory + "/data/DBpedia/SPARQL/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
//            questions.addAll(JSONParser.parseQuADFile(currentDirectory + "/data/DBpedia/SPARQL/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));

//            questions.addAll(JSONParser.parseNo(currentDirectory + "/data/DBpedia/No_SPARQL/V1/train.json", "QUAD", "dbpedia"));
//            questions.addAll(JSONParser.parseNo(currentDirectory + "/data/DBpedia/No_SPARQL/V1/test.json", "QUAD", "dbpedia"));
//            questions.addAll(JSONParser.parseNo(currentDirectory + "/data/DBpedia/No_SPARQL/V1/valid.json", "QUAD", "dbpedia"));
        }

        if (benchmark == Benchmark.LC_QUAD_2 || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseQuAD2File(currentDirectory + "/data/DBpedia/SPARQL/LC-QuAD2-data/test.json", "QUAD2", "wikidata"));
//            questions.addAll(JSONParser.parseQuAD2File(currentDirectory + "/data/DBpedia/SPARQL/LC-QuAD2-data/train.json", "QUAD2", "wikidata"));
//            questions.addAll(JSONParser.parseQuAD2File(currentDirectory + "/data/DBpedia/SPARQL/LC-QuAD2-data/lcquad_2_0.json", "QUAD2", "wikidata"));

        }

        if (benchmark == Benchmark.GraphQuestions || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseGra(currentDirectory + "/data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.testing.json", "Freebase_Graph", "freebase"));
            questions.addAll(JSONParser.parseGra(currentDirectory + "/data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.training.json", "Freebase_Graph", "freebase"));
        }
        if (benchmark == Benchmark.WebQuestions || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseWeb(currentDirectory + "/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory + "/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.partial.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory + "/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory + "/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.partial.json", "Freebase_Web", "freebase"));
        }
        if (benchmark == Benchmark.WebQSP || benchmark == Benchmark.PropertiesDefined) {
            questions.addAll(JSONParser.parseWebQSP(currentDirectory + "/data/Freebase/SPARQL/WebQSP/data/WebQSP.test.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWebQSP(currentDirectory + "/data/Freebase/SPARQL/WebQSP/data/WebQSP.test.partial.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWebQSP(currentDirectory + "/data/Freebase/SPARQL/WebQSP/data/WebQSP.train.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWebQSP(currentDirectory + "/data/Freebase/SPARQL/WebQSP/data/WebQSP.train.partial.json", "Freebase_Web", "freebase"));
        }
        if (benchmark == Benchmark.ComQA) {
            questions.addAll(JSONParser.parseComQA(currentDirectory + "/data/Freebase/No_SPARQL/ComQA/comqa_train.json", "ComQA", "freebase"));
            questions.addAll(JSONParser.parseComQA(currentDirectory + "/data/Freebase/No_SPARQL/ComQA/comqa_dev.json", "ComQA", "freebase"));
            questions.addAll(JSONParser.parseFreeNo2(currentDirectory + "/data/Freebase/No_SPARQL/ComQA/comqa_test.json", "ComQA", "freebase"));
        }
        if (benchmark == Benchmark.ComplexQuestions) {
            questions.addAll(JSONParser.parseFreeNo(currentDirectory + "/data/Freebase/No_SPARQL/Complex_Questions/questions.json", "ComplexQuestions", "freebase"));
        }
        if (benchmark == Benchmark.Free917) {
            questions.addAll(JSONParser.parseFree917(currentDirectory + "/data/Freebase/No_SPARQL/Free917/free917.train.examples.canonicalized.json", "Free917", "freebase"));
            questions.addAll(JSONParser.parseFree917(currentDirectory + "/data/Freebase/No_SPARQL/Free917/free917.test.examples.canonicalized.json", "Free917", "freebase"));
        }
        if (benchmark == Benchmark.SimpleDBpediaQA) {
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory + "/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/train.json", "SimpleDBpediaQA", "dbpediaa"));
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory + "/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/test.json", "SimpleDBpediaQA", "dbpediaa"));
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory + "/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/valid.json", "SimpleDBpediaQA", "dbpediaa"));
        }
        if (benchmark == Benchmark.TempQuestions) {
            questions.addAll(JSONParser.parseTemp(currentDirectory + "/data/Freebase/No_SPARQL/TempQuestions/TempQuestions.json", "TempQuestions", "freebase"));
        }
        if (benchmark == Benchmark.UserDefined) {
            questions.addAll(JSONParser.parseQald9File(currentDirectory + "/data/userDefinied.json", "UserDefined", "dbpedia"));
        }
        
        if (benchmark == Benchmark.SMART_1) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_1_2000_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_2) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_2_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_3) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_3_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_4) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_4_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_5) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_5_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_6) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_6_1000_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_7) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_7_1000_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_8) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_8_1000_pruned.json", "SMARTBecnh", "dbpedia"));
        }
        if (benchmark == Benchmark.SMART_9) {
            questions.addAll(JSONParser.parseSmart(currentDirectory + "/data/Smart_9_1000_pruned.json", "SMARTBecnh", "dbpedia"));
        }

        //Reverse List to remain most recent
        Collections.reverse(questions);

        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       +++++++++++++++++++++++++");
        System.out.println("++++++> Questions Preprocessing +");
        System.out.println("        +++++++++++++++++++++++++");
        System.out.println("           CBench will");
        System.out.println("              . Read data from benchmark files");
        System.out.println("              . Remove Dublicates");
        System.out.println("              . Exclude Queries that are not valid SPARQL 1.1");
        System.out.println("");
        //System.out.print("             Press 's' then Enter to Start ...");
        //in.next();
        
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i).getQuestionString());
        }
        String format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
        System.out.format(format, "#", "Status", "Source", "File name", "Question", "File Answers");
        System.out.format(format, "======", "======", "======", "=========", "========", "==============");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.format(format,
                    (i + 1) + "",
                    "Remain",
                    questions.get(i).getQuestionSource() + "",
                    questions.get(i).getFilepath().substring(questions.get(i).getFilepath().lastIndexOf("/") + 1) + "",
                    questions.get(i).getQuestionString() + "",
                    questions.get(i).getAnswers().toString() + "");

            //Remove duplicates
            for (int j = 0; j < questions.size(); j++) {
                if (q.getQuestionString().equals(questions.get(j).getQuestionString()) && i != j) {

                    System.out.format(format,
                            "-" + "",
                            "Removed" + "",
                            questions.get(j).getQuestionSource() + "",
                            questions.get(j).getFilepath().substring(questions.get(j).getFilepath().lastIndexOf("/") + 1) + "",
                            questions.get(j).getQuestionString() + "",
                            questions.get(j).getAnswers().toString() + "");

                    questions.remove(j);
                }
            }

            //System.out.println("");
            questionsWithoutDuplicates = questions;
        }

        //Build Properties Defined benchmark
        if (benchmark == Benchmark.PropertiesDefined) {
            PropertiesDefinedBenchmark.getSatisfiedQuestions(questions);

            format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
            System.out.println("\n\n\n\n\n\nSelected Questions");
            System.out.format(format, "#T", "Type", "Source", "File name", "Question", "File Answers");
            System.out.format(format, "======", "======", "======", "=========", "========", "==============");
            for (Question q : questions) {
                System.out.format(format,
                        QuestionByQuestionAnalysis.QuestionQueryAnalysis(q).triples + "",
                        QuestionByQuestionAnalysis.QuestionQueryAnalysis(q).type + "",
                        q.getQuestionSource() + "",
                        q.getFilepath().substring(q.getFilepath().lastIndexOf("/") + 1) + "",
                        q.getQuestionString() + "",
                        q.getAnswers().toString() + "");
            }
        }

        queriesWithProblems = new ArrayList<>();

        //Extract the queries
        /*
         Some queries are not valid SPARQL 1.1
         to support non-standard SPARQL via Jena we can turn on extended syntax when we parse the query
         Query q = QueryFactory.create("Query", Syntax.syntaxARQ);
         Note that even with this turned on there are still lots of non-standard SPARQL syntactic 
         constructs that Virtuoso supports that ARQ will still reject.
         */
        org.apache.jena.query.ARQ.init();
        String wikidataPrefix = "# list of prefixes for import\n"
                + "PREFIX bd: <http://www.bigdata.com/rdf#>\n"
                + "PREFIX cc: <http://creativecommons.org/ns#>\n"
                + "PREFIX dct: <http://purl.org/dc/terms/>\n"
                + "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n"
                + "PREFIX ontolex: <http://www.w3.org/ns/lemon/ontolex#>\n"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                + "PREFIX p: <http://www.wikidata.org/prop/>\n"
                + "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n"
                + "PREFIX pqn: <http://www.wikidata.org/prop/qualifier/value-normalized/>\n"
                + "PREFIX pqv: <http://www.wikidata.org/prop/qualifier/value/>\n"
                + "PREFIX pr: <http://www.wikidata.org/prop/reference/>\n"
                + "PREFIX prn: <http://www.wikidata.org/prop/reference/value-normalized/>\n"
                + "PREFIX prov: <http://www.w3.org/ns/prov#>\n"
                + "PREFIX prv: <http://www.wikidata.org/prop/reference/value/>\n"
                + "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n"
                + "PREFIX psn: <http://www.wikidata.org/prop/statement/value-normalized/>\n"
                + "PREFIX psv: <http://www.wikidata.org/prop/statement/value/>\n"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX schema: <http://schema.org/>\n"
                + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
                + "PREFIX wd: <http://www.wikidata.org/entity/>\n"
                + "PREFIX wdata: <http://www.wikidata.org/wiki/Special:EntityData/>\n"
                + "PREFIX wdno: <http://www.wikidata.org/prop/novalue/>\n"
                + "PREFIX wdref: <http://www.wikidata.org/reference/>\n"
                + "PREFIX wds: <http://www.wikidata.org/entity/statement/>\n"
                + "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"
                + "PREFIX wdtn: <http://www.wikidata.org/prop/direct-normalized/>\n"
                + "PREFIX wdv: <http://www.wikidata.org/value/>\n"
                + "PREFIX wikibase: <http://wikiba.se/ontology#>\n"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
        for (int i = 0; i < questions.size(); i++) {
            String queryString = questions.get(i).getQuestionQuery();
            if(questions.get(i).getDatabase().equals("wikidata"))
                queryString = wikidataPrefix + queryString;
            try {
                if (queryString != null || !queryString.equals("")) {
                    Query query = QueryFactory.create(queryString);
                    queries.add(query);
                }
            } catch (Exception e) {
                queriesWithProblems.add(queryString + "\n\n" + e.getMessage());
            }
        }
        System.out.println("=========================================================================================");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       ++++++++++++++++++++++");
        System.out.println("++++++> Benchmark Statistics +");
        System.out.println("        ++++++++++++++++++++++");
        System.out.println("                Questions: " + questions.size());
        System.out.println("                Queries with problems: " + queriesWithProblems.size()
                + " [These queies are not valid SPARQL 1.1 and are not supported by Apache Jena parser. (Excluded from the experiment)]");
        System.out.println("                Valid Queries: " + queries.size());
        System.out.println("        +++++++++++++++++++++++++");
        return queries;
    }

    /*This method enable you to check the questions of a dataset and their quires and answers. 
     Some queries may not support SPARQL 1.1 and are excluded from the experiment.*/
    public static void main(String[] args) {
        //Select the required benchmark by changing the constant variable that represent the benchmark.
        //All Constants values are in the Benchmark class.
        getQueriesWithoutDuplicates(Benchmark.QALD_ALL);
        for (Question q : questionsWithoutDuplicates) {
            System.out.print(q.getDatabase() + "\t");
            System.out.print(q.getFilepath() + "\t");
            System.out.print(q.getQuestionString() + "\t");
            System.out.print(q.getQuestionQuery().replaceAll("\n", " ").replaceAll("\t", " ") + "\t");
            System.out.println("");
        }
    }
}
