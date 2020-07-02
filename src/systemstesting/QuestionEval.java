package systemstesting;

import java.util.ArrayList;

/**
 *
 * @author aorogat
 */
public class QuestionEval {
    String questionString;
    ArrayList<String> G; //Gold(Correct) Answers
    ArrayList<String> A; //System Answers
    double P_Micro = 0;
    double R_Micro = 0;
    double F_Micro = 0;

    public QuestionEval(String questionString, ArrayList<String> G, ArrayList<String> A) {
        this.questionString = questionString;
        this.G = (ArrayList)G.clone();
        this.A = (ArrayList)A.clone();
        
        int A_intersect_G_length = intersectLength(A,G);
        
        if(A.size()>0)
            P_Micro = A_intersect_G_length/(double)A.size();
        if(G.size()>0)
            R_Micro = A_intersect_G_length/(double)G.size();
        if(P_Micro>0 || R_Micro>0)
            F_Micro = (2*P_Micro*R_Micro)/(P_Micro+R_Micro);
    }

    private int intersectLength(ArrayList<String> A, ArrayList<String> G) {
        int intersectCount = 0;
        for (String g : G) {
            for (String a : A) {
                if(a.equals(g))
                    intersectCount++;
            }
        }
        return intersectCount;
    }

    @Override
    public String toString() {
        String q = questionString 
                + "\n" + "Gold Answer = " + G.toString()
                + "\n" + "System Answer = " + A.toString()
                + "\n" + "R = " + R_Micro + " ||||| " + 
                         "P = " + P_Micro + " ||||| " + 
                         "F1 = " + F_Micro;
        return q;
    }
    
    
    
    
}
