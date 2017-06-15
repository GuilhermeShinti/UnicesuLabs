package br.com.guism.unicesulabs.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class Pagina {

    protected static String html;
    protected Document document;

    Pagina(Document html) {
        this.html = html.toString();
        document = Jsoup.parse(this.html);
    }

}
