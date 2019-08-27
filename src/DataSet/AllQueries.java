package DataSet;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.XMLParser;

public class AllQueries {

    public static ArrayList getAllQueries() {
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Query> queries = new ArrayList<>();
        
        //Dataset
        questions = XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-test.xml", "QALD-1", "dbpedia", false);
        questions.addAll(XMLParser.parseQald1("./data/original/QALD-master/1/data/dbpedia-train.xml", "QALD-1", "dbpedia", false));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/1/data/dbpedia-train-CDATA.xml", "QALD-1", "dbpedia", false));
        questions.addAll(XMLParser.parseQald1("./data/original/QALD-master/2/data/dbpedia-train-answers.xml", "QALD-2", "dbpedia", false));
        questions.addAll(XMLParser.parseQald1("./data/original/QALD-master/2/data/participants-challenge-answers.xml", "QALD-2", "dbpedia", false));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/3/data/dbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-test-answers.xml", "QALD-3", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/3/data/esdbpedia-train-answers.xml", "QALD-3", "dbpedia", true));
      questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test.xml", "QALD-4", "dbpedia", true));
      questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald4("./data/original/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml", "QALD-4", "dbpedia", true));
        questions.addAll(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_train.xml", "QALD-5", "dbpedia"));
        questions.addAll(XMLParser.parseQald5("./data/original/QALD-master/5/data/qald-5_test.xml", "QALD-5", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-test-hybrid.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-test-multilingual.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File4("./data/original/QALD-master/6/data/qald-6-train-datacube.json", "QALD-6", "linkedspending"));
        questions.addAll(JSONParser.parseQald7File6("./data/original/QALD-master/6/data/qald-6-train-hybrid.json", "QALD-6", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-en-wikidata.json", "QALD-7", "wikidata"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File1("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-en-wikidata.json", "QALD-7", "wikidata"));
        questions.addAll(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-hybrid-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File4("./data/original/QALD-master/7/data/qald-7-train-hybrid.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File3("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-multilingual.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json", "QALD-7", "dbpedia"));
        questions.addAll(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-test-multilingual.json", "QALD-8", "dbpedia"));
        questions.addAll(JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-train-multilingual.json", "QALD-8", "dbpedia"));
        questions.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/8/data/wikidata-train-7.json", "QALD-8", "wikidata"));
        questions.addAll(JSONParser.parseQald9File("./data/original/QALD-master/9/data/qald-9-train-multilingual.json", "QALD-9", "dbpedia"));
        
        //Extract the queries
        for (int i = 0; i < questions.size(); i++) {

            try {
                String queryString = questions.get(i).getQuestionQuery();
                Query query = QueryFactory.create(queryString);
                queries.add(query);
            } catch (Exception e) {
            }
        }
        return queries;
    }
}
