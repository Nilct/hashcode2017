/**
 * Created by Camille on 23/02/2017.
 */
public class Request implements Comparable<Request> {
    int noOf;
    int endPoint;
    int idVideo;

    public Request(int no, int ep, int idv) {
        noOf= no; endPoint= ep; idVideo= idv;
    }

    @Override
    public int compareTo(Request o) {
        return o.noOf - noOf; // TODO check
    }
}
