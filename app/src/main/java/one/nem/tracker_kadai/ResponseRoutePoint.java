package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseRoutePoint {
    @JsonProperty
    public int length;
    @JsonProperty
    public List<ResponsePoints> route;

    public ResponseRoutePoint(int length, List<ResponsePoints> route) {
        this.length = length;
        this.route = route;
    }

    public ResponseRoutePoint() {
    }
}
