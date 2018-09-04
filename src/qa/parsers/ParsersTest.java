package qa.parsers;

import org.junit.Test;

import qa.dataStructures.Question;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class ParsersTest {
	
	
	@Test
	public void testJSONParserQALD9() {
		ArrayList<Question> questionsList = JSONParser.parseQald9File("./data/original/QALD-master/9/data/qald-9-train-multilingual.json",
				"QALD-9");
	    assertEquals(408, questionsList.size());		
	}
	
	@Test
	public void testJSONParserQALD8() {
		ArrayList<Question> questionsList = JSONParser.parseQald8File("./data/original/QALD-master/8/data/qald-8-train-multilingual.json",
				"QALD-8");
	    assertEquals(219, questionsList.size());		
	}
	
	@Test
	public void testJSONParserQALD7() {
		ArrayList<Question> questionsList = JSONParser.parseQald7File1("./data/original/QALD-master/7/data/qald-7-train-multilingual-extended-json.json",
				"QALD-7");
	    assertEquals(215, questionsList.size());
	    
	    questionsList.clear();
	    questionsList.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-test-multilingual.json",
				"QALD-7"));
	    assertEquals(43, questionsList.size());
	    
	    questionsList.clear();
	    questionsList.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/7/data/qald-7-train-largescale.json",
				"QALD-7"));
	    assertEquals(100, questionsList.size());
	}
	
	@Test
	public void testJSONParserQALD6() {
		ArrayList<Question> questionsList = JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-train-multilingual.json",
				"QALD-6");
	    assertEquals(335, questionsList.size());
	    
	    questionsList.clear();
	    questionsList.addAll(JSONParser.parseQald7File2("./data/original/QALD-master/6/data/qald-6-test-multilingual.json",
				"QALD-6"));
	    assertEquals(96, questionsList.size());
	}

}
