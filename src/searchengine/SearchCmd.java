/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class SearchCmd {

    public static String SearchOptions(String word, String datafile) throws IOException{
        String wordSecond, urls;
        String[] args;
        args = new String[1];
        args[0] = datafile;
        
        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchEngine <datafile>");
            // System.exit(0);
        }
        
        HTMLlist file = Searcher.readHtmlList (args[0]);
        
        // Ask for a single word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));
        boolean quit = false;
        while (!quit) {

            // Advanced Task
            if(word.matches(".*\\band\\b.*")){
                // String and = "\nYou're using AND\n";
                StringTokenizer tokenAnd = new StringTokenizer(word, " and ");
                while(tokenAnd.hasMoreTokens()){
                    try{
                        word = tokenAnd.nextToken(); // Go to first singleWord
                        wordSecond = word; // Set the first singleWord as second 
                        word = tokenAnd.nextToken(); // Go to the next singleWord and set it as word
                        if (Searcher.exists(file,word) && Searcher.exists(file,wordSecond)) { // can we maybe use a for loop or an arraylist to feed several words to look for? 
                            HTMLlist result = Searcher.updateURL(file, word);
                            urls = Searcher.compileString(result);
                            return "The combination " + wordSecond + " and " + word + " has been found, in the following urls:\n" + urls;
                        } else {
                            return "The combination " + wordSecond + " and " + word + " has NOT been found.";
                        }
                    }catch(NoSuchElementException e){
                        return "The searchengine only supports two words at the time."; // Is there a way to get the last unused singleWord and display it?
                    }
                }
            }else if(word.matches(".*\\bor\\b.*")){
                // String or = "\nYou're using OR\n";
                String[] wordArray = word.split("or");
                for(String singleWord: wordArray){
                    String wordWithNoSpace = singleWord.replaceAll("\\s","");
                    if (Searcher.exists(file,wordWithNoSpace)) {
                        HTMLlist result = Searcher.updateURL(file, wordWithNoSpace);
                        urls = Searcher.compileString(result);
                        return "The word " + wordWithNoSpace + " has been found, in the following urls:\n" + urls;
                    } else {
                        return "The word " + wordWithNoSpace + " has NOT been found.";
                    }
                }
            }else{
                if (word == null || word.length() == 0) {
                    quit = true;
                } else if (Searcher.exists(file, word)) {
                    HTMLlist result = Searcher.updateURL(file, word);
                    urls = Searcher.compileString(result);
                    return "The word " + word + " has been found, in the following urls:\n" + urls;
                } else {
                    HTMLlist result = Searcher.contains(file, word);
                    urls = Searcher.compileString(result); // might return a NullPointerException
                    if(result != null){
                       return "The exact word " + word + " has NOT been found." + 
                              "\nBut we found a word that was pretty close to, here are the urls\n" + urls;
                    }else{
                       return "The exact word " + word + " has NOT been found.";
                    }       
                }
            }
        }
        return null;
    }
    
}
