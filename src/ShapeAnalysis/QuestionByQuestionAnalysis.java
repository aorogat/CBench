package ShapeAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import ShallowAnalysis.NoOfTriples;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
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
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            //1
            System.out.print(question.getQuestionString() + "\t");
            //2
            try {

                if (QueryCategory.isCQ(question.getQuestionQuery())) {
                    System.out.print("CQ" + "\t");
                } else if (QueryCategory.isCQ_F(question.getQuestionQuery())) {
                    System.out.print("CQ_F" + "\t");
                } else if (QueryCategory.isCQ_OF(question.getQuestionQuery())) {
                    System.out.print("CQ_OF" + "\t");
                } else {
                    System.out.print("NONE" + "\t");
                }
            } catch (Exception e) {
                System.out.print("----" + "\t");
            }
            //3 No of triples
            QuestionByQuestionAnalysis.counter = 0;
            try {
                Query query = QueryFactory.create(question.getQuestionQuery());
                ElementWalker.walk(query.getQueryPattern(),
                        // For each element...
                        new ElementVisitorBase() {
                            // ...when it's a block of triples...
                            public void visit(ElementPathBlock el) {
                                // ...go through all the triples...
                                Iterator<TriplePath> triples = el.patternElts();
                                while (triples.hasNext()) {
                                    TriplePath triple = triples.next();
                                    QuestionByQuestionAnalysis.counter++;
                                }
                            }
                        }
                );

                System.out.print(QuestionByQuestionAnalysis.counter + "\t");
            } catch (Exception e) {
                System.out.print("----" + "\t");
            }
            
            //4 Keywords
            String keywords = "";
            if(question.getQuestionQuery().toLowerCase().contains("select")) keywords += "select ";
            else if(question.getQuestionQuery().toLowerCase().contains("ask"))  keywords += "ask "; 
            else if(question.getQuestionQuery().toLowerCase().contains("describe"))  keywords += "describe "; 
            else if(question.getQuestionQuery().toLowerCase().contains("construct"))  keywords += "construct ";
            
            if(question.getQuestionQuery().toLowerCase().contains("distinct"))  keywords += "distinct "; 
            if(question.getQuestionQuery().toLowerCase().contains("limit"))  keywords += "limit ";
            if(question.getQuestionQuery().toLowerCase().contains("offset"))  keywords += "offset ";
            if(question.getQuestionQuery().toLowerCase().contains("orderBy"))  keywords += "orderBy ";
            
            if(question.getQuestionQuery().toLowerCase().contains("filter"))  keywords += "filter ";
            if(question.getQuestionQuery().toLowerCase().contains(" ."))  keywords += "and ";
            if(question.getQuestionQuery().toLowerCase().contains("union"))  keywords += "union ";
            if(question.getQuestionQuery().toLowerCase().contains("opt"))  keywords += "opt ";
            if(question.getQuestionQuery().toLowerCase().contains("graph"))  keywords += "graph ";
            if(question.getQuestionQuery().toLowerCase().contains("not exists"))  keywords += "notExists ";
            else if(question.getQuestionQuery().toLowerCase().contains("exists"))  keywords += "exists ";
            if(question.getQuestionQuery().toLowerCase().contains("minus"))  keywords += "minus ";
            
            if(question.getQuestionQuery().toLowerCase().contains("aggregators"))  keywords += "aggregators ";
            if(question.getQuestionQuery().toLowerCase().contains("groupBy"))  keywords += "groupBy ";
            if(question.getQuestionQuery().toLowerCase().contains("having"))  keywords += "having ";

            System.out.print(keywords + "\t");
            System.out.println("");
        }
    }

}
