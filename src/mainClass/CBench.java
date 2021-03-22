package mainClass;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.util.Scanner;
import static mainClass.BenchmarkAnalysis.analyze;
import static mainClass.BenchmarkAnalysis.benchmark;
import org.json.JSONException;
import systemstesting.Evaluator_File_Based;
import systemstesting.Evaluator_WebServiceBased;
import systemstesting.GenerateBenchmarkFile;

public class CBench {

    static Evaluator_WebServiceBased evaluator = new Evaluator_WebServiceBased();

    public static void main(String[] args) throws JSONException, Exception {
        run();
    }

    public static void run() throws JSONException, Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+ Welcome to CBench, a QA benchmarking System +");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       ++++++++++++++++++++++");
        System.out.println("++++++> System Configuration +");
        System.out.println("        ++++++++++++++++++++++");
        System.out.println("        |");
        System.out.println("        ++++> Would you like to evaluate a QA system (Enter 1) or analyize a benchmark (Enter 2)?");
        System.out.print("               Your answer is: ");
        int purpose = in.nextInt();

        if (purpose == 1) {
            System.out.println("        |");
            System.out.println("        ++++> There are 2 modes to evaluate your QA system");
            System.out.println("              (1) Syncronously (via http requests)\t (2) Asyncronously (via benchmark upload)");
            System.out.println("              Enter the integer value");
            System.out.print("                Your answer is: ");
            int eval = in.nextInt();
            if (eval == 1) {
                Evaluator_WebServiceBased.evaluate(evaluator);
            } else if (eval == 2) {
                System.out.println("        |");
                System.out.println("        ++++> If your benchmark file is ready, press 1.");
                System.out.println("              If you need the benchmark file, press 2.");
                System.out.print("                Your answer is: ");
                int f = in.nextInt();
                if (f == 1) {
                    Evaluator_File_Based.evaluate(evaluator);
                } else if (eval == 2) {
                    System.out.println("        |");
                    System.out.println("        ++++> Select the Benchmark (Enter the integer value) from");
                    System.out.println("                [1- QALD_1, \t\t2- QALD_2, ..., \t\t9- QALD_9, \t\t10- QALD_ALL, ");
                    System.out.println("                 11- LC-QUAD, \t\t12- WebQuestions, \t\t13- GraphQuestions, ");
                    System.out.println("                 14- ComplexQuestions, \t15- ComQA, \t\t\t16- TempQuestions, ");
                    System.out.println("                 17- SimpleDBpediaQA, \t18- SimpleQuestions, ");
                    System.out.println("                 19- User Defined, \t20- Properties Defined]");
                    System.out.print("               Benchmark is: ");

                    benchmark = in.nextInt();

                    switch (benchmark) {
                        case 1:
                            benchmark = Benchmark.QALD_1;
                            break;
                        case 2:
                            benchmark = Benchmark.QALD_2;
                            break;
                        case 3:
                            benchmark = Benchmark.QALD_3;
                            break;
                        case 4:
                            benchmark = Benchmark.QALD_4;
                            break;
                        case 5:
                            benchmark = Benchmark.QALD_5;
                            break;
                        case 6:
                            benchmark = Benchmark.QALD_6;
                            break;
                        case 7:
                            benchmark = Benchmark.QALD_7;
                            break;
                        case 8:
                            benchmark = Benchmark.QALD_8;
                            break;
                        case 9:
                            benchmark = Benchmark.QALD_9;
                            break;
                        case 10:
                            benchmark = Benchmark.QALD_ALL;
                            break;
                        case 11:
                            benchmark = Benchmark.LC_QUAD;
                            break;
                        case 12:
                            benchmark = Benchmark.WebQuestions;
                            break;
                        case 13:
                            benchmark = Benchmark.GraphQuestions;
                            break;
                        case 14:
                            benchmark = Benchmark.ComplexQuestions;
                            break;
                        case 15:
                            benchmark = Benchmark.ComQA;
                            break;
                        case 16:
                            benchmark = Benchmark.TempQuestions;
                            break;
                        case 17:
                            benchmark = Benchmark.SimpleDBpediaQA;
                            break;
                        case 18:
                            benchmark = Benchmark.SimpleQuestions;
                            break;
                        case 19:
                            benchmark = Benchmark.UserDefined;
                            break;
                        case 20:
                            benchmark = Benchmark.PropertiesDefined;
                            break;
                        default:
                            analyze();
                    }

                    GenerateBenchmarkFile.generateFile(benchmark);
                }
            } else {
                run();
            }
        } else if (purpose == 2) {
            BenchmarkAnalysis.analyze();
        } else {
            run();
        }

    }

}
