package testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import qa.parsers.ParsersTest;

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(ParsersTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println("No failures in testing..." + result.wasSuccessful());

	}

}
