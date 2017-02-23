import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;

public class Video implements Comparable<Video> {
    int id;
    int size;
    int totalRequest= 0;
    ArrayList<Request> listOfRequest= new ArrayList<Request>();
    ArrayList<Integer> cache; // destination

    Video(int id, int s){
      this.id= id; size = s;
      cache = new ArrayList<Integer>();
    }

    public void addRequest(Request r) {
        totalRequest+= r.noOf;
        listOfRequest.add(r);
    }

    public void addCacheServer(int id){
      cache.add(id);
    }

    public void removeCacheServer(int id){
      cache.remove(cache.indexOf(id));
    }

    @Override
    public int compareTo(Video o) {
        return o.totalRequest - totalRequest;  // TODO check
    }
}
