/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
   Updated by Rasmus Taarnby <reta@itu.dk>
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
        String word, wordSecond;
        args = new String[1];
        args[0] = "small.txt";
        
        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchEngine <datafile>");
            System.exit(1);
        }
        
        HTMLlist file = Searcher.readHtmlList (args[0]);
        
        // Ask for a singleWord to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        while (!quit) {
            System.out.print ("Search for: ");
            word = inuser.readLine(); // Read a line from the terminal
            
            // Advanced Task
            if(word.matches(".*\\band\\b.*")){
                StringTokenizer tokenAnd = new StringTokenizer(word, " and ");
                while(tokenAnd.hasMoreTokens()){
                try{
                    word = tokenAnd.nextToken(); // Go to first singleWord
                    wordSecond = word; // Set the first singleWord as second 
                    word = tokenAnd.nextToken(); // Go to the next singleWord and set it as word
                    if (Searcher.exists(file,word) && Searcher.exists(file,wordSecond)) { // can we maybe use a for loop or an arraylist to feed several words to look for? 
                        HTMLlist result = Searcher.updateURL(file, word);
                        System.out.println ("\nThe combination " + wordSecond + " and " + word + " has been found, in the following urls:");
                        Searcher.print(result);
                    } else {
                        System.out.println ("\nThe combination " + wordSecond + " and " + word + " has NOT been found.");
                    }
                }catch(NoSuchElementException e){
                    System.out.println("The searchengine only supports two words at the time."); // Is there a way to get the last unused singleWord and display it?
                }
                }
                System.out.println("\nYou're using AND\n");
            }else if(word.matches(".*\\bor\\b.*")){
                String[] wordArray = word.split("or");
                for(String singleWord: wordArray){
                    String wordWithNoSpace = singleWord.replaceAll("\\s","");
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
                if (word == null || word.length() == 0) {
                    quit = true;
                } else if (Searcher.exists(file, word)) {
                    HTMLlist result = Searcher.updateURL(file, word);
                    System.out.println ("\nThe word " + word + " has been found, in the following urls:");
                    Searcher.print(result);
                } else {
                    System.out.println ("The exact word " + word + " has NOT been found.");
                    
                    
                    HTMLlist result = Searcher.contains(file, word);
                    if(result != null){
                        System.out.println("\nBut we found a word that was pretty close to, here are the urls");
                        Searcher.print(result); // might return a NullPointerException
                    }
                }
            }
        }
    }
}
