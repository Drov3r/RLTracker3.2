import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Logger {
    static boolean  first=true;

    static int current=2;
    static boolean[] change=new boolean[7];

    //static String number = "76561198176055490";
    static  String [] Players;
    static  String [] Platform;
    static String[] playlistNames = {"Solo", "Twos", "Threes", "Tournaments"};
    public static void main(String[] args ) throws IOException {
        String [][] records=new String[10][2];
        int c=0;
        try (BufferedReader br = new BufferedReader(new FileReader("check.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records[c]=values;
                //System.out.println(values);
                c++;
            }
        }

        Players=new String[records.length];
        Platform=new String[records.length];
        for (int i = 0; i < records.length; i++) {
                Players[i]=records[i][0];
                Platform[i]=records[i][1];

        }
        c=0;
        for(String number :Players) {
            int[] mmr = Tracker.Tracker(number,Platform[c]);
            System.out.println(Arrays.toString(mmr));
            for (int i = 0; i < mmr.length; i++) {
                writeFile(number,mmr[i],i);
            }
            c++;
        }
    }


    public static void writeFile(String number,int mmr,int i) throws IOException {
        String PATH = number+"/";
        File directory = new File(PATH);

        if (!directory.exists()){
            directory.mkdir();
        }
                String fileName=playlistNames[i]+".txt";
                String filepath=PATH+fileName;
                //System.out.println(filepath);
                File file = new File(filepath);
                if(!file.exists()){
                    file.createNewFile();
                }else {
                    //System.out.println(fileName+"Exists");
                }

        FileWriter fr = new FileWriter(file, true);
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(filepath));
        String line = reader.readLine();
        int data=0;
        try {

            while (line != null) {
                data=Integer.parseInt(line);
                System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data!=mmr&&mmr!=0)
            fr.write(String.valueOf(mmr)+'\n');

        fr.close();

        }
    }
