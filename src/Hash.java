import java.io.*;
import java.util.Scanner;

/**
 * Created by Camille on 23/02/2017.
 */
public class Hash {


    int nbVideo = 0;
    int nbEndpoints = 0;
    int nbRequestDescription = 0;
    int nbCaches = 0;
    int defaultSizeCache = 0;

    public Hash() {

    }

    private int getInt(final String value) {
        return Integer.parseInt(value);
    }

    private String[] getNextLineParts(final BufferedReader br) throws IOException {
        String line = br.readLine();
        return line.split(" ");
    }

    public boolean load(String name) {
        String s1= name + ".in";
        System.out.printf("%s\n", s1);
        try (BufferedReader br = new BufferedReader(new FileReader(name + ".in"))) {


          // Read first line
          String s= br.readLine();
          System.out.printf("%s\n", s);

          String[] parts = s.split(" ");

          nbVideo = Integer.parseInt(parts[0]);
          nbEndpoints = Integer.parseInt(parts[1]);
          nbRequestDescription = Integer.parseInt(parts[2]);
          nbCaches = Integer.parseInt(parts[3]);
          defaultSizeCache = Integer.parseInt(parts[4]);
          System.out.printf("Encoded : %d %d %d %d %d \n", nbVideo, nbEndpoints, nbRequestDescription, nbCaches, defaultSizeCache );






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(String name, String contents) {
        try {

            FileWriter fw = new FileWriter(name + ".out");


            fw.write(contents);
            fw.write(contents + "\n");
            fw.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void main(String[] args) {
        Hash h = new Hash();
        //String s= "C:\\Users\\Camille\\IdeaProjects\\hashcode2017\\data\\test";
        String s= "/home/loic/Bureau/hashcode2017/data/me_at_the_zoo";
        h.load(s);
    }
}
