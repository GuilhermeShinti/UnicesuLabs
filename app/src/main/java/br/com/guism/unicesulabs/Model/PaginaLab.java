package br.com.guism.unicesulabs.Model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Guilherme Shinti on 23/05/2017.
 */

public class PaginaLab extends Pagina{
    static String aula;
    static Element labo;

    public PaginaLab(Document html){
        super(html);
    }

    public static void getPaginaLabs(){

        Document doc = Jsoup.parse(html.toString());
        Elements blocos = doc.select("table[class=bloco]");
        Element bloco;
        String nBloco;
        Elements labs;
        String nLab;

        for (int i = 0; i < blocos.size(); i++){

            bloco = blocos.get(i);
            nBloco = bloco.select("tr").get(0).select("th").get(0).html();

            labs = bloco.select("table[class=tableReserva");
            for (int j = 0; j < labs.size();j++){

                labo = labs.get(j);
                nLab = labo.select("tr").get(0).select("td").html();
                Lab.array.add(new Lab(nBloco, nLab, aula(1), aula(2)));
                Log.d("nLAB","-->"+nBloco +" - "+ nLab +" - "+ aula(1) +" - "+ aula(2));

            }

        }
    }

    public static String aula(int pos){

        if (labo.select("tr").get(pos).select("div[class=reserva ]").select("span").hasClass("disponivel")) {
            aula = labo.select("tr").get(pos).select("div[class=reserva ]").select("span").text();
        }else {
            aula = labo.select("tr").get(pos).select("div[class=reserva ]").text();
        }
        return aula;

    }
}
