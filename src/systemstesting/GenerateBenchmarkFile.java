package systemstesting;
 
import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import mainClass.CBench;
import org.apache.jena.query.Query;
import qa.dataStructures.Question;

public class GenerateBenchmarkFile
{
    //static int benchmark = Benchmark.LC_QUAD;
    //static String BenchPrefix= "qlad_"+ benchmark;
    //static String BenchPrefix= "lcquad";
    //static ArrayList<Query> qs = DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
    static ArrayList<Question> questions;

    private static FileWriter file;
 
    public static void generateFile(int benchmark) throws Exception {
        DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
        questions = DataSetPreprocessing.questions;
        // JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject root = new JSONObject();

        int counter = 0;
        JSONArray qStringObjs = new JSONArray();
        
        for (Question q : questions) {
            JSONObject qStringObj = new JSONObject();
            qStringObj.put("questionString", q.getQuestionString());
            qStringObj.put("generatedSparql", "");
            qStringObjs.add(qStringObj);
        }
        root.put("questions", qStringObjs);
        
        try {
            file = new FileWriter("Generated_Benchmark.json");
            file.write(root.toJSONString()
            .replaceAll("\\n", " ").replaceAll("\\r", " ").replace("\\/", "/"));
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
 
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("CBench generated a file called 'Generated_Benchmark.json' in the root directory of the project.");
        System.out.println("Please fill the 'generatedSparql' fileds in this file by your QA system then return it to CBench and rename the file to QA.json.");
        System.out.println("Select 'benchmark file is ready' when CBench asks you again.");
        System.out.println("Write s then press Enter to run CBench again.");
        Scanner in = new Scanner(System.in);
        in.next();
        CBench.run();
    }
    
    
    public static void main(String[] args) throws Exception {
        generateFile(Benchmark.QALD_1);
    }
 
    static public void CrunchifyLog(String str) {
        System.out.println("str");
    }
}
