import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;
/**
 * Created by Camille on 23/02/2017.
 */
public class Request implements Comparable<Request> {
    int noOf;
    int endPoint;
    int idVideo;

    public Request(int no, int ep, int idv) {
        noOf= no; endPoint= ep; idVideo= idv;
        // System.out.printf("Request créée : noOf : %d, From endpoint : %d about video n: %d\n", no, endPoint,idVideo );
    }

    public long eval(ArrayList<Video> listVideo, ArrayList<EndPoint> listEndPoint){

      int latencyDataCenter;
      int latencyCache;


      latencyDataCenter = listEndPoint.get(this.endPoint).latencyDatacenter;
      latencyCache = listEndPoint.get(this.endPoint).getMinimumLatencyPositive();

      return noOf * (latencyDataCenter - latencyCache);
    }

    @Override
    public int compareTo(Request o) {
        return o.noOf - noOf; // TODO check
    }
}
