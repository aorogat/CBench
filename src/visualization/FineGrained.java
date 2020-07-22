package visualization;

import DataSet.DataSetPreprocessing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FineGrained {

    public static void main(String[] args) throws IOException, InterruptedException {
        visualize();
    }
    
    public static void visualize() throws IOException, InterruptedException 
    {
        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       ++++++++++++++++++++");
        System.out.println("++++++> Data Visualization +");
        System.out.println("        ++++++++++++++++++++");
        Scanner in = new Scanner(System.in);
        String currentDirectory = System.getProperty("user.dir");
        //System.out.println("The current working directory is " + currentDirectory + "/visualize.py");

        //System.out.println("cd " + currentDirectory);
        //Process p = Runtime.getRuntime().exec("cd "+currentDirectory); //There is no cd from java program
        //System.out.println("python3 " + currentDirectory + "/visualize.py");
        System.out.println("\n\n"); 
        System.out.println("        |");
        System.out.println("        ++++> Enter your pthon3 path on your computer (for example /home/xxxx/anaconda3/bin)");
        System.out.print("              Your answer is: ");
        String python = "/home/aorogat/anaconda3/bin";
        python = in.next();
        Process p = Runtime.getRuntime().exec(python+"/python3 " + DataSetPreprocessing.currentDirectory + "/visualize.py");

        p.waitFor();
        
       BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

//             read the output from the command
            String s = null;
            System.out.println("Python:: Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
//             read any errors from the attempted command
            System.out.println("Python:: Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
    }

}
