import java.util.ArrayList;

/**
 * Created by Camille on 23/02/2017.
 */
public class Cache {
    int id;
    ArrayList<Integer> listOfVideo= new ArrayList<Integer>();
    int availSpace;
    int availSpaceDefault;

    public Cache(int id, int a) {
        this.id= id; availSpace= a;
        availSpaceDefault = a;
        // System.out.printf("Cache crée : id : %d , availSpace : %d \n", id, availSpace );
    }

    public boolean addVideo(Video v) {
        // TODO check if already in
        for (int i = 0; i < listOfVideo.size(); i++) {
            if (listOfVideo.get(i)==v.id) return false;
        }
        listOfVideo.add(v.id);
        availSpace-= v.size;
        return true;
    }

    public void resetCacheServer(){
      listOfVideo= new ArrayList<Integer>();
      availSpace = availSpaceDefault;
    }

    public String output()  {
        StringBuffer bs= new StringBuffer();
        bs.append(id).append(" ");
        for (int i = 0; i < listOfVideo.size(); i++) {
            bs.append(listOfVideo.get(i)).append(" ");
        }
        bs.append("\n");
        return bs.toString();

    }

}
