package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ResponseRoutes {
    @JsonProperty
    public int id;
    @JsonProperty
    public String route_name;
    @JsonProperty
    public int visibility;

    public ResponseRoutes(int id, String route_name, int visibility) {
        this.id = id;
        this.route_name = route_name;
        this.visibility = visibility;
    }

    public ResponseRoutes() {
    }
}
