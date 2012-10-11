/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

/**
 *
 * @author rasmus
 */
public class UrlList {
    String url;
    UrlList UrlNext;
  
    UrlList(String s, UrlList n) {
        url=s;
        UrlNext=n;
    }
}
