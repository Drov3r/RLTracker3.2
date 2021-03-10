import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Logger {
    static boolean  first=true;

    static int current=2;
    static boolean[] change=new boolean[7];

    //static String number = "76561198176055490";
    static  String [] Players={"76561198176055490","76561198275589508","76561198148742258","76561198271159103"};
    public static void main(String[] args ) throws IOException {

        //while (true) {
        for(String number:Players) {
            int[] playlist = {10, 11, 13, 27, 28, 29, 34};
            String[] playlistNames = {"Solo", "Twos", "Threes", "Hoops", "Rumble", "DropShot", "Tournaments"};

            for (boolean x : change) x = false;
            //"76561198176055490"   mine


            int[] current = new int[7];
            int i = 0;
            int j = 0;
            writeFile(number, playlistNames);
            String PATH = number + "/";

            for (String x : playlistNames) {
                int num;
                BufferedReader reader;
                try {
                    String fileName = x + ".txt";
                    reader = new BufferedReader(new FileReader(PATH + "/" + fileName));
                    String line = reader.readLine();
                    String l = line;
                    while (line != null) {
                        //System.out.println(line);
                        // read next line
                        line = reader.readLine();
                        if (line != null) {
                            l = line;

                        }
                    }
                    //System.out.println(playlistNames[j]);
                    if (l.contains(",")) {
                        num = removeComma(l);
                    } else num = Integer.parseInt(l);
                    current[j] = num;
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                j++;
            }


            for (int x : playlist) {
                Tracker mmr = new Tracker(x, number);
                String rank = mmr.getMmr();
                int num;
                if (rank.contains(",")) {
                    num = removeComma(rank);
                } else num = Integer.parseInt(rank);

                if (num != current[i]) {
                    change[i] = true;
                    try (FileWriter fw = new FileWriter(PATH + playlistNames[i] + ".txt", true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        out.println(num);
                        current[i] = removeComma(rank);

                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    try (FileWriter fw = new FileWriter(PATH + playlistNames[i] + "time.txt", true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println(dtf.format(now));

                        out.println(dtf.format(now));

                    } catch (IOException e) {
                        //exception handling left as an exercise for the reader
                    }
                }
                i++;


            }
//            try {
//                makeGraph(number, playlistNames);
//                System.out.println("Sleeping");
//                TimeUnit.MINUTES.sleep(4);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //while true }
        }
    }

    private static void makeGraph(String number,String[] pN ) {
        List<Double> list=new ArrayList<Double>();
        for (int i = 0; i <change.length; i++) {
            if(change[i]){
                current=i;
                change[i]=false;
            }
        }
        String PATH = number + "/";

        BufferedReader reader;
        try {
            String fileName = pN[current] + ".txt";
            reader = new BufferedReader(new FileReader(PATH + "/" + fileName));
            String line = reader.readLine();
            String l = line;
            while (line != null) {
                //System.out.println(line);
                // read next line
                list.add(Double.parseDouble(line));
                line = reader.readLine();


            }


        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static int removeComma(String rank){
        char []t=rank.toCharArray();
        char []r=new char[t.length-1];
        boolean found=false;
        for (int k = 0; k < t.length-1 ; k++) {
            if (t[k]==','){
                found=true;
            }
            if (found){
                t[k]=t[k+1];
            }
            //t[t.length-1]= 48;

            for (int m = 0; m <r.length ; m++) {
                r[m]=t[m];

            }
        }
        String l= new String(r);
        return Integer.parseInt(l);
    }
    public static void writeFile(String number, String[] name) throws IOException {
        String PATH = number+"/";
        File directory = new File(PATH);

        if (! directory.exists()){
            directory.mkdir();
            for(String x:name){
                String fileName=x+".txt";
                File file = new File(PATH  + fileName);
                if(file.exists()){
                    file.createNewFile();
                }else {
                    System.out.println(fileName+"Exists");
                }
            }
        }
    }
}
