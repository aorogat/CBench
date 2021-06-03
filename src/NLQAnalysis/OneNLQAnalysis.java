package NLQAnalysis;


public class OneNLQAnalysis {
    public static boolean yesNoQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("is ") || question.trim().startsWith("was") || question.trim().startsWith("are ")
                || question.trim().startsWith("were") || question.trim().startsWith("do ") || question.trim().startsWith("does")
                || question.trim().startsWith("did");
    }

    public static boolean requestQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("name") || question.trim().startsWith("list") || question.trim().startsWith("find")
                    || question.trim().startsWith("identify") || question.trim().startsWith("search") || question.trim().startsWith("locate")
                    || question.trim().startsWith("enumerate") || question.trim().startsWith("look for") || question.trim().startsWith("return")
                    || question.trim().startsWith("give") || question.trim().startsWith("show") || question.trim().startsWith("tell")
                    || question.trim().startsWith("can you") || question.trim().startsWith("could you") || question.trim().startsWith("describe")
                    || question.trim().startsWith("make") || question.trim().startsWith("please") || question.trim().startsWith("count")
                    || question.trim().startsWith("state");
    }

    
    public static boolean whatQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})what.*$");
    }
    
    public static boolean whoQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})who.*$");
    }
    
    public static boolean whichQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})which.*$");
    }
    
    public static boolean whereQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})where.*$");
    }
    
    public static boolean whenQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})when.*$");
    }
    
    public static boolean whyQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("why");
    }
    
    public static boolean whoseQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})whose.*$");
    }
    
    public static boolean whomQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})whom.*$");
    }
    
    
    public static boolean howQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().matches("^(.{0,8})\\show.*$")
             ||question.trim().matches("how.*$");
    }

    public static void main(String[] args) {
        if (whichQuestion("in which country Ahmed  fbvjklg"))
            System.out.println("yes");
    }
    
}
