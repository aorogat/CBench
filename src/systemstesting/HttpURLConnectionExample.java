package systemstesting;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HttpURLConnectionExample {

    public static void main(String[] args) throws Exception {
        HttpURLConnectionExample obj = new HttpURLConnectionExample();
        //obj.sendGet();
        obj.sendPost();
    }

    private void sendGet() throws Exception {

        String question = "What is the capital of Cameroon";
        String url = "http://start.csail.mit.edu/justanswer.php?query="+question;

        Document doc = Jsoup.connect(url).get();
        Elements div = doc.select("p span a");
        System.out.println(div.get(0).text());
    }

    private void sendPost() throws Exception {

        String url = "https://qanswer-core1.univ-st-etienne.fr/api/gerbil";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "query=Who is the wife of Barack Obama";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println(response.toString());

        }

    }

}
