package systemstesting;
 
import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.query.Query;
import qa.dataStructures.Question;

public class GenerateBenchmarkFile
{
    static int benchmark = Benchmark.LC_QUAD;
    //static String BenchPrefix= "qlad_"+ benchmark;
    static String BenchPrefix= "lcquad";
    static ArrayList<Query> qs = DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

        private static FileWriter file;
 
    @SuppressWarnings("unchecked")
    public static void generateFile() {
        // JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject root = new JSONObject();

        int counter = 0;
        JSONArray qStringObjs = new JSONArray();
        
        for (Question q : questions) {
            JSONObject qStringObj = new JSONObject();
            qStringObj.put("question", q.getQuestionString());
            qStringObj.put("generated_sparql", "");
            qStringObjs.add(qStringObj);
        }
        root.put("questions", qStringObjs);
        
        try {
 
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(BenchPrefix+".json");
            file.write(root.toJSONString()
            .replaceAll("\\n", " ").replaceAll("\\r", " ").replace("\\/", "/"));
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
 
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    
    public static void main(String[] args) {
        generateFile();
    }
 
    static public void CrunchifyLog(String str) {
        System.out.println("str");
    }
}
