import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;
import java.util.stream.*;

/**
 * Created by Camille on 23/02/2017.
 */
public class Hash {
    int nbVideo = 0;
    int nbEndpoints = 0;
    int nbRequestDescription = 0;
    public static int nbCaches = 0;
    public static int defaultSizeCache = 0;
    public static ArrayList<Video> listVideo = new ArrayList<Video>();
    public static ArrayList<EndPoint> listEndPoint = new ArrayList<EndPoint>();
    public static ArrayList<Cache> listCache = new ArrayList<Cache>();
    public static ArrayList<Cache> originalCache= new ArrayList<Cache>();

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

                //  System.out.printf("Score for request %d : %d\n", j, r.eval(listVideo, listEndPoint));
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
                v.addCacheServer(c.id);
            }
        }
    }


    // Read input file and create object-based data
    public boolean load(String name) {
        String s1= name + ".in";

        try (BufferedReader br = new BufferedReader(new FileReader(name + ".in"))) {
          // Read first line
          String s = br.readLine();

          String[] parts = s.split(" ");

          nbVideo = Integer.parseInt(parts[0]);
          nbEndpoints = Integer.parseInt(parts[1]);
          nbRequestDescription = Integer.parseInt(parts[2]);
          nbCaches = Integer.parseInt(parts[3]);
          defaultSizeCache = Integer.parseInt(parts[4]);
          // System.out.printf("Encoded : %d %d %d %d %d \n", nbVideo, nbEndpoints, nbRequestDescription, nbCaches, defaultSizeCache );

          for(int c = 0; c < nbCaches; c++){
            listCache.add(new Cache(c, defaultSizeCache));
          }

          // Read second line
          s = br.readLine();
          parts = s.split(" ");

          for(int c = 0; c < parts.length ; c++){
            listVideo.add(new Video(c, Integer.parseInt(parts[c])));
          }

          if (DEBUG) { // Check order
            System.out.printf("%d Videos detected \n", listVideo.size());
            System.out.printf("First video : id : %d, size : %d Mb\n", listVideo.get(0).id, listVideo.get(0).size);
            System.out.printf("Last video : id : %d, size : %d Mb\n", listVideo.get(listVideo.size()-1).id, listVideo.get(listVideo.size()-1).size);
          }

          // Read Endpoints entries
          for(int c = 0; c < nbEndpoints ; c++){
            // Read endpoints line initialize
            s = br.readLine();
            parts = s.split(" ");
            int latencyDatacenter = Integer.parseInt(parts[0]);
            int nbCachesConnected = Integer.parseInt(parts[1]);
            EndPoint endpointFactory =  new EndPoint(c, latencyDatacenter, nbCaches);

            for (int cache = 0; cache < nbCachesConnected; cache++) {
              String detail = br.readLine();
              String[] partsDetail = detail.split(" ");
              int fromCacheID = Integer.parseInt(partsDetail[0]);
              int latencyValue = Integer.parseInt(partsDetail[1]);
              endpointFactory.changeLatency(fromCacheID, latencyValue);
            }

            // save the endpoint to the list
            listEndPoint.add(endpointFactory);
          }

          if (DEBUG) { // Check order
            System.out.printf("Nombre d'Endpoint : %d\n",listEndPoint.size());
            System.out.printf("First Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(0).id, listEndPoint.get(0).countNotDefault());
            System.out.printf("Last Endpoint : id : %d, countNotDefault = %d\n", listEndPoint.get(listEndPoint.size()-1).id, listEndPoint.get(listEndPoint.size()-1).countNotDefault());
          }

          // Read Requests entries
          for(int c = 0; c < nbRequestDescription; c++){
            // Read endpoints line initializer
            s = br.readLine();
            parts = s.split(" ");

            int idVideo = Integer.parseInt(parts[0]);
            int fromEndPoint = Integer.parseInt(parts[1]);
            int requestsCount = Integer.parseInt(parts[2]);

            Request requestToAdd = new Request(requestsCount, fromEndPoint, idVideo);
            listVideo.get(idVideo).addRequest(requestToAdd);
          }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    // Write listCache to the output file
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


    public void solveWithGeneticAlgorithm(){
      originalCache = new ArrayList<>(listCache); // Save the cache of the local solution

      // Parameters
      int MAXGENERATION = 5000;


      // debutIA
      String chromosome = this.generateChromosome();
      // FitnessCalc.setSolution(chromosome);

      Population myPop = new Population(50, true, chromosome);
      int generationCount = 0;
      while(generationCount < MAXGENERATION){
        generationCount++;
        myPop = Algorithm.evolvePopulation(myPop);

        // Display only 1/100 message
        if (generationCount%100 == 0){
          System.out.println("Generation: "+generationCount);
          if ( myPop.getFittest().getFitness() > -1 ){
            System.out.println("Score fittest : " + myPop.getFittest().getFitness());
          }
        }
      }
      System.out.println("Solution initiale : ");
      System.out.println(chromosome);
      System.out.println("Modification génétique :");
      System.out.println(myPop.getFittest());
      System.out.println("Différences : ");
      System.out.println(compareBitwise(chromosome, myPop.getFittest().toString()));

      if (myPop.getFittest().getFitness()>0){
        System.out.println("Utilisation de la solution de l'algorithme genetique");
        // Clean the listCache data
        for (Cache cache : listCache) {
          cache.resetCacheServer();
        }
        String[] bestIndividual = myPop.getFittest().toString().split("");
        for (int i = 0; i < bestIndividual.length; i++){
          if (bestIndividual[i].equals("1")){ // if video is copied to cache server
            listCache.get(i%nbCaches).addVideo(listVideo.get(i/nbCaches)); // update cache list of the video
          }
        }
      } else {
        System.out.println("Solution de l'algorithme genetique rejetée");
        System.out.println("Restauration des caches");
        listCache = new ArrayList<>(originalCache);
      }
    }

    public static void main(String[] args) {
        Hash h = new Hash();

        String s= args[0];

        h.load(s); // Read data

        h.solve(); // Find a local solution
        h.solve(); // Find a local solution
        h.solve(); // Find a local solution

        h.solveWithGeneticAlgorithm(); // Try to optimize the local solution

        h.save(s);
    }

    // Calculate inidividuals fitness
    public static int getFitness(Individual individual) {
        int fitness = 0;

        // Reset cache server linked to a video
        for (Video video : listVideo) {
          video.resetCacheServer();
        }
        for (Cache cache : listCache) {
          cache.resetCacheServer();
        }

        // Check validity (Cache server not overfull)
        int[] sizeCache = new int[nbCaches];
        int[] ecart = new int[nbCaches];
        boolean eliminate = false;
        int id_video;
        int id_cache;
        // Initialise variables
        for (int i = 0 ; i < nbCaches; i++) {
          sizeCache[i] = 0;
          ecart[i] = 0;
        }
        // translate chromosome to object-based data
        for (int i = 0; i < individual.size(); i++){
          if (individual.getGene(i) == 1){ // if video is put in cache server
            // System.out.println("Video n° "+ i/nbCaches + " dans cache n°" + i%nbCaches +" : " + (individual.getGene(i) == 1));
            id_video = i/nbCaches; // because of the structure of the chromosome
            id_cache = i%nbCaches; // because of the structure of the chromosome
            sizeCache[id_cache] += listVideo.get(id_video).size; // add its size on sizeCache
            listCache.get(id_cache).addVideo(listVideo.get(id_video)); // update cache list of the video
          }
        }
        // check capacity of cache server
        for (int i = 0; i < nbCaches; i++) {
          if (sizeCache[i] > defaultSizeCache){
            eliminate=true; // eliminate the cache server if cache server is overfull
          }
          ecart[i] = sizeCache[i] - defaultSizeCache;
        }
        // attribute points to eliminated cache server (to easily find a solution)
        int sum = IntStream.of(ecart).sum();
        sum = -1 * sum;
        if (eliminate){
            return sum;
        }

        // Compute the score of possible solution
        fitness += Request.eval(listEndPoint);

        return fitness;
    }

    // Compare two chromosome string
    public static String compareBitwise(String string1, String string2){
      String[] string1_split = string1.split("");
      String[] string2_split = string2.split("");

      StringBuilder result = new StringBuilder();
      for (int i = 0; i < string1_split.length; i++) {
        result.append((Integer.parseInt(string1_split[i])+Integer.parseInt(string2_split[i]))%2);
      }

      return result.toString();
    }

    // Generate a n x m chromosome where n represents videos and m the cache servers. 1 if video is copied on the corresponding cache server. 0 otherwise.
    public String generateChromosome(){
      String chromosome;
      boolean[] partOfChromosome = new boolean[nbCaches];

      StringBuilder chromosomeBuilder = new StringBuilder();
      for (Video video : listVideo) {
        partOfChromosome = new boolean[nbCaches];

        for (int cache : video.cache) {
          partOfChromosome[cache] = true;
        }

        for (boolean cache : partOfChromosome ) {
          chromosomeBuilder.append(cache?"1":"0");
        }
      }
      System.out.printf("%s\n", chromosomeBuilder.toString());
      return chromosomeBuilder.toString();
    }
}
