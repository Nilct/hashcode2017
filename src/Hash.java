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
    int nbVideo = 0;
    int nbEndpoints = 0;
    int nbRequestDescription = 0;
    int nbCaches = 0;
    int defaultSizeCache = 0;
    ArrayList<Video> listVideo = new ArrayList<Video>();
    ArrayList<EndPoint> listEndPoint = new ArrayList<EndPoint>();
    ArrayList<Cache> listCache = new ArrayList<Cache>();

    ArrayList<Cache> originalCache= new ArrayList<Cache>();

    boolean DEBUG= true;


    public Hash() {}


    public ArrayList<CacheValue> createCacheValue() {
        ArrayList<CacheValue> ac= new ArrayList<CacheValue>(nbCaches);
        for (int i = 0; i < nbCaches; i++) {
            CacheValue cv= new CacheValue(i);
            ac.add(cv);
        }
        return ac;
    }


    public void smartsolve() {
        // order
        Collections.sort(listVideo);
        for (int i = 0; i < nbVideo; i++) {
            Video v= listVideo.get(i);
            if (v.totalRequest==0) continue;
            Request r;
            EndPoint ep;
            CacheValue cv;
            // for all ep wanting video, update cache list
            // cache list initially ordered
            ArrayList<CacheValue> listOfCacheValue= createCacheValue();

            for (int j = 0; j < v.listOfRequest.size(); j++) { // treat all request for this video
                r= v.listOfRequest.get(j);
                ep= listEndPoint.get(r.endPoint); // TODO not comparable !!!
                if (ep.id != r.endPoint) System.err.printf("ERROR ENDPOINT - sort FORBIDDEN\n");
                for (int k = 0; k < nbCaches; k++) { // all cache,
                    if (ep.latency[k] == EndPoint.DEFAULT) continue; // cache not available
                    if (listCache.get(k).availSpace < v.size) continue; // video is too big
                    // cache : check available size vs video size, set cache value according to noOfQueries
                    //if (ep. > 0) { // TODO trou de mémoire ??
                    cv = listOfCacheValue.get(k);
                    cv.value+= r.noOf;
                    //}
                }
            }
            // sort all cachevalue
            Collections.sort(listOfCacheValue); // TODO check
            if (listOfCacheValue.get(0).value < listOfCacheValue.get(nbCaches-1).value) System.out.printf("ERROR------------____\n");
            if (listOfCacheValue.get(0).value>0) { // TODO use threshold
                // add video to cache
                Cache c= listCache.get(listOfCacheValue.get(0).id); // TODO check id
                c.addVideo(v);
            }
        }
    }

    public void solve() {
        // order
        Collections.sort(listVideo);
        if (DEBUG) { // Check order
            System.out.printf("video %d : %d, video %d: %d\n", listVideo.get(0).id, listVideo.get(0).totalRequest,
                    listVideo.get(nbVideo-1).id,listVideo.get(nbVideo-1).totalRequest);
        }
        for (int i = 0; i < nbVideo; i++) {
            Video v= listVideo.get(i);
            if (v.totalRequest==0) continue;
            Request r;
            EndPoint ep;
            CacheValue cv;
            // for all ep wanting video, update cache list
            // cache list initially ordered
            ArrayList<CacheValue> listOfCacheValue= createCacheValue();

            for (int j = 0; j < v.listOfRequest.size(); j++) { // treat all request for this video
                r= v.listOfRequest.get(j);
                ep= listEndPoint.get(r.endPoint); // TODO not comparable !!!
                if (ep.id != r.endPoint) System.err.printf("ERROR ENDPOINT - sort FORBIDDEN\n");
                for (int k = 0; k < nbCaches; k++) { // all cache,
                    if (ep.latency[k] == EndPoint.DEFAULT) continue; // cache not available
                    if (listCache.get(k).availSpace < v.size) continue; // video is too big
                    // cache : check available size vs video size, set cache value according to noOfQueries
                    //if (ep. > 0) { // TODO trou de mémoire ??
                        cv = listOfCacheValue.get(k);
                        cv.value+= r.noOf;
                    //}
                }
            }
            // sort all cachevalue
            Collections.sort(listOfCacheValue); // TODO check

            if (listOfCacheValue.get(0).value>0) { // TODO use threshold
                // add video to cache
                Cache c= listCache.get(listOfCacheValue.get(0).id); // TODO check id
                c.addVideo(v);
            }
        }
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

            //System.out.printf("Endpoint crée : %d, latency : %d\n", endpointFactory.id, endpointFactory.latencyDatacenter);
            for (int cache = 0; cache < nbCachesConnected; cache++) {
              String detail = br.readLine();
              String[] partsDetail = detail.split(" ");
              int fromCacheID = Integer.parseInt(partsDetail[0]);
              int latencyValue = Integer.parseInt(partsDetail[1]);
              endpointFactory.changeLatency(fromCacheID, latencyValue);
            }

            //System.out.printf("Endpoint %d : countNotDefault = %d\n", endpointFactory.id, endpointFactory.countNotDefault());
            // ajouter l'endpoint à la liste
            listEndPoint.add(endpointFactory);
          }

          System.out.printf("Nombre d'Endpoint : %d\n",listEndPoint.size());
          System.out.printf("First Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(0).id, listEndPoint.get(0).countNotDefault());
          System.out.printf("Last Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(listEndPoint.size()-1).id, listEndPoint.get(listEndPoint.size()-1).countNotDefault());


          // Read Requests entries
          for(int c = 0; c < nbRequestDescription ; c++){
            // Read endpoints line initializer

            s= br.readLine();
            parts = s.split(" ");

            int idVideo = Integer.parseInt(parts[0]);
            int fromEndPoint = Integer.parseInt(parts[1]);
            int requestsCount = Integer.parseInt(parts[2]);

            Request requestToAdd = new Request(requestsCount, fromEndPoint, idVideo);
            listVideo.get(idVideo).addRequest(requestToAdd);
            // 3 0 1500 ||| 1500 requests for video 3 coming from endpoint 0.

          }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(String name) {
        try {
            FileWriter fw = new FileWriter(name + ".out");
            fw.write(nbCaches+"\n");
            for (int i = 0; i < nbCaches; i++) {
                fw.write(listCache.get(i).output());
            }
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
        h.solve();
        h.save(s);
    }
}
