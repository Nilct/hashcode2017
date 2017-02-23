import java.util.ArrayList;

/**
 * Created by Camille on 23/02/2017.
 */
public class EndPoint {
    int DEFAULT = -1;
    int id;
    int latencyDatacenter;
    int[] latency;


    EndPoint(int id, int latencyDatacenter, int nbTotalCache){
      this.id = id;
      this.latencyDatacenter = latencyDatacenter;

      latency = new int[nbTotalCache];
      for (int c = 0 ; c < latency.length; c++) {
        latency[c] = DEFAULT;
      }
    }

    public void changeLatency(int fromCache, int latencyValue){
      latency[fromCache] = latencyValue;
    }

    public int countNotDefault(){
      int result = 0;
      for (int c = 0; c < latency.length; c++){
        if (latency[c]==DEFAULT) {
            result++;
        }
      }
      return result;
    }
}
