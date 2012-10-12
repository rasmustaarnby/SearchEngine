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

