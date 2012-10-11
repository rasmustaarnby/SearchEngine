/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

/**
 *
 * @author rasmus
 */
 
public class HTMLlist {
    String str;
    HTMLlist next;
    UrlList urlLink;

    HTMLlist (String s, UrlList u, HTMLlist n) {
        str = s;
        next = n;
        urlLink=u;
    }
}

