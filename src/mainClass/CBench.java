package mainClass;

import java.util.Scanner;
import org.json.JSONException;
import systemstesting.Evaluator_WebServiceBased;

public class CBench 
{
    static Evaluator_WebServiceBased evaluator = new Evaluator_WebServiceBased();
    public static void main(String[] args) throws JSONException, Exception {
        run();
    }
    
    public static void run() throws JSONException, Exception
    {
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
        
        if(purpose==1)
            Evaluator_WebServiceBased.evaluate(evaluator);
        else if (purpose == 2)
            BenchmarkAnalysis.analyze();
        else
            run();
        
    }
    
}
