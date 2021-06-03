package DataSet;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import qa.dataStructures.Question;
import qa.dataStructures.QuestionWithOldQuery;

public class QALDAnalysis {

    public static ArrayList<QuestionWithOldQuery> intersectList_Questions_Only;
    public static ArrayList<Question> intersectList_Questions_and_Queries;
    
    public static void main(String[] args) {
                
        ArrayList<Query> q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_1);
        ArrayList<Question> Qald_1 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_2);
        ArrayList<Question> Qald_2 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_3);
        ArrayList<Question> Qald_3 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_4);
        ArrayList<Question> Qald_4 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_5);
        ArrayList<Question> Qald_5 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_6);
        ArrayList<Question> Qald_6 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_7);
        ArrayList<Question> Qald_7 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_8);
        ArrayList<Question> Qald_8 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);

        q = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_9);
        ArrayList<Question> Qald_9 = new ArrayList<>(DataSetPreprocessing.questionsWithoutDuplicates);
        
        System.out.println("Qald 1:" + Qald_1.size());
        System.out.println("Qald 2:" + Qald_2.size());
        System.out.println("Qald 3:" + Qald_3.size());
        System.out.println("Qald 4:" + Qald_4.size());
        System.out.println("Qald 5:" + Qald_5.size());
        System.out.println("Qald 6:" + Qald_6.size());
        System.out.println("Qald 7:" + Qald_7.size());
        System.out.println("Qald 8:" + Qald_8.size());
        System.out.println("Qald 9:" + Qald_9.size());
        
        ArrayList<Question> old = new ArrayList<>();
//        old.addAll(Qald_8);
//        old.addAll(Qald_7);
//        old.addAll(Qald_6);
//        old.addAll(Qald_5);
//        old.addAll(Qald_4);
//        old.addAll(Qald_3);
//        old.addAll(Qald_2);
        old.addAll(Qald_1);
        
        ArrayList<Question> Qald_t = Qald_2;
        
        intersect(Qald_t, old);
        System.out.println("Qald length: " + Qald_t.size());
        System.out.println("Q&Q Intersect: " + intersectList_Questions_and_Queries.size());
        System.out.println("Q only Intersect length: " + intersectList_Questions_Only.size());
        
//        for (QuestionWithOldQuery qq : intersectList_Questions_Only) {
//            System.out.println(qq.question.getQuestionString());
//            System.out.println("New Query " + qq.question.getFilepath());
//            System.out.println(qq.question.getQuestionQuery());
//            System.out.println("OLD Query" + qq.oldQueryDatabase);
//            System.out.println(qq.oldQuery);
//        }
        
    }

    
        public static void intersect(ArrayList<Question> recentList, ArrayList<Question> oldList){
            intersectList_Questions_Only = new ArrayList<>();
            intersectList_Questions_and_Queries = new ArrayList<>();
            
            for (Question new_q : recentList) {
                for (Question old_q : oldList) {
                    if(new_q.getQuestionString().trim().toLowerCase().equals(
                            old_q.getQuestionString().trim().toLowerCase())
                        &&
                        new_q.getQuestionQuery().trim().toLowerCase().equals(
                            old_q.getQuestionQuery().trim().toLowerCase()))
                    {
                        intersectList_Questions_and_Queries.add(new_q);break;
                    }
                            
                    else if(new_q.getQuestionString().trim().toLowerCase().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").equals(
                            old_q.getQuestionString().trim().toLowerCase().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "")))
                    {
                        intersectList_Questions_Only.add(new QuestionWithOldQuery(new_q,old_q.getQuestionQuery(),old_q.getFilepath()));
                        break;
                    }
                    
                }
                
            }
            
        }
    
}
