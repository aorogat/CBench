package NLQAnalysis;

import DataSet.DataSetPreprocessing;
import java.io.IOException;
import java.util.ArrayList;
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
        for (Question q : DataSetPreprocessing.questionsWithoutDuplicates) {
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

    
    public void print(){
        System.out.println("\n\n\nNLQ Analysis ----->");
        String format = "%-30s%-20s%-20s%n";
        System.out.format(format, "Type\t", "#Questions\t", "Relative%\t");
        System.out.format(format, "=======", "========", "=========");
        System.out.format(format, "what-questions \t", what_Qs.size() + "    \t", 100*(float) what_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "when-questions \t", when_Qs.size() + "    \t", 100*(float) when_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "where-questions \t", where_Qs.size() + "    \t", 100*(float) where_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "which-questions \t", which_Qs.size() + "    \t", 100*(float) which_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "who-questions \t", who_Qs.size() + "    \t", 100*(float) who_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "whom-questions \t", whom_Qs.size() + "    \t", 100*(float) whom_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "whose-questions \t", whose_Qs.size() + "    \t", 100*(float) whose_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "how-questions \t", how_Qs.size() + "    \t", 100*(float) how_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "yes-no-questions \t",yes_no_Qs.size() + "    \t", 100*(float) yes_no_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "request-questions \t", request_Qs.size() + "    \t", 100*(float) request_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        System.out.format(format, "Topical-questions \t", Topical_Qs.size() + "    \t", 100*(float) Topical_Qs.size() / DataSetPreprocessing.questionsWithoutDuplicates.size() + "%");
        
    }
}
