import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.*;
import java.util.*;


public class QABenchmark {
	
	static ArrayList<ArrayList<String>> quesList(String filepath, String filename, String fileformat) {
		
		ArrayList<ArrayList<String>> qlst = new ArrayList<ArrayList<String>>();
		
		try   
		{  
		// creating a constructor of file class and parsing an XML file  
		File file = new File(filepath + filename + fileformat);
		// an instance of factory that gives a document builder  
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		// an instance of builder to parse the specified xml file  
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(file);  
		doc.getDocumentElement().normalize();  
		NodeList nodeList = doc.getElementsByTagName("question"); 
		// nodeList is not iterable, so we are using for loop  
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
		Node node = nodeList.item(itr);  
		if (node.getNodeType() == Node.ELEMENT_NODE)   
		{  
		Element eElement = (Element) node;
		ArrayList<String> subQlst = new ArrayList<>();
		subQlst.add(eElement.getElementsByTagName("questionSource").item(0).getTextContent());
		subQlst.add(("\"" + eElement.getElementsByTagName("questionString").item(0).getTextContent().replace("\"", "") + "\"").toLowerCase());
		qlst.add(subQlst);
		} 
		}
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		} 
	    return qlst;
	}
	
	static void writeToFile(ArrayList<ArrayList<String>> qlst, String filepath, String filename) {
		try { 
	        // create FileWriter object with file as parameter
	    	FileWriter writer = new FileWriter(filepath + filename + ".csv");
	        for (int i = 0; i < qlst.size(); i++) {
	        	for (int j = 0; j < qlst.get(i).size()-1; j++) {
	        		writer.write(qlst.get(i).get(j) + ",");
	        	}
	        	writer.write(qlst.get(i).get(qlst.get(i).size()-1) + "\n");
	        	//writer.write(qlst.get(i) + "\n");
	        }
	        // closing writer connection
	        writer.close();
	    } 
	    catch (Exception e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    }
	}
	
	static ArrayList<ArrayList<String>> whHowQues(ArrayList<ArrayList<String>> qlst, String start){
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < qlst.size(); i++) {
        	if (qlst.get(i).get(1).startsWith("\"" + start) || qlst.get(i).get(1).startsWith(start, qlst.get(i).get(1).indexOf(" ") + 1)) {
        		result.add(qlst.get(i));
        		qlst.get(i).add(1, start);
        	}
        }
		return result;
	}
	
	static ArrayList<ArrayList<String>> specWhQues(ArrayList<ArrayList<String>> qlst, String start){
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < qlst.size(); i++) {
        	if (qlst.get(i).get(2).startsWith("\"" + start) || qlst.get(i).get(2).startsWith(start, qlst.get(i).get(2).indexOf(" ") + 1)) {
        		result.add(qlst.get(i));
        	}
        }
		return result;
	}
	
	static ArrayList<ArrayList<String>> yesNoQues(ArrayList<ArrayList<String>> qlst){
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < qlst.size(); i++) {
        	if (qlst.get(i).get(1).startsWith("\"is ") || qlst.get(i).get(1).startsWith("\"was") || qlst.get(i).get(1).startsWith("\"are ") || 
        			qlst.get(i).get(1).startsWith("\"were") || qlst.get(i).get(1).startsWith("\"do ") || qlst.get(i).get(1).startsWith("\"does") || 
        			qlst.get(i).get(1).startsWith("\"did")) {
        		result.add(qlst.get(i));
        		qlst.get(i).add(1, "yes/no");
        	}
        }
		return result;
	}
	
	static ArrayList<ArrayList<String>> request(ArrayList<ArrayList<String>> qlst){
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < qlst.size(); i++) {
        	if (qlst.get(i).get(1).startsWith("\"name") || qlst.get(i).get(1).startsWith("\"list") || qlst.get(i).get(1).startsWith("\"find") ||
        			qlst.get(i).get(1).startsWith("\"identify") || qlst.get(i).get(1).startsWith("\"search") || qlst.get(i).get(1).startsWith("\"locate") ||
        			qlst.get(i).get(1).startsWith("\"enumerate") || qlst.get(i).get(1).startsWith("\"look for") || qlst.get(i).get(1).startsWith("\"return") || 
        			qlst.get(i).get(1).startsWith("\"give") || qlst.get(i).get(1).startsWith("\"show") || qlst.get(i).get(1).startsWith("\"tell") || 
        			qlst.get(i).get(1).startsWith("\"can you") || qlst.get(i).get(1).startsWith("\"could you") || qlst.get(i).get(1).startsWith("\"describe") ||
        			qlst.get(i).get(1).startsWith("\"make") || qlst.get(i).get(1).startsWith("\"please") || qlst.get(i).get(1).startsWith("\"count") ||
        			qlst.get(i).get(1).startsWith("\"state")) {
        		result.add(qlst.get(i));
        		qlst.get(i).add(1, "request");
        	}
        }
		return result;
	}
	
	static ArrayList<ArrayList<String>> topicalized(ArrayList<ArrayList<String>> qlst){
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < qlst.size(); i++) {
			if (qlst.get(i).size() != 3) {
				result.add(qlst.get(i));
				qlst.get(i).add(1, "topicalized");
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepath = "";
		// get the path to the data
		String userDir = System.getProperty("user.dir");
		File file = new File(userDir+"/../..");
		try {
			filepath = file.getCanonicalPath()+"/Data/";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// create question list for each data file
		ArrayList<ArrayList<String>> freebase = quesList(filepath, "FREEBASE", ".xml");
		ArrayList<ArrayList<String>> lcquad = quesList(filepath, "LC-QUAD", ".xml");
		ArrayList<ArrayList<String>> qald1 = quesList(filepath, "QALD_1", ".xml");
		ArrayList<ArrayList<String>> qald2 = quesList(filepath, "QALD_2", ".xml");
		ArrayList<ArrayList<String>> qald3 = quesList(filepath, "QALD_3", ".xml");
		ArrayList<ArrayList<String>> qald4 = quesList(filepath, "QALD_4", ".xml");
		ArrayList<ArrayList<String>> qald5 = quesList(filepath, "QALD_5", ".xml");
		ArrayList<ArrayList<String>> qald6 = quesList(filepath, "QALD_6", ".xml");
		ArrayList<ArrayList<String>> qald7 = quesList(filepath, "QALD_7", ".xml");
		ArrayList<ArrayList<String>> qald8 = quesList(filepath, "QALD_8", ".xml");
		ArrayList<ArrayList<String>> qald9 = quesList(filepath, "QALD_9", ".xml");
		ArrayList<ArrayList<String>> qaldAll = quesList(filepath, "QALD_ALL", ".xml");
		
		ArrayList<ArrayList<String>> web = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> graph = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < freebase.size(); i++) {
			if (freebase.get(i).get(0).equals("Freebase_Web")) {
				web.add(freebase.get(i));
			} else if (freebase.get(i).get(0).equals("Freebase_Graph")) {
				graph.add(freebase.get(i));
			}
		}
		
		
		ArrayList<ArrayList<String>> web_wh = whHowQues(web, "wh");
		ArrayList<ArrayList<String>> web_how = whHowQues(web, "how");
		ArrayList<ArrayList<String>> web_yn = yesNoQues(web);
		ArrayList<ArrayList<String>> web_rqst = request(web);
		ArrayList<ArrayList<String>> web_top = topicalized(web);
		System.out.format("Number of wh-questions in web is %d, and the percentage is %.4f%n", web_wh.size(), (float) web_wh.size() / web.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(web, "what").size(), (float) specWhQues(web, "what").size() / web.size());
		System.out.format("which %d, %.3f%n", specWhQues(web, "which").size(), (float) specWhQues(web, "which").size() / web.size());
		System.out.format("who %d, %.3f%n", specWhQues(web, "who ").size(), (float) specWhQues(web, "who ").size() / web.size());
		System.out.format("whose %d, %.3f%n", specWhQues(web, "whose").size(), (float) specWhQues(web, "whose").size() / web.size());
		System.out.format("whom %d, %.3f%n", specWhQues(web, "whom").size(), (float) specWhQues(web, "whom").size() / web.size());
		System.out.format("where %d, %.3f%n", specWhQues(web, "where").size(), (float) specWhQues(web, "where").size() / web.size());
		System.out.format("when %d, %.3f%n", specWhQues(web, "when").size(), (float) specWhQues(web, "when").size() / web.size());
		*/
		System.out.format("Number of how-questions in web is %d, and the percentage is %.4f%n", web_how.size(), (float) web_how.size() / web.size());
		System.out.format("Number of yes-no-questions in web is %d, and the percentage is %.4f%n", web_yn.size(), (float) web_yn.size() / web.size());
		System.out.format("Number of requests in web is %d, and the percentage is %.4f%n", web_rqst.size(), (float) web_rqst.size() / web.size());
		System.out.format("Number of topicalized questions in web is %d, and the percentage is %.4f%n", web_top.size(), (float) web_top.size() / web.size());
		System.out.println();
		
		
		ArrayList<ArrayList<String>> graph_wh = whHowQues(graph, "wh");
		ArrayList<ArrayList<String>> graph_how = whHowQues(graph, "how");
		ArrayList<ArrayList<String>> graph_yn = yesNoQues(graph);
		ArrayList<ArrayList<String>> graph_rqst = request(graph);
		ArrayList<ArrayList<String>> graph_top = topicalized(graph);
		System.out.format("Number of wh-questions in graph is %d, and the percentage is %.4f%n", graph_wh.size(), (float) graph_wh.size() / graph.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(graph, "what").size(), (float) specWhQues(graph, "what").size() / graph.size());
		System.out.format("which %d, %.3f%n", specWhQues(graph, "which").size(), (float) specWhQues(graph, "which").size() / graph.size());
		System.out.format("who %d, %.3f%n", specWhQues(graph, "who ").size(), (float) specWhQues(graph, "who ").size() / graph.size());
		System.out.format("whose %d, %.3f%n", specWhQues(graph, "whose").size(), (float) specWhQues(graph, "whose").size() / graph.size());
		System.out.format("whom %d, %.3f%n", specWhQues(graph, "whom").size(), (float) specWhQues(graph, "whom").size() / graph.size());
		System.out.format("where %d, %.3f%n", specWhQues(graph, "where").size(), (float) specWhQues(graph, "where").size() / graph.size());
		System.out.format("when %d, %.3f%n", specWhQues(graph, "when").size(), (float) specWhQues(graph, "when").size() / graph.size());
		*/
		System.out.format("Number of how-questions in graph is %d, and the percentage is %.4f%n", graph_how.size(), (float) graph_how.size() / graph.size());
		System.out.format("Number of yes-no-questions in graph is %d, and the percentage is %.4f%n", graph_yn.size(), (float) graph_yn.size() / graph.size());
		System.out.format("Number of requests in graph is %d, and the percentage is %.4f%n", graph_rqst.size(), (float) graph_rqst.size() / graph.size());
		System.out.format("Number of topicalized questions in graph is %d, and the percentage is %.4f%n", graph_top.size(), (float) graph_top.size() / graph.size());
		System.out.println();
		
		
		ArrayList<ArrayList<String>> lcquad_wh = whHowQues(lcquad, "wh");
		ArrayList<ArrayList<String>> lcquad_how = whHowQues(lcquad, "how");
		ArrayList<ArrayList<String>> lcquad_yn = yesNoQues(lcquad);
		ArrayList<ArrayList<String>> lcquad_rqst = request(lcquad);
		ArrayList<ArrayList<String>> lcquad_top = topicalized(lcquad);
		System.out.format("Number of wh-questions in lcquad is %d, and the percentage is %.4f%n", lcquad_wh.size(), (float) lcquad_wh.size() / lcquad.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(lcquad, "what").size(), (float) specWhQues(lcquad, "what").size() / lcquad.size());
		System.out.format("which %d, %.3f%n", specWhQues(lcquad, "which").size(), (float) specWhQues(lcquad, "which").size() / lcquad.size());
		System.out.format("who %d, %.3f%n", specWhQues(lcquad, "who ").size(), (float) specWhQues(lcquad, "who ").size() / lcquad.size());
		System.out.format("whose %d, %.3f%n", specWhQues(lcquad, "whose").size(), (float) specWhQues(lcquad, "whose").size() / lcquad.size());
		System.out.format("whom %d, %.3f%n", specWhQues(lcquad, "whom").size(), (float) specWhQues(lcquad, "whom").size() / lcquad.size());
		System.out.format("where %d, %.3f%n", specWhQues(lcquad, "where").size(), (float) specWhQues(lcquad, "where").size() / lcquad.size());
		System.out.format("when %d, %.3f%n", specWhQues(lcquad, "when").size(), (float) specWhQues(lcquad, "when").size() / lcquad.size());
		*/
		System.out.format("Number of how-questions in lcquad is %d, and the percentage is %.4f%n", lcquad_how.size(), (float) lcquad_how.size() / lcquad.size());
		System.out.format("Number of yes-no-questions in lcquad is %d, and the percentage is %.4f%n", lcquad_yn.size(), (float) lcquad_yn.size() / lcquad.size());
		System.out.format("Number of requests in lcquad is %d, and the percentage is %.4f%n", lcquad_rqst.size(), (float) lcquad_rqst.size() / lcquad.size());
		System.out.format("Number of topicalized questions in lcquad is %d, and the percentage is %.4f%n", lcquad_top.size(), (float) lcquad_top.size() / lcquad.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald1_wh = whHowQues(qald1, "wh");
		ArrayList<ArrayList<String>> qald1_how = whHowQues(qald1, "how");
		ArrayList<ArrayList<String>> qald1_yn = yesNoQues(qald1);
		ArrayList<ArrayList<String>> qald1_rqst = request(qald1);
		ArrayList<ArrayList<String>> qald1_top = topicalized(qald1);
		System.out.format("%d, %.4f%n", qald1_wh.size(), (float) qald1_wh.size() / qald1.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald1, "what").size(), (float) specWhQues(qald1, "what").size() / qald1.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald1, "which").size(), (float) specWhQues(qald1, "which").size() / qald1.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald1, "who ").size(), (float) specWhQues(qald1, "who ").size() / qald1.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald1, "whose").size(), (float) specWhQues(qald1, "whose").size() / qald1.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald1, "whom").size(), (float) specWhQues(qald1, "whom").size() / qald1.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald1, "where").size(), (float) specWhQues(qald1, "where").size() / qald1.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald1, "when").size(), (float) specWhQues(qald1, "when").size() / qald1.size());
		*/
		System.out.format("%d, %.4f%n", qald1_how.size(), (float) qald1_how.size() / qald1.size());
		System.out.format("%d, %.4f%n", qald1_yn.size(), (float) qald1_yn.size() / qald1.size());
		System.out.format("%d, %.4f%n", qald1_rqst.size(), (float) qald1_rqst.size() / qald1.size());
		System.out.format("%d, %.4f%n", qald1_top.size(), (float) qald1_top.size() / qald1.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald2_wh = whHowQues(qald2, "wh");
		ArrayList<ArrayList<String>> qald2_how = whHowQues(qald2, "how");
		ArrayList<ArrayList<String>> qald2_yn = yesNoQues(qald2);
		ArrayList<ArrayList<String>> qald2_rqst = request(qald2);
		ArrayList<ArrayList<String>> qald2_top = topicalized(qald2);
		System.out.format("%d, %.4f%n", qald2_wh.size(), (float) qald2_wh.size() / qald2.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald2, "what").size(), (float) specWhQues(qald2, "what").size() / qald2.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald2, "which").size(), (float) specWhQues(qald2, "which").size() / qald2.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald2, "who ").size(), (float) specWhQues(qald2, "who ").size() / qald2.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald2, "whose").size(), (float) specWhQues(qald2, "whose").size() / qald2.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald2, "whom").size(), (float) specWhQues(qald2, "whom").size() / qald2.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald2, "where").size(), (float) specWhQues(qald2, "where").size() / qald2.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald2, "when").size(), (float) specWhQues(qald2, "when").size() / qald2.size());
		*/
		System.out.format("%d, %.4f%n", qald2_how.size(), (float) qald2_how.size() / qald2.size());
		System.out.format("%d, %.4f%n", qald2_yn.size(), (float) qald2_yn.size() / qald2.size());
		System.out.format("%d, %.4f%n", qald2_rqst.size(), (float) qald2_rqst.size() / qald2.size());
		System.out.format("%d, %.4f%n", qald2_top.size(), (float) qald2_top.size() / qald2.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald3_wh = whHowQues(qald3, "wh");
		ArrayList<ArrayList<String>> qald3_how = whHowQues(qald3, "how");
		ArrayList<ArrayList<String>> qald3_yn = yesNoQues(qald3);
		ArrayList<ArrayList<String>> qald3_rqst = request(qald3);
		ArrayList<ArrayList<String>> qald3_top = topicalized(qald3);
		System.out.format("%d, %.4f%n", qald3_wh.size(), (float) qald3_wh.size() / qald3.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald3, "what").size(), (float) specWhQues(qald3, "what").size() / qald3.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald3, "which").size(), (float) specWhQues(qald3, "which").size() / qald3.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald3, "who ").size(), (float) specWhQues(qald3, "who ").size() / qald3.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald3, "whose").size(), (float) specWhQues(qald3, "whose").size() / qald3.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald3, "whom").size(), (float) specWhQues(qald3, "whom").size() / qald3.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald3, "where").size(), (float) specWhQues(qald3, "where").size() / qald3.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald3, "when").size(), (float) specWhQues(qald3, "when").size() / qald3.size());
		*/
		System.out.format("%d, %.4f%n", qald3_how.size(), (float) qald3_how.size() / qald3.size());
		System.out.format("%d, %.4f%n", qald3_yn.size(), (float) qald3_yn.size() / qald3.size());
		System.out.format("%d, %.4f%n", qald3_rqst.size(), (float) qald3_rqst.size() / qald3.size());
		System.out.format("%d, %.4f%n", qald3_top.size(), (float) qald3_top.size() / qald3.size());
		System.out.println();
		
		
		ArrayList<ArrayList<String>> qald4_wh = whHowQues(qald4, "wh");
		ArrayList<ArrayList<String>> qald4_how = whHowQues(qald4, "how");
		ArrayList<ArrayList<String>> qald4_yn = yesNoQues(qald4);
		ArrayList<ArrayList<String>> qald4_rqst = request(qald4);
		ArrayList<ArrayList<String>> qald4_top = topicalized(qald4);
		System.out.format("%d, %.4f%n", qald4_wh.size(), (float) qald4_wh.size() / qald4.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald4, "what").size(), (float) specWhQues(qald4, "what").size() / qald4.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald4, "which").size(), (float) specWhQues(qald4, "which").size() / qald4.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald4, "who ").size(), (float) specWhQues(qald4, "who ").size() / qald4.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald4, "whose").size(), (float) specWhQues(qald4, "whose").size() / qald4.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald4, "whom").size(), (float) specWhQues(qald4, "whom").size() / qald4.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald4, "where").size(), (float) specWhQues(qald4, "where").size() / qald4.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald4, "when").size(), (float) specWhQues(qald4, "when").size() / qald4.size());
		*/
		System.out.format("%d, %.4f%n", qald4_how.size(), (float) qald4_how.size() / qald4.size());
		System.out.format("%d, %.4f%n", qald4_yn.size(), (float) qald4_yn.size() / qald4.size());
		System.out.format("%d, %.4f%n", qald4_rqst.size(), (float) qald4_rqst.size() / qald4.size());
		System.out.format("%d, %.4f%n", qald4_top.size(), (float) qald4_top.size() / qald4.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald5_wh = whHowQues(qald5, "wh");
		ArrayList<ArrayList<String>> qald5_how = whHowQues(qald5, "how");
		ArrayList<ArrayList<String>> qald5_yn = yesNoQues(qald5);
		ArrayList<ArrayList<String>> qald5_rqst = request(qald5);
		ArrayList<ArrayList<String>> qald5_top = topicalized(qald5);
		System.out.format("%d, %.4f%n", qald5_wh.size(), (float) qald5_wh.size() / qald5.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald5, "what").size(), (float) specWhQues(qald5, "what").size() / qald5.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald5, "which").size(), (float) specWhQues(qald5, "which").size() / qald5.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald5, "who ").size(), (float) specWhQues(qald5, "who ").size() / qald5.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald5, "whose").size(), (float) specWhQues(qald5, "whose").size() / qald5.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald5, "whom").size(), (float) specWhQues(qald5, "whom").size() / qald5.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald5, "where").size(), (float) specWhQues(qald5, "where").size() / qald5.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald5, "when").size(), (float) specWhQues(qald5, "when").size() / qald5.size());
		*/
		System.out.format("%d, %.4f%n", qald5_how.size(), (float) qald5_how.size() / qald5.size());
		System.out.format("%d, %.4f%n", qald5_yn.size(), (float) qald5_yn.size() / qald5.size());
		System.out.format("%d, %.4f%n", qald5_rqst.size(), (float) qald5_rqst.size() / qald5.size());
		System.out.format("%d, %.4f%n", qald5_top.size(), (float) qald5_top.size() / qald5.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald6_wh = whHowQues(qald6, "wh");
		ArrayList<ArrayList<String>> qald6_how = whHowQues(qald6, "how");
		ArrayList<ArrayList<String>> qald6_yn = yesNoQues(qald6);
		ArrayList<ArrayList<String>> qald6_rqst = request(qald6);
		ArrayList<ArrayList<String>> qald6_top = topicalized(qald6);
		System.out.format("%d, %.4f%n", qald6_wh.size(), (float) qald6_wh.size() / qald6.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald6, "what").size(), (float) specWhQues(qald6, "what").size() / qald6.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald6, "which").size(), (float) specWhQues(qald6, "which").size() / qald6.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald6, "who ").size(), (float) specWhQues(qald6, "who ").size() / qald6.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald6, "whose").size(), (float) specWhQues(qald6, "whose").size() / qald6.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald6, "whom").size(), (float) specWhQues(qald6, "whom").size() / qald6.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald6, "where").size(), (float) specWhQues(qald6, "where").size() / qald6.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald6, "when").size(), (float) specWhQues(qald6, "when").size() / qald6.size());
		*/
		System.out.format("%d, %.4f%n", qald6_how.size(), (float) qald6_how.size() / qald6.size());
		System.out.format("%d, %.4f%n", qald6_yn.size(), (float) qald6_yn.size() / qald6.size());
		System.out.format("%d, %.4f%n", qald6_rqst.size(), (float) qald6_rqst.size() / qald6.size());
		System.out.format("%d, %.4f%n", qald6_top.size(), (float) qald6_top.size() / qald6.size());
		System.out.println();
		
		
		ArrayList<ArrayList<String>> qald7_wh = whHowQues(qald7, "wh");
		ArrayList<ArrayList<String>> qald7_how = whHowQues(qald7, "how");
		ArrayList<ArrayList<String>> qald7_yn = yesNoQues(qald7);
		ArrayList<ArrayList<String>> qald7_rqst = request(qald7);
		ArrayList<ArrayList<String>> qald7_top = topicalized(qald7);
		System.out.format("%d, %.4f%n", qald7_wh.size(), (float) qald7_wh.size() / qald7.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald7, "what").size(), (float) specWhQues(qald7, "what").size() / qald7.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald7, "which").size(), (float) specWhQues(qald7, "which").size() / qald7.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald7, "who ").size(), (float) specWhQues(qald7, "who ").size() / qald7.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald7, "whose").size(), (float) specWhQues(qald7, "whose").size() / qald7.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald7, "whom").size(), (float) specWhQues(qald7, "whom").size() / qald7.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald7, "where").size(), (float) specWhQues(qald7, "where").size() / qald7.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald7, "when").size(), (float) specWhQues(qald7, "when").size() / qald7.size());
		*/
		System.out.format("%d, %.4f%n", qald7_how.size(), (float) qald7_how.size() / qald7.size());
		System.out.format("%d, %.4f%n", qald7_yn.size(), (float) qald7_yn.size() / qald7.size());
		System.out.format("%d, %.4f%n", qald7_rqst.size(), (float) qald7_rqst.size() / qald7.size());
		System.out.format("%d, %.4f%n", qald7_top.size(), (float) qald7_top.size() / qald7.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald8_wh = whHowQues(qald8, "wh");
		ArrayList<ArrayList<String>> qald8_how = whHowQues(qald8, "how");
		ArrayList<ArrayList<String>> qald8_yn = yesNoQues(qald8);
		ArrayList<ArrayList<String>> qald8_rqst = request(qald8);
		ArrayList<ArrayList<String>> qald8_top = topicalized(qald8);
		System.out.format("%d, %.4f%n", qald8_wh.size(), (float) qald8_wh.size() / qald8.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald8, "what").size(), (float) specWhQues(qald8, "what").size() / qald8.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald8, "which").size(), (float) specWhQues(qald8, "which").size() / qald8.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald8, "who ").size(), (float) specWhQues(qald8, "who ").size() / qald8.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald8, "whose").size(), (float) specWhQues(qald8, "whose").size() / qald8.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald8, "whom").size(), (float) specWhQues(qald8, "whom").size() / qald8.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald8, "where").size(), (float) specWhQues(qald8, "where").size() / qald8.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald8, "when").size(), (float) specWhQues(qald8, "when").size() / qald8.size());
		*/
		System.out.format("%d, %.4f%n", qald8_how.size(), (float) qald8_how.size() / qald8.size());
		System.out.format("%d, %.4f%n", qald8_yn.size(), (float) qald8_yn.size() / qald8.size());
		System.out.format("%d, %.4f%n", qald8_rqst.size(), (float) qald8_rqst.size() / qald8.size());
		System.out.format("%d, %.4f%n", qald8_top.size(), (float) qald8_top.size() / qald8.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qald9_wh = whHowQues(qald9, "wh");
		ArrayList<ArrayList<String>> qald9_how = whHowQues(qald9, "how");
		ArrayList<ArrayList<String>> qald9_yn = yesNoQues(qald9);
		ArrayList<ArrayList<String>> qald9_rqst = request(qald9);
		ArrayList<ArrayList<String>> qald9_top = topicalized(qald9);
		System.out.format("%d, %.4f%n", qald9_wh.size(), (float) qald9_wh.size() / qald9.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qald9, "what").size(), (float) specWhQues(qald9, "what").size() / qald9.size());
		System.out.format("which %d, %.3f%n", specWhQues(qald9, "which").size(), (float) specWhQues(qald9, "which").size() / qald9.size());
		System.out.format("who %d, %.3f%n", specWhQues(qald9, "who ").size(), (float) specWhQues(qald9, "who ").size() / qald9.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qald9, "whose").size(), (float) specWhQues(qald9, "whose").size() / qald9.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qald9, "whom").size(), (float) specWhQues(qald9, "whom").size() / qald9.size());
		System.out.format("where %d, %.3f%n", specWhQues(qald9, "where").size(), (float) specWhQues(qald9, "where").size() / qald9.size());
		System.out.format("when %d, %.3f%n", specWhQues(qald9, "when").size(), (float) specWhQues(qald9, "when").size() / qald9.size());
		*/
		System.out.format("%d, %.4f%n", qald9_how.size(), (float) qald9_how.size() / qald9.size());
		System.out.format("%d, %.4f%n", qald9_yn.size(), (float) qald9_yn.size() / qald9.size());
		System.out.format("%d, %.4f%n", qald9_rqst.size(), (float) qald9_rqst.size() / qald9.size());
		System.out.format("%d, %.4f%n", qald9_top.size(), (float) qald9_top.size() / qald9.size());
		System.out.println();

		
		ArrayList<ArrayList<String>> qaldAll_wh = whHowQues(qaldAll, "wh");
		ArrayList<ArrayList<String>> qaldAll_how = whHowQues(qaldAll, "how");
		ArrayList<ArrayList<String>> qaldAll_yn = yesNoQues(qaldAll);
		ArrayList<ArrayList<String>> qaldAll_rqst = request(qaldAll);
		ArrayList<ArrayList<String>> qaldAll_top = topicalized(qaldAll);
		System.out.format("Number of wh-questions in qald is %d, and the percentage is %.4f%n", qaldAll_wh.size(), (float) qaldAll_wh.size() / qaldAll.size());
		/**
		System.out.format("what %d, %.3f%n", specWhQues(qaldAll, "what").size(), (float) specWhQues(qaldAll, "what").size() / qaldAll.size());
		System.out.format("which %d, %.3f%n", specWhQues(qaldAll, "which").size(), (float) specWhQues(qaldAll, "which").size() / qaldAll.size());
		System.out.format("who %d, %.3f%n", specWhQues(qaldAll, "who ").size(), (float) specWhQues(qaldAll, "who ").size() / qaldAll.size());
		System.out.format("whose %d, %.3f%n", specWhQues(qaldAll, "whose").size(), (float) specWhQues(qaldAll, "whose").size() / qaldAll.size());
		System.out.format("whom %d, %.3f%n", specWhQues(qaldAll, "whom").size(), (float) specWhQues(qaldAll, "whom").size() / qaldAll.size());
		System.out.format("where %d, %.3f%n", specWhQues(qaldAll, "where").size(), (float) specWhQues(qaldAll, "where").size() / qaldAll.size());
		System.out.format("when %d, %.3f%n", specWhQues(qaldAll, "when").size(), (float) specWhQues(qaldAll, "when").size() / qaldAll.size());
		*/
		System.out.format("Number of how-questions in qald is %d, and the percentage is %.4f%n", qaldAll_how.size(), (float) qaldAll_how.size() / qaldAll.size());
		System.out.format("Number of yes-no-questions in qald is %d, and the percentage is %.4f%n", qaldAll_yn.size(), (float) qaldAll_yn.size() / qaldAll.size());
		System.out.format("Number of requests in qald is %d, and the percentage is %.4f%n", qaldAll_rqst.size(), (float) qaldAll_rqst.size() / qaldAll.size());
		System.out.format("Number of topicalized questions in qald is %d, and the percentage is %.4f%n", qaldAll_top.size(), (float) qaldAll_top.size() / qaldAll.size());
		System.out.println();
		
		
		writeToFile(web, filepath, "WEB");
		writeToFile(graph, filepath, "GRAPH");
		writeToFile(lcquad, filepath, "LC-QUAD");
		writeToFile(qald1, filepath, "QALD_1");
		writeToFile(qald2, filepath, "QALD_2");
		writeToFile(qald3, filepath, "QALD_3");
		writeToFile(qald4, filepath, "QALD_4");
		writeToFile(qald5, filepath, "QALD_5");
		writeToFile(qald6, filepath, "QALD_6");
		writeToFile(qald7, filepath, "QALD_7");
		writeToFile(qald8, filepath, "QALD_8");
		writeToFile(qald9, filepath, "QALD_9");
		writeToFile(qaldAll, filepath, "QALD_ALL");
		
		
	}

}
