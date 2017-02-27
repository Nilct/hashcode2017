import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;
import java.math.*;
/**
 * Created by Camille on 23/02/2017.
 */
public class Request implements Comparable<Request> {
    int noOf;
    int endPoint;
    int idVideo;
    public static ArrayList<Request> listRequest = new ArrayList<Request>();

    public Request(int no, int ep, int idv) {
        noOf= no; endPoint= ep; idVideo= idv;
        listRequest.add(this);
        // System.out.printf("Request créée : noOf : %d, From endpoint : %d about video n: %d\n", no, endPoint,idVideo );
    }

    public static long eval(ArrayList<EndPoint> listEndPoint){


      int latencyDataCenter;
      int latencyCache;
      long score = 0;
      int minimum = -1;

      for (Request r : listRequest) {
        minimum = -1;
        latencyDataCenter = Hash.listEndPoint.get(r.endPoint).latencyDatacenter;
        for (Cache c : Hash.listCache){
          for (Integer index_video : c.listOfVideo){
            if (index_video.intValue() == r.idVideo){

              int latencyWithEndpoint = listEndPoint.get(r.endPoint).latency[c.id];
              if (latencyWithEndpoint > -1) {
                if (minimum < 0 ){
                  minimum = latencyWithEndpoint;
                } else {
                  minimum = Math.min(minimum, latencyWithEndpoint);
                }
              }
            }
          }
        }
        if (minimum > 0) {

          latencyCache = minimum;
          // System.out.println("Latency Minimum : " + latencyCache);
          // System.out.println("Latency Minimum : " + latencyCache + " liste Cache : " + Hash.listCache.size());

          score += r.noOf * (latencyDataCenter + 100*(latencyDataCenter - latencyCache));
        } else{
          // System.out.println("Probleme detecte avec la requete r : " + r.noOf + " " + r.endPoint + " " + r.idVideo);
          score += r.noOf * latencyDataCenter;
        }
      }

      // System.out.println(score);
      return score;
    }

    @Override
    public int compareTo(Request o) {
        return o.noOf - noOf; // TODO check
    }
}
