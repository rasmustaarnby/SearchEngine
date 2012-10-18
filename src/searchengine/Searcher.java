package searchengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author rasmus
 */
public class Searcher {

    public static boolean exists (HTMLlist file, String word) {
        while (file != null) {
            if (file.str.equalsIgnoreCase(word)) {
                return true;
            }
            file = file.next;
        }
        return false;
    }
    
    public static HTMLlist updateURL(HTMLlist url, String word){
        while(url != null){
            if(url.str.equalsIgnoreCase(word)){
                return url;
            }
            url = url.next;
        }
        return null;
    }
    
    public static ArrayList print(HTMLlist file) {
        ArrayList returnUrlList = new ArrayList();
        String returnUrl;
        UrlList url = file.urlLink; 
        while(url!= null){ 
            returnUrlList.add(url.url);
            url=url.UrlNext;
        }
        return returnUrlList;
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
    
    
    public static void toArray(ArrayList list){
        for(Object url: list){
            System.out.println(url);
        }
            String sQueue = "";   
            // traverse queue and build string with all items    
            for(int i=0; i<QUEUE_SIZE; i++)
                    {
                     sItem = queue [i] + "--";   
                     sQueue = sQueue + sItem; 
                     //System.out.println("Count is: " + i);
                     }
            //System.out.println(sQueue);
            msg = sQueue;  
               }
    }
    
    public static HTMLlist readHtmlList (String filename) throws IOException {
        String word;
        HTMLlist start = null, current = null, tmp, urlObj;
        String urlString = null;

        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));
        word = infile.readLine(); //Read the first line
        
        while (word != null) { // Exit if there is nothing
            if (word.charAt(0) == '*'){ // urlString found
                urlString = word.substring(6); // the substring removes *PAGE:
                // System.out.println(urlString); // prints all urls 
            }else{ // we read a urlObj from the file
                if(start == null){ 
                    start = new HTMLlist(word, null, null); // create new list from word
                    current = start;
                    current.urlLink = new UrlList(urlString, null); // create new list of url containing the word
                    // Searcher.print(start); // prints urlObj (urls)
                }else{ // if we already have a list of the word
                    urlObj = Searcher.updateURL(start,word); // compare word to urlObj
//                    if(urlObj != null){
//                        Searcher.print(urlObj); // prints urls 
//                        System.out.println(word); // prints words 
//                    }
                    if(urlObj==null){ // if word doesn't exist under any url, create new
                       tmp = new HTMLlist(word, null, null);
                       // System.out.println(word); // prints all words 
                       current.next = tmp;
                       current = tmp; // Update the linked list
                       current.urlLink = new UrlList(urlString,null); 
                       // Searcher.print(current); // prints urls several times
                    }else{ // add word to the correct url
                        if(!urlObj.urlLink.url.equals(urlString)){ // check if urlObj is already in the UrlList 
                            UrlList newUrl = new UrlList(urlString,urlObj.urlLink);// add urlObj to the UrlList
                            urlObj.urlLink = newUrl; 
                        }
                    }
                }
            }
            word = infile.readLine(); // Read the next line 
        }
        infile.close(); // Close the file
        return start;
    }
}
