package systemstesting;

import java.util.ArrayList;

public class BenchmarkEval 
{
    //Benchmark related data
    String benchmarkName;
    int allQuestions;
    private int questionsWithCorrectAnswers;
    
    //System related data
    private int answeredWithThetaThreshold; //For Correectly or partially Answered
    private int answered;
    
    public ArrayList<QuestionEval> evaluatedQuestions = new ArrayList<>();
    

    public BenchmarkEval(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }
    
    public void printScores()
    {        
        System.out.println("\n============== " + benchmarkName + " Scores ====================");
        System.out.println("allQuestions: " + allQuestions);
        System.out.println("Answered: " + answered);
        System.out.println("questionsWithCorrectAnswers: " + questionsWithCorrectAnswers);
        System.out.println("R_Mi : " + R_Mi() + "\t" + "P_Mi : " + P_Mi() + "\t" + "F_Mi : " + F_Mi());
        System.out.println("F_Ma : " + F_Ma());
        System.out.println();
        System.out.println("R_G : " + R_G()+ "\t" + "P_MG : " + P_G() + "\t" + "F_MG : " + F_G());
        System.out.println("answeredWithThetaThreshold: " + answeredWithThetaThreshold);
        System.out.println();
        System.out.println("R_G(0.000001) : " + R_G(0.000001)+ "\t" + "P_MG(0.000001) : " + P_G(0.000001) + "\t" + "F_MG(0.000001) : " + F_G(0.000001));
        System.out.println("answeredWithThetaThreshold: " + answeredWithThetaThreshold);
        
        System.out.println("\n============== " + benchmarkName + " Questions ====================");
        for (QuestionEval q : evaluatedQuestions) {
            System.out.println(q.toString());
        }
    }
    
    public void calculateParameters()
    {
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            if (evaluatedQuestion.G.size() > 0) {
                questionsWithCorrectAnswers++;
                if (evaluatedQuestion.A.size() > 0) {
                    answered++;
                }
            }
        }
    }
    
    //Micro scores
    public double R_Mi()
    {
        int sumOfIntesect = 0;
        int sumOfGi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) 
        {
            sumOfIntesect += evaluatedQuestion.A_intersect_G_length;
            sumOfGi += evaluatedQuestion.G.size();
        }
        return sumOfIntesect/ (double)sumOfGi;
    }
    
    public double P_Mi()
    {
        int sumOfIntesect = 0;
        int sumOfAi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) 
        {
            sumOfIntesect += evaluatedQuestion.A_intersect_G_length;
            sumOfAi += evaluatedQuestion.A.size();
        }
        return sumOfIntesect/ (double)sumOfAi;
    }
    
    public double F_Mi()
    {
        double R = R_Mi();
        double P = P_Mi();
        
        return (2 * R * P)   / (R + P);
    }
    
    //Macro scores
    public double F_Ma()
    {
        double sumOfFi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) 
            sumOfFi += evaluatedQuestion.F_q;
        return sumOfFi/ (double)questionsWithCorrectAnswers;
    }
    
    public double R_G()
    {
        return R_G(1);
    }
    
    public double P_G()
    {
        return P_G(1);
    }
    
    public double F_G()
    {
        return F_G(1);
    }
    
    //Global scores with threshold theta
    private double R_G(double theta)
    {
        answeredWithThetaThreshold = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions)
            if (evaluatedQuestion.F_q >= theta)
                answeredWithThetaThreshold++;

        
        return answeredWithThetaThreshold / (double) questionsWithCorrectAnswers;
    }
    
    public double P_G(double theta)
    {
        answeredWithThetaThreshold = 0;
        for(QuestionEval evaluatedQuestion : evaluatedQuestions)
            if (evaluatedQuestion.F_q >= theta)
                answeredWithThetaThreshold++;

        return answeredWithThetaThreshold / (double) answered;
    }
    
    public double F_G(double theta)
    {
        double R = R_G(theta);
        double P = P_G(theta);
        
        return (2 * R * P)   / (R + P);
    }
    
    
}
