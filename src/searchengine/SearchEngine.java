/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
*/
package searchengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *
 * @author rasmus
 */
public class SearchEngine {

    /**
     * @param args the command line arguments
     */
    
    public static void main (String[] args) throws IOException {
        String name, nameSecond;
        args = new String[1];
        args[0] = "small.txt";
        
        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchEngine <datafile>");
            System.exit(1);
        }
        
        HTMLlist file = Searcher.readHtmlList (args[0]);
        
        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            
            // Advanced Task 1
            if(name.matches(".*\\band\\b.*")){
                StringTokenizer tokenAnd = new StringTokenizer(name, " and ");
                while(tokenAnd.hasMoreTokens()){
                try{
                    //System.out.println(tokenAnd.nextToken()); // prints the tokens (words)
                    name = tokenAnd.nextToken(); // Go to first word
                    nameSecond = name; // Set the first word as second 
                    name = tokenAnd.nextToken(); // Go to the next word and set it as name
                    if (Searcher.exists(file,name) && Searcher.exists(file,nameSecond)) { // can we maybe use a for loop or an arraylist to feed several words to look for? 
                        HTMLlist result = Searcher.updateURL(file, name);
                        System.out.println ("\nThe combination " + nameSecond + " and " + name + " has been found, in the following urls:");
                        Searcher.print(result);
                    } else {
                        System.out.println ("\nThe combination " + nameSecond + " and " + name + " has NOT been found.");
                    }
                }catch(NoSuchElementException e){
                    System.out.println("The searchengine only supports two words at the time."); // Is there a way to get the last unused word and display it?
                }
                }
                System.out.println("\nYou're using AND\n");
            }else if(name.matches(".*\\bor\\b.*")){
                // StringTokenizer tokenOr = new StringTokenizer(name, "or"); // splits the word incorrectly
                // while(tokenOr.hasMoreTokens()){
                String[] wordArray = name.split("or");
                for(String word: wordArray){
                    String wordWithNoSpace = word.replaceAll("\\s","");
                    //System.out.println(tokenOr.nextToken()); // prints the tokens (words)
                    //name = tokenOr.nextToken(); // Go through the words
                    if (Searcher.exists(file,wordWithNoSpace)) {
                        HTMLlist result = Searcher.updateURL(file, wordWithNoSpace);
                        System.out.println ("\nThe word " + wordWithNoSpace + " has been found, in the following urls:");
                        Searcher.print(result);
                    } else {
                        System.out.println ("\nThe word " + wordWithNoSpace + " has NOT been found.");
                    }
                }
                System.out.println("\nYou're using OR\n");
            }else{
                if (name == null || name.length() == 0) {
                    quit = true;
                } else if (Searcher.exists(file, name)) {
                    HTMLlist result = Searcher.updateURL(file, name);
                    System.out.println ("\nThe word " + name + " has been found, in the following urls:");
                    Searcher.print(result);
                } else {
                    System.out.println ("The exact word " + name + " has NOT been found.");
                    
                    
                    HTMLlist result = Searcher.contains(file, name);
                    if(result != null){
                        System.out.println("\nBut we found a word that was pretty close to, here are the urls");
                        Searcher.print(result); // might return a NullPointerException
                    }
                }
            }
        }
    }
}
