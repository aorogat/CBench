package systemstesting;

import java.util.ArrayList;
import java.util.Collections;

public class Performance {
    
    static int correctAnswered;
    static int patiallyAnswered;
    static int falseAnswered;
    static int answered;
    static int questionsWithCorrectAnswers;
    static double precision;
    static double recall;
    static double f1;


    static boolean hasAnyCorrectAnswer(ArrayList<String> correctAnswerList, ArrayList<String> systemAnswerList) {
        for (int i = 0; i < systemAnswerList.size(); i++) {
            systemAnswerList.get(i).replaceAll("_", " ");
        }

        for (int i = 0; i < correctAnswerList.size(); i++) {
            correctAnswerList.get(i).replaceAll("_", " ");
        }

        for (String systemAnswerList1 : systemAnswerList) {
            for (String correctAnswerList1 : correctAnswerList) {
                if (systemAnswerList1.equals(correctAnswerList1)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasCompleteCorrectAnswer(ArrayList<String> correctAnswerList, ArrayList<String> systemAnswerList) {
        for (String systemAnswerList1 : systemAnswerList) {
            systemAnswerList1.replace('_', ' ');
        }

        for (String correctAnswerList1 : correctAnswerList) {
            correctAnswerList1.replace('_', ' ');
        }

        Collections.sort(correctAnswerList);
        Collections.sort(systemAnswerList);

        boolean found = false;
            for (int i = 0; i < correctAnswerList.size(); i++) {
                found = false;
                for (int j = 0; j < systemAnswerList.size(); j++) {
                    if (correctAnswerList.get(i).equals(systemAnswerList.get(j))) {
                        found = true;
                    }
                }
                if (!found) 
                    return false;
            }
            return true;
        
    }

    
    
    
}
