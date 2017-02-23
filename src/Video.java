import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;

public class Video {

    public float size;
    public ArrayList<Integer> cache;

    Video(float sizeInput){
      size = sizeInput;
      cache = new ArrayList<Integer>();
    }

    public void addCacheServer(int id){
      cache.add(id);
    }

    public void removeCacheServer(int id){
      cache.remove(cache.indexOf(id));
    }
}
