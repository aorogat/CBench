
package mainClass;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import ShallowAnalysis.Keywords;
import ShallowAnalysis.NoOfTriples;
import ShallowAnalysis.OperatorDistribution;
import ShapeAnalysis.ShapeAnalysis;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.jena.query.Query;


public class BenchmarkAnalysis {

    public static ArrayList<Query> qs;
    public static int benchmark;
    
    public static void analyze()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("        |");
        System.out.println("        ++++> Select the Benchmark (Enter the integer value) from");
        System.out.println("                [1- QALD_1, \t\t2- QALD_2, ..., \t\t9- QALD_9, \t\t10- QALD_ALL, ");
        System.out.println("                 11- LC-QUAD, \t\t12- WebQuestions, \t\t13- GraphQuestions, ");
        System.out.println("                 14- ComplexQuestions, \t15- ComQA, \t\t\t16- TempQuestions, ");
        System.out.println("                 17- SimpleDBpediaQA, \t18- SimpleQuestions, ");
        System.out.println("                 19- UserDefined]");
        System.out.print("               Benchmark is: ");
        
        benchmark = in.nextInt();
        
       switch(benchmark)
       {
           case 1: benchmark = Benchmark.QALD_1; break;
           case 2: benchmark = Benchmark.QALD_2; break;
           case 3: benchmark = Benchmark.QALD_3; break;
           case 4: benchmark = Benchmark.QALD_4; break;
           case 5: benchmark = Benchmark.QALD_5; break;
           case 6: benchmark = Benchmark.QALD_6; break;
           case 7: benchmark = Benchmark.QALD_7; break;
           case 8: benchmark = Benchmark.QALD_8; break;
           case 9: benchmark = Benchmark.QALD_9; break;
           case 10: benchmark = Benchmark.QALD_ALL; break;
           case 11: benchmark = Benchmark.LC_QUAD; break;
           case 12: benchmark = Benchmark.WebQuestions; break;
           case 13: benchmark = Benchmark.GraphQuestions; break;
           case 14: benchmark = Benchmark.ComplexQuestions; break;
           case 15: benchmark = Benchmark.ComQA; break;
           case 16: benchmark = Benchmark.TempQuestions; break;
           case 17: benchmark = Benchmark.SimpleDBpediaQA; break;
           case 18: benchmark = Benchmark.SimpleQuestions; break;
           case 19: benchmark = Benchmark.UserDefined; break;
           default: analyze();
       }
        
        
       qs = DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
        
        //Shallow Anlysis
        //===============
        //1- Keyword Analysis
        Keywords k = new Keywords(qs);
        k.keywordsAnalysis();
        
        //2- Number of triples analysis
        NoOfTriples triples = new NoOfTriples(qs);
        triples.triplesAnalysis();
        
        //3- Operator Distribution
        OperatorDistribution distribution = new OperatorDistribution(qs);
        distribution.analysis();
        
        //Shape Analysis
        ShapeAnalysis shapes = new ShapeAnalysis(qs);
        shapes.analysis();
    }
    
}
