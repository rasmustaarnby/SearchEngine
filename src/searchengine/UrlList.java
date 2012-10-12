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
