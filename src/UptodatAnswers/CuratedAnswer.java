package UptodatAnswers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CuratedAnswer {

    public static String endpoint = "http://dbpedia.org/sparql?";
    public static String wikidataEndpoint = "https://query.wikidata.org/bigdata/namespace/wdq/sparql?query=";

    public static void main(String[] args) throws UnsupportedEncodingException {
        upToDateAnswerDBpedia("ASK WHERE { <http://dbpedia.org/resource/Ganymede_(moon)> <http://dbpedia.org/property/discoverer> <http://dbpedia.org/resource/Galileo_Galilei> }", "dbpedia");
    }

    public static ArrayList upToDateAnswer(String query, String kg) throws UnsupportedEncodingException {
        if (kg.toLowerCase().equals("dbpedia")) {
            return upToDateAnswerDBpedia(query, kg);
        } else if (kg.toLowerCase().equals("freebase")) {
            return upToDateAnswerFreebase(query, kg);
        } else if (kg.toLowerCase().equals("wikidata")) {
            return upToDateAnswerWikidata(query, kg);
        } else {
            return null;
        }
    }

    public static ArrayList upToDateAnswerFreebase(String query, String kg) throws UnsupportedEncodingException {
        return upToDateAnswerDBpedia(query, kg);
    }

    public static ArrayList upToDateAnswerWikidata(String query, String kg) throws UnsupportedEncodingException {
        ArrayList<String> answersList = new ArrayList<>();
        if (query.equals("") || query == null) {
            return answersList;
        }
        String url = wikidataEndpoint
                + URLEncoder.encode(query, StandardCharsets.UTF_8.toString());

        String xmlText = "";

        //Read XML file
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            xmlText = readAll(rd);
            //System.out.println(xmlText);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Communication Failed");
        }

        //Get result from XML data
        Document doc = null;
        Element root = null;
        try {
            doc = loadXMLFromString(xmlText);
            root = doc.getDocumentElement();
            //System.out.println("root:: " + root.toString());
            NodeList all = root.getElementsByTagName("uri");
            //System.out.println("uris:: " + all.toString());

            for (int i = 0; i < all.getLength(); i++) {
                String c = all.item(i).getTextContent()
                        .replace("https://en.wikipedia.org/wiki/", "")
                        .replace("http://www.wikidata.org/entity/", "");
                answersList.add(c);
                //System.out.println("c:: " + c);
            }
        } catch (Exception ex) {
            try {
                NodeList all = root.getElementsByTagName("literal");
                //System.out.println("literals:: " + all.toString());

                for (int i = 0; i < all.getLength(); i++) {
                    String c = all.item(i).getTextContent()
                            .replace("https://en.wikipedia.org/wiki/", "")
                            .replace("http://www.wikidata.org/entity/", "");
                    answersList.add(c);
                    //System.out.println("c:: " + c);
                }
            } catch (Exception e) {
                System.out.println("Cannot parse xml");
            }

        }

        return answersList;
    }

    public static ArrayList upToDateAnswerDBpedia(String query, String kg) throws UnsupportedEncodingException {
        ArrayList<String> answersList = new ArrayList<>();
        String url = endpoint
                + "default-graph-uri=http%3A%2F%2Fdbpedia.org&"
                + "query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) + "&"
                + "format=application%2Fsparql-results%2Bjson&"
                + "timeout=0&"
                + "debug=on";
        try {
            JSONObject json = readJsonFromUrl(url);
            try {

                JSONArray vars = json.getJSONObject("head").getJSONArray("vars");
                JSONArray bindings = json.getJSONObject("results").getJSONArray("bindings");

                for (Object var : vars) {
                    for (Object binding : bindings) {
                        try {
                            String v = (String) var;
                            JSONObject b = (JSONObject) binding;
                            answersList.add(b.getJSONObject(v).getString("value")
                                    .replace("http://dbpedia.org/resource/", "")
                                    .trim().replace('_', ' '));

                        } catch (Exception ex) {
                            return answersList;
                        }
                    }
                }

            } catch (Exception e) {
                try {
                    //Boolean
                    if (json.getBoolean("boolean")) {
                        answersList.add("Yes");
                    } else {
                        answersList.add("No");
                    }

                } catch (Exception et) {
                }
            }
        } catch (Exception ee) {
        }
        try {
        } catch (Exception e) {
        }
        return answersList;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

}
