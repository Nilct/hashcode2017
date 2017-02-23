import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;

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




    int nbVideo = 0;
    int nbEndpoints = 0;
    int nbRequestDescription = 0;
    int nbCaches = 0;
    int defaultSizeCache = 0;
    ArrayList<Video> listVideo = new ArrayList<Video>();
    ArrayList<EndPoint> listEndPoint = new ArrayList<EndPoint>();
    ArrayList<Cache> listCache = new ArrayList<Cache>();

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

          for(int c = 0; c < nbCaches ; c++){
            listCache.add(new Cache(c, defaultSizeCache));
          }

          // Read second line
          s= br.readLine();
          System.out.printf("%s\n", s);
          parts = s.split(" ");

          for(int c = 0; c < parts.length ; c++){
            listVideo.add(new Video(c, Integer.parseInt(parts[c])));
          }

          System.out.printf("%d Videos detected \n", listVideo.size());
          System.out.printf("First video : id : %d, size : %d Mb\n", listVideo.get(0).id, listVideo.get(0).size);
          System.out.printf("Last video : id : %d, size : %d Mb\n", listVideo.get(listVideo.size()-1).id, listVideo.get(listVideo.size()-1).size);

          // Read Endpoints entries
          for(int c = 0; c < nbEndpoints ; c++){
            // Read endpoints line initializer

            s= br.readLine();
            System.out.printf("%s\n", s);
            parts = s.split(" ");
            int latencyDatacenter = Integer.parseInt(parts[0]);
            int nbCachesConnected = Integer.parseInt(parts[1]);
            EndPoint endpointFactory =  new EndPoint(c, latencyDatacenter, nbCaches);

            System.out.printf("Endpoint crée : %d, latency : %d\n", endpointFactory.id, endpointFactory.latencyDatacenter);
            for (int cache = 0; cache < nbCachesConnected; cache++) {
              String detail = br.readLine();
              String[] partsDetail = detail.split(" ");
              int fromCacheID = Integer.parseInt(partsDetail[0]);
              int latencyValue = Integer.parseInt(partsDetail[1]);
              endpointFactory.changeLatency(fromCacheID, latencyValue);
            }

            System.out.printf("Endpoint %d : countNotDefault = %d\n", endpointFactory.id, endpointFactory.countNotDefault());
            // ajouter l'endpoint à la liste
            listEndPoint.add(endpointFactory);
          }

          System.out.printf("Nombre d'Endpoint : %d\n",listEndPoint.size());
          System.out.printf("First Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(0).id, listEndPoint.get(0).countNotDefault());
          System.out.printf("Last Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(listEndPoint.size()-1).id, listEndPoint.get(listEndPoint.size()-1).countNotDefault());


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
        String s= args[0];
        h.load(s);
    }
}
