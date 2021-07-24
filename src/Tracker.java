import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Tracker {


    public Tracker(String number) {
    }

    public static int[] Tracker(String number,String plat) {
        int ones=0,twos=0,threes=0,tourny=0;
        try {
            Document doc = Jsoup.connect("https://api.yannismate.de/rank/"+plat+"/"+number+"?playlists=ranked_1v1,ranked_2v2,ranked_3v3,TOURNAMENTS").get() ;


            Elements paragraphs = doc.select("body");
            for(Element p : paragraphs) {
                //System.out.println(p.text());
                String str=p.toString();
                char [] text=str.toCharArray();
                int count=0;

                boolean record=false;
                for (int i = 0; i < text.length; i++) {
                    int start,finish;
                    if(text[i]=='('){
                        start=i;
                        for (int j = i; j < i+6; j++) {
                            if(text[j]==')') {
                                finish=j;
                                if (count==0)ones=getMMR(text,start,finish);
                                if (count==1)twos=getMMR(text,start,finish);
                                if (count==2)threes=getMMR(text,start,finish);
                                if (count==3)tourny=getMMR(text,start,finish);
                                count++;
                            }
                        }
                    }
                }
            }


            //System.out.println(doc);
        }catch (IOException e){
            System.out.println(e);
        }
        int mmr[]= new int[4];
        mmr[0]=ones;
        mmr[1]=twos;
        mmr[2]=threes;
        mmr[3]=tourny;
        return mmr;
    }




    public static void main(String[] args ){
        boolean printnext=false;

        try {
            Document doc = Jsoup.connect("https://api.yannismate.de/rank/steam/76561198176055490?playlists=ranked_1v1,ranked_2v2,ranked_3v3,TOURNAMENTS").get() ;


            Elements paragraphs = doc.select("body");
            for(Element p : paragraphs) {
                System.out.println(p.text());
                String str=p.toString();
                char [] text=str.toCharArray();
                int count=0;
                int ones,twos,threes,tourny;
                boolean record=false;
                for (int i = 0; i < text.length; i++) {
                    int start,finish;
                    if(text[i]=='('){
                        start=i;
                        for (int j = i; j < i+6; j++) {
                            if(text[j]==')') {
                                finish=j;
                                if (count==0)ones=getMMR(text,start,finish);
                                if (count==1)twos=getMMR(text,start,finish);
                                if (count==2)threes=getMMR(text,start,finish);
                                if (count==3)tourny=getMMR(text,start,finish);
                                count++;
                            }
                        }
                    }
                }
            }


            System.out.println(doc);
        }catch (IOException e){
            System.out.println(e);
        }

    }

    private static int getMMR(char[] text, int start, int finish) {
        int mmr;
        char[] temp= new char[finish-start-1];
        for (int i = 0; i < finish-start-1; i++) {
            temp[i]=text[start+i+1];
        }
        mmr=Integer.parseInt(String.copyValueOf(temp));
        //System.out.println(mmr);
        return mmr;
    }
}
