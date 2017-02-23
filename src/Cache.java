import java.util.ArrayList;

/**
 * Created by Camille on 23/02/2017.
 */
public class Cache {
    int id;
    ArrayList<int> listOfVideo;
    int latency;
    int availSpace;

    public Cache(int id, int l, int a) {
        this.id= id; latency= l; availSpace= a;
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

    public String output()  {
        StringBuffer bs= new StringBuffer();
        bs.append(id).append(" ");
        for (int i = 0; i < listOfVideo.size(); i++) {
            bs.append(listOfVideo.get(i)).append(" ");
        }
        return bs.toString();

    }

}
