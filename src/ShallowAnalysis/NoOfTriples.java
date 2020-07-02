package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import Graph.Edge;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

/**
 *
 * @author Abdelghny Orogat
 */
public final class NoOfTriples {

    ArrayList<String> results = new ArrayList<>();
    ArrayList<Query> qs;
    String s;
    public static int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0,
            eight = 0, nine = 0, ten = 0, elevenOrMore = 0, total = 0, counter = 0;

    public NoOfTriples() {
        s = "bench\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11+";
        results.add(s);

        //QALD 1 to 9
        for (int i = 1; i < 10; i++) {
            zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven = 0;
            eight = 0; nine = 0; ten = 0; elevenOrMore = 0; total = 0;
            s = "QALD " + i;
            qs = DataSetPreprocessing.getQueriesWithoutDuplicates(i);
            triplesAnalysis();
            results.add(s);
            qs.clear();
        }
        
        zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven = 0;
            eight = 0; nine = 0; ten = 0; elevenOrMore = 0; total = 0;
        s = "QALD ALL";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_ALL);
        triplesAnalysis();
        results.add(s);
        
        qs.clear();
        
        zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven = 0;
            eight = 0; nine = 0; ten = 0; elevenOrMore = 0; total = 0;
        s = "LC-QUAD";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.LC_QUAD);
        triplesAnalysis();
        results.add(s);
        
        qs.clear();
        
        zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven = 0;
            eight = 0; nine = 0; ten = 0; elevenOrMore = 0; total = 0;
        s = "Graph";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.GraphQuestions);
        triplesAnalysis();
        results.add(s);
        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("=============  Graph "+total+"======================");
        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("====================================================");
        
        qs.clear();
        
        zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven = 0;
            eight = 0; nine = 0; ten = 0; elevenOrMore = 0; total = 0;
        s = "Web";
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.WebQuestions);
        triplesAnalysis();
        results.add(s);
        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("=============  Web "+total+"======================");
        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("====================================================");

        
        for (String result : results) {
            System.out.println(result);
        }
        
    }

    public static void main(String[] args) {
        NoOfTriples k = new NoOfTriples();
    }

    public void triplesAnalysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        for (Query q : qs) {

            try {
                NoOfTriples.counter = 0;
                NoOfTriples.total++;
                Element e = q.getQueryPattern();
                
                System.out.println("===============================");
                System.out.println(e.getClass().toString());
                System.out.println("===============================");
                System.out.println(e.toString());
                ElementVisitorBase visitor = new ElementVistorImpl();
                ElementWalker.walk(e, visitor);
                System.out.println("Counter    " + NoOfTriples.counter+"        "+e.getClass().toString());
                System.out.println("===============================");
                        
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        s += "\t"+ (double) NoOfTriples.zero / NoOfTriples.total * 100 + "\t";
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

}
