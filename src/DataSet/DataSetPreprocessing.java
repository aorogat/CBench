package DataSet;

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
        System.out.println("        |");
        System.out.println("        ++++> Enter the main project directory path; the path of the folder that contains the CBench.jar.");
        System.out.print("               Your answer is: ");
        currentDirectory = in.next();
        
        questions.clear();
        //Dataset
        if(benchmark == Benchmark.QALD_1 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
//            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "dbpedia", false));
//            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "dbpedia", false));
        }
        if(benchmark == Benchmark.QALD_2 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-test.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
//            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-test.xml", "QALD-2", "dbpedia", false));
//            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train.xml", "QALD-2", "dbpedia", false));
//            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
        }
        if(benchmark == Benchmark.QALD_3 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
//            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test.xml", "QALD-3", "dbpedia", true));
//            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "dbpedia", true));
//            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train.xml", "QALD-3", "dbpedia", true));
//            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "dbpedia", true));
        }
        if(benchmark == Benchmark.QALD_4 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_test_withanswers.xml", "QALD-4", "dbpedia", true));
        }
        if(benchmark == Benchmark.QALD_5 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald5(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
            questions.addAll(XMLParser.parseQald5(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
        }
        if(benchmark == Benchmark.QALD_6 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
//            questions.addAll(JSONParser.parseQald7File4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
            questions.addAll(JSONParser.parseQald7File6(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File6(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
        }
        if(benchmark == Benchmark.QALD_7 || benchmark == Benchmark.QALD_ALL)
        {
//            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File1(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
//            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File3(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File3(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
        }
        if(benchmark == Benchmark.QALD_8 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald8File(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald8File(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
//            questions.addAll(JSONParser.parseQald7File2(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
        }
        if(benchmark == Benchmark.QALD_9 || benchmark == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald9File(currentDirectory+"/data/DBpedia/SPARQL/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
        }
        if(benchmark == Benchmark.LC_QUAD)
        {
            questions.addAll(JSONParser.parseQuADFile(currentDirectory+"/data/DBpedia/SPARQL/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseQuADFile(currentDirectory+"/data/DBpedia/SPARQL/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));
            
            //questions.addAll(JSONParser.parseNo(currentDirectory+"/data/DBpedia/No_SPARQL/V1/train.json", "QUAD", "dbpedia"));
            //questions.addAll(JSONParser.parseNo(currentDirectory+"/data/DBpedia/No_SPARQL/V1/test.json", "QUAD", "dbpedia"));
            //questions.addAll(JSONParser.parseNo(currentDirectory+"/data/DBpedia/No_SPARQL/V1/valid.json", "QUAD", "dbpedia"));
        }      
        if(benchmark == Benchmark.GraphQuestions)
        {
            questions.addAll(JSONParser.parseGra(currentDirectory+"/data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.testing.json", "Freebase_Graph", "freebase"));
            questions.addAll(JSONParser.parseGra(currentDirectory+"/data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.training.json", "Freebase_Graph", "freebase"));
        }
        if(benchmark == Benchmark.WebQuestions)
        {
            questions.addAll(JSONParser.parseWeb(currentDirectory+"/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory+"/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.partial.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory+"/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.json", "Freebase_Web", "freebase"));
            questions.addAll(JSONParser.parseWeb(currentDirectory+"/data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.partial.json", "Freebase_Web", "freebase"));
        }
        
        //Every Question has some other questions with the same answer
        if(benchmark == Benchmark.ComQA)
        {
            questions.addAll(JSONParser.parseComQA(currentDirectory+"/data/Freebase/No_SPARQL/ComQA/comqa_train.json", "ComQA", "freebase"));
            questions.addAll(JSONParser.parseComQA(currentDirectory+"/data/Freebase/No_SPARQL/ComQA/comqa_dev.json", "ComQA", "freebase"));
            questions.addAll(JSONParser.parseFreeNo2(currentDirectory+"/data/Freebase/No_SPARQL/ComQA/comqa_test.json", "ComQA", "freebase"));
        }
        
        
        if(benchmark == Benchmark.ComplexQuestions)
        {
            questions.addAll(JSONParser.parseFreeNo(currentDirectory+"/data/Freebase/No_SPARQL/Complex_Questions/questions.json", "ComplexQuestions", "freebase"));
        }        
        
        //No Answers available in the Benchmarks. Only Lamda Calculas Expresions assoiated with questions.
        if(benchmark == Benchmark.Free917)
        {
            questions.addAll(JSONParser.parseFree917(currentDirectory+"/data/Freebase/No_SPARQL/Free917/free917.train.examples.canonicalized.json", "Free917", "freebase"));
            questions.addAll(JSONParser.parseFree917(currentDirectory+"/data/Freebase/No_SPARQL/Free917/free917.test.examples.canonicalized.json", "Free917", "freebase"));
        }
        
        if(benchmark == Benchmark.SimpleDBpediaQA)
        {
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory+"/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/train.json", "SimpleDBpediaQA", "dbpediaa"));
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory+"/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/test.json", "SimpleDBpediaQA", "dbpediaa"));
            questions.addAll(JSONParser.parseSimpleDB(currentDirectory+"/data/DBpedia/No_SPARQL/SimpleDBpediaQA-master/V1/valid.json", "SimpleDBpediaQA", "dbpediaa"));
        }
        
        
        if(benchmark == Benchmark.TempQuestions)
        {
            questions.addAll(JSONParser.parseTemp(currentDirectory+"/data/Freebase/No_SPARQL/TempQuestions/TempQuestions.json", "TempQuestions", "freebase"));
        }
        
        

        //Reverse List to remain most recent
        Collections.reverse(questions);
        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       +++++++++++++++++++++++++");
        System.out.println("++++++> Questions Preprocessing +");
        System.out.println("        +++++++++++++++++++++++++");
        System.out.println("              . Read data from benchmark files");
        System.out.println("              . Remove Dublicates");
        System.out.println("              . Exclude Queries that are not valid SPARQL 1.1");
        System.out.println(".");
        String format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
        System.out.format(format, "#", "Status", "Source", "File name", "Question", "File Answers");
        System.out.format(format, "======", "======", "======", "=========", "========", "==============");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.format(format,
                    (i + 1)+"",
                    "Remain",
                    questions.get(i).getQuestionSource()+"",
                    questions.get(i).getFilepath().substring(questions.get(i).getFilepath().lastIndexOf("/") + 1)+"",
                    questions.get(i).getQuestionString()+"",
                    questions.get(i).getAnswers().toString()+"");
            
            //Remove duplicates
            for (int j = 0; j < questions.size(); j++) {
                if (q.getQuestionString().equals(questions.get(j).getQuestionString()) && i != j) {

                    System.out.format(format,
                            "-"+"",
                            "Removed"+"",
                            questions.get(j).getQuestionSource()+"",
                            questions.get(j).getFilepath().substring(questions.get(j).getFilepath().lastIndexOf("/") + 1)+"",
                            questions.get(j).getQuestionString()+"",
                            questions.get(j).getAnswers().toString()+"");
                    
                    questions.remove(j);
                }
            }
            //System.out.println("");
            questionsWithoutDuplicates = questions;
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
        
        for (int i = 0; i < questions.size(); i++) {
            String queryString = questions.get(i).getQuestionQuery();
            try {
                if(queryString != null || !queryString.equals(""))
                {
                    Query query = QueryFactory.create(queryString);
                    queries.add(query);
                }
            } catch (Exception e) {
                queriesWithProblems.add(queryString +"\n\n"+ e.getMessage());
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
        getQueriesWithoutDuplicates(Benchmark.QALD_1);
    }
}
