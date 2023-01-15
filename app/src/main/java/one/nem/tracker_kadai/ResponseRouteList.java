package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseRouteList {
    @JsonProperty
    public int length;
    @JsonProperty
    public List<ResponseRoutes> routes;

    public ResponseRouteList(int length, List<ResponseRoutes> routes) {
        this.length = length;
        this.routes = routes;
    }

    public ResponseRouteList() {
    }

}

