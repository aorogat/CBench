package ShallowAnalysis;

import DataSet.DataSetPreprocessing;
import Graph.Edge;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

/**
 *
 * @author Abdelghny Orogat
 */
public class NoOfTriples {

    ArrayList<Query> qs;
    public static int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0,
                eight = 0, nine = 0, ten = 0, elevenOrMore = 0, total = 0;

    public NoOfTriples() {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates();
    }

    public static void main(String[] args) {
        NoOfTriples k = new NoOfTriples();
        k.triplesAnalysis();
    }

    public void triplesAnalysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        

        for (Query q : qs) {
            
            try{
            ElementWalker.walk(q.getQueryPattern(),
                    // For each element...
                    new ElementVisitorBase() {
                        // ...when it's a block of triples...
                        public void visit(ElementPathBlock el) {
                            int counter=0;
                            NoOfTriples.total++;
                            // ...go through all the triples...
                            Iterator<TriplePath> triples = el.patternElts();
                            while (triples.hasNext()) {
                                TriplePath triple = triples.next();
                                counter++;
                            }
                            switch(counter)
                            {
                                case 0: NoOfTriples.zero++; break;
                                case 1: NoOfTriples.one++; break;
                                case 2: NoOfTriples.two++; break;
                                case 3: NoOfTriples.three++; break;
                                case 4: NoOfTriples.four++; break;
                                case 5: NoOfTriples.five++; break;
                                case 6: NoOfTriples.six++; break;
                                case 7: NoOfTriples.seven++; break;
                                case 8: NoOfTriples.eight++; break;
                                case 9: NoOfTriples.nine++; break;
                                case 10: NoOfTriples.ten++; break;
                                default: NoOfTriples.elevenOrMore++; break;
                                    
                            }
                        }
                    }
            );
        }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.print(formatter.format(Double.valueOf(zero)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(one)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(two)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(three)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(four)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(five)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(six)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(seven)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(eight)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(nine)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(ten)/NoOfTriples.total*100) + "\t");
        System.out.print(formatter.format(Double.valueOf(elevenOrMore)/NoOfTriples.total*100) + "\t");
        
        
        
        
        
    }

}
