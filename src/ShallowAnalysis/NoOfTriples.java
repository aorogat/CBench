package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;
import qa.dataStructures.Question;

public final class NoOfTriples {

    ArrayList<String> results = new ArrayList<>();
    ArrayList<Query> qs;
    Set<String> uniqueQueries = new HashSet<>();
    String s;
    public static int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0,
            eight = 0, nine = 0, ten = 0, elevenOrMore = 0, total = 0, counter = 0;

    public NoOfTriples() {
        s = "bench\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11+";
        results.add(s);

        //QALD 1 to 9
        for (int i = 1; i < 10; i++) {
            zero = 0;
            one = 0;
            two = 0;
            three = 0;
            four = 0;
            five = 0;
            six = 0;
            seven = 0;
            eight = 0;
            nine = 0;
            ten = 0;
            elevenOrMore = 0;
            total = 0;
            s = "QALD " + i;
            qs = DataSetPreprocessing.getQueriesWithoutDuplicates(i);
            triplesAnalysis();
            results.add(s);
            qs.clear();
        }

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        s = "QALD ALL";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_ALL);
        triplesAnalysis();
        results.add(s);

        qs.clear();

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        s = "LC-QUAD";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.LC_QUAD);
        triplesAnalysis();
        results.add(s);

        qs.clear();

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        s = "Graph";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.GraphQuestions);
        triplesAnalysis();
        results.add(s);

        qs.clear();

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        s = "Web";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.WebQuestions);
        triplesAnalysis();
        results.add(s);

        for (String result : results) {
            System.out.println(result);
        }

    }

    public NoOfTriples(ArrayList<Query> queries) {
        qs = queries;
        s = "bench\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11+";
        results.add(s);

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        s = "Count";
        triplesAnalysis();
        results.add(s);
    }

    public static void main(String[] args) {
//        NoOfTriples k = new NoOfTriples(DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.GraphQuestions));
//        k.triplesTokensAssociation();
//        ArrayList<String> queriesString = new ArrayList<String>();
//        for (Query q : k.qs) {
//            k.uniqueQueries.add(q.toString());
//        }
//        System.out.println("All Quieries = " + k.qs.size());
//        System.out.println("Unique Quieries = " + k.uniqueQueries.size());
        
        NoOfTriples k = new NoOfTriples();
    }

    public void triplesAnalysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        for (Query q : qs) {

            try {
                NoOfTriples.counter = 0;
                NoOfTriples.total++;
                Element e = q.getQueryPattern();

                ElementVisitorBase visitor = new ElementVistorImpl();
                ElementWalker.walk(e, visitor);

                switch (NoOfTriples.counter) {
                    case 0:
                        NoOfTriples.zero++;
                        break;
                    case 1:
                        NoOfTriples.one++;
                        break;
                    case 2:
                        NoOfTriples.two++;
                        break;
                    case 3:
                        NoOfTriples.three++;
                        break;
                    case 4:
                        NoOfTriples.four++;
                        break;
                    case 5:
                        NoOfTriples.five++;
                        break;
                    case 6:
                        NoOfTriples.six++;
                        break;
                    case 7:
                        NoOfTriples.seven++;
                        break;
                    case 8:
                        NoOfTriples.eight++;
                        break;
                    case 9:
                        NoOfTriples.nine++;
                        break;
                    case 10:
                        NoOfTriples.ten++;
                        break;
                    default:
                        NoOfTriples.elevenOrMore++;
                        break;

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        s += "\t" + (double) NoOfTriples.zero / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.one / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.two / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.three / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.four / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.five / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.six / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.seven / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.eight / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.nine / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.ten / NoOfTriples.total * 100 + "\t";
        s += (double) NoOfTriples.elevenOrMore / NoOfTriples.total * 100 + "\t";

    }

    
    public void triplesTokensAssociation()
    {
        
        for (Question question : DataSetPreprocessing.questionsWithoutDuplicates) {
            try{
            StringTokenizer tokenizer = new StringTokenizer(question.getQuestionString());
            Query query = QueryFactory.create(question.getQuestionQuery());
            System.out.print(tokenizer.countTokens()+"\t");
            System.out.print(countTriples(query)+"\t");
            System.out.print(question.getQuestionString()+"\t");
            System.out.println(question.getQuestionQuery().replaceAll("\n", " "));
            }catch(Exception e){}
        }
    }
    
    public int countTriples(Query q) {
        NoOfTriples.counter = 0;
        Element e = q.getQueryPattern();

        ElementVisitorBase visitor = new ElementVistorImpl();
        ElementWalker.walk(e, visitor);

        return NoOfTriples.counter;
    }

}
