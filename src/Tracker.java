import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Tracker {
    String mmr="null";

    public Tracker(int x,String number) {
        boolean printnext=false;

        String site="https://rocketleague.tracker.network/rocket-league/profile/steam/"+number+"/mmr?playlist="+x;
        try {

            Document doc = Jsoup.connect(site).get() ;


            Elements paragraphs = doc.select(".value");
            for(Element p : paragraphs) {
                // System.out.println(p.text());
                if (printnext){
                    printnext=false;
                    mmr=p.text();
                }
                if (p.text().contains("Division")){
                    printnext=true;
                }
            }
            //System.out.println(doc.body());
        }catch (IOException e){
            System.out.println(e);
        }
        System.out.println(mmr);
        //return mmr;
    }

    public String getMmr() {
        return mmr;
    }


    public static void main(String[] args ){
        boolean printnext=false;
        String mmr="null";

        try {
            Document doc = Jsoup.connect("https://rocketleague.tracker.network/rocket-league/profile/steam/76561198176055490/mmr?playlist=11").get() ;


            Elements paragraphs = doc.select(".value");
            for(Element p : paragraphs) {
                // System.out.println(p.text());
                if (printnext){
                    printnext=false;
                    mmr=p.text();
                }
                if (p.text().contains("Division")){
                    printnext=true;
                }
            }
            //System.out.println(doc.body());
        }catch (IOException e){
            System.out.println(e);
        }
        System.out.println(mmr);
    }
}
