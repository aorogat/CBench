/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import com.chilkatsoft.CkHttp;
import com.chilkatsoft.CkHttpRequest;
import com.chilkatsoft.CkHttpResponse;

public class TeBAQA{

  static {
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }

  public static void main(String argv[])
  {
    // This example requires the Chilkat API to have been previously unlocked.
    // See Global Unlock Sample for sample code.
      
      try {
    	System.load("/home/aorogat/Downloads/chilkat-9.5.0-jdk8-x64/chilkat.dll");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }

    CkHttp http = new CkHttp();

    CkHttpRequest req = new CkHttpRequest();

    // Add the form parameters to the HTTP request:
    req.AddParam("firstname","John");
    req.AddParam("lastname","Doe");
    req.AddParam("myHiddenField1","Hidden Value 1");
    req.AddParam("myHiddenField2","Hidden Value 2");

    // Send the HTTP POST by calling PostUrlEncoded.
    // The Content-Type used w/ the request will be application/x-www-form-urlencoded
    CkHttpResponse resp = http.PostUrlEncoded("http://www.chilkatsoft.com/echoPost.asp",req);
    if (http.get_LastMethodSuccess() == false) {
        System.out.println(http.lastErrorText());
        return;
        }

    // Display the Body of the HTTP resp
    // (This is the HTML that the browser would receive if it
    // had been an interactive form submission from the browser.)
    System.out.println(resp.bodyStr());
  }
}
