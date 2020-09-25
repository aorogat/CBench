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
        return question.trim().startsWith("what");
    }
    
    public static boolean whoQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("who");
    }
    
    public static boolean whichQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("which");
    }
    
    public static boolean whereQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("where");
    }
    
    public static boolean whenQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("when");
    }
    
    public static boolean whyQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("why");
    }
    
    public static boolean howQuestion(String question) {
        question = question.toLowerCase();
        return question.trim().startsWith("how");
    }

}
