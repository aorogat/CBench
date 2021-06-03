package NLQAnalysis;

import DataSet.DataSetPreprocessing;
import ShapeAnalysis.QueryShapeType;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
public class NLQCategoraizer {
    ArrayList<Question> what_Qs = new ArrayList<Question>();
    ArrayList<Question> when_Qs = new ArrayList<Question>();
    ArrayList<Question> where_Qs = new ArrayList<Question>();
    ArrayList<Question> which_Qs = new ArrayList<Question>();
    ArrayList<Question> who_Qs = new ArrayList<Question>();
    ArrayList<Question> whom_Qs = new ArrayList<Question>();
    ArrayList<Question> whose_Qs = new ArrayList<Question>();
    ArrayList<Question> how_Qs = new ArrayList<Question>();
    ArrayList<Question> yes_no_Qs = new ArrayList<Question>();
    ArrayList<Question> request_Qs = new ArrayList<Question>();
    ArrayList<Question> Topical_Qs = new ArrayList<Question>();

    
    
    public NLQCategoraizer() throws IOException {
    what_Qs = new ArrayList<Question>();
    when_Qs = new ArrayList<Question>();
    where_Qs = new ArrayList<Question>();
    which_Qs = new ArrayList<Question>();
    who_Qs = new ArrayList<Question>();
    whom_Qs = new ArrayList<Question>();
    whose_Qs = new ArrayList<Question>();
    how_Qs = new ArrayList<Question>();
    yes_no_Qs = new ArrayList<Question>();
    request_Qs = new ArrayList<Question>();
    Topical_Qs = new ArrayList<Question>();

       
        
        
        for (Question q : DataSetPreprocessing.questionsWithoutDuplicates) {
            String queryString = q.getQuestionQuery();
            try {
                if (OneNLQAnalysis.whatQuestion(q.getQuestionString())) {
                    what_Qs.add(q);
                }
                else if (OneNLQAnalysis.whenQuestion(q.getQuestionString())) {
                    when_Qs.add(q);
                }
                else if (OneNLQAnalysis.whereQuestion(q.getQuestionString())) {
                    where_Qs.add(q);
                }
                else if (OneNLQAnalysis.whichQuestion(q.getQuestionString())) {
                    which_Qs.add(q);
                }
                else if (OneNLQAnalysis.whomQuestion(q.getQuestionString())) {
                    whom_Qs.add(q);
                }
                else if (OneNLQAnalysis.whoseQuestion(q.getQuestionString())) {
                    whose_Qs.add(q);
                }
                else if (OneNLQAnalysis.whoQuestion(q.getQuestionString())) {
                    who_Qs.add(q);
                }
                else if (OneNLQAnalysis.howQuestion(q.getQuestionString())) {
                    how_Qs.add(q);
                }
                else if (OneNLQAnalysis.yesNoQuestion(q.getQuestionString())) {
                    yes_no_Qs.add(q);
                }
                else if (OneNLQAnalysis.requestQuestion(q.getQuestionString())) {
                    request_Qs.add(q);
                }
                else{
                    Topical_Qs.add(q);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //System.out.println(queryString);
            }
        }
        
      
    }
    

    public ArrayList<Question> getWhat_Qs() {
        return what_Qs;
    }

    public ArrayList<Question> getWhen_Qs() {
        return when_Qs;
    }

    public ArrayList<Question> getWhere_Qs() {
        return where_Qs;
    }

    public ArrayList<Question> getWhich_Qs() {
        return which_Qs;
    }

    public ArrayList<Question> getWho_Qs() {
        return who_Qs;
    }

    public ArrayList<Question> getWhom_Qs() {
        return whom_Qs;
    }

    public ArrayList<Question> getWhose_Qs() {
        return whose_Qs;
    }

    public ArrayList<Question> getHow_Qs() {
        return how_Qs;
    }

    public ArrayList<Question> getYes_no_Qs() {
        return yes_no_Qs;
    }

    public ArrayList<Question> getRequest_Qs() {
        return request_Qs;
    }

    public ArrayList<Question> getTopical_Qs() {
        return Topical_Qs;
    }

    
    
}
