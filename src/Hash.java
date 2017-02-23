import java.io.*;
import java.util.Scanner;

/**
 * Created by Camille on 23/02/2017.
 */
public class Hash {
/*
    ● V (1 ≤ V ≤ 10000) - the number of videos
● E (1 ≤ E ≤ 1000) - the number of endpoints
● R (1 ≤ R ≤ 1000000) - the number of request descriptions
● C (1 ≤ C ≤ 1000) - the number of cache servers
● X (1 ≤ X ≤ 500000) - the capacity of each cache server in megabytes
*/



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
            String s= br.readLine();
            System.out.printf("%s\n", s);
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
    }/


    public static void main(String[] args) {
        Hash h = new Hash();
        String s= "C:\\Users\\Camille\\IdeaProjects\\hashcode2017\\data\\test";
        h.load(s);
    }
}
