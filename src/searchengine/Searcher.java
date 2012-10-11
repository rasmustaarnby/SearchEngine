/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author rasmus
 */
public class Searcher {

    public static boolean exists (HTMLlist file, String word) {
        while (file != null) {
            if (file.str.equals (word)) {
                return true;
            }
            file = file.next;
        }
        return false;
    }
    
    public static HTMLlist updateURL(HTMLlist url, String word){
        while(url != null){
            if(url.str.equals(word)){
                return url;
            }
            url = url.next;
        }
        return null;
    }
    
    public static void print(HTMLlist file) {
        UrlList k = file.urlLink; 
        while(k!= null){ 
            System.out.println(k.url);
            k=k.UrlNext;
        }   
    }
    
    public static HTMLlist contains(HTMLlist url, String word){
        while(url != null){
            if(url.str.contains(word)){ // would be nice if you could return the word as well
                return url;
            }
            url = url.next;
        }
        return null;
    }
    
    public static HTMLlist readHtmlList (String filename) throws IOException {
        String name;
        HTMLlist start = null, current = null, tmp, word;
        String url = null;

        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));
        name = infile.readLine(); //Read the first line
        
        while (name != null) { // Exit if there is nothing
            if (name.charAt(0) == '*'){ // url found
                url = name.substring(6); // the substring removes *PAGE:
                // System.out.println(url); // prints all urls 
            }else{ // we read a word from the file
                if(start == null){  
                    start = new HTMLlist(name, null, null); // start list of word
                    current = start;
                    current.urlLink = new UrlList(url, null); // start list of urls(UrlList) by the "name"
                }else{ // if we already have a list of words
                    word = Searcher.updateURL(start,name); // Running through all words and compare to url
                    if(word==null){
                       tmp = new HTMLlist(name, null, null);
                       current.next = tmp;
                       current = tmp; // Update the linked list
                       current.urlLink = new UrlList(url,null); // start list of urls(UrlList)
                    }else{
                        if(!word.urlLink.url.equals(url)){ // check if url is already in the UrlList 
                            UrlList newUrl = new UrlList(url,word.urlLink);// add url to the UrlList
                            word.urlLink = newUrl; 
                        }
                    }
                }
            }
            name = infile.readLine(); // Read the next line 
        }
        infile.close(); // Close the file
        return start;
    }
}
