/**
 * Created by Camille on 23/02/2017.
 */
public class CacheValue implements Comparable<CacheValue>{
    int id;
    int value= 0;

    public CacheValue(int i) {
        id=i;
    }

    @Override
    public int compareTo(CacheValue o) {
        return o.value - value;
    }
}
