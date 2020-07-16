package ShapeAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import ShallowAnalysis.ElementVistorImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;
import qa.dataStructures.Question;

public class QuestionByQuestionAnalysis {

    static ArrayList<Query> qs;
    static ArrayList<Question> questions;
    static int counter = 0;

    public QuestionByQuestionAnalysis() {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_1);
        questions = DataSetPreprocessing.questionsWithoutDuplicates;
    }

    public static void main(String[] args) {
        QuestionByQuestionAnalysis analysis = new QuestionByQuestionAnalysis();
        System.out.println("===========================================================");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            //1
            System.out.println(question.getQuestionString());
            System.out.println(question.getQuestionQuery().
                    replace("\n", "").replace("\r", ""));

            QueryProperties properties = QuestionQueryAnalysis(question);
            
            if(properties.keywords == null)
                System.out.println("Query is not supporrted by Apache Jena parser");
            else
            {
                System.out.println("("+properties.type + ", " + properties.keywords + ", T=" + properties.triples + ")");
            }
            System.out.println("");
        }
    }

    public static QueryProperties QuestionQueryAnalysis(Question question) {
        QueryProperties properties = new QueryProperties();
        try {
            Query q = QueryFactory.create(question.getQuestionQuery());

            //2
            try {

                if (QueryCategory.isCQ(question.getQuestionQuery())) {
                    properties.type = "CQ";
                } else if (QueryCategory.isCQ_F(question.getQuestionQuery())) {
                    properties.type = "CQ_F";
                } else if (QueryCategory.isCQ_OF(question.getQuestionQuery())) {
                    properties.type = "CQ_OF";
                } else {
                    properties.type = "NONE";
                }
            } catch (Exception e) {
                properties.type = "----";
            }

            //3 No of triples
            QuestionByQuestionAnalysis.counter = 0;
            try {
                Query query = QueryFactory.create(question.getQuestionQuery());
                ElementWalker.walk(query.getQueryPattern(),
                        // For each element...
                        new ElementVisitorBase() {
                            // ...when it's a block of triples...
                            @Override
                            public void visit(ElementPathBlock el) {
                                // ...go through all the triples...
                                Iterator<TriplePath> triples = el.patternElts();
                                while (triples.hasNext()) {
                                    TriplePath triple = triples.next();
                                    QuestionByQuestionAnalysis.counter++;
                                }
                            }

                            @Override
                            public void visit(ElementSubQuery el) {
                                Query q = el.getQuery();
                                Element e = q.getQueryPattern();

                                ElementVisitorBase visitor = new ElementVistorImpl();
                                ElementWalker.walk(e, visitor);

                            }

                        }
                );

                properties.triples = QuestionByQuestionAnalysis.counter;
            } catch (Exception e) {
                properties.triples = -1;
            }

            //4 Keywords
            properties.keywords = "";
            if (q.isSelectType()) {
                properties.keywords += "select ";
            } else if (q.isAskType()) {
                properties.keywords += "ask ";
            } else if (q.isDescribeType()) {
                properties.keywords += "describe ";
            } else if (q.isConstructType()) {
                properties.keywords += "construct ";
            }

            if (q.isDistinct()) {
                properties.keywords += "distinct ";
            }
            if (q.hasLimit()) {
                properties.keywords += "limit ";
            }
            if (q.hasOffset()) {
                properties.keywords += "offset ";
            }
            if (q.hasOrderBy()) {
                properties.keywords += "orderBy ";
            }

            if (q.toString().toLowerCase().
                    replace("\n", "").replace("\r", "").replaceAll(" ", "").contains("filter(")) {
                properties.keywords += "filter ";
            }
            if (q.toString().toLowerCase().contains(" .")
                    || q.toString().toLowerCase().contains(";")) {
                properties.keywords += "and ";
            }
            if (q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("union{")) {
                properties.keywords += "union ";
            }
            if (q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("optional{")) {
                properties.keywords += "opt ";
            }
            if (q.toString().toLowerCase().contains("graph")) {
                properties.keywords += "graph ";
            }
            if (q.toString().toLowerCase().contains("not exists")) {
                properties.keywords += "notExists ";
            } else if (q.toString().toLowerCase().contains("exists")) {
                properties.keywords += "exists ";
            }
            if (q.toString().toLowerCase().contains("minus")) {
                properties.keywords += "minus ";
            }

            if (q.hasGroupBy()) {
                properties.keywords += "groupBy ";
            }
            if (q.hasHaving()) {
                properties.keywords += "having ";
            }

            if (q.toString().toLowerCase().contains("count(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("min(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("max(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("sum(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("avg(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("groupconcat(")) {
                properties.keywords += "aggregators ";
            }
            else if (q.toString().toLowerCase().contains("sample(")) {
                properties.keywords += "aggregators ";
            }
            
        } catch (Exception ex) {}
        return properties;
    }

}
