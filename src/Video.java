import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;

public class Video {
    int id;
    public int size;
    public ArrayList<Integer> cache;

    Video(int id, int s){
      this.id= id; size = s;
      cache = new ArrayList<Integer>();
    }

    public void addCacheServer(int id){
      cache.add(id);
    }

    public void removeCacheServer(int id){
      cache.remove(cache.indexOf(id));
    }
}
