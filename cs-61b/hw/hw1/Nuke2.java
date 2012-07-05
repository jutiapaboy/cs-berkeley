/* cs61b-fg, toa@berkeley.edu, Annie To */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Nuke2 {

          public static void main(String[] arg) throws Exception {

                    BufferedReader keyboard;
                    String inputLine;

                    keyboard = new BufferedReader(new InputStreamReader(System.in));

                    System.out.print("");
                    System.out.flush();  /* Make sure the line is printed immediately. */
                    inputLine = keyboard.readLine();

                    int n = inputLine.length();
                    String first = new String(inputLine.substring(0, 1));
                    String rest = new String (inputLine.substring(2, n));

                    System.out.println(first + rest);
          }
}


