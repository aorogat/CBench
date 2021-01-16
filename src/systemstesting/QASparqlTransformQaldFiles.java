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

public class QASparqlTransformQaldFiles 
{
    static int benchmark = Benchmark.QALD_7;
    static ArrayList<Query> qs = DataSetPreprocessing.getQueriesWithoutDuplicates(benchmark);
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

        private static FileWriter file;
 
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
 
        // JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject root = new JSONObject();
        
        JSONObject datasetId = new JSONObject();
        datasetId.put("id", "Qald_"+ benchmark);
        
 
        JSONArray quess = new JSONArray();
        
        int counter = 0;
        for (Question q : questions) {
            JSONObject qObj = new JSONObject();
            qObj.put("id", counter++ +"");
            
                JSONArray qStringObjs = new JSONArray();
                JSONObject qStringObj = new JSONObject();
                qStringObj.put("language", "en");
                qStringObj.put("string", q.getQuestionString());
                qStringObj.put("keywords", q.getKeywords()==null?"":q.getKeywords());
                qStringObjs.add(qStringObj);
                
            
            
            JSONObject queryObj = new JSONObject();
            queryObj.put("sparql", q.getQuestionQuery());
                
            JSONArray answersObjs = new JSONArray();
            
//            for (String a : q.getAnswers()) {
//                answersObjs.add(a)
//            }
            
            qObj.put("question", qStringObjs);
            qObj.put("query", queryObj);
            qObj.put("answers", answersObjs);
            
            quess.add(qObj);
        }
        
        root.put("dataset", datasetId);
        root.put("questions", quess);
        
        try {
 
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter("Qald_"+benchmark+".json");
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
 
    static public void CrunchifyLog(String str) {
        System.out.println("str");
    }
}
