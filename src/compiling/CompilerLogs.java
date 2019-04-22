package compiling;

import java.util.ArrayList;
import java.util.Hashtable;

public class CompilerLogs {
	//Changelog messages are stored here
	
	public static ArrayList<String> changeLogMessagesOutdated= new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesDuplicate = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesNoAnswers = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesOther = new ArrayList<String>();
	public static ArrayList<String> changeLogMessagesRemoved = new ArrayList<String>();
	public static ArrayList<String> errorLogMessages = new ArrayList<String>();
	
	public static ArrayList<String> TypesofExceptions = new ArrayList<String>();
	public static ArrayList<String> UnresolvedPrefixedNameExceptions = new ArrayList<String>();
	public static ArrayList<String> LexicalErrorExceptions = new ArrayList<String>();
	public static ArrayList<String> UnexpectedEncounterExceptions = new ArrayList<String>();
	public static ArrayList<String> EncounteredPname  = new ArrayList<String>();
	public static ArrayList<String> OtherExceptions = new ArrayList<String>();
	
	
	public static Hashtable<Integer, Integer> tripleCounter = new Hashtable<Integer, Integer>();
	public static Hashtable<String, Integer> operatorDistribution = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> subqueryDistribution = new Hashtable<String, Integer>();
	
	
	public static Hashtable<String, Integer> keywordCount = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> fileQuestionCount = new Hashtable<String, Integer>();
	public static Hashtable<Integer, Integer> tripleCounterFull = new Hashtable<Integer, Integer>();
	public static Hashtable<String, Integer> operatorDistributionFull = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> subqueryDistributionFull = new Hashtable<String, Integer>();
	
	
	
}
