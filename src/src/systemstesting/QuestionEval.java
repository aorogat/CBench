package systemstesting;

import java.util.ArrayList;

public class QuestionEval {
    String questionString;
    ArrayList<String> G; //Gold(Correct) Answers
    ArrayList<String> A; //System Answers
    int A_intersect_G_length;
    double P_q = 0;
    double R_q = 0;
    double F_q = 0;

    public QuestionEval(String questionString, ArrayList<String> G, ArrayList<String> A) {
        this.questionString = questionString;
        this.G = (ArrayList)G.clone();
        this.A = (ArrayList)A.clone();
        
        A_intersect_G_length = intersectLength(A,G);
        
        if(A.size()>0)
            P_q = A_intersect_G_length/(double)A.size();
        if(G.size()>0)
            R_q = A_intersect_G_length/(double)G.size();
        if(P_q>0 || R_q>0)
            F_q = (2*P_q*R_q)/(P_q+R_q);
    }

    private int intersectLength(ArrayList<String> A, ArrayList<String> G) {
        int intersectCount = 0;
        for (String g : G) {
            for (String a : A) {
                if(a.toLowerCase().equals(g.toLowerCase()))
                    intersectCount++;
            }
        }
        return intersectCount;
    }

    @Override
    public String toString() {
        String q = questionString 
                + "\t" + "Gold Answer : " + G.toString()
                + "\t" + "System Answer : " + A.toString()
                + "\t" + "R : " + R_q + " \t " + 
                         "P : " + P_q + " \t " + 
                         "F1 : " + F_q;
        return q;
    }
    
    
    
    
}
