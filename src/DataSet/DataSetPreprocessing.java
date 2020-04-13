package DataSet;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.Parser;
import qa.parsers.XMLParser;

public class DataSetPreprocessing {
    public static ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<Question> questionsWithoutDuplicates = new ArrayList<>();
    public static ArrayList<Query> queries = new ArrayList<>();


    public static ArrayList getQueriesWithoutDuplicates() {
        
        //Dataset
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "dbpedia", false));
        
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-test.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-test.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge.xml", "QALD-2", "dbpedia", false));
        questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
        
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "dbpedia", true));
        
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4Hy("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_train.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4Hy("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_test_withanswers.xml", "QALD-4", "dbpedia", true));
        
        questions.addAll(XMLParser.parseQald5("./data/DBpedia/SPARQL/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
        questions.addAll(XMLParser.parseQald5("./data/DBpedia/SPARQL/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
        
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File4("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
        questions.addAll(JSONParser.parseQald7File6("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File6("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
        
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File1("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
        questions.addAll(JSONParser.parseQald7File3("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File4("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File3("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
        
        questions.addAll(JSONParser.parseQald8File("./data/DBpedia/SPARQL/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
        questions.addAll(JSONParser.parseQald8File("./data/DBpedia/SPARQL/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
//        
        questions.addAll(JSONParser.parseQald9File("./data/DBpedia/SPARQL/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
//        
        questions.addAll(JSONParser.parseQuADFile("./data/DBpedia/SPARQL/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
        questions.addAll(JSONParser.parseQuADFile("./data/DBpedia/SPARQL/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));
                
        questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/train.json", "QUAD", "dbpedia"));
        questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/test.json", "QUAD", "dbpedia"));
        questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/valid.json", "QUAD", "dbpedia"));
                
        questions.addAll(JSONParser.parseGra("./data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.testing.json", "Freebase_Graph", "Freebase"));
        questions.addAll(JSONParser.parseGra("./data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.training.json", "Freebase_Graph", "Freebase"));
        
        questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.json", "Freebase_Web", "Freebase"));
        questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.partial.json", "Freebase_Web", "Freebase"));
        questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.json", "Freebase_Web", "Freebase"));
        questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.partial.json", "Freebase_Web", "Freebase"));
        
        questions.addAll(JSONParser.parseFreeNo("./data/Freebase/No_SPARQL/Complex_Questions/questions.json", "Freebase_NO", "Freebase"));
        
        questions.addAll(JSONParser.parseFreeNo2("./data/Freebase/No_SPARQL/ComQA/comqa_test.json", "Freebase_NO", "Freebase"));
        questions.addAll(JSONParser.parseFreeNo3("./data/Freebase/No_SPARQL/ComQA/comqa_dev.json", "Freebase_NO", "Freebase"));
        questions.addAll(JSONParser.parseFreeNo3("./data/Freebase/No_SPARQL/ComQA/comqa_train.json", "Freebase_NO", "Freebase"));
        
        //Reverse List to remain most recent
        Collections.reverse(questions);
        //Remove duplicates
        String format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
        System.out.format(format, "#", "Status", "Source", "File name", "Question", "Answers");
        System.out.format(format, "======", "======", "======", "=========", "========", "========");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.format(format,
                    i + 1,
                    "Remain",
                    questions.get(i).getQuestionSource(),
                    questions.get(i).getFilepath().substring(questions.get(i).getFilepath().lastIndexOf("/") + 1),
                    questions.get(i).getQuestionString(),
                    questions.get(i).getAnswers().toString());
            for (int j = 0; j < questions.size(); j++) {
                if (q.getQuestionString().equals(questions.get(j).getQuestionString()) && i != j) {

                    System.out.format(format,
                            "-",
                            "Removed",
                            questions.get(j).getQuestionSource(),
                            questions.get(j).getFilepath().substring(questions.get(j).getFilepath().lastIndexOf("/") + 1),
                            questions.get(j).getQuestionString(),
                            questions.get(j).getAnswers().toString());
                    questions.remove(j);
                }
            }
            System.out.println("");
            questionsWithoutDuplicates = questions;
        }

        ArrayList<String> queriesWithProblems = new ArrayList<>();
        

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
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                queries.add(query);
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                queriesWithProblems.add(queryString);
                //System.out.println(queryString);
            }
        }
        System.out.println("Questions: " + questions.size());
        System.out.println("Queries with problems: " + queriesWithProblems.size());
        System.out.println("Queries: " + queries.size());
        return queries;
    }

    
    public static ArrayList getQueriesWithoutDuplicates(int qald, boolean quad, boolean graph, boolean web) {
        
        //Dataset
        if(qald == 1 || qald == 100)
        {
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-test.xml", "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-train.xml", "QALD-1", "dbpedia", false));
        }
        if(qald == 2 || qald == 100)
        {
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-test.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-test.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train-answers.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge.xml", "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1("./data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
        }
        if(qald == 3 || qald == 100)
        {
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test-answers.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train.xml", "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train-answers.xml", "QALD-3", "dbpedia", true));
        }
        if(qald == 4 || qald == 100)
        {
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_train.xml", "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy("./data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_test_withanswers.xml", "QALD-4", "dbpedia", true));
        }
        if(qald == 1 || qald == 100)
        {
            questions.addAll(XMLParser.parseQald5("./data/DBpedia/SPARQL/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
            questions.addAll(XMLParser.parseQald5("./data/DBpedia/SPARQL/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
        }
        if(qald == 6 || qald == 100)
        {
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
            questions.addAll(JSONParser.parseQald7File6("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File6("./data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
        }
        if(qald == 7 || qald == 100)
        {
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File1("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File3("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File3("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
        }
        if(qald == 8 || qald == 100)
        {
            questions.addAll(JSONParser.parseQald8File("./data/DBpedia/SPARQL/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald8File("./data/DBpedia/SPARQL/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2("./data/DBpedia/SPARQL/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
        }
        if(qald == 9 || qald == 100)
        {
            questions.addAll(JSONParser.parseQald9File("./data/DBpedia/SPARQL/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
        }
        if(quad)
        {
            questions.addAll(JSONParser.parseQuADFile("./data/DBpedia/SPARQL/LC-QuAD-data/test-data.json", "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseQuADFile("./data/DBpedia/SPARQL/LC-QuAD-data/train-data.json", "QUAD", "dbpedia"));
            
            questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/train.json", "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/test.json", "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseNo("./data/DBpedia/No_SPARQL/V1/valid.json", "QUAD", "dbpedia"));
        }      
        if(graph)
        {
            questions.addAll(JSONParser.parseGra("./data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.testing.json", "Freebase_Graph", "Freebase"));
            questions.addAll(JSONParser.parseGra("./data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.training.json", "Freebase_Graph", "Freebase"));
        }
        if(web)
        {
            questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.json", "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.partial.json", "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.json", "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb("./data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.partial.json", "Freebase_Web", "Freebase"));
        }
//        questions.addAll(JSONParser.parseFreeNo("./data/Freebase/No_SPARQL/Complex_Questions/questions.json", "Freebase_NO", "Freebase"));
//        
//        questions.addAll(JSONParser.parseFreeNo2("./data/Freebase/No_SPARQL/ComQA/comqa_test.json", "Freebase_NO", "Freebase"));
//        questions.addAll(JSONParser.parseFreeNo3("./data/Freebase/No_SPARQL/ComQA/comqa_dev.json", "Freebase_NO", "Freebase"));
//        questions.addAll(JSONParser.parseFreeNo3("./data/Freebase/No_SPARQL/ComQA/comqa_train.json", "Freebase_NO", "Freebase"));
        
        //Reverse List to remain most recent
        Collections.reverse(questions);
        //Remove duplicates
        String format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
        System.out.format(format, "#\t", "Status\t", "Source\t", "File name\t", "Question\t", "Answers\t");
        System.out.format(format, "======\t", "======\t", "======\t", "=========\t", "========\t", "========\t");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.format(format,
                    (i + 1)+"\t",
                    "Remain\t",
                    questions.get(i).getQuestionSource()+"\t",
                    questions.get(i).getFilepath().substring(questions.get(i).getFilepath().lastIndexOf("/") + 1)+"\t",
                    questions.get(i).getQuestionString()+"\t",
                    questions.get(i).getAnswers().toString()+"\t");
            for (int j = 0; j < questions.size(); j++) {
                if (q.getQuestionString().equals(questions.get(j).getQuestionString()) && i != j) {

                    System.out.format(format,
                            "-"+"\t",
                            "Removed"+"\t",
                            questions.get(j).getQuestionSource()+"\t",
                            questions.get(j).getFilepath().substring(questions.get(j).getFilepath().lastIndexOf("/") + 1)+"\t",
                            questions.get(j).getQuestionString()+"\t",
                            questions.get(j).getAnswers().toString()+"\t");
                    questions.remove(j);
                }
            }
            //System.out.println("");
            questionsWithoutDuplicates = questions;
        }

        ArrayList<String> queriesWithProblems = new ArrayList<>();
        

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
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                queries.add(query);
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                queriesWithProblems.add(queryString);
                //System.out.println(queryString);
            }
        }
        System.out.println("Questions: " + questions.size());
        System.out.println("Queries with problems: " + queriesWithProblems.size());
        System.out.println("Queries: " + queries.size());
        return queries;
    }
    
    public static void main(String[] args) {
        System.out.println("Queries: " + getQueriesWithoutDuplicates().size());
    }
}
